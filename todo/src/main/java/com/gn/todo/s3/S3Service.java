package com.gn.todo.s3;

import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gn.todo.dto.AttachDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	
	private final AmazonS3 amazonS3;
	
	// application-secret.properties
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	public AttachDto uploadFile(MultipartFile file) {
		// 메타 데이터 insert를 할 수 있다
		AttachDto dto = new AttachDto();
		try(InputStream is = file.getInputStream()) {
			if(file == null || file.isEmpty()) {
				throw new Exception("존재하지 않는 파일입니다.");
			}
			long fileSize = file.getSize();
			if(fileSize >= 1048576) {
				throw new Exception("허용 용량을 초과한 파일입니다.");
			}
			String oriName = file.getOriginalFilename();
			dto.setOri_name(oriName);
			
			String fileExt = oriName.substring(oriName.lastIndexOf("."),oriName.length());
			UUID uuid = UUID.randomUUID();
			String uniqueName = uuid.toString().replace("-", "");
			String newName = uniqueName+fileExt;
			dto.setNew_name(newName);
			// 객체
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(file.getContentType());
			meta.setContentLength(file.getSize());
			
			// 새롭게 객체를 넣어준다
			amazonS3.putObject(new PutObjectRequest(bucket ,newName, is, meta)
								.withCannedAcl(CannedAccessControlList.PublicRead));
			
			String region = amazonS3.getRegionName();
			String fileUrl = "https://"+bucket+".s3."+region+".amazonaws.com/"+newName;
			dto.setAttach_path(fileUrl);
			
		}catch(Exception e) {
			dto = null;
			e.printStackTrace();
		}
		return dto;
	}
}
