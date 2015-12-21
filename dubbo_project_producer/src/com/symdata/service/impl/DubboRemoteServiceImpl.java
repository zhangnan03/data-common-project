package com.symdata.service.impl;

import org.springframework.stereotype.Service;

import com.symdata.entity.Person;
import com.symdata.service.DubboRemoteService;
@Service
public class DubboRemoteServiceImpl implements DubboRemoteService {
	@Override
	public String publishMessage() {
		return "hello world !";
	}

	@Override
	public Person publishPerson(String id) {
		Person person = new Person();
		person.setId(id);
		person.setName("name"+id);
		return person;
	}
}
