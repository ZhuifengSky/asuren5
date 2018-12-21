package cn.service.modules;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.service.modules.exercise.service.IHelloService;

public class AspectTest2 {

	public static void main(String[] args) {
		/*ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/resources/spring-context.xml"); 		
		IHelloService helloService =(IHelloService) ac.getBean("helloService");
		helloService.sayHello("world");*/
		@SuppressWarnings("resource")
		ApplicationContext ac2 = new ClassPathXmlApplicationContext("main/resources/spring-context.xml");
		IHelloService helloService2 =(IHelloService) ac2.getBean("helloService");
		helloService2.sayHello("world");
	}
}
