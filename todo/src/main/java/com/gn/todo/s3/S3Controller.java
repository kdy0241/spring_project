package com.gn.todo.s3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {
	
	private final S3Service s3Service;
	private final AttachService attachService;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,String> uploadFile(@RequestParam("files") List<MultipartFile> files){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "파일 업로드중 오류가 발생하였습니다.");
		
		try {
			// List<MultipartFile> 
			for(MultipartFile mf : files) {
				// 1. 업로드
				AttachDto dto = s3Service.uploadFile(mf);
				// 2. 메타 데이터 insert
				if(dto != null) attachService.createAttach(dto);
			}
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "파일일 업로드되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultMap;
		
	}
	// 파일 삭제 코드 작성(2025-03-31)
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String,String> deleteFile(@PathVariable("id") Long id){
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "파일 삭제 중 오류가 발생하였습니다.");
		
		try {
			Attach fileData = attachService.selectAttachOne(id);
			if(fileData == null) {
				resultMap.put("res_msg", "존재하지 않는 파일입니다.");
				return resultMap;
			}
			// S3에서 파일 삭제
			boolean isDeleted = s3Service.deleteFile(fileData.getOriName());
			if(isDeleted) {
				// DB에서 삭제
				attachService.deleteAttach(id);
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "파일이 삭제되었습니다.");
			} else {
				resultMap.put("res_msg", "파일 삭제 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
}
