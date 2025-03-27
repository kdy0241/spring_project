package com.gn.mvc.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="chat_msg")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatMsg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="msg_no")
	private Long msgNo;
	
	@OneToOne
	@JoinColumn(name="send_member")
	private Member sendMember;
	
	@ManyToOne
	@JoinColumn(name="room_no")
	private ChatRoom chatRoom;
	
	@Column(name="msg_content")
	private String msgContent;
	
	@CreationTimestamp
	@Column(updatable = false, name="send_date")
	private LocalDateTime sendDate;
	
	
	
	
	
	
	
}
