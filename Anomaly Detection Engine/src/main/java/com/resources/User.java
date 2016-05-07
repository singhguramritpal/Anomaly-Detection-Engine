package com.resources;

public class User {
	
	private String name;
	private String email;
	private String phoneNumber;
	private String organizationName;
	private String password;
	private String question;
	private String answer;
	
	public User(){}
	
	public User(String name, String email, String phoneNumber, String organizationName, String password,
			String question, String answer) {
	
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.organizationName = organizationName;
		this.password = password;
		this.question = question;
		this.answer = answer;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String paswword) {
		this.password = paswword;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
}
