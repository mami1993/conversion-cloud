package com.cli;

public class Information {
	private int id_client;
	private String email_client;
	private String docname;

	public Information(int id_client, String email_client) {
		this.id_client = id_client;
		this.email_client = email_client;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getEmail_client() {
		return email_client;
	}

	public void setEmail_client(String email_client) {
		this.email_client = email_client;
	}

	public String getDoc() {
		return docname;
	}

	public void setDoc(String doc) {
		this.docname = doc;
	}

}
