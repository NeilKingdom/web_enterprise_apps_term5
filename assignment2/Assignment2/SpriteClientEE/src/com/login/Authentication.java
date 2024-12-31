package com.login;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

import java.util.Base64;

public class Authentication {
	private String host = "@localhost:8080/SpriteJohn-war/webresources/javaee8";
	HttpURLConnection urlc;
	URL u;
	
	public boolean bacicAuth(String usr, String pwd) {
		boolean success = false;
		
		try {
			u = new URL("http://"+usr+":"+pwd+host);
			urlc = (HttpURLConnection) u.openConnection();
			urlc.setRequestMethod("GET");
			urlc.setDoInput(true);
			urlc.setRequestProperty("Authorization",getBasicAuthEncode(usr,pwd));		
			urlc.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
			urlc.setRequestProperty("Connection", "Keep-Alive");

			try(InputStream os = urlc.getInputStream()) {
				int choice;
				while( (choice=os.read())!= -1) {
					//System.out.print((char)choice);
				}
			}
			success = true;
		}catch(IOException e) {
			//e.printStackTrace();
			System.err.println("401 - Not Authorized");
		}catch(Exception e) {
			//e.printStackTrace();
			System.err.println("System Error");
		}	
		return success;
	}
	
	public String getBasicAuthEncode(String usr, String pwd) {
		String auth;
		String encoded="";
		try {
			auth = getCredential(usr,pwd);
			encoded =Base64.getUrlEncoder().encodeToString(auth.getBytes("utf-8"));
		}catch(NullPointerException e) {
			System.err.println("Encoding failed Null value");
		}catch(Exception e) {
			System.err.println("Encoding Failed");
		}	
		return "Basic " + encoded ;
	            
	}
	public String getCredential(String usr, String pwd) {
		return usr+":"+pwd;
	}
}

