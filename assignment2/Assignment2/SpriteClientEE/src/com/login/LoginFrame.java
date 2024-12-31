package com.login;
import javax.swing.*;

import com.sprite.game.SpriteGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class LoginFrame extends JFrame implements ActionListener{
	
	Container container = getContentPane();
	JLabel userLabel=new JLabel("USERNAME");
	JLabel passwordLabel=new JLabel("PASSWORD");
	JTextField userTextField=new JTextField();
	JPasswordField passwordField=new JPasswordField();
	JButton loginButton=new JButton("LOGIN");
	JButton resetButton=new JButton("RESET");
	JCheckBox showPassword=new JCheckBox("Show Password");
	Authentication auth;
	Credential cre;
	private String usr;
	private String pwd;

 
	LoginFrame(Credential cred){
		//Calling setLayoutManger() method inside the constructor.
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        auth = new Authentication();
        cre = cred;
        

	}
			
	public void setLayoutManager(){
		container.setLayout(null);
	}
	public void setLocationAndSize(){
		//Setting location and Size of each components using setBounds() method.
		userLabel.setBounds(50,100,100,30);
		passwordLabel.setBounds(50,150,100,30);
		userTextField.setBounds(150,100,150,30);
		passwordField.setBounds(150,150,150,30);
		showPassword.setBounds(150,180,150,30);
		loginButton.setBounds(50,220,100,30);
		resetButton.setBounds(200,220,100,30);
	}
	public void addComponentsToContainer(){
		//Adding each components to the Container
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(resetButton);
	}
	public void addActionEvent(){
		//adding Action listener to components
		loginButton.addActionListener(this);
		resetButton.addActionListener(this);
		showPassword.addActionListener(this);
	}
	
	public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e){
		//Coding Part of LOGIN button
		if (e.getSource() == loginButton) {
			usr = userTextField.getText();
			pwd = passwordField.getText();

			if (auth.bacicAuth(usr, pwd)) {
				
				cre.setGoodToGo(true);
				//this.dispose();
				JOptionPane.showMessageDialog(this, "Login Successful");
				this.close();
			} else {
				JOptionPane.showMessageDialog(this, "Invalid Username or Password");
			}
		}
		//Coding Part of RESET button
		if (e.getSource() == resetButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		//Coding Part of showPassword JCheckBox
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			}else{
				passwordField.setEchoChar('*');
			}
		}
	}
	
}
