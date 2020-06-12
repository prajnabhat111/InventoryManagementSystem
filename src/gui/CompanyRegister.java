package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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

public class CompanyRegister extends JFrame implements ActionListener {
	private static int workload = 12;
	
	JPanel panel;
    JLabel rnameJLabel,logoJLabel,addressJLabel,passwordJLabel,confirmpswJLabel;
    JTextField rnameJTextField,logoJTextField,addressJTextField;
    JPasswordField password_text,confirm_psw_text;
    JButton cancel,register;
    private String companylogopath =null;
    private RetailerDao retailerDao = new RetailerDao();
    private Retailer_repo retailer_repo;
    boolean from_rdb;
    boolean from_db;
    
    public CompanyRegister() {
    	//rname
    	rnameJLabel = new JLabel();
    	rnameJLabel.setText("Company Name :");
    	rnameJTextField = new JTextField(15);
    	
    	//logo
    	logoJLabel =new JLabel();
    	logoJLabel.setText("Logo :");
    	logoJTextField =new JTextField(15);
    	
    	//address
    	addressJLabel = new JLabel();
    	addressJLabel.setText("Address :");
    	addressJTextField = new JTextField(15);
    	
    	//password
    	passwordJLabel=new JLabel();
    	passwordJLabel.setText("Password :");
    	password_text = new JPasswordField(15);
    	
    	//confirm password
    	confirmpswJLabel = new JLabel();
    	confirmpswJLabel.setText("Confirm Password :");
    	confirm_psw_text = new JPasswordField(15);
    	
    	//Cancel
        cancel = new JButton("CANCEL");
        //Register
        register = new JButton("REGISTER");
        
    	
    	//Browse
    	JButton browse = new JButton("Browser");
		
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileDialog = new JFileChooser();
				int returnVal = fileDialog.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					java.io.File file = fileDialog.getSelectedFile();
					companylogopath = file.getAbsolutePath();
					logoJTextField.setText(companylogopath);
					 
				} else {
					JOptionPane.showMessageDialog(null, "Select Company Logo [ format - .jpg or .png only]");
				}
			}
		});
		
		
		
		 panel = new JPanel();
		 panel.add(rnameJLabel);
		 panel.add(rnameJTextField);
		 panel.add(addressJLabel);
		 panel.add(addressJTextField);
		 panel.add(logoJLabel);
		 panel.add(logoJTextField);
		 panel.add(browse);
		 panel.add(passwordJLabel);
		 panel.add(password_text);
		 panel.add(confirmpswJLabel);
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
    	   CompanyRegister frame=new CompanyRegister();
    	   frame.setSize(605,300);
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
    	
    	
    	String userName = rnameJTextField.getText();
        String password = password_text.getText();
        String logo =logoJTextField.getText(), address = addressJTextField.getText(), confirm_psw = confirm_psw_text.getText() ;
        

        if ( password!=null && address!=null && confirm_psw!=null && userName!=null) {
        	
        if(password.equals(confirm_psw))	{
        	try {
				from_rdb = retailerDao.isCompanyNameExists(userName);
				
					if(!from_rdb) {
					password = hashPassword(password);
					System.out.println("logo ="+logoJTextField.getText());
					if(logoJTextField.getText().equals("")) {
						System.out.println("inside if");
						logo = "defaultlogo.png";
					}
					System.out.println("logo ="+logo);
					retailer_repo = new Retailer_repo(userName, logo, address, password);
					retailerDao.addCompany(retailer_repo);
					int rid = retailerDao.getCompanyId(userName);
					String msg = rid + " is your retailer id! You will need this for further logins.";
					int response = JOptionPane.showConfirmDialog(this,msg,"Confirmation",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
					if(response==JOptionPane.OK_CANCEL_OPTION) {
						String finalmsg = rid+ " This cannot be reteived later!";
						JOptionPane.showMessageDialog(this, finalmsg);
					}
					
					JOptionPane.showMessageDialog(this, "yaaaayy!!! Registration successful!");	
					LogIn logIn = new LogIn();
					logIn.setSize(200, 500);
					logIn.setVisible(true);
					dispose();
					}
					else {
						JOptionPane.showMessageDialog(this, "Company already exist !");
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
    public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return(hashed_password);
	}
    
}
