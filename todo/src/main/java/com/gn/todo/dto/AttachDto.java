package com.gn.todo.dto;

import com.gn.todo.entity.Attach;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AttachDto {
	
	private Long attach_no;
	private String ori_name;
	private String new_name;
	private String attach_path;

	public Attach toEntity() {
		return Attach.builder()
				.attachNo(attach_no)
				.oriName(ori_name)
				.newName(new_name)
				.attachPath(attach_path)
				.build();
	}
	// Attach 객체를 매개변수로 받는 생성자 추가
	public AttachDto(Attach attach) {
		this.attach_no = attach.getAttachNo();
		this.ori_name = attach.getOriName();
		this.new_name = attach.getNewName();
		this.attach_path = attach.getAttachPath();
	}
}
