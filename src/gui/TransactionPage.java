package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import Dao.ProductDao;
import Dao.RetailerDao;
import Dao.SalesDao;
import repositories.ProductTableModel;
import repositories.Product_repo;
import repositories.Sales_repo;

public class TransactionPage extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	JFrame framey;
	JLabel welcome, product_nameJLabel,totJLabel,avaiLabel,soldJLabel,ivestJLabel,salesJLabel,profitJLabel,tot,avail,sold,invest,sales,profit,Report,itemJLabel,item,img;
	JTextField product_nameTextField;
	String s1;
	JButton search,sell,update,add,delete;
	private ProductDao productdao = new ProductDao();
	private RetailerDao retailerDao = new RetailerDao();
	private SalesDao salesDao = new SalesDao();
	private Sales_repo sales_repo;
	JPanel contentPane;
	JScrollPane scrollPane;
	JTable table;
	String rname;
	int rid;
	boolean initial= false;
	ArrayList<Product_repo> items = new ArrayList<>();
	double saless,invv;
	ImageIcon imgIcon;
	

	public TransactionPage(String rname, String role) throws Exception{
		
		this.rname = rname;
		int rid = retailerDao.getCompanyId(rname);
		
		framey = new JFrame();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);
		
		s1 = "Welcome to " + rname + " !";
		welcome = new JLabel(s1,SwingConstants.CENTER);
		
		Report = new JLabel();
		Report.setText("Report");
		Report.setForeground(new Color(160, 82, 45));
		Report.setFont(new Font("Tahoma", Font.BOLD, 14));

		
		product_nameJLabel = new JLabel();
		product_nameJLabel.setText("Enter product name to search");
		product_nameTextField = new JTextField(15);
		
		//labels
		totJLabel = new JLabel();
		totJLabel.setText("Total Products :");
		totJLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		totJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		avaiLabel = new JLabel();
		avaiLabel.setText("Available Products :");
		avaiLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		avaiLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		soldJLabel= new JLabel();
		soldJLabel.setText("Sold Products :");
		soldJLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		soldJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		salesJLabel = new JLabel();
		salesJLabel.setText("Total Sales :");
		salesJLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		salesJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		ivestJLabel = new JLabel();
		ivestJLabel.setText("Total Investment :");
		ivestJLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		ivestJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
	
		
		itemJLabel = new JLabel();
		itemJLabel.setText("Total items Available :");
		itemJLabel.setForeground(UIManager.getColor("ToggleButton.darkShadow"));
		itemJLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		//Logo
		String path = retailerDao.getLogoPath(rid);
		imgIcon = new ImageIcon(path);
		int width = 200;
        int height = 200;
        Image scaled = scaleImage(imgIcon.getImage(), width, height);
        ImageIcon scaledIcon = new ImageIcon(scaled);
		img = new JLabel(scaledIcon);
		
		//values
		String soldItemNum = productdao.getSoldItems(rid);
		sold = new JLabel(soldItemNum);
		sold.setBackground(new Color(255, 240, 245));
		sold.setForeground(new Color(30, 144, 255));
		sold.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		
		String available = productdao.getAvaiItems(rid);
		avail = new JLabel(available);
		avail.setBackground(new Color(255, 240, 245));
		avail.setForeground(new Color(30, 144, 255));
		avail.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		
		String total = productdao.getTotalProducts(rid);
		tot = new JLabel(total);
		tot.setBackground(new Color(255, 240, 245));
		tot.setForeground(new Color(30, 144, 255));
		tot.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		String investment = productdao.getTotalInvest(rid);
		invest = new JLabel(investment);
		invest.setBackground(new Color(255, 240, 245));
		invest.setForeground(new Color(30, 144, 255));
		invest.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		
		String sale = productdao.getTotalSell(rid);
		sales = new JLabel(sale);
		sales.setBackground(new Color(255, 240, 245));
		sales.setForeground(new Color(30, 144, 255));
		sales.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		String itm = productdao.getTotalItems(rid);
		item = new JLabel(itm);
		item.setBackground(new Color(255,240,245));
		item.setForeground(new Color(30, 144, 255));
		item.setFont(new Font("Segoe Script", Font.BOLD, 14));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();

		table = new JTable();
		scrollPane.setBounds(10, 20, 100, 100);
		scrollPane.setViewportView(table);
		
		search = new JButton("Search");
		
		
		sell = new JButton("Sell");
		sell.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(TransactionPage.this, "You must select a Product.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				int response = 	JOptionPane.showConfirmDialog(TransactionPage.this,"Sell this products?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				
				if(response != JOptionPane.YES_OPTION){
					return;
				}
				
				Product_repo tmpProduct = (Product_repo) table.getValueAt(row, ProductTableModel.OBJECT_COL);
				
				try {
					
					productdao.soldItem(tmpProduct.getId(),tmpProduct.getStatus());
					saless = salesDao.getSales(rid);
					saless += tmpProduct.getCp();
					invv = salesDao.getinvestment(rid);
					sales_repo = new Sales_repo(rid,invv, saless);
					salesDao.updateSales(sales_repo);
					refreshProductView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// get selected row
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(TransactionPage.this, "You must select a Product.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Product_repo tmpProduct = (Product_repo) table.getValueAt(row, ProductTableModel.OBJECT_COL);
				// System.out.println(tmpProduct);
				AddNewProduct dialog = new AddNewProduct(productdao, TransactionPage.this, tmpProduct, true,rid,false);

				dialog.setVisible(true);
				
			}
		});
		
		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getRowCount()==0) {
					initial = true;
				}
				
				AddNewProduct addNewProduct = new AddNewProduct(productdao, TransactionPage.this,rid,initial);
				addNewProduct.setVisible(true);
				
			}
		});
		
		delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//get selected row
				int row = table.getSelectedRow();

				if (row < 0) {
					JOptionPane.showMessageDialog(TransactionPage.this, "You must select a Product.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int response = 	JOptionPane.showConfirmDialog(TransactionPage.this,"Delete this products?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				
				if(response != JOptionPane.YES_OPTION){
					return;
				}
				
				//get selected product object
				Product_repo tmpProduct = (Product_repo) table.getValueAt(row, ProductTableModel.OBJECT_COL);

				try {
					productdao.deleteItem(tmpProduct.getId());
					refreshProductView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
	
		
		
		if(role == "Admin") {
			AdminPage(rid);
		}else {
			EmpPage(rid);
		}
		
		
		
		
		
		
	}
	
	public void AdminPage(int rid) throws Exception {
		
		items = productdao.getAllProduct(rid);
		setAdminTable(items);
		
		search.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (product_nameTextField != null && product_nameTextField.getText().trim().length() > 0) {
						items = productdao.searchProduct(product_nameTextField.getText());
					}else {
						items = productdao.getAllProduct(rid);
					}
					setAdminTable(items);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}	
			
		});
		
		GroupLayout groupLayout = new GroupLayout(contentPane);
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(product_nameJLabel)
										.addComponent(product_nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(search))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sell)))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(welcome)
						.addComponent(img)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(delete)
										)
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(update))
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(add))))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(Report)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(totJLabel)
												.addComponent(tot))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(avaiLabel)
												.addComponent(avail))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(soldJLabel)
												.addComponent(sold))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(itemJLabel)
												.addComponent(item))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(ivestJLabel)
												.addComponent(invest))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(salesJLabel)
												.addComponent(sales))
										)))
				);
		
		   groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						   .addComponent(welcome))
				   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						   .addGroup(groupLayout.createSequentialGroup()
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										   .addComponent(product_nameJLabel)
										   .addComponent(product_nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										   .addComponent(search)
										   .addComponent(Report))
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										   .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										   .addComponent(img)
										   .addGroup(groupLayout.createSequentialGroup()
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														 
																   .addComponent(totJLabel)
																   )
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														  
																   .addComponent(avaiLabel)
																  )
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   
																   .addComponent(soldJLabel)
																   )
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														  
																   .addComponent(itemJLabel)
																   )
													.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
															.addComponent(ivestJLabel)
															)
													.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
															.addComponent(salesJLabel))
												   
												   )
										   .addGroup(groupLayout.createSequentialGroup()
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(tot))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(avail))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(sold))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(item))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(invest))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(sales))
												   ))
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										  .addComponent(sell)
										  .addComponent(delete)
										  .addComponent(update)
										  .addComponent(add))
								   )));
		
	}
	
	public void EmpPage(int rid) throws Exception {
		
		items = productdao.getAllProduct(rid);
		setEmpTable(items);
		
		search.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Product_repo> items = new ArrayList<>();
					if (product_nameTextField != null && product_nameTextField.getText().trim().length() > 0) {
						items = productdao.searchProduct(product_nameTextField.getText());
					}else {
						items = productdao.getAllProduct(rid);
					}
					setEmpTable(items);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}	
			
		});
		
		GroupLayout groupLayout = new GroupLayout(contentPane);
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(product_nameJLabel)
										.addComponent(product_nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(search))
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sell)))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(welcome)
						.addComponent(img))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(Report)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(totJLabel)
												.addComponent(tot))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(avaiLabel)
												.addComponent(avail))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(soldJLabel)
												.addComponent(sold))
										)))
				);
		
		   groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						   .addComponent(welcome))
				   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						   .addGroup(groupLayout.createSequentialGroup()
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										   .addComponent(product_nameJLabel)
										   .addComponent(product_nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										   .addComponent(search)
										   .addComponent(Report))
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										   .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										   .addComponent(img)
										   .addGroup(groupLayout.createSequentialGroup()
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														 
																   .addComponent(totJLabel)
																   )
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														  
																   .addComponent(avaiLabel)
																  )
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   
																   .addComponent(soldJLabel)
																   )
												   )
										   .addGroup(groupLayout.createSequentialGroup()
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(tot))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(avail))
												   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														   .addComponent(sold))))
								   .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										  .addComponent(sell)
										  ))));
		
	}
	
	
	 public static void main(String[] args) {
	    	try
	    	   {
	    	   TransactionPage frame=new TransactionPage("Praj","Admin");
	    	   frame.setSize(1000,1000);
	    	   frame.setVisible(true);
	    	   //frame.setResizable(false);
	    	   }
	    	   catch(Exception e)
	    	   {
	    		   e.printStackTrace();
	    		   }
	    }
	 public void refreshProductView() {
			try {
				ArrayList<Product_repo> list = productdao.getAllProduct(rid);
				ProductTableModel mode = new ProductTableModel(list);
				table.setModel(mode);
				updateReport();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error:" + e, "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	 private void updateReport() throws SQLException {
		    rid = retailerDao.getCompanyId(rname); 
			tot.setText(productdao.getTotalProducts(rid));
			avail.setText(productdao.getAvaiItems(rid));
			sold.setText(productdao.getSoldItems(rid));
			invest.setText(productdao.getTotalInvest(rid));
			sales.setText(productdao.getTotalSell(rid));
			item.setText(productdao.getTotalItems(rid));
			
		}
	 
	 private void setEmpTable(ArrayList<Product_repo> items) {
		 ProductTableModel model = new ProductTableModel(items);
			table.setModel(model);
			TableColumn tcol = table.getColumnModel().getColumn(3);
			table.removeColumn(tcol);
			tcol= table.getColumnModel().getColumn(4);
			table.removeColumn(tcol);
			scrollPane.setViewportView(table);
	 }
	 private void setAdminTable(ArrayList<Product_repo> items) {
		    ProductTableModel model = new ProductTableModel(items);
			table.setModel(model);
			scrollPane.setViewportView(table);
	 }
	 private Image scaleImage(Image image, int w, int h) {

	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);

	        return scaled;
	    }

}
