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

    public AttachDto uploadFile(MultipartFile file) throws Exception {
        AttachDto dto = new AttachDto();
        try {
            if (file == null || file.isEmpty()) {
                throw new Exception("존재하지 않는 파일입니다.");
            }
            long fileSize = file.getSize();
            if (fileSize >= 1048576) {
                throw new Exception("허용 용량을 초과한 파일입니다.");
            }

            String oriName = file.getOriginalFilename();
            dto.setOri_name(oriName);

            String fileExt = oriName.substring(oriName.lastIndexOf("."));
            UUID uuid = UUID.randomUUID();
            String newName = uuid.toString().replaceAll("-", "") + fileExt;
            dto.setNew_name(newName);

            String downDir = fileDir + newName;
            dto.setAttach_path(downDir);

            File saveFile = new File(downDir);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);
        } catch (Exception e) {
            dto = null;
            e.printStackTrace();
        }
        return dto;
    }
    

    public void saveAttach(AttachDto dto) {
        Attach attach = dto.toEntity();
        attachRepository.save(attach);
    }

    public List<AttachDto> getAllFiles() {
        List<Attach> attaches = attachRepository.findAll();
        List<AttachDto> attachDtos = new ArrayList<>();
        for (Attach attach : attaches) {
            attachDtos.add(new AttachDto(attach));
        }
        return attachDtos;
    }

    public void deleteAttach(Long id) {
        attachRepository.deleteById(id);
    }

    public void editFileName(Long id, String newName) {
        Attach attach = attachRepository.findById(id).orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));
        attach.setNewName(newName);
        attachRepository.save(attach);
    }
}