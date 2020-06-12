package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Dao.ProductDao;
import Dao.SalesDao;
import config.GenrateTagNum;
import repositories.Product_repo;
import repositories.Sales_repo;

public class AddNewProduct extends JFrame{
	

	private static final long serialVersionUID = 1L;
	JLabel heading,pname,pbrand,pcp,psp,ptag,number;
	JTextField tag;
	JTextField name,brand,cp,sp,num;
	JPanel panel,contentPane;
	private ProductDao productDao;
	private Product_repo previousProduct;
	private boolean updateMode,initial;
	JButton generate,add,cancel;
	private TransactionPage transactionPage;
	private Sales_repo sales_repo;
	private SalesDao salesDao = new SalesDao();
	int rid;
	double investment=0.00,sale=0.00;
	
	public AddNewProduct(ProductDao productDao,TransactionPage transactionPage, Product_repo previousProduct, boolean updatemode,int rid, boolean initial) {
		
		this();
		this.productDao = productDao;
		this.transactionPage = transactionPage;
		this.previousProduct = previousProduct;
		this.updateMode = updatemode;
		this.rid = rid;
		this.initial=initial;

		if(updatemode) {
			heading.setText("Update Product Deatils");
			panel.add(heading);
			populateGui(previousProduct);
		}else {
			heading.setText("Add New Item Details");
			panel.add(heading);
		}
		
	}
	public AddNewProduct(ProductDao productdao, TransactionPage transactionPage,int rid,boolean initial) {
		this(productdao, transactionPage, null, false,rid,initial);
	}

	private void populateGui(Product_repo previousProduct) {
		name.setText(previousProduct.getName());
		brand.setText(previousProduct.getCompany());
		cp.setText(previousProduct.getCp() + "");
		sp.setText(previousProduct.getSp() + "");
		tag.setText(previousProduct.getTag());
		num.setText(previousProduct.getStatus()+ "");
		
		if(updateMode) {
			setTextFields();
		}
	}
	
	public AddNewProduct() {
		setTitle("Item Details");
		setBounds(100, 100, 450, 448);
		
		panel = new JPanel();
		heading = new JLabel();
		
		pname = new JLabel("Product Name :");		
		name = new JTextField(15);
		
		pbrand = new JLabel("Product Brand :");		
		brand = new JTextField(15);
		
		pcp = new JLabel("Cost Price :");
		cp = new JTextField(15);
		
		psp = new JLabel("Selling Price :");
		sp = new JTextField(15);
		
		ptag = new JLabel("Tag Number :");
		tag = new JTextField(15);
		
		number = new JLabel("Number of Items :");
		num = new JTextField(15);
		
		generate = new JButton("Generate");
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		
		
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GenrateTagNum genrateTagNum = new GenrateTagNum();
				String textString = genrateTagNum.getTagNumber();
				tag.setText(textString);
			}
		});
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addProductDetailstoDB();
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GroupLayout groupLayout = new GroupLayout(contentPane);
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(heading)
						.addComponent(pname)
						.addComponent(pbrand)
						.addComponent(ptag)
						.addComponent(pcp)
						.addComponent(psp)
						.addComponent(number)
						)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(name)
						.addComponent(brand)
						.addComponent(tag)
						.addComponent(cp)
						.addComponent(sp)
						.addComponent(num)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(add)
								.addComponent(cancel)))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(generate)));
		
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addComponent(heading)
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pname)
						.addComponent(name))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pbrand)
						.addComponent(brand))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(ptag)
						.addComponent(tag)
						.addComponent(generate))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pcp)
						.addComponent(cp))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(psp)
						.addComponent(sp))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(number)
						.addComponent(num))
				.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(add)
						.addComponent(cancel)));
	
		
	}
	protected void addProductDetailstoDB() {
		String productName = name.getText();
		String productTagNum = tag.getText();
		String productBrand = brand.getText();
		double cprice = Double.parseDouble(cp.getText());
		double sprice = Double.parseDouble(sp.getText());
		int status = Integer.parseInt(num.getText());

		Product_repo tmpProduct = null;
		if (updateMode) {
			tmpProduct = previousProduct;
			tmpProduct.setName(productName);
			tmpProduct.setCompany(productBrand);
			tmpProduct.setTag(productTagNum);
			tmpProduct.setCp(cprice);
			tmpProduct.setSp(sprice);
			tmpProduct.setStatus(status);

		} else {
			tmpProduct = new Product_repo(productTagNum, productName, productBrand, cprice, sprice, status,rid);
		}
		try {
			// save to db
			System.out.println(updateMode);
			if (updateMode) {
				int stat = productDao.getOldStatus(rid);
				double inv = cprice * stat;
				investment = salesDao.getinvestment(rid);
				investment = investment + (Double.parseDouble(cp.getText())*Double.parseDouble(num.getText())) - inv;
				sale = salesDao.getSales(rid); 
				sales_repo = new Sales_repo(rid,investment, sale);
				salesDao.updateSales(sales_repo);
				
				
				productDao.UpdateProduct(tmpProduct);
			} else {
				productDao.addProduct(tmpProduct);
			
				if(initial) {
					investment = productDao.initialInvest(rid);
					sales_repo = new Sales_repo(rid,investment, sale);
					salesDao.addSales(sales_repo);
				}else {
					investment = salesDao.getinvestment(rid);
					investment+= (Double.parseDouble(cp.getText())*Double.parseDouble(num.getText()));
					sale = salesDao.getSales(rid); 
					sales_repo = new Sales_repo(rid,investment, sale);
					salesDao.updateSales(sales_repo);
				}
			}
			// closing dialog box
			setVisible(false);
			transactionPage.refreshProductView();
			dispose();
			transactionPage.refreshProductView();
			// show saved message
			JOptionPane.showMessageDialog(transactionPage, "Product added Successfully.",
					"New Product Added", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(transactionPage, "Error Saving Product Details.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setTextFields() {
		name.setEditable(false);
		brand.setEditable(false);
		cp.setEditable(false);
		sp.setEditable(false);
		tag.setEditable(false);
		generate.setEnabled(false);
	}
		
	

}
