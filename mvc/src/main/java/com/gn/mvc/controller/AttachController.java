package com.gn.mvc.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gn.mvc.entity.Attach;
import com.gn.mvc.service.AttachService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AttachController {
	
	private final AttachService service;
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Object> fileDownload(@PathVariable("id") Long id) {
		try {
			// 1. id 기준으로 파일 메타 데이터 조회
			Attach fileData = service.selectAttachOne(id);
			// 2. 해당 파일이 없다면 404 에러
			if(fileData == null) {
				return ResponseEntity.notFound().build();
			}
			// 3. 파일 경로로 가서 자바 안으로 파일 읽기
			Path filePath = Paths.get(fileData.getAttachPath());
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
			// 4. 한글 파일명 인코딩(StandardCharsets.UTF_8)
			String oriFileName = fileData.getOriName();
			// chrome,Firefox,Edge에만 인코딩이되는데 다른 브라우저에서는 추가 코드를 적어야한다.
			String encodedName = URLEncoder.encode(oriFileName,StandardCharsets.UTF_8);
			// 5. 브라우저에게 파일 정보 전달
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+encodedName);
			// 200 상태(HttpStatus.OK)
			return new ResponseEntity<Object>(resource,headers,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			// 400 에러 발생
			return ResponseEntity.badRequest().build();
		}
	}
}
