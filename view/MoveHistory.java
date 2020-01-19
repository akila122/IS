package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Font;

public class MoveHistory extends JFrame {

	JTextArea textArea;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoveHistory frame = new MoveHistory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void putString(String s) {
		textArea.setText(textArea.getText()+s+"\n");
	}
	
	public MoveHistory() {
		setTitle("History");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Century Gothic", Font.ITALIC, 20));
		textArea.setEditable(false);
		contentPane.add(textArea, BorderLayout.CENTER);
	}

}
