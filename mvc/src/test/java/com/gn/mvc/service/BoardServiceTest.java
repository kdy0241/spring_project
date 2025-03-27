package com.gn.mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.gn.mvc.entity.Board;
import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoardServiceTest {
	
	@Autowired
	private BoardService service;

	@Test
	void selectBoardOne_success() {
		// 1. 예상 데이터
		Long id = 37L;
		Board expected = Board.builder().boardTitle("ㅇㅇㅇㅇ").build();
		// 2. 실제 데이터
		Board real = service.selectBoardOne(id);
		// 3. 비교 및 검증(기대값,실제값)
		assertEquals(expected.getBoardTitle(),real.getBoardTitle());
	}
	// 실패 테스트
	// 존재하지 않는 PK기준으로 조회 요청
	@Test
	void selectBoardOne_fail() {
		// 1. 예상 데이터
		Long id = 1000L;
		Board expected = null;
		// 2. 실제 데이터
		Board real = service.selectBoardOne(id);
		// 3. 비교 및 검증(기대값,실제값) 
		// 예상 데이터,실제 데이터가 존재하지 않으면 
		assertEquals(expected,real);
	}

}
