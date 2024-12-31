package com.sprite.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cst8218.hoan0105.entity.Sprite;
import cst8218.hoan0105.game.SpriteSessionRemote;

public class SpriteControlFrame extends JFrame implements Runnable, ActionListener,ChangeListener{
	//entities.sprite=["1321"]
	int newCount;
	int listNum=0;
	List<Sprite> sprites;
	Sprite sprite;
	SpriteSessionRemote session;
	
	StringBuilder mutator;
	
	
	JButton submit = new JButton("Submit");
	
	int gameWidth=500;
	int gameHeight=500;
	final int PEASTWIDTH = 150;
	final int PEASTHEIGHT = 500;
	final int COMPONENTWID = 150;
	final int COMPONENTHEI = 75;
	final int INPUTWID = 650;
	final int INPUTHEI = 70;
	JPanel[] panel= new JPanel[6];
	JPanel[] inputPanel = new JPanel[6];
	JLabel[] label = new JLabel[6];
	JPanel westPan;
	JPanel eastPan;
	String[] labelName = new String[] {
			"Sprite ID number :","Sprite Color :","Location X :","Location Y :","Horizontal Velocity :","Vertical Velocity :"
	};
	
	JSlider vSpeed = new JSlider(-5,5,0);
	JSlider hSpeed = new JSlider(-5,5,0);
	
	JSlider xPos;
	JSlider yPos;
	
	String spriteid;
	int spriteIndex;
	int x=gameHeight/2;
	int y=gameHeight/2;
	int dx=0;
	int dy=0;
	Color c = Color.RED;
		
	JComboBox sId;
	
	String[] colors = {"RED","GREEN","BLUE","MAGENTA","ORANGE","YELLOW","BLACK","GREY","PINK","CYAN"};
	Color[]  colorCode = {Color.RED,Color.GREEN,Color.BLUE,Color.MAGENTA,Color.ORANGE,Color.YELLOW,Color.BLACK,Color.GRAY,Color.PINK,Color.CYAN};
	JComboBox colorText = new JComboBox(colors);

