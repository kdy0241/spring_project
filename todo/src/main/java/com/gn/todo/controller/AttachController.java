package com.gn.todo.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gn.todo.dto.AttachDto;
import com.gn.todo.entity.Attach;
import com.gn.todo.service.AttachService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {

    private final AttachService attachService;
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Object> fileDownload(@PathVariable("id") Long id){
    	System.out.println("ci/cd test1");
    	try {
    		Attach fileData = attachService.selectAttachOne(id);
    		// 파일이 없다면 404 에러
    		if(fileData == null) {
    			return ResponseEntity.notFound().build();
    		}
    		Path filePath = Paths.get(fileData.getAttachPath());
    		Resource resource = new InputStreamResource(Files.newInputStream(filePath));
    		
    		String oriFileName = fileData.getOriName();
    		String encodedName = URLEncoder.encode(oriFileName,StandardCharsets.UTF_8);
    		// 브라우저에게 파일 정보 전달
    		HttpHeaders headers = new HttpHeaders();
    		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+encodedName);
    		
    		return new ResponseEntity<Object>(resource,headers,HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
    		// 400 에러 발생
    		return ResponseEntity.badRequest().build();
    	}
    } 

    @PostMapping("/create")
    @ResponseBody
    public Map<String, String> createAttachApi(@RequestParam("files") List<MultipartFile> files) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("res_code", "500");
        resultMap.put("res_msg", "파일 업로드 중 오류가 발생하였습니다.");

        try {
            for (MultipartFile file : files) {
                AttachDto dto = attachService.uploadFile(file);
                if (dto != null) {
                    attachService.createAttach(dto);
                }
            }
            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "파일 업로드 성공");
        } catch (Exception e) {
        	e.printStackTrace();
            // 예외 처리 - 로그 기록 등 추가 가능
        }
        return resultMap;
    }

//    @PostMapping("/edit/{id}")
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> editFile(@PathVariable Long id, @RequestBody Map<String, String> request) {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("res_code", "500");
//        resultMap.put("res_msg", "파일 이름 수정 중 오류가 발생하였습니다.");
//
//        String newName = request.get("newName");
//        try {
//            attachService.editFileName(id, newName);
//            resultMap.put("res_code", "200");
//            resultMap.put("res_msg", "파일 이름 수정 성공");
//        } catch (Exception e) {
//        	e.printStackTrace();
//            // 예외 처리 - 로그 기록 등 추가 가능
//        }
//        return ResponseEntity.ok(resultMap);
//    }
//    @GetMapping("/attach/list")
//    public String listFiles(@RequestParam(required = false) String searchQuery, Model model) {
//        Specification<Attach> spec = Specification.where(null);
//        if (searchQuery != null && !searchQuery.isEmpty()) {
//            spec = spec.and(AttachSpecification.fileNameContains(searchQuery));
//        }
//        List<Attach> fileList = attachRepository.findAll(spec);
//        model.addAttribute("fileList", fileList);
//        return "attach/list"; // 파일 목록을 보여주는 뷰 이름
//    }
//    

//    @DeleteMapping("/delete/{id}")
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> deleteAttach(@PathVariable Long id) {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("res_code", "500");
//        resultMap.put("res_msg", "파일 삭제 중 오류가 발생하였습니다.");
//
//        try {
//            attachService.deleteAttach(id);
//            resultMap.put("res_code", "200");
//            resultMap.put("res_msg", "파일 삭제 성공");
//        } catch (Exception e) {
//        	e.printStackTrace();
//            // 예외 처리 - 로그 기록 등 추가 가능
//        }
//        return ResponseEntity.ok(resultMap);
//    }
    
}