package com.gn.todo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gn.todo.dto.AttachDto;
import com.gn.todo.entity.Attach;
import com.gn.todo.repository.AttachRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachService {
    @Value("${ffupload.location}")
    private String fileDir;

    private final AttachRepository attachRepository;

    // 파일 메타 데이터 삭제
    public int deleteMetaData(Long attach_no) {
        int result = 0;
        try {
            Attach target = attachRepository.findById(attach_no).orElse(null);
            if (target != null) {
                // Entity 기준으로 삭제
                attachRepository.delete(target);
            }
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 파일 자체 메모리에서 삭제
    public int deleteFileData(Long attach_no) {
        int result = 0;
        try {
            Attach attach = attachRepository.findById(attach_no).orElse(null);
            if (attach != null) {
                // C:/upload/ -> 파일 경로
                File file = new File(attach.getAttachPath());
                // 파일이 존재하면 삭제
                if (file.exists()) {
                    file.delete();
                }
            }
            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 단일 첨부파일 조회
    public Attach selectAttachOne(Long id) {
        return attachRepository.findById(id).orElse(null);
    }

    // 파일 업로드 메소드
    public AttachDto uploadFile(MultipartFile file) {
		AttachDto dto = new AttachDto();
		try {
			// 1. 정상 파일 여부 확인
			if(file == null || file.isEmpty()) {
				throw new Exception("존재하지 않는 파일입니다.");
			}
			// 2. 파일 최대 용량 체크
			// Spring 허용 파일 최대 용량 1MB(1048576byte)
			// byte -> KB -> MB
			long fileSize = file.getSize();
			if(fileSize >= 1048576) {
				throw new Exception("허용 용량을 초과한 파일입니다.");
			}
			
			// 3. 파일 최초 이름 읽어오기
			String oriName = file.getOriginalFilename();
			dto.setOri_name(oriName);
			
			// 4. 파일 확장자 자르기
			String fileExt 
				= oriName.substring(oriName.lastIndexOf("."),oriName.length());
			
			// 5. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 6. uuid의 8자리마다 반복되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 7. 새로운 파일명 생성
			String newName = uniqueName+fileExt;
			dto.setNew_name(newName);
			// 8. 파일 저장 경로 설정
			String downDir = fileDir+"/"+newName;
			dto.setAttach_path(downDir);
			
			// 9. 파일 껍데기 생성
			File saveFile = new File(downDir);
			// 10. 경로 존재 유무 확인
			if(saveFile.exists() == false) {
				saveFile.mkdirs();
			}
			// 11. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
			
		}catch(Exception e) {
			dto = null;
			e.printStackTrace();
		}
		return dto;
    }
    // 2. 파일 메타 데이터 insert
    public Attach createAttach(AttachDto dto) {
    	Attach param = dto.toEntity();
    	return attachRepository.save(param);
    }
    
    // 모든 첨부파일 조회
    public List<Attach> selectAttachList() {
    	return attachRepository.findAll();
    	
    }
    
    // 파일 삭제 기능 추가(2025-03-31)
    public void deleteAttach(Long id) {
    	attachRepository.deleteById(id);
    }
    // 파일 이름 수정
//    public void editFileName(Long id, String newName) {
//        Attach attach = attachRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));
//        attach.setNewName(newName); // 새로운 이름으로 설정
//        attachRepository.save(attach); // 변경된 엔티티 저장
//    }
}
// 특정 게시글에 대한 첨부파일 목록 조회
//    public List<Attach> selectAttachList(Long boardNo) {
//        // 1. boardNo 기준 Board 엔티티 조회
//        Board board = boardRepository.findById(boardNo).orElse(null);
//        // 2. Specification 생성(attach)
//        Specification<Attach> spec = (root, query, criteriaBuilder) -> null;
//        spec = spec.and(AttachSpecification.boardEquals(board));
//        // 3. findAll 메소드에 전달(spec)
//        return attachRepository.findAll(spec);
//    }