package library;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class lendbook {

	 JFrame frame;
	private JTable table;
	Connection con;
	Statement stmt;
	String mid[];
	String bid[];
	int i=0,l=0;
	int j=0,m=0;
	private JTextField sname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lendbook window = new lendbook();
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
	public lendbook() {
		connect();
		loadcombo();
		initialize();
		tableload();
	}
	
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
	
	public void tableload() 
	{
		try {
			con.setAutoCommit(false);
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from issuebook");
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void loadcombo() 
	{
		
		try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select count(id) from student");
			if(rs.next()) 
			{
				l=Integer.parseInt(rs.getString(1));
			}
			ResultSet r=stmt.executeQuery("select id from student");
			mid=new String[l];
			while(r.next()) 
			{
				mid[i]=r.getString(1);
				i++;
			}
			ResultSet rs1=stmt.executeQuery("select count(bname) from book");
			if(rs1.next()) 
			{
				m=Integer.parseInt(rs1.getString(1));
			}
			
			bid=new String[m];
			
			ResultSet r1=stmt.executeQuery("select bname from book");
			
			while(r1.next()) 
			{
				bid[j]=r1.getString(1);
				j++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1063, 591);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(66, 70, 924, 445);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT ID");
		lblNewLabel.setBounds(41, 56, 71, 21);
		panel.add(lblNewLabel);
		
		JComboBox mcombobox = new JComboBox(mid);
		mcombobox.setSelectedIndex(-1);
		mcombobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mid =  mcombobox.getSelectedItem().toString();
				try {
		        	 
					ResultSet rs=stmt.executeQuery("select mname from student where  id='"+mid+"'");
					if(rs.next()) 
					{
					   String membername=rs.getString(1);
					   sname.setText(membername);
					}
				}
				catch(SQLException e1) {e1.printStackTrace();}
			}
		});
		mcombobox.setBounds(148, 55, 118, 22);
		panel.add(mcombobox);
		
		JLabel lblBookName = new JLabel("BOOK NAME");
		lblBookName.setBounds(41, 152, 71, 21);
		panel.add(lblBookName);
		
		JComboBox bcombobox = new JComboBox(bid);
		bcombobox.setSelectedIndex(-1);
		bcombobox.setBounds(148, 152, 118, 21);
		panel.add(bcombobox);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(336, 29, 565, 393);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
				},
				new String[] {
					"STUDENT ID", "STUDENT NAME", "BOOK NAME", "ISSUE DATE", "RETURN DATE"
				}
			));
		
		JLabel lblIssueDate = new JLabel("ISSUE DATE");
		lblIssueDate.setBounds(41, 196, 71, 21);
		panel.add(lblIssueDate);
		
		JLabel lblReturnDate = new JLabel("RETURN DATE");
		lblReturnDate.setBounds(41, 253, 71, 21);
		panel.add(lblReturnDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(148, 196, 118, 21);
		panel.add(dateChooser);
		
		JDateChooser dateChooser1 = new JDateChooser();
		dateChooser1.setBounds(148, 253, 118, 21);
		panel.add(dateChooser1);
		
		JButton btnNewButton = new JButton("ISSUE BOOK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname =  bcombobox.getSelectedItem().toString();
				String mid =  mcombobox.getSelectedItem().toString();
				String membername = null;
				SimpleDateFormat Date_Format = new SimpleDateFormat("dd-MMM-y");
		        String date = Date_Format.format(dateChooser.getDate());
		          
		          SimpleDateFormat Date_Format1 = new SimpleDateFormat("dd-MMM-y");
		          String date1 = Date_Format1.format(dateChooser1.getDate());
		          try {
		        	 
				ResultSet rs=stmt.executeQuery("select mname from student where  id='"+mid+"'");
				if(rs.next()) 
				{
				    membername=rs.getString(1);
				}
				ResultSet rs1=stmt.executeQuery("insert into issuebook(id,mname,bname,issuedate,returndate) values('"+mid+"','"+membername+"','"+bname+"','"+date+"','"+date1+"')");
	            con.commit();
	            JOptionPane.showMessageDialog(btnNewButton, "Book Issued !");
	            tableload();
			}catch(SQLException e1) {e1.printStackTrace();}
			}
		});
		btnNewButton.setBounds(83, 324, 118, 21);
		panel.add(btnNewButton);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main m=new main();
				frame.setVisible(false);
				m.mframe.setVisible(true);
			}
		});
		btnCancel.setBounds(83, 385, 118, 21);
		panel.add(btnCancel);
		
		JLabel lblStudentName = new JLabel("STUDENT NAME");
		lblStudentName.setBounds(41, 103, 107, 21);
		panel.add(lblStudentName);
		
		sname = new JTextField();
		sname.setEditable(false);
		sname.setBounds(148, 103, 118, 21);
		panel.add(sname);
		sname.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ISSUE BOOK");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_2.setBounds(433, 23, 246, 34);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_1.setBounds(0, 0, 1047, 552);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
