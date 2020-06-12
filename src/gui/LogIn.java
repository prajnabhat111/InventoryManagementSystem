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

public class LogIn extends JFrame implements ActionListener {

	    private static final long serialVersionUID = 1L;
	    private static int workload = 12;
		JPanel panel;
	    JLabel user_label, password_label,retailer_Label,retailer_id_Label, lbl,msg,role_label;
	    JTextField userName_text,retailername_text,retailerid_text;
	    JPasswordField password_text;
	    JButton submit, cancel,register,companyreg;
	    private EmployeeDao employeeDao = new EmployeeDao();
	    private RetailerDao retailerDao = new RetailerDao();
	    Retailer_repo from_rdb;
	    Employee_repo from_db;
	    String psw_from_db, retailername_from_db, role_from_db;
	    int rid_from_db;
	    JComboBox<String> javComboBox;
	    String s1[] = {"Admin","Employee"};
	    BCrypt bCrypt;

	
	    public LogIn() {
	    	
	    	
	    	//Retailer name Label
	    	retailer_Label = new JLabel();
	    	retailer_Label.setText("Retailer Name :");
	    	retailername_text = new JTextField(15);
	    	
	    	//Retailer id Label
	    	retailer_id_Label = new JLabel();
	    	retailer_id_Label.setText("Retailer id :");
	    	retailerid_text = new JTextField(15);
	    	
	    	// User Label
	        user_label = new JLabel();
	        user_label.setText("User Email Id :");
	        userName_text = new JTextField(15);
	        
	        // Password

	        password_label = new JLabel();
	        password_label.setText("Password :");
	        password_text = new JPasswordField(15);
	        
	        //role
	        role_label = new JLabel();
	        role_label.setText("Role :");

	        // Submit

	        submit = new JButton("SUBMIT");
	        
	        //Cancel
	        cancel = new JButton("CANCEL");
	        //Register
	        register = new JButton("SIGN UP");
	        companyreg = new JButton("REGISTER");
	        
	        //Message
	        msg = new JLabel();
	        msg.setText("new retailer? Register here");
	        lbl = new JLabel();
	        lbl.setText("new user? Sign Up here");
	        
	        javComboBox = new JComboBox<>(s1);

	        panel = new JPanel();
	        
	        panel.add(retailer_Label);
	        panel.add(retailername_text);
	        panel.add(retailer_id_Label);
	        panel.add(retailerid_text);
	        panel.add(role_label);
	        panel.add(javComboBox);
	        panel.add(user_label);
	        panel.add(userName_text);
	        panel.add(password_label);
	        panel.add(password_text);

	        
	  
	        panel.add(submit);
	        panel.add(cancel);
	        panel.add(lbl);
	        panel.add(register);
	        panel.add(msg);
	        panel.add(companyreg);
	       
	        
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        // Adding the listeners to components..
	        submit.addActionListener(this);
	        add(panel, BorderLayout.CENTER);
	        setTitle("Please Login Here !");
	        
	        
	        register.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					Register reg = new Register();
					reg.setSize(200,500);
					reg.setVisible(true);
					
					
				}
			});
	        
	        companyreg.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					CompanyRegister cRegister = new CompanyRegister();
					cRegister.setSize(605,300);
					cRegister.setVisible(true);
					
				}
			});
	        
	        cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					
				}
			});
		}
	    
	    public static void main(String[] args) {
	    	try
	    	   {
	    	   LogIn frame=new LogIn();
	    	   frame.setSize(200,500);
	    	   frame.setVisible(true);
	    	   //frame.setResizable(false);
	    	   }
	    	   catch(Exception e)
	    	   {JOptionPane.showMessageDialog(null, e.getMessage());}
	    }
	    
	    
	    @Override
	    public void actionPerformed(ActionEvent ae) {
	    	
	    	if(userName_text==null || password_text==null ||retailerid_text==null||retailername_text==null) {
	    		dispose();
	    		JOptionPane.showMessageDialog(this, "Fields can't be empty! ");
	    	}
	    	
	        String userName = userName_text.getText();
	        String password = password_text.getText();
	        String retailer_name = retailername_text.getText(), role =(String) javComboBox.getSelectedItem() ;
	        int retailer_id = Integer.parseInt(retailerid_text.getText());
	        
	        
	        try {
				from_db = employeeDao.searchEmployee(userName);
				psw_from_db = from_db.getPassword();
				rid_from_db = from_db.getRid();
				role_from_db = from_db.getRole();
				from_rdb = retailerDao.searchRetailer(retailer_id);
				retailername_from_db = from_rdb.getRname();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}catch(NullPointerException ne) {
				JOptionPane.showMessageDialog(this, "User does not exist !");
			}

	        if ( BCrypt.checkpw(password, psw_from_db) && retailer_name.trim().equals(retailername_from_db)  && retailer_id == rid_from_db && role.trim().equals(role_from_db) ) {

	        	JOptionPane.showMessageDialog(this, "Log in Successful!");
	        	TransactionPage frame;
				try {
					frame = new TransactionPage(retailer_name,role);
					frame.setSize(1000,1000);
			    	frame.setVisible(true);
			    	dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	      
	        } else {
	            JOptionPane.showMessageDialog(this, "Invalid User");
	        }
	
}
}
