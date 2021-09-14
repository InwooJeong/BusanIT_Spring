package com.example.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //@Component 계열 애노테이션에 해당
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SampleTests {
	
	private Car car;
	
	@Autowired
	public void setCar(Car car) {
		this.car = car;
	}
	
	@Test
	public void testExist() {
		assertNotNull(car);
		
		car.drive();
		
	}
	
}
