package main;

import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RandomControls implements ActionListener {

	File newFile;
	File optionsFile;
	String filePath;
	BufferedReader br;
	BufferedWriter wr;
	
    JTextField field;
    JButton enter;
	
	//List Of Controls Minecraft Accepts Not Counting A NumPad As I Do Not Have One//
	String[] controls = {"mouse.left, mouse.right", "mouse.middle", "keyboard.escape", "keyboard.grave", "keyboard.1", "keyboard.2", "keyboard.3",
			"keyboard.4", "keyboard.5", "keyboard.6", "keyboard.7", "keyboard.8", "keyboard.9", "keyboard.0", "keyboard.minus", "keyboard.equals",
			"keyboard.backspace", "keyboard.tab", "keyboard.q", "keyboard.w", "keyboard.e", "keyboard.r", "keyboard.t", "keyboard.y", "keyboard.u",
			"keyboard.i", "keyboard.o", "keyboard.p", "keyboard.left.bracket", "keyboard.right.bracket", "keyboard.backslash", "keyboard.a", "keyboard.s",
			"keyboard.d", "keyboard.f", "keyboard.g", "keyboard.h", "keyboard.j", "keyboard.k", "keyboard.l", "keyboard.semicolon", "keyboard.apostrophe",
			"keyboard.enter", "keyboard.left.shift", "keyboard.z", "keyboard.x", "keyboard.c", "keyboard.v", "keyboard.b", "keyboard.n", "keyboard.m",
			"keyboard.comma", "keyboard.period", "keyboard.slash", "keyboard.right.shift", "keyboard.left.control", "keyboard.left.alt", "keyboard.space",
			"keyboard.right.alt", "keyboard.right.control", "keyboard.up", "keyboard.down", "keyboard.left", "keyboard.right", "keyboard.print.screen",
			"keyboard.scroll.lock", "keyboard.pause", "keyboard.insert", "keyboard.home", "keyboard.page.up", "keyboard.page.down", "keyboard.end", "keyboard.delete"};
	
	boolean[] controlsUsed; //Parallel Array To Track If The Control Has Been Used Already
	
	public void setupFrame() {
		JFrame frame = new JFrame("Minecraft Control Randomizer!");	
	    field = new JTextField("Enter Path To options.txt");
	    enter = new JButton("Enter");
		
		
		field.setBounds(90, 150, 200, 20);
		enter.setBounds(140, 180, 100, 30);
		
		frame.add(field);
		frame.add(enter);
		
		frame.setSize(400, 300);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		enter.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter) {
			filePath = field.getText();
			Init(); //Initializes Variables
			try {
				Randomize();//Randomizes The Controls!
			} catch (Exception args) {
				args.printStackTrace();
			}
		}
		
	}
	
	public RandomControls() {
		setupFrame(); //Create Frame!
	}
	
	private void Init() {
		newFile = new File(System.getProperty("user.home") + "\\Desktop\\options.txt");
		optionsFile = new File(filePath);
		try {
			br = new BufferedReader(new FileReader(optionsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		try {
			wr = new BufferedWriter(new FileWriter(newFile, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		controlsUsed = new boolean[controls.length];
	}
	
	private void Randomize() throws Exception {
		//Reads Orginal File And Writes To New One
		String s;
		while((s = br.readLine()) != null) {
			if(s.contains("key_key")) {
				int temp = (int) (Math.random() * controls.length);
				
				while(controlsUsed[temp]) { //Checks To See If Control Has Been Used
					temp = (int) (Math.random() * controls.length);
				}
				
				s = s.replaceAll(s.substring(s.indexOf(":") + 1, s.length()), "key." + controls[temp]);
				
				controlsUsed[temp] = true;
				
			} 
			wr.write(s);
			wr.newLine();
		}
		wr.close();
		br.close();
	}
	

}
