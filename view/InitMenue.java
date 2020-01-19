package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.Choice;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class InitMenue extends JFrame {

	private JPanel contentPane;
	private JTextField tfTreeDepth;
	private JTextField tfM;
	private JTextField tfN;
	private File file;
	JComboBox cbBpSettings;
	JComboBox cbRpSettings;
	JComboBox cbBpMode;
	JComboBox cbRpMode;
	JCheckBox cbCpSm;
	
	private int treeDepth,n,m;
	private boolean stepMode;
	
	private void _enable() {
		cbCpSm.setEnabled(true);
		tfTreeDepth.setEnabled(true);
	}
	
	private void _disable() {
		cbCpSm.setEnabled(false);
		tfTreeDepth.setEnabled(false);
		cbCpSm.setSelected(false);
		tfTreeDepth.setText("");
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitMenue frame = new InitMenue();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public InitMenue() {
		setResizable(false);
		setTitle("Dots & Boxes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Project developed for Intelligent Systems course ETF by ra160248");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Player settings");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 11));
		
		JLabel lblNewLabel_3 = new JLabel("Initialization File");
		lblNewLabel_3.setFont(new Font("Century Gothic", Font.BOLD, 11));
		
		JLabel lblNewLabel_4 = new JLabel("Red Player");
		lblNewLabel_4.setForeground(new Color(255, 0, 0));
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		JLabel lblNewLabel_5 = new JLabel("Blue Player");
		lblNewLabel_5.setForeground(new Color(0, 0, 255));
		lblNewLabel_5.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		cbBpSettings = new JComboBox();
		
		cbBpSettings.setModel(new DefaultComboBoxModel(new String[] {"Human", "Computer"}));
		cbBpSettings.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		
		
		cbRpSettings = new JComboBox();
		cbRpSettings.setModel(new DefaultComboBoxModel(new String[] {"Human", "Computer"}));
		cbRpSettings.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		
		cbBpMode = new JComboBox();
		cbBpMode.setEnabled(false);
		cbBpMode.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Advanced", "Expert"}));
		cbBpMode.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		cbRpMode = new JComboBox();
		cbRpMode.setEnabled(false);
		cbRpMode.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Advanced", "Expert"}));
		cbRpMode.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		cbBpSettings.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
					cbBpMode.setEnabled(e.getItem().toString().equals("Computer"));
					if(cbBpSettings.getSelectedItem().equals("Computer") || cbRpSettings.getSelectedItem().equals("Computer"))
						_enable();
					else _disable();
			}
		});;
		cbRpSettings.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
					cbRpMode.setEnabled(e.getItem().toString().equals("Computer"));
					if(cbBpSettings.getSelectedItem().equals("Computer") || cbRpSettings.getSelectedItem().equals("Computer"))
						_enable();
					else _disable();
			}
		});;
		
		JButton btnNewButton = new JButton("Chose File");
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		
		JLabel lblNewLabel_2 = new JLabel("Tree depth");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 11));
		
		JLabel lblNewLabel_3_1 = new JLabel("Game Size (m,n)");
		lblNewLabel_3_1.setFont(new Font("Century Gothic", Font.BOLD, 11));
		
		tfTreeDepth = new JTextField();
		tfTreeDepth.setEnabled(false);
		tfTreeDepth.setColumns(10);
		
		tfM = new JTextField();
		tfM.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		tfM.setColumns(10);
		
		tfN = new JTextField();
		tfN.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		tfN.setColumns(10);
		
		cbCpSm = new JCheckBox("Computer Player Step Mode");
		cbCpSm.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		cbCpSm.setEnabled(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_3)
								.addComponent(lblNewLabel_2))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(tfTreeDepth, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_5)
									.addGap(18)
									.addComponent(cbBpSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbBpMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_4)
									.addGap(18)
									.addComponent(cbRpSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbRpMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfM, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
							.addGap(48)
							.addComponent(tfN, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
					.addGap(244))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(171)
					.addComponent(cbCpSm)
					.addContainerGap(197, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(24)
							.addComponent(lblNewLabel_1))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(15)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_5)
								.addComponent(cbBpSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbBpMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_4)
								.addComponent(cbRpSettings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbRpMode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(tfTreeDepth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(btnNewButton))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(tfN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(cbCpSm)
					.addGap(38))
		);
		panel.setLayout(gl_panel);
		
		JButton btnNewButton_1 = new JButton("Start");
		btnNewButton_1.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		contentPane.add(btnNewButton_1, BorderLayout.SOUTH);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    int returnVal = chooser.showOpenDialog(InitMenue.this);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				     InitMenue.this.file = chooser.getSelectedFile();
				     tfM.setEnabled(false);
				     tfN.setEnabled(false);
				    }
				    else {
				    	JOptionPane.showMessageDialog(InitMenue.this, "Invalid init file");

				    }
				
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameStart();
				
			}
		});
	}

	
	public synchronized void gameStart() {
		

		boolean err = false;
		
		
		
		try {
			
			boolean test = cbBpSettings.getSelectedItem().toString().equals("Computer") || cbRpSettings.toString().equals("Computer");
			
			if(test)
				treeDepth = Integer.parseInt(tfTreeDepth.getText());
			
			if(file==null) {
				n = Integer.parseInt(tfN.getText());
				m = Integer.parseInt(tfM.getText());
			}
			
			if(test && treeDepth <= 0 ||file==null&&( n<2 || m<2)) {
				JOptionPane.showMessageDialog(new JFrame(),"Tree Depth must be greater than zero, n and m must be greater than one" , "Invalid parameters",
				        JOptionPane.ERROR_MESSAGE);
			
				err = true;
			}
			}
			catch(NumberFormatException  e) {
				JOptionPane.showMessageDialog(new JFrame(),"Parsing failed" , "Invalid parameters",
				        JOptionPane.ERROR_MESSAGE);
				err = true;
			}
		
		if(!err) 
			notify();
	}


	public File getInitFile() {
		return this.file;
	}


	public int getTreeDepth() {
		return treeDepth;
	}


	public int getN() {
		return n;
	}


	public int getM() {
		return m;
	}


	public String getRMode() {
		return (String) this.cbRpMode.getSelectedItem();
	}


	public String getRType() {
		return (String) this.cbRpSettings.getSelectedItem();
	}


	public String getBMode() {
		return (String) this.cbBpMode.getSelectedItem();

	}
	
	public boolean getStepMode() {
		return cbCpSm.isSelected();
	}


	public String getBType() {
		return (String) this.cbBpSettings.getSelectedItem();

	}
}
