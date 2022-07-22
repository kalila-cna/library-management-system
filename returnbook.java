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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;
import java.awt.Color;
import java.awt.Font;

public class returnbook {

    JFrame frame;
	private JTextField sname;
	private JTextField rdate;
	private JTable table;
	Connection con;
	Statement stmt;
	int i=0,l=0;
	String s[];
	private JTextField elp;
	private JTextField tfine;
	private JTextField tid;
	JComboBox bcombobox;
	JDateChooser dateChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					returnbook window = new returnbook();
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
	public returnbook() {
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
			ResultSet rs=stmt.executeQuery("select * from returnbook");
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void loadcombo() 
	{
		
		try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select count(bname) from issuebook");
			if(rs.next()) 
			{
				l=Integer.parseInt(rs.getString(1));
			}
			s=new String[l];
			ResultSet r=stmt.executeQuery("select bname from issuebook");
			while(r.next()) 
			{
				s[i]=r.getString(1);
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();}
		}
		

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1054, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(58, 61, 940, 446);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT ID");
		lblNewLabel.setBounds(31, 49, 76, 21);
		panel.add(lblNewLabel);
		
		JLabel lblStudentName = new JLabel("STUDENT NAME");
		lblStudentName.setBounds(31, 104, 115, 21);
		panel.add(lblStudentName);
		
		JLabel lblBookName = new JLabel("BOOK NAME");
		lblBookName.setBounds(31, 155, 76, 21);
		panel.add(lblBookName);
		
		JLabel lblReturnDate = new JLabel("RETURN DATE");
		lblReturnDate.setBounds(31, 204, 76, 21);
		panel.add(lblReturnDate);
		
		sname = new JTextField();
		sname.setEditable(false);
		sname.setBounds(140, 104, 115, 21);
		panel.add(sname);
		sname.setColumns(10);
		
		rdate = new JTextField();
		rdate.setEditable(false);
		rdate.setColumns(10);
		rdate.setBounds(140, 204, 115, 21);
		panel.add(rdate);
		
		JLabel lblFine = new JLabel("DAYS ELAPSED");
		lblFine.setBounds(31, 297, 99, 21);
		panel.add(lblFine);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 26, 617, 409);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"STUDENT ID", "NAME", "BOOK NAME", "RETURN DATE", "DAYS ELAPSED", "FINE"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(59);
		
		elp = new JTextField();
		elp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int elapsed=Integer.parseInt(elp.getText());
				int fine=elapsed*20;
				
				tfine.setText(String.valueOf(fine));
				
			}
		});
		elp.setColumns(10);
		elp.setBounds(140, 297, 115, 21);
		panel.add(elp);
		
		tfine = new JTextField();
		tfine.setEditable(false);
		tfine.setColumns(10);
		tfine.setBounds(140, 340, 115, 21);
		panel.add(tfine);
		
		JLabel lblFine_1 = new JLabel("FINE");
		lblFine_1.setBounds(31, 340, 76, 21);
		panel.add(lblFine_1);
		JComboBox bcombobox = new JComboBox(s);
		bcombobox.setSelectedIndex(-1);
		bcombobox.setBounds(140, 154, 115, 22);
		panel.add(bcombobox);
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(140, 252, 115, 21);
		panel.add(dateChooser);
		
		JButton btnNewButton = new JButton("RETURN BOOK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat Date_Format = new SimpleDateFormat("dd-MMM-y");
				String memberid=tid.getText();
				String membername=sname.getText();
				String bname =  bcombobox.getSelectedItem().toString();
		        String date = Date_Format.format(dateChooser.getDate());
		        String elapsed=elp.getText();
		        String fine=tfine.getText();
		        
		          try {
				ResultSet rs1=stmt.executeQuery("insert into returnbook(id,mname,bname,returndate,elapsed,fine) values('"+memberid+"','"+membername+"','"+bname+"','"+date+"','"+elapsed+"','"+fine+"')");
	            con.commit();
	            JOptionPane.showMessageDialog(btnNewButton, "Returned Book !");
	            tableload();
	            ResultSet rs=stmt.executeQuery("delete from issuebook where id='"+memberid+"' and bname='"+bname+"'");
	            con.commit();
			}catch(SQLException e1) {e1.printStackTrace();}
			}
		});
		btnNewButton.setBounds(20, 393, 126, 21);
		panel.add(btnNewButton);
		
		tid = new JTextField();
		tid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String mid = tid.getText();
				try {
		        	 
					ResultSet rs=stmt.executeQuery("select * from issuebook where  id='"+mid+"'");
					if(rs.next()) 
					{
					   String membername=rs.getString(2);
					   String returndate=rs.getString(5);
					   sname.setText(membername);
					   rdate.setText(returndate);
					}
				}
				catch(SQLException e1) {e1.printStackTrace();}
			}
		});
		
		
		tid.setBounds(140, 49, 115, 21);
		panel.add(tid);
		tid.setColumns(10);
		
		JLabel lblTodayDate = new JLabel("TODAY DATE");
		lblTodayDate.setBounds(31, 252, 99, 21);
		panel.add(lblTodayDate);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main m=new main();
				frame.setVisible(false);
				m.mframe.setVisible(true);
			}
		});
		btnCancel.setBounds(169, 393, 103, 21);
		panel.add(btnCancel);
		
		JLabel lblNewLabel_1 = new JLabel("RETURN BOOK");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(409, 11, 242, 39);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_2.setBounds(0, 0, 1038, 553);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
