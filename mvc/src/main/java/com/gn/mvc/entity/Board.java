package com.gn.mvc.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="board_no")
	private Long boardNo;
	
	@Column(name="board_title")
	private String boardTitle;
	
	@Column(name="board_content")
	private String boardContent;
	
	@CreationTimestamp
	@Column(updatable=false,name="reg_date")
	private LocalDateTime regDate;
	
	@UpdateTimestamp
	@Column(insertable=false,name="mod_date")
	private LocalDateTime modDate;
	
	@ManyToOne
	@JoinColumn(name="board_writer")
	private Member member;
	
	@OneToMany(mappedBy="board")
	private List<Attach> attachs;
	
	
	
	
	
	
	
	
}
