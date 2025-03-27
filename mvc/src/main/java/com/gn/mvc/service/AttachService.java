package com.gn.mvc.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gn.mvc.dto.AttachDto;
import com.gn.mvc.entity.Attach;
import com.gn.mvc.entity.Board;
import com.gn.mvc.repository.AttachRepository;
import com.gn.mvc.repository.BoardRepository;
import com.gn.mvc.specification.AttachSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachService {
	@Value("${ffupload.location}")
	private String fileDir;
	
	private final BoardRepository boardRepository;
	private final AttachRepository attachRepository;
	
	// 파일 메타 데이터 삭제
	public int deleteMetaData(Long attach_no) {
		int result = 0;
		try {
			Attach target = attachRepository.findById(attach_no).orElse(null);
			if(target != null) {
				// Entity 기준으로 삭제
				attachRepository.delete(target);
			}
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 파일 자체 메모리에서 삭제
	public int deleteFileData(Long attach_no) {
		int result = 0;
		try {
			Attach attach = attachRepository.findById(attach_no).orElse(null);
			if(attach != null) {
				// C:/upload/ -> 파일 경로
				File file = new File(attach.getAttachPath());
				// 파일이 존재하면 삭제
				if(file.exists()) {
					file.delete();
				}
			}
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Attach selectAttachOne(Long id) {
		return attachRepository.findById(id).orElse(null);
	}
	
	public List<Attach> selectAttachList(Long boardNo){
		// 1. boardNo 기준 Board 엔티티 조회
		Board board = boardRepository.findById(boardNo).orElse(null);
		// 2. Specification 생성(attach)
		Specification<Attach> spec = (root, query, criteriaBuilder) -> null;
		spec = spec.and(AttachSpecification.boardEquals(board));
		// 3. findAll 메소드에 전달(spec)
		return attachRepository.findAll(spec);
	}
	
	public AttachDto uploadFile(MultipartFile file) {
		AttachDto dto = new AttachDto();
		try {
			// 1. 정상 파일 여부 확인
			if(file == null || file.isEmpty()) {
				throw new Exception("존재하지 않는 파일입니다.");
			}
			// 2. 파일 최대 용량 체크
			// spring 허용 파일 최대 용량 1MB(1048576byte)
			// byte -> KB -> MB
			long fileSize = file.getSize();
			if(fileSize >= 1048576) {
				throw new Exception("허용 용량을 초과한 파일입니다.");
			}
			// 3. 파일 최초 이름 읽어오기
			String oriName = file.getOriginalFilename();
			dto.setOri_name(oriName);
			
			// 4. 파일 확장자 자르기
			String fileExt = oriName.substring(oriName.lastIndexOf("."),oriName.length());
			
			// 5. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 6. uuid의 8자리마다 반복되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 7. 새로운 파일명 생성
			String newName = uniqueName+fileExt;
			dto.setNew_name(newName);
			// 8. 파일 저장 경로 설정
			// C:/upload/board
			String downDir = fileDir+"board/"+newName;
			dto.setAttach_path(downDir);
			// 9. 파일 껍데기 생성
			File saveFile = new File(downDir);
			// 10. 경로 존재 유무 확인
			if(saveFile.exists() == false) {
				saveFile.mkdirs();
			}
			// 11. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
			
		} catch(Exception e) {
			dto = null;
			e.printStackTrace();
		}
		return dto;
	}
}
