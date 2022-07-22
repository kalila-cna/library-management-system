package library;
import java.sql.*;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class login {

	JFrame frame;
	private JTextField txtuser;
	private JPasswordField txtpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login() {
		initialize();
		connect();
	}
	Connection con;
	Statement stmt;
	
	
	public void connect() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","kalilacna","kalila");
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 602, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.PINK);
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(89, 69, 412, 261);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USER NAME");
		lblNewLabel.setBackground(Color.PINK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(51, 65, 87, 28);
		panel.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPassword.setBounds(51, 136, 87, 28);
		panel.add(lblPassword);
		
		txtuser = new JTextField();
		txtuser.setBounds(173, 70, 178, 23);
		panel.add(txtuser);
		txtuser.setColumns(10);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String username=txtuser.getText();
				String password=txtpass.getText();
				main m=new main();
				try {
					stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from login where username='"+username+"' and password='"+password+"'");
					if(rs.next()) 
					{
						m.mframe.setVisible(true);
						frame.setVisible(false);
					}
					else 
					{
						JOptionPane.showMessageDialog(btnNewButton, "username or password do not match");		
						txtuser.setText("");
						txtpass.setText("");
						txtuser.requestFocus();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(124, 201, 102, 28);
		panel.add(btnNewButton);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnCancel.setBounds(249, 201, 102, 28);
		panel.add(btnCancel);
		
		JLabel lblNewLabel_1 = new JLabel("LOG IN");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(165, 11, 145, 28);
		panel.add(lblNewLabel_1);
		
		txtpass = new JPasswordField();
		txtpass.setBounds(173, 141, 178, 23);
		panel.add(txtpass);
		
		JLabel lblNewLabel_2 = new JLabel("LIBRARY MANAGEMENT");
		lblNewLabel_2.setBackground(Color.YELLOW);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_2.setBounds(142, 24, 335, 21);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_3.setBounds(0, 0, 586, 374);
		frame.getContentPane().add(lblNewLabel_3);
	}
}
