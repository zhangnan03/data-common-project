package com.symdata.entity;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5050654395623952948L;

	private String name;
	
	private String card;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	
}
