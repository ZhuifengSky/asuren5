package cn.service.modules.exercise.service.impl;

import org.springframework.stereotype.Service;

import cn.service.modules.exercise.service.IHelloService;

@Service("helloService")
public class HelloServiceImpl implements IHelloService{

	@Override
	public String sayHello(String userName) {
		return "hello" + userName;
	}

}
