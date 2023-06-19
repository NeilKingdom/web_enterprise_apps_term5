package com.sprite.game;

//%W%	%G%
/*
 This app bounces a blue ball inside a JPanel.  The ball is created and begins
 moving with a mousePressed event.  When the ball hits the edge of
 the JPanel, it bounces off the edge and continues in the opposite
 direction.  
*/
import java.awt.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.login.Credential;

import cst8218.hoan0105.game.*;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;


public class SpriteGame {
		JFrame frame;
		SpriteControlFrame control;

		private final int WIDTH = 800;
		private final int HEIGHT = 550;

		

    	public SpriteGame(Credential credential){	
    	}
    	
    	public void StartGame() {
    		 
    		frame = new JFrame("Bouncing Sprites");
    		
    		SpriteSessionRemote session = null;
    		System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
    		System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

    			try {
    				System.out.println("about to try for a session...");
    				session = 
    				(SpriteSessionRemote) new InitialContext().lookup("java:global/SpriteJohn/SpriteJohn-ejb/SpriteSession");
    				//(SpriteSessionRemote) new InitialContext().lookup("java:global/SpriteJohn/SpriteJohn-ejb/SpriteSession");
                System.out.println("I got a session");
                System.out.println("This is the height: " + session.getHeight());
    					} catch (NamingException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			} catch (RemoteException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    		if (session != null){
    		   System.out.println("I got game");
    		}else{
    			System.err.println("Could not contact game server");
    			System.exit(1);
    		}
    		try {
    			control = new SpriteControlFrame("Sprites Controller",session);
    			control.setGameWidth(session.getWidth());
    			control.setGameHeight(session.getHeight());
    			frame.setLayout(new BorderLayout());
    			frame.setPreferredSize(new Dimension(session.getWidth()+50,session.getHeight()+50));
    			frame.setMaximumSize(new Dimension(session.getWidth()+50,session.getHeight()+50));
    			frame.setMinimumSize(new Dimension(session.getWidth()+50,session.getHeight()+50));
    		} catch (RemoteException e) {
    			System.err.println("Could not get one or both of HEIGHT, WIDTH for this game");
    			e.printStackTrace();
    		}
    		
    		//controller
    				
    		initialControlFrame(control);

    		control.add(control.getEastPan(),BorderLayout.EAST);
    		control.add(control.getWestPan(),BorderLayout.WEST);
    		control.add(control.getSubmit(),BorderLayout.SOUTH);
    		

            control.validate();
            new Thread(control).start();
    			
    		//sprite
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
    		SpritePanel panel = new SpritePanel(session);
            frame.add(panel,BorderLayout.CENTER);
            frame.validate();
            new Thread(panel).start();
    	}
    	
    	public void initialControlFrame(JFrame con) {
    		con.setVisible(true);
    		con.setSize(WIDTH,HEIGHT);
    		con.setLayout(new BorderLayout());
            con.setVisible(true);
            con.setLocationRelativeTo(null);
    		con.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		//con.setResizable(false);
    	}


    	


}

