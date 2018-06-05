package com.seunghoo.na.advice;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
//객체를 자동으로 생성하기 위한 어노테이션 
//advice 클래스로 만들기 위한 어노테이션 
@Aspect
@Component
public class LogginAdvice {
	
	// 접근 지정자, *은 모든 리턴타입 
	// kr.co.pk.. 은 kr.co.pk 패키지 안에 있는 모든 
	// * Dao는 Dao로 끝나는 클래스 .*은 메소드 이름이 무엇이든지 
	// (..)은 매개변수 개수에 상관없이 
	// advice로 수행될 메소드 
	@Around("execution(public * com.seunghoo.na..*Dao.*(..))")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable{
		
		//pointcut으로 설정된 메소드가 호출되기 전에 수행할 내용 
		
		//메소드 이름 가져오기 
		String methodName = joinPoint.getSignature().toLongString();
		//현재 시간 만들기 
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTimeInMillis());
		
		//파일에 문자열 기록하기 -파일이 존재하면 이어쓰기 
		FileOutputStream fos = new FileOutputStream("d:\\log.txt",true);
		
		
		//문자열을 기록할 수 있는 클래스의 객체 만들기 
		PrintWriter pw = new PrintWriter(fos);
		
		pw.println(methodName+""+date.toString()+"\n");
		
		//파일에 기록 
		pw.flush();
		fos.close();
		
		
		Object obj = joinPoint.proceed();
		
		//pointcut으로 설정된 메소드가 호출된 후에 수행할 내용 
		
		return obj;
	}
	
}