package com.github.leeonardoo.algestruturas;

import javax.swing.*;
import java.awt.*;
import java.awt.Window.Type;

public class MainScreen {

	private JFrame frmTrabalhoN;
	private JTextPane tP1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frmTrabalhoN.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrabalhoN = new JFrame();
		frmTrabalhoN.setTitle("Trabalho N1");
		frmTrabalhoN.setType(Type.UTILITY);
		frmTrabalhoN.setResizable(false);
		frmTrabalhoN.setBounds(100, 100, 450, 300);
		frmTrabalhoN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrabalhoN.getContentPane().setLayout(null);
		
		JLabel lbl1 = new JLabel("Arquivo:");
		lbl1.setBounds(10, 23, 46, 14);
		frmTrabalhoN.getContentPane().add(lbl1);
		
		JTextField tf1 = new JTextField();
		tf1.setBounds(70, 20, 234, 20);
		frmTrabalhoN.getContentPane().add(tf1);
		tf1.setColumns(10);
		
		JButton btn1 = new JButton("Analisar");
		btn1.setBounds(314, 19, 106, 23);
		frmTrabalhoN.getContentPane().add(btn1);
		
		tP1 = new JTextPane();
		tP1.setEditable(false);
		tP1.setBounds(24, 53, 400, 96);
		frmTrabalhoN.getContentPane().add(tP1);
		
		JLabel lblNewLabel = new JLabel("N\u00FAmero de ocorr\u00EAncias");
		lblNewLabel.setBounds(226, 158, 139, 14);
		frmTrabalhoN.getContentPane().add(lblNewLabel);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(24, 155, 396, 17);
		frmTrabalhoN.getContentPane().add(desktopPane);
		
		JLabel lbl2 = new JLabel("Tag");
		lbl2.setBounds(10, 0, 46, 14);
		desktopPane.add(lbl2);
		
		JTextField tf2 = new JTextField();
		tf2.setText("1\r\n");
		tf2.setBounds(24, 183, 400, 14);
		frmTrabalhoN.getContentPane().add(tf2);
		tf2.setColumns(10);
		
		JTextField tf3 = new JTextField();
		tf3.setBackground(SystemColor.control);
		tf3.setText("1\r\n");
		tf3.setColumns(10);
		tf3.setBounds(24, 196, 400, 14);
		frmTrabalhoN.getContentPane().add(tf3);
		
		JTextField tF4 = new JTextField();
		tF4.setText("1\r\n");
		tF4.setColumns(10);
		tF4.setBounds(24, 209, 400, 14);
		frmTrabalhoN.getContentPane().add(tF4);
		
		JTextField tf5 = new JTextField();
		tf5.setBackground(SystemColor.control);
		tf5.setText("1\r\n");
		tf5.setColumns(10);
		tf5.setBounds(24, 221, 400, 14);
		frmTrabalhoN.getContentPane().add(tf5);
	}
}