package com.gn.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gn.todo.dto.AttachDto;
import com.gn.todo.entity.Attach;
import com.gn.todo.service.AttachService;
import com.gn.todo.specification.AttachSpecification;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attach")
public class AttachController {

    private final AttachService attachService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createAttachApi(@RequestParam("files") List<MultipartFile> files) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("res_code", "500");
        resultMap.put("res_msg", "파일 업로드 중 오류가 발생하였습니다.");

        try {
            for (MultipartFile file : files) {
                AttachDto dto = attachService.uploadFile(file);
                if (dto != null) {
                   // attachService.saveAttach(dto);
                }
            }
            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "파일 업로드 성공");
        } catch (Exception e) {
        	e.printStackTrace();
            // 예외 처리 - 로그 기록 등 추가 가능
        }
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> editFile(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("res_code", "500");
        resultMap.put("res_msg", "파일 이름 수정 중 오류가 발생하였습니다.");

        String newName = request.get("newName");
        try {
            attachService.editFileName(id, newName);
            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "파일 이름 수정 성공");
        } catch (Exception e) {
        	e.printStackTrace();
            // 예외 처리 - 로그 기록 등 추가 가능
        }
        return ResponseEntity.ok(resultMap);
    }
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