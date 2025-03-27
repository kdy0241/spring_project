package com.gn.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedularConfig {
	
	// 1. fixedRate(이전 작업 시작 시점)
//	@Scheduled(fixedRate=5000)
//	public void runTask1() {
//		System.out.println("5초마다 실행!!");
//		System.out.println(System.currentTimeMillis());
//	}
	
	// 2. fixedDelay(이전 작업 종료 시점)
//	@Scheduled(fixedDelay=3000)
//	public void runTask2() {
//		System.out.println(System.currentTimeMillis());
//	}
	
	// 3. initialDelay
//	@Scheduled(initialDelay = 3000, fixedRate = 5000)
//	public void runTask3() {
//		System.out.println("앱 실행 후 3초 후 첫 실행");
//		System.out.println("이후 5초마다 실행");
	@Value("${scheduler.enable}")
	private boolean isSchedulerEnabled;
	
	// 4. cron식
	@Scheduled(cron="0 50 15 * * *")
	public void runTask4() {
		if(!isSchedulerEnabled) {
			return;
		}
		System.out.println("매일 3시 50분에 실행");
	}
	
	
	
	
	
}
