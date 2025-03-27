package com.gn.mvc.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatRoom {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="room_no")
	private Long roomNo;
	
	@OneToOne
	@JoinColumn(name="from_member")
	private Member fromMember;
	
	@OneToOne
	@JoinColumn(name="to_member")
	private Member toMember;
	
	@Column(name="last_msg")
	private String lastMsg;
	
	@Column(name="last_date")
	private LocalDateTime lastDate;
	
	@OneToMany(mappedBy="chatRoom")
	private List<ChatMsg> chatMsgs;
}