	public SpriteControlFrame(String c,SpriteSessionRemote session) {
		super(c);
		this.session = session;
		initializeWestPanel();
		initializeInputComp();
		

	}
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		int tmp;
		int tmp2;
		if(e.getSource() == sId) {
			spriteid = sId.getSelectedItem().toString();
			spriteIndex = sId.getSelectedIndex();
		}
		if(e.getSource()==colorText) {
			c = colorCode[colorText.getSelectedIndex()];
		}
		if(e.getSource()== submit) {
			//submit edit sprite
			//System.out.printf("\nid:%s \ncolor:%s \nx:%d \ny:%d \ndx:%d \ndy:%d",spriteid,c.toString(),x,y,dx,dy);
			tmp=sprites.size();
			tmp2=sId.getComponentCount();
			if(tmp!=tmp2) {
				sId.removeAll();
				
				for(int i=0;i<sprites.size();i++) {
					mutator = new StringBuilder();
					String[] tmpnum = sprites.get(i).toString().split("=|\\s");
					Long num = Long.parseLong(tmpnum[2]);
					mutator.append("ID: "+num+ " ");
					mutator.append("SpeedX: "+sprites.get(i).getDx()+" ");
					mutator.append("SpeedY: "+sprites.get(i).getDy()+" ");
					String[] str = sprites.get(i).getColor().toString().split("\\[|\\]");
					mutator.append("Color: "+str[1]+"  ");
					
					this.addItemComboBox(mutator.toString());
				}
			}
			sprite = new Sprite();
			sprite.setColor(c);
			sprite.setDx(dx);
			sprite.setDy(dy);
			sprite.setX(x);
			sprite.setY(y);
			
			String[] ss = spriteid.split("=|\\s");	
			Long num = Long.parseLong(ss[1]);
			sprite.setId(num);
			try {
				session.setSprite(num, sprite);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	@Override
	public synchronized void stateChanged(ChangeEvent e) {
		if (e.getSource()== xPos) {
			x = xPos.getValue();
		}
		if (e.getSource()== yPos) {
			y = yPos.getValue();
		}
		if (e.getSource()== hSpeed) {
			dx = hSpeed.getValue();
		}
		if (e.getSource()== vSpeed) {
			dy = vSpeed.getValue();
		}
		
	}
	public synchronized void addItemComboBox(String item) {
		sId.addItem(item);
	}
	public synchronized void removeAllComboBox() {
		sId.removeAll();
	}
	
	@Override
	public void run() {
		try{ 
			while (true){
				try{
				
				sprites = session.getSpriteList();
				if (listNum == 0) {
					
					for(int i=0;i<sprites.size();i++) {
						mutator = new StringBuilder();
						String[] tmpnum = sprites.get(i).toString().split("=|\\s");
						Long num = Long.parseLong(tmpnum[2]);
						mutator.append("ID: "+num+ " ");
						mutator.append("SpeedX: "+ Integer.toString(sprites.get(i).getDx()) +"  ");
						mutator.append("SpeedY: "+ Integer.toString(sprites.get(i).getDy())+"  ");
						String[] str = sprites.get(i).getColor().toString().split("\\[|\\]");
						mutator.append("Color: "+str[1]+"  ");
						
						this.addItemComboBox(mutator.toString());
					}
					listNum = sId.getComponentCount();
				}

				}catch(javax.ejb.NoSuchEJBException e){
					System.out.println("Lost contact with server, exiting...");
					e.printStackTrace();
					System.exit(1);
				}
				try {
					Thread.sleep(200);
				}
				catch ( InterruptedException exception ) {
					exception.printStackTrace();
				}
			}
		}catch(RemoteException e){
			System.out.println("game over? exiting...");
		}
		
	}
	
	public void initializeWestPanel() {
		westPan = new JPanel();
		westPan.setLayout(new FlowLayout());
		westPan.setPreferredSize(new Dimension(PEASTWIDTH,PEASTHEIGHT));
		westPan.setVisible(true);
		
		initializeComp();
	}
	public void initializeComp() {
		for(int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			label[i] = new JLabel(labelName[i],JLabel.CENTER);
			panel[i].add(label[i],BorderLayout.CENTER);
			panel[i].setPreferredSize(new Dimension(COMPONENTWID,COMPONENTHEI));
			westPan.add(panel[i]);
			
		}
	}
	public void initializeInputComp() {
		eastPan = new JPanel();
		eastPan.setLayout(new FlowLayout());
		eastPan.setPreferredSize(new Dimension(INPUTWID,PEASTHEIGHT));
		eastPan.setVisible(true);
		//id
		sId = new JComboBox();
		sId.setPreferredSize(new Dimension(INPUTWID-100,30));
		inputPanel[0] = new JPanel();
		inputPanel[0].add(sId,BorderLayout.CENTER);
		inputPanel[0].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[0]);
		
		//color
		inputPanel[1] = new JPanel();
		colorText.setPreferredSize(new Dimension(INPUTWID-100,30));
		inputPanel[1].add(colorText,BorderLayout.CENTER);
		inputPanel[1].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[1]);
				
		//x
		xPos = new JSlider(0,gameWidth,(int)(gameWidth/2));
		xPos.setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		xPos.setPaintTicks(true);
		xPos.setMinorTickSpacing(8);
		xPos.setPaintTrack(true);
		xPos.setMajorTickSpacing(25);
		xPos.setPaintLabels(true);
		
		inputPanel[2] = new JPanel();
		inputPanel[2].add(xPos,BorderLayout.CENTER);
		inputPanel[2].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[2]);
		
		
		//y
		yPos = new JSlider(0,gameHeight,(int)(gameHeight/2));
		yPos.setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		yPos.setPaintTicks(true);
		yPos.setMinorTickSpacing(8);
		yPos.setPaintTrack(true);
		yPos.setMajorTickSpacing(25);
		yPos.setPaintLabels(true);
		
		inputPanel[3] = new JPanel();
		inputPanel[3].add(yPos,BorderLayout.CENTER);
		inputPanel[3].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[3]);
		
		//x speed
		hSpeed.setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		hSpeed.setPaintTrack(true);
		hSpeed.setMajorTickSpacing(1);
		hSpeed.setPaintLabels(true);
		
		inputPanel[4] = new JPanel();
		inputPanel[4].add(hSpeed,BorderLayout.CENTER);
		inputPanel[4].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[4]);

		//y speed
		vSpeed.setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		vSpeed.setPaintTrack(true);
		vSpeed.setMajorTickSpacing(1);
		vSpeed.setPaintLabels(true);
		
		inputPanel[5] = new JPanel();
		inputPanel[5].add(vSpeed,BorderLayout.CENTER);
		inputPanel[5].setPreferredSize(new Dimension(INPUTWID,INPUTHEI));
		eastPan.add(inputPanel[5]);
		x= gameWidth/2;
		y= gameHeight/2;
		submit.setPreferredSize(new Dimension(WIDTH,50));
		submit.addActionListener(this);
		sId.addActionListener(this);
		xPos.addChangeListener(this);
		yPos.addChangeListener(this);
		vSpeed.addChangeListener(this);
		hSpeed.addChangeListener(this);
		colorText.addActionListener(this);

	}
	
	public int getGameWidth() {
		return gameWidth;
	}
	public void setGameWidth(int gameWidth) {
		this.gameWidth = gameWidth;
	}
	public int getGameHeight() {
		return gameHeight;
	}
	public void setGameHeight(int gameHeight) {
		this.gameHeight = gameHeight;
	}
	public JPanel getEastPan() {
		return eastPan;
	}
	public JPanel getWestPan() {
		return westPan;
	}
	public JButton getSubmit() {
		return submit;
	}

	
	
	
}
