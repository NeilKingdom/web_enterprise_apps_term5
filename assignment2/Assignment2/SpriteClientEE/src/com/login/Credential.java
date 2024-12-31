package com.login;

public class Credential {
	private String usr;
	private String credential;
	private volatile boolean goodToGo = false;
	
	public boolean isGoodToGo() {
		return goodToGo;
	}
	public synchronized void setGoodToGo(boolean goodToGo) {
		this.goodToGo = goodToGo;
	}
	public synchronized String getUsr() {
		return usr;
	}
	public synchronized void setUsr(String usrpwd) {
		this.usr = new String(usr);
	}
	public String getCredential() {
		return credential;
	}
	public synchronized void setCredential(String credential) {
		this.credential = new String(credential);
	}
	
}
