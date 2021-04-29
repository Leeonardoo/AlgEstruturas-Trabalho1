package com.github.leeonardoo.algestruturas;

import javax.swing.*;
import java.awt.*;
import java.awt.Window.Type;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;

public class MainScreen {

	private JFrame frmTrabalhoN;
	private JTextPane statusTextArea;
	private JTable tagsTable;

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
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException exception) {
			try {
				UIManager.setLookAndFeel(new MetalLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
		
		frmTrabalhoN = new JFrame();
		frmTrabalhoN.setTitle("Trabalho N1");
		frmTrabalhoN.setBounds(100, 100, 450, 300);
		frmTrabalhoN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel filePathInputLabel = new JLabel("Arquivo:");
		
		JTextField filePathInput = new JTextField();
		filePathInput.setPreferredSize(new Dimension(7, 28));
		filePathInput.setSize(new Dimension(0, 28));
		filePathInput.setMinimumSize(new Dimension(7, 28));
		filePathInput.setMaximumSize(new Dimension(2147483647, 28));
		filePathInput.setAlignmentY(Component.TOP_ALIGNMENT);
		filePathInput.setMargin(new Insets(3, 0, 2, 2));
		filePathInput.setColumns(10);
		
		JButton analyzeFileButton = new JButton("Analisar");
		analyzeFileButton.setPreferredSize(new Dimension(71, 28));
		analyzeFileButton.setMinimumSize(new Dimension(71, 28));
		analyzeFileButton.setMaximumSize(new Dimension(71, 28));
		
		statusTextArea = new JTextPane();
		statusTextArea.setEditable(false);
		
		JButton selectFileButton = new JButton("");
		selectFileButton.setSize(new Dimension(28, 28));
		selectFileButton.setMinimumSize(new Dimension(28, 28));
		selectFileButton.setMaximumSize(new Dimension(28, 28));
		selectFileButton.setPreferredSize(new Dimension(28, 28));
		
		selectFileButton.setIcon(UIManager.getIcon("FileView.directoryIcon"));
		
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(frmTrabalhoN.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(filePathInputLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(filePathInput, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(analyzeFileButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addComponent(statusTextArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(selectFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(filePathInputLabel)
							.addComponent(filePathInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(analyzeFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(statusTextArea, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tagsTable = new JTable();
		scrollPane.setViewportView(tagsTable);
		tagsTable.setCellEditor(null);
		tagsTable.setModel(new DefaultTableModel(
			new Object[][] {
				new String[] {
						"NewText", "NewText"
				}
			},
			new String[] {
				"Tag", "Número de ocorrências"
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tagsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		frmTrabalhoN.getContentPane().setLayout(groupLayout);
	}
}