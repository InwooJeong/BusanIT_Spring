package com.example.sample;

import org.springframework.stereotype.Component;

@Component
public class HyundaiEngine implements Engine {

	@Override
	public void start() {
		System.out.println("현대 엔진이 동작 : 부릉부릉~~");
	}

}
