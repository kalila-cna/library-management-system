package library;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class member {

	public JFrame frame;
	private JTextField mid;
	private JTextField mname;
	private JTextField mdep;
	private JTextField msec;
	private JTextField smid;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					member window = new member();
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
	Connection con;
	Statement stmt;
	public member() {
		initialize();
		connect();
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
			ResultSet rs=stmt.executeQuery("select * from student");
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1046, 611);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT ID");
		lblNewLabel.setBounds(94, 128, 85, 27);
		frame.getContentPane().add(lblNewLabel);
		
		mid = new JTextField();
		mid.setBounds(208, 131, 106, 24);
		frame.getContentPane().add(mid);
		mid.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(69, 80, 909, 422);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblDepartment = new JLabel("DEPARTMENT");
		lblDepartment.setBounds(26, 147, 85, 27);
		panel.add(lblDepartment);
		
		mdep = new JTextField();
		mdep.setColumns(10);
		mdep.setBounds(138, 148, 106, 24);
		panel.add(mdep);
		
		JLabel lblSection = new JLabel("SECTION");
		lblSection.setBounds(26, 192, 85, 27);
		panel.add(lblSection);
		
		msec = new JTextField();
		msec.setColumns(10);
		msec.setBounds(138, 193, 106, 24);
		panel.add(msec);
		
		JLabel lblMemberName = new JLabel("STUDENT NAME");
		lblMemberName.setBounds(26, 99, 85, 27);
		panel.add(lblMemberName);
		
		mname = new JTextField();
		mname.setBounds(138, 100, 106, 24);
		panel.add(mname);
		mname.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=mid.getText();
			    String name = mname.getText();
		        String department = mdep.getText();
		        String section = msec.getText();
		        try {
		        
		        	ResultSet rs=stmt.executeQuery("insert into student(id,mname,department,section) values('"+id+"','"+name+"','"+department+"','"+section+"')");
		            con.commit();
		            JOptionPane.showMessageDialog(btnNewButton, "Student Added !");
		            tableload();
		            mid.setText("");
		            mname.setText("");
		            mdep.setText("");
		            msec.setText("");
		            mid.requestFocus();
		            
		        } catch (SQLException ex) {
		            System.out.print(ex);
		        }
		}
			}
		);
		btnNewButton.setBounds(22, 249, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=smid.getText();
				try {
					ResultSet rs=stmt.executeQuery("delete from student where id='"+i+"'");
					if(rs.next()==true) {
						con.commit();
						tableload();
						JOptionPane.showMessageDialog(btnNewButton, "Student Deleted !");
						mid.setText("");
			            mname.setText("");
			            mdep.setText("");
			            msec.setText("");
			            mid.requestFocus();
					}
					}
					
				 catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(155, 249, 89, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=smid.getText();
				String id=mid.getText();
			    String name = mname.getText();
		        String department = mdep.getText();
		        String section = msec.getText();
		        try {
		        
		        	ResultSet rs=stmt.executeQuery("update student set id='"+id+"',mname='"+name+"',department='"+department+"',section='"+section+"' where id='"+i+"'");
		            con.commit();
		            JOptionPane.showMessageDialog(btnNewButton, "Details updated Succesfully !");
		            tableload();
		            mid.setText("");
		            mname.setText("");
		            mdep.setText("");
		            msec.setText("");
		            mid.requestFocus();
		            
		        } catch (SQLException ex) {
		            System.out.print(ex);
		        }
			}
		});
		btnNewButton_2.setBounds(22, 300, 89, 23);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("CANCEL");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main m=new main();
				frame.setVisible(false);
				m.mframe.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(155, 300, 89, 23);
		panel.add(btnNewButton_3);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(26, 364, 20, 27);
		panel.add(lblId);
		
		smid = new JTextField();
		smid.setColumns(10);
		smid.setBounds(42, 365, 106, 24);
		panel.add(smid);
		
		JButton btnNewButton_3_1 = new JButton("SEARCH");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=smid.getText();
				try {
					ResultSet rs=stmt.executeQuery("select * from student where id='"+i+"'");
					if(rs.next()==true) {
					String txtid1=rs.getString(1);
					String stuname=rs.getString(2);
					String studep=rs.getString(3);
					String stusec=rs.getString(4);
					
					mid.setText(txtid1);
					mname.setText(stuname);
					mdep.setText(studep);
					msec.setText(stusec);
					}
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3_1.setBounds(155, 366, 89, 23);
		panel.add(btnNewButton_3_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 31, 555, 357);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, "", null},
			},
			new String[] {
				"STUDENT ID", "STUDENT NAME", "DEPARTMENT", "SECTION"
			}
		));
		
		JLabel lblNewLabel_1 = new JLabel("STUDENT DETAILS");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(365, 27, 283, 42);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_2.setBounds(0, 0, 1030, 572);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
