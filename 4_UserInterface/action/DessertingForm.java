package com.project.prj_Desserting.webapp.action.desserting;

import com.project.prj_Desserting.webapp.action.prj_DessertingForm;

public class DessertingForm extends prj_DessertingForm{

	private String name;
	private String id;
	private int topicNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

}
