package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.GameController;
import model.Game;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class InputFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField textField;
	private JButton btnNewButton;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private GameController gc;
	
	public String getInput() {
		return textField.getText();
	}
	
	public void enableAll() {
		textField.setEnabled(true);
		btnNewButton.setEnabled(true);
		textField.setText("");
	}
	
	public void disableAll() {
		textField.setEnabled(false);
		btnNewButton.setEnabled(false);
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputFrame frame = new InputFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InputFrame() {
		
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Input");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Actions");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Show history");
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gc.showHistory();
			}
		});
		
		mntmNewMenuItem_1 = new JMenuItem("Export History");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gc.export();
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setFont(new Font("Century Gothic", Font.BOLD, 50));
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Make move");
		btnNewButton.setEnabled(false);
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (InputFrame.this) {
					InputFrame.this.notify();
				}
			}
		});

	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}

	public void enableButton() {
		textField.setText("Comp.");
		btnNewButton.setEnabled(true);
		
	}
	public void disableButton() {
		textField.setText("");
		btnNewButton.setEnabled(false);
	}

}
