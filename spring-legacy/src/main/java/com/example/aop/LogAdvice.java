package com.example.aop;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/*

 AOP(Aspect Oriented Programming: 관점 지향 프로그래밍)
 : 주변부 로직(공통 기능)을 분리해서 개발하는 방식
  스프링은 AOP기능을 타겟 메소드 호출 기준으로 주요 로직에 주변부 로직을 결합시켜줌
 
 - Advice : 주변부 로직(공통기능)을 가진 클래스
 - Target : 주요 로직을 가지고 있는, 주변부 로직이 적용될 대상 오브젝트
 - Join Point : 주변부 로직이 적용될 수 있는 메소드 후보들
 - Point Cut : 조인포인트(후보)들 중 실제 결합시킬 메소드
 
*/

@Aspect // 주변부 로직을 가진 어드바이스 클래스 표현
@Component //스프링 빈으로 등록
public class LogAdvice{

	//AOP 애노테이션 값 포인트컷 표현식으로 타깃 오브젝트 포인트컷을 표현
	@Before("execution( * com.example.service.MemberService.*(..))")
	public void logBefore() {
		System.out.println("==========================logeBefore()============================");
	}
	
	//@Around 사용 시 매개변수는 ProceedingJoinPoint 타입을 선언하고
	//리턴 타입은 Object
	@Around("execution( * com.example.service.MemberService.*(..))")
	public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("==========================logeBefore()============================");
		
		Object[] args = joinPoint.getArgs(); //타깃 메소드 매개변수 정보
		List<Object> argsList = Arrays.asList(args);

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getName(); //타깃 메소드 이름
		
		System.out.println("메소드 명 : " + methodName +", 매개변수 : " + argsList);
		
		long beginTime = System.currentTimeMillis();
		
		Object result = joinPoint.proceed(); //실제 타겟 메소드를 호출
		System.out.println("result : " + result);
		
		long endTime = System.currentTimeMillis();
		
		long diff = endTime - beginTime;
		System.out.println(methodName + "메소드 실행 시간 : " + diff + "ms");
		
		return result;
	}
}
