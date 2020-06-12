package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Dao.EmployeeDao;
import Dao.RetailerDao;
import encrypter.BCrypt;
import repositories.Employee_repo;
import repositories.Retailer_repo;

public class Register extends JFrame implements ActionListener{
		private static int workload = 12;
	
		JPanel panel;
	    JLabel user_label, password_label,retailerid_Label, email_Label, confirm_psw_Label, ph_Label,role_Label;
	    JTextField userName_text,retailerid_text,email_text,ph_text;
	    JPasswordField password_text,confirm_psw_text;
	    JButton cancel,register;
	    private EmployeeDao employeeDao = new EmployeeDao();
	    private RetailerDao retailerDao = new RetailerDao();
	    private Employee_repo employee_repo;
	    boolean from_rdb;
	    boolean from_db;
	    JComboBox<String> jComboBox;
	    String s1[] = {"Admin","Employee"};
	    private BCrypt byBCrypt;
	    
	    public Register() {
	    	
	    	//Retailer id Label
	    	retailerid_Label = new JLabel();
	    	retailerid_Label.setText("Retailer id :");
	    	retailerid_text = new JTextField(15);
	    	
	    	// User Label
	        user_label = new JLabel();
	        user_label.setText("Name :");
	        userName_text = new JTextField(15);
	        
	        // Password
	        password_label = new JLabel();
	        password_label.setText("Password :");
	        password_text = new JPasswordField(15);
	        
	        //role
	        role_Label = new JLabel();
	        role_Label.setText("Role :");
	        
	        
	        // User email
	        email_Label = new JLabel();
	        email_Label.setText("User Email Id :");
	        email_text = new JTextField(15);
	        
	        // Password
	        confirm_psw_Label = new JLabel();
	        confirm_psw_Label.setText("Confirm Password :");
	        confirm_psw_text = new JPasswordField(15);
	        
	        //Ph
	        ph_Label = new JLabel();
	        ph_Label.setText("Phone No");
	        ph_text = new JTextField(15);
	        
	        jComboBox = new JComboBox<String>(s1);
	        
	        
	        //Cancel
	        cancel = new JButton("CANCEL");
	        //Register
	        register = new JButton("REGISTER");
	        
	        panel = new JPanel();
	        
	        panel.add(retailerid_Label);
	        panel.add(retailerid_text);
	        panel.add(email_Label);
	        panel.add(email_text);
	        panel.add(user_label);
	        panel.add(userName_text);
	        panel.add(role_Label);
	        panel.add(jComboBox);
	        panel.add(ph_Label);
	        panel.add(ph_text);
	        panel.add(password_label);
	        panel.add(password_text);
	        panel.add(confirm_psw_Label);
	        panel.add(confirm_psw_text);
	        
	        panel.add(register);
	        panel.add(cancel);
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        register.addActionListener(this);
	        add(panel, BorderLayout.CENTER);
	        setTitle("Please Register Here !");
	        
	        cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					LogIn logIn = new LogIn();
					logIn.setSize(200, 500);
					logIn.setVisible(true);
					
				}
			});
	    }
	    
	    public static void main(String[] args) {
	    	try
	    	   {
	    	   Register frame=new Register();
	    	   frame.setSize(200,500);
	    	   frame.setVisible(true);
	    	   frame.setResizable(false);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    		   }
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent ae) {
	    	
	    	String userName = userName_text.getText();
	        String password = password_text.getText();
	        long ph_no = Long.parseLong(ph_text.getText().trim());
	        String role =(String) jComboBox.getSelectedItem(), email = email_text.getText(), confirm_psw = confirm_psw_text.getText() ;
	        int retailer_id = Integer.parseInt(retailerid_text.getText());
	        

	        if ( password!=null && retailerid_text!=null  && email!=null && confirm_psw!=null && ph_text!=null && userName!=null) {
	        	
	        if(password.equals(confirm_psw))	{
	        	try {
					from_db = employeeDao.isUserExists(email);
					from_rdb = retailerDao.isCompanyExists(retailer_id);
					if(!from_db) {
						if(from_rdb) {	
						password = BCrypt.hashpw(password, BCrypt.gensalt());
						System.out.println(BCrypt.checkpw(password_text.getText(),password));
						employee_repo = new Employee_repo( userName, email, retailer_id, ph_no, role, password);
						employeeDao.addUser(employee_repo);
						JOptionPane.showMessageDialog(this, "yaaaayy!!! Registration successful!");	
						String retailer_name = retailerDao.getCompanyName(retailer_id);
						TransactionPage transactionPage = new TransactionPage(retailer_name, role);
						transactionPage.setSize(1000,1000);
				    	transactionPage.setVisible(true);
						dispose();
						}
						else {
							JOptionPane.showMessageDialog(this, "Company does not exist !");
							dispose();
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "User already exists!");
						dispose();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
	        	
	        }else {
	        	JOptionPane.showMessageDialog(this, "passwords mismatch ");
	        	
	        }
	        	
	        } else {
	            JOptionPane.showMessageDialog(this, "Fields can't be empty.");
	        }
	
}

	    
}
