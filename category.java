package library;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;



import java.awt.Color;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Font;

public class category {

	 JFrame frame;
	private JTextField txtcat;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					category window = new category();
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
	public category() {
		initialize();
		connect();
		tableload();
		
	}
	Connection con;
	Statement stmt;
	private JTextField txtid;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTextField sid;
	
	
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
			ResultSet rs=stmt.executeQuery("select * from category");
			table_2.setModel(DbUtils.resultSetToTableModel(rs));
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
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(61, 58, 904, 466);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCategoryName = new JLabel("CATEGORY NAME");
		lblCategoryName.setBounds(51, 132, 98, 22);
		panel.add(lblCategoryName);
		
		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setBounds(51, 189, 98, 22);
		panel.add(lblStatus);
		
		txtcat = new JTextField();
		txtcat.setBounds(194, 133, 86, 20);
		panel.add(txtcat);
		txtcat.setColumns(10);
		
		JComboBox txtstatus = new JComboBox();
		txtstatus.setBounds(194, 189, 86, 22);
		txtstatus.setModel(new DefaultComboBoxModel(new String[] {"Available", "Unavailable"}));
		panel.add(txtstatus);
		
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(51, 248, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    String id=txtid.getText();
				    String category = txtcat.getText();
			        String status = txtstatus.getSelectedItem().toString();
			        try {
			        
			        	ResultSet rs=stmt.executeQuery("insert into category(id,categorytype,status) values('"+id+"','"+category+"','"+status+"')");
			            con.commit();
			            JOptionPane.showMessageDialog(btnNewButton, "Category added");
			            tableload();
			            txtcat.setText("");
			            txtid.setText("");
			            txtstatus.setSelectedIndex(-1);
			            txtcat.requestFocus();
			            
			        } catch (SQLException ex) {
			            System.out.print(ex);
			        }
			}
			
		});
		panel.add(btnNewButton);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=txtid.getText();
			    String category = txtcat.getText();
		        String status = txtstatus.getSelectedItem().toString();
		        String i=sid.getText();
		        try {
		        
		        	ResultSet rs=stmt.executeQuery("update category set id='"+id+"',categorytype='"+category+"',status='"+status+"' where id='"+i+"'");
		            con.commit();
		            JOptionPane.showMessageDialog(btnNewButton, "Category updated");
		            tableload();
		            txtcat.setText("");
		            txtid.setText("");
		            txtstatus.setSelectedIndex(-1);
		            txtcat.requestFocus();
		            
		        } catch (SQLException ex) {
		            System.out.print(ex);
		        }
			}
		});
		btnUpdate.setBounds(191, 248, 89, 23);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=sid.getText();
				try {
					ResultSet rs=stmt.executeQuery("delete from category where id='"+i+"'");
					if(rs.next()==true) {
						con.commit();
						tableload();
						txtcat.setText("");
						txtid.setText("");
			            txtstatus.setSelectedIndex(-1);
			            txtcat.requestFocus();
					}
					}
					
				 catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(51, 318, 89, 23);
		panel.add(btnDelete);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				main m=new main();
				frame.setVisible(false);
				m.mframe.setVisible(true);
				
			}
		});
		btnCancel.setBounds(194, 318, 89, 23);
		panel.add(btnCancel);
		
		JLabel lblCategoryId = new JLabel("CATEGORY ID");
		lblCategoryId.setBounds(51, 79, 98, 22);
		panel.add(lblCategoryId);
		
		txtid = new JTextField();
		txtid.setBounds(194, 80, 86, 20);
		txtid.setColumns(10);
		panel.add(txtid);
		
		table = new JTable();
		table.setBounds(682, 232, -216, -175);
		panel.add(table);
		
		table_1 = new JTable();
		table_1.setBounds(538, 144, 98, -60);
		panel.add(table_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(347, 32, 520, 332);
		panel.add(scrollPane);
		
		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "CATEGORY", "STATUS"
			}
		));
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				String i=sid.getText();
				int set;
				try {
					ResultSet rs=stmt.executeQuery("select * from category where id='"+i+"'");
					if(rs.next()==true) {
					String txtid1=rs.getString(1);
					String catid=rs.getString(2);
					String stid=rs.getString(3);
					if(stid.equals("Available")) { set=0;}
					else { set=1;}
					
					txtid.setText(txtid1);
					txtcat.setText(catid);
					txtstatus.setSelectedIndex(set);
					}
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(194, 408, 89, 23);
		panel.add(btnSearch);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(36, 408, 44, 22);
		panel.add(lblId);
		
		sid = new JTextField();
		sid.setColumns(10);
		sid.setBounds(51, 409, 86, 20);
		panel.add(sid);
		
		JLabel lblNewLabel = new JLabel("CATEGORY");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(403, 11, 178, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_1.setBounds(0, 0, 1030, 572);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
