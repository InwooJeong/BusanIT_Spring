package com.example.myapp;

// 객체간의 관계 두가지
// 1) 상속관계 (A is a B) A는 B다. A는 B를 상속받았다.
// 2) 포함관계 (A has a B) A는 B를 가진다.


class HyundaiEngine implements Engine {
	@Override
	public void start() {
		System.out.println("현대 엔진이 동작 : 부릉부릉~~");
	}
}
class ToyotaEngine implements Engine {
	@Override
	public void start() {
		System.out.println("도요타 엔진이 동작 : 부릉부릉~~");
	}
}
class FordEngine implements Engine {
	@Override
	public void start() {
		System.out.println("포드 엔진이 동작 : 부릉부릉~~");
	}
}


interface Engine {
	void start();
}

class Car {
	private Engine engine; // 업캐스팅 (다형성)
	
	public Car(Engine engine) {
		this.engine = engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void drive() {
		engine.start();
	}
}


public class Ex {
	public static void main(String[] args) {
		
		Engine engine = new ToyotaEngine();
		
		Car car = new Car(engine); // 의존관계 설정 주입(Dependency Injection)
		car.drive();
		
		engine = new FordEngine();
		car.setEngine(engine);
		car.drive();
		
		car.setEngine(new HyundaiEngine());
		car.drive();
		
	}
}
