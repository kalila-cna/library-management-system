package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class main {

	public JFrame mframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.mframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mframe = new JFrame();
		mframe.setBounds(100, 100, 996, 633);
		mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mframe.getContentPane().setLayout(null);
		
		category c=new category();
		lendbook l=new lendbook();
		book b=new book();
		returnbook r=new returnbook();
		member m=new member();
		login login=new login();
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(243, 104, 500, 371);
		mframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("CATEGORY");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				c.frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(62, 50, 120, 35);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("MEMBER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				m.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(62, 154, 120, 35);
		panel.add(btnNewButton_1);
		
		JButton btnIssueBook = new JButton("ISSUE BOOK ");
		btnIssueBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				l.frame.setVisible(true);
			}
		});
		btnIssueBook.setBounds(322, 50, 109, 35);
		panel.add(btnIssueBook);
		
		JButton btnReturnBook = new JButton("RETURN BOOK");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				r.frame.setVisible(true);
			}
		});
		btnReturnBook.setBounds(322, 154, 128, 35);
		panel.add(btnReturnBook);
		
		JButton btnBookDetails = new JButton("BOOK DETAILS");
		btnBookDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				b.frame.setVisible(true);
			}
		});
		btnBookDetails.setBounds(62, 257, 120, 35);
		panel.add(btnBookDetails);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.setVisible(false);
				login.frame.setVisible(true);
			}
		});
		btnLogout.setBounds(322, 257, 109, 35);
		panel.add(btnLogout);
		
		JLabel lblNewLabel_2 = new JLabel("LIBRARY MANAGEMENT");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_2.setBackground(Color.YELLOW);
		lblNewLabel_2.setBounds(309, 41, 427, 38);
		mframe.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel.setBounds(0, 0, 980, 594);
		mframe.getContentPane().add(lblNewLabel);
	}
}