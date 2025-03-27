package com.gn.mvc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="attach")
@Builder
public class Attach {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attachNo;
	
	@Column(name="ori_name")
	private String oriName;
	
	@Column(name="new_name")
	private String newName;
	
	@Column(name="attach_path")
	private String attachPath;
	
	@ManyToOne
	@JoinColumn(name="board_no")
	private Board board;
	
	
}
