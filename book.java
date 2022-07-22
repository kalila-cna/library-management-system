package library;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class book {

	 JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					book window = new book();
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
	public book() {
		connect();
		loadcombo();
		initialize();
		tableload();
		
	}
	Connection con;
	Statement stmt;
	private JTable table;
	private JTextField tbookid;
	private JTextField tbookname;
	private JTextField tauthor;
	private JTextField tpage;
	String s[];
	int i=0,l=0;
	private JTextField sbid;

	
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
	
	public void loadcombo() 
	{
		try {
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select count(categorytype) from category");
			if(rs.next()) 
			{
				l=Integer.parseInt(rs.getString(1));
			}
			s=new String[l];
			ResultSet r=stmt.executeQuery("select categorytype from category");
			while(r.next()) 
			{
				s[i]=r.getString(1);
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public void tableload() 
	{
		try {
			con.setAutoCommit(false);
			stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from book");
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	 void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1165, 644);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 204, 255));
		panel.setBounds(72, 70, 1023, 475);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JComboBox comboBox = new JComboBox(s);
		comboBox.setSelectedIndex(-1);
		comboBox.setBounds(148, 147, 83, 22);
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(299, 11, 704, 452);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"BOOK ID", "BOOK NAME", "CATEGORY", "AUTHOR", "NO OF PAGES"
				}
			));
			table.getColumnModel().getColumn(4).setPreferredWidth(87);
		
		JLabel lblNewLabel = new JLabel("BOOK ID");
		lblNewLabel.setBounds(40, 52, 64, 22);
		panel.add(lblNewLabel);
		
		JLabel lblBookName = new JLabel("BOOK NAME");
		lblBookName.setBounds(40, 95, 95, 22);
		panel.add(lblBookName);
		
		JLabel lblCategory = new JLabel("CATEGORY");
		lblCategory.setBounds(40, 147, 64, 22);
		panel.add(lblCategory);
		
		JLabel lblAuthor = new JLabel("AUTHOR");
		lblAuthor.setBounds(40, 203, 64, 22);
		panel.add(lblAuthor);
		
		JLabel lblNoOfPages = new JLabel("NO OF PAGES");
		lblNoOfPages.setBounds(40, 258, 83, 22);
		panel.add(lblNoOfPages);
		
		tbookid = new JTextField();
		tbookid.setBounds(145, 53, 86, 20);
		panel.add(tbookid);
		tbookid.setColumns(10);
		
		tbookname = new JTextField();
		tbookname.setColumns(10);
		tbookname.setBounds(145, 96, 86, 20);
		panel.add(tbookname);
		
		tauthor = new JTextField();
		tauthor.setColumns(10);
		tauthor.setBounds(148, 204, 86, 20);
		panel.add(tauthor);
		
		tpage = new JTextField();
		tpage.setColumns(10);
		tpage.setBounds(148, 259, 86, 20);
		panel.add(tpage);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=tbookid.getText();
			    String name = tbookname.getText();
		        String category =  comboBox.getSelectedItem().toString();
		        String author = tauthor.getText();
		        String page = tpage.getText();
		        try {
		        
		        	ResultSet rs=stmt.executeQuery("insert into book(id,bname,category,author,page) values('"+id+"','"+name+"','"+category+"','"+author+"','"+page+"')");
		            con.commit();
		            JOptionPane.showMessageDialog(btnNewButton, "Book Added !");
		            tableload();
		            tbookid.setText("");
		            tbookname.setText("");
		            comboBox.setSelectedIndex(-1);
		            tauthor.setText("");
		            tpage.setText("");
		            tbookid.requestFocus();
		            
		        } catch (SQLException ex) {
		            System.out.print(ex);
		        }
			}
		});
		btnNewButton.setBounds(15, 314, 89, 23);
		panel.add(btnNewButton);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=sbid.getText();
				try {
					ResultSet rs=stmt.executeQuery("delete from book where id='"+i+"'");
					if(rs.next()==true) {
						con.commit();
						tableload();
						JOptionPane.showMessageDialog(btnNewButton, "Book Deleted !");
						tbookid.setText("");
			            tbookname.setText("");
			            comboBox.setSelectedIndex(-1);
			            tauthor.setText("");
			            tpage.setText("");
			            tbookid.requestFocus();
					}
					}
					
				 catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(142, 314, 89, 23);
		panel.add(btnDelete);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=sbid.getText();
				String id=tbookid.getText();
			    String name = tbookname.getText();
		        String bcategory =  comboBox.getSelectedItem().toString();
		        String author = tauthor.getText();
		        String page = tpage.getText();
		        try {
		        
		        	ResultSet rs=stmt.executeQuery("update book set id='"+id+"',bname='"+name+"',category='"+bcategory+"',author='"+author+"',page='"+page+"' where id='"+i+"'");
		            con.commit();
		            JOptionPane.showMessageDialog(btnNewButton, "Details updated Succesfully !");
		            tableload();
		            tbookid.setText("");
		            tbookname.setText("");
		            comboBox.setSelectedIndex(-1);
		            tauthor.setText("");
		            tpage.setText("");
		            tbookid.requestFocus();
		            
		        } catch (SQLException ex) {
		            System.out.print(ex);
		        }
			}
		});
		btnUpdate.setBounds(15, 360, 89, 23);
		panel.add(btnUpdate);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main m=new main();
				frame.setVisible(false);
				m.mframe.setVisible(true);
			}
		});
		btnCancel.setBounds(142, 360, 89, 23);
		panel.add(btnCancel);
		
		sbid = new JTextField();
		sbid.setColumns(10);
		sbid.setBounds(40, 426, 86, 20);
		panel.add(sbid);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i=sbid.getText();
				try {
					ResultSet rs=stmt.executeQuery("select * from book where id='"+i+"'");
					if(rs.next()==true) {
					String id=rs.getString(1);
					String name=rs.getString(2);
					String category=rs.getString(3);
					String author=rs.getString(4);
					String page=rs.getString(5);
					
					tbookid.setText(id);
					tbookname.setText(name);
					comboBox.setSelectedItem(category);
					tauthor.setText(author);
					tpage.setText(page);
					}
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(142, 425, 89, 23);
		panel.add(btnSearch);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(15, 424, 64, 22);
		panel.add(lblId);
		
		JLabel lblNewLabel_1 = new JLabel("BOOK DETAILS");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(459, 22, 235, 37);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\cnaka\\Downloads\\Library-Supply-Management-System-preview.jpg"));
		lblNewLabel_2.setBounds(0, 0, 1149, 605);
		frame.getContentPane().add(lblNewLabel_2);
	}
}
