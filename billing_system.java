import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import java.sql.*;
import javax.swing.table.*;

//Add to cart buttons 				- atc1,atc2,atc3,atc4,atc5,atc6
//Tab names 						- menu_pane, cart_pane, bill_pane
//Combo box current value 			- fruit_combo, veg_combo,groce_combo,cloth_combo,kit_combo,misc_item
//Spinners 							- fruit_spin, veg_spin, groce_spin, cloth_spin, kit_spin, misc_spin
//Price text fields					- fruit_cost, veg_cost, groce_cost, cloth_cost, kit_cost, misc_cost
//Category label 					- fruit_label, veg_label, groce_label, kit_label, misc_label
//Rupees label 						- rs1, rs2, rs3, rs4, rs5, rs6
//Miscellaneous item price 			- misc_price(Integer)
//Miscellaneous item quantity 		- misc_quan
//Spinner current integer value 	- spin_int[]
//Price text field integer value 	- price[]
//Price text field string value 	- price_str
//Spinner models 					- value_fruit, value_veg, value_grocery, value_cloth, value_kitchen, value_misc



public class billing_system implements ActionListener
{
	int []spin_int = new int[15];
	int []price = new int[15];
	static int sl_no = 0;
	JButton atc1,atc2,atc3,atc4,atc5,atc6,proceed, delete, next;
	static JTable table, bill_table;
	JPanel cart_pane;
	String fruit_combo, veg_combo,groce_combo,cloth_combo,kit_combo,misc_item,price_str;
	int misc_quan;
	static int misc_price;
	static String tab_sl, tab_item, tab_quan, tab_price;
	
	static Connection conn;
	static String userName;
	static String url;
	static String password;
	static Statement stmt;
	static ResultSet rs;
	static PreparedStatement pstmt;
	
	public void bill_ui() throws InterruptedException, SQLException
	{
		JFrame j = new JFrame("BILL");
		String fruits[] = {"Apple", "Banana", "Orange"};
		String vegetables[] = {"Bottle gourd","Onion", "Potato"};
		String clothes[] = {"Shirt", "Pant", "T-shirt"};
		String grocery[] = {"Bread", "Rice", "Wheat"};
		String kitchenware[] = {"Spoons", "Forks", "Plates"};
		JLabel menu_pane=new JLabel();
		cart_pane=new JPanel();  
    	JPanel bill_pane=new JPanel();    	
    	
    	JTabbedPane tp=new JTabbedPane(); 
    	tp.setBounds(0,0,500,500);  
    	tp.add("Menu",menu_pane);  
    	tp.add("Cart",cart_pane);  
    	tp.add("Bill",bill_pane);
    	
    	SpinnerModel value_fruit =  
                new SpinnerNumberModel(0, //initial value  
                   0, //minimum value  
                   10, //maximum value  
                   1); //step  
    	SpinnerModel value_veg =  
                new SpinnerNumberModel(0, //initial value  
                   0, //minimum value  
                   10, //maximum value  
                   1); //step  
    	SpinnerModel value_grocery =  
                new SpinnerNumberModel(0, //initial value  
                   0, //minimum value  
                   10, //maximum value  
                   1); //step  
    	SpinnerModel value_cloth =  
                new SpinnerNumberModel(0, //initial value  
                   0, //minimum value  
                   10, //maximum value  
                   1); //step  
    	SpinnerModel value_kitchen =  
    			new SpinnerNumberModel(0, //initial value  
    					0, //minimum value  
    					10, //maximum value  
    					1); //step  
    	SpinnerModel value_misc =  
    			new SpinnerNumberModel(0, //initial value  
    					0, //minimum value  
    					10, //maximum value  
    					1); //step  
    	
    	
    	JComboBox<String> catc1 = new JComboBox<>(fruits);
    	catc1.setBounds(50, 50, 100, 20);
    	menu_pane.add(catc1); //add combobox1 to menu panel
    	JLabel fruit_label=new JLabel("Fruits:");
    	menu_pane.add(fruit_label); // add fruits label to menu panel
    	fruit_label.setBounds(30, 30, 70, 20);
    	JLabel rs1 = new JLabel("Rs.");
    	rs1.setBounds(250, 50, 20, 20);
    	menu_pane.add(rs1);
    	//text field for fruits
    	JTextField fruit_cost = new JTextField();
    	fruit_cost.setBounds(270, 50, 50, 20);
    	menu_pane.add(fruit_cost);
    	//spinner for fruits
        JSpinner fruit_spin = new JSpinner(value_fruit);   
        fruit_spin.setBounds(170,50,50,20);    
        menu_pane.add(fruit_spin);
        //button
        atc1 = new JButton("Add to Cart");  
        atc1.setBounds(345, 50, 100, 20);  
        menu_pane.add(atc1);  
        
    	JComboBox<String> catc2 = new JComboBox<>(vegetables);
    	catc2.setBounds(50, 100+15, 100, 20);
    	menu_pane.add(catc2); //add combobox2 to menu panel
    	JLabel veg_label=new JLabel("Vegetables:");
    	menu_pane.add(veg_label); // add vegetables label to menu panel
    	veg_label.setBounds(30, 80+15, 70, 20);
    	JLabel rs2 = new JLabel("Rs.");
    	rs2.setBounds(250, 100+15, 20, 20);
    	menu_pane.add(rs2);
    	//text field for vegetables
    	JTextField veg_cost = new JTextField();
    	veg_cost.setBounds(270, 100+15, 50, 20);
    	menu_pane.add(veg_cost);
    	//spinner for vegetables
        JSpinner veg_spin = new JSpinner(value_veg);   
        veg_spin.setBounds(170,100+15,50,20);    
        menu_pane.add(veg_spin);
        //button
        atc2 = new JButton("Add to Cart");  
        atc2.setBounds(345, 100+15, 100, 20);  
        menu_pane.add(atc2);
        
    	JComboBox<String> catc3 = new JComboBox<>(grocery);
    	catc3.setBounds(50, 150+30, 100, 20);
    	menu_pane.add(catc3); //add combobox3 to menu panel
    	JLabel groce_label=new JLabel("Grocery:");
    	menu_pane.add(groce_label); // add grocery label to menu panel
    	groce_label.setBounds(30, 130+30, 70, 20);
    	JLabel rs3 = new JLabel("Rs.");
    	rs3.setBounds(250, 150+30, 20, 20);
    	menu_pane.add(rs3);
    	//text field for grocery
    	JTextField groce_cost = new JTextField();
    	groce_cost.setBounds(270, 150+30, 50, 20);
    	menu_pane.add(groce_cost);
    	 //spinner for grocery
        JSpinner groce_spin = new JSpinner(value_grocery);   
        groce_spin.setBounds(170,150+30,50,20);    
        menu_pane.add(groce_spin);
        //button
        atc3 = new JButton("Add to Cart");  
        atc3.setBounds(345, 150+30, 100, 20);  
        menu_pane.add(atc3);
    	
        JComboBox<String> catc4 = new JComboBox<>(clothes);
    	catc4.setBounds(50, 200+45, 100, 20);
    	menu_pane.add(catc4); //add combobox4 to menu panel
    	JLabel cloth_label=new JLabel("Clothes:");
    	menu_pane.add(cloth_label); // add clothes label to menu panel
    	cloth_label.setBounds(30, 180+45, 70, 20);
    	JLabel rs4 = new JLabel("Rs.");
    	rs4.setBounds(250, 200+45, 20, 20);
    	menu_pane.add(rs4);
    	//text field for clothes
    	JTextField cloth_cost = new JTextField();
    	cloth_cost.setBounds(270, 200+45, 50, 20);
    	menu_pane.add(cloth_cost);
    	 //spinner for clothes
        JSpinner cloth_spin = new JSpinner(value_cloth);   
        cloth_spin.setBounds(170,200+45,50,20);    
        menu_pane.add(cloth_spin);
        //button
        atc4 = new JButton("Add to Cart");  
        atc4.setBounds(345, 200+45, 100, 20);  
        menu_pane.add(atc4);
        
        JComboBox<String> catc5 = new JComboBox<>(kitchenware);
        catc5.setBounds(50, 250+60, 100, 20);
        menu_pane.add(catc5); //add combobox5 to menu panel
    	JLabel kit_label=new JLabel("Kitchenware:");
        menu_pane.add(kit_label); // add kitchen ware label to menu panel
        kit_label.setBounds(30, 230+60, 90, 20);
        JLabel rs5 = new JLabel("Rs.");
    	rs5.setBounds(250, 250+60, 20, 20);
    	menu_pane.add(rs5);
        //text field for kitchen ware
        JTextField kit_cost = new JTextField();
        kit_cost.setBounds(270, 250+60, 50, 20);
        menu_pane.add(kit_cost);
        //spinner for kitchen ware
        JSpinner kit_spin = new JSpinner(value_kitchen);   
        kit_spin.setBounds(170,250+60,50,20);    
        menu_pane.add(kit_spin);
        //button
        atc5 = new JButton("Add to Cart");  
        atc5.setBounds(345, 250+60, 100, 20);  
        menu_pane.add(atc5);
    	
        JTextField misc_prod = new JTextField();
        misc_prod.setBounds(50, 300+70, 100, 20);
        misc_prod.setEditable(true);
        menu_pane.add(misc_prod);
    	JLabel misc_label=new JLabel("Misc:");
        menu_pane.add(misc_label); // add misc label to menu panel
        misc_label.setBounds(30, 280+70, 90, 20);
        JLabel rs6 = new JLabel("Rs.");
    	rs6.setBounds(250, 300+70, 20, 20);
    	menu_pane.add(rs6);
        //text field for misc
        JTextField misc_cost = new JTextField("0");
        misc_cost.setEditable(true);
        misc_cost.setBounds(270, 300+70, 50, 20);
        menu_pane.add(misc_cost);
        //spinner for misc
        JSpinner misc_spin = new JSpinner(value_misc);   
        misc_spin.setBounds(170,300+70,50,20);    
        menu_pane.add(misc_spin);
        //button
        atc6 = new JButton("Add to Cart");  
        atc6.setBounds(345, 300+70, 100, 20);  
        menu_pane.add(atc6);
        
        next = new JButton("Next");
        next.setBounds(190,400,100,30);
        menu_pane.add(next);
        
        //cart-table
       	String column[] = {"Sl No." ,"Item Name", "Quantity", "Amount" };
		String data[][] = new String[20][4];
		DefaultTableModel model = new DefaultTableModel(data, column);
		table = new JTable(model);
		table.setEnabled(false);
		JScrollPane t1 = new JScrollPane(table);	
		t1.setPreferredSize(new Dimension(400, 345));
		//buttons for cart-table
		cart_pane.setLayout(new BorderLayout());
		proceed = new JButton("PROCEED");
		delete = new JButton("DELETE");
		proceed.setBounds(295,350,100,50);
		delete.setBounds(75,350,100,50);
		proceed.setBackground(Color.GREEN);
		delete.setBackground(Color.RED);
		cart_pane.add(proceed);
		cart_pane.add(delete);
		cart_pane.add(t1);
		
		//bill-table
		String bill_column[] = {"Sl No." ,"Item Name", "Quantity", "Amount" };
		String bill_data[][] = new String[20][4];
		DefaultTableModel bill_model = new DefaultTableModel(bill_data, bill_column);
		bill_table = new JTable(bill_model);
		bill_table.setEnabled(false);
		JScrollPane bill_t1 = new JScrollPane(bill_table);	
		bill_t1.setPreferredSize(new Dimension(400, 345));
		//buttons for cart-table
		bill_pane.setLayout(new BorderLayout());
		bill_pane.add(bill_t1);
		
		
    	j.add(tp);
    	j.setResizable(false);
		j.setVisible(true);
		j.setSize(500,500);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		atc1.addActionListener(this);  
       	atc2.addActionListener(this);
      	atc3.addActionListener(this);  
       	atc4.addActionListener(this);  
       	atc5.addActionListener(this); 
        atc6.addActionListener(this);     
        delete.addActionListener(this);
        proceed.addActionListener(this);
        next.addActionListener(this);
		for(;;)
        {
			
			fruit_combo = catc1.getSelectedItem().toString();
			veg_combo = catc2.getSelectedItem().toString();
			groce_combo = catc3.getSelectedItem().toString();
			cloth_combo = catc4.getSelectedItem().toString();
			kit_combo = catc5.getSelectedItem().toString();
			misc_item = misc_prod.getText();
			misc_quan = (Integer)misc_spin.getValue();
			String dummy = misc_cost.getText();
			misc_price = Integer.valueOf(dummy);
		
        	switch(fruit_combo)
        	{ 
	        	case "Apple" : spin_int[0] = (Integer)fruit_spin.getValue();
	        				   price[0] = (spin_int[0]* 50);
							   price_str = Integer.toString(spin_int[0]* 50);
							   fruit_cost.setText(price_str); break;
	        	case "Banana" :  spin_int[1] = (Integer)fruit_spin.getValue();
	        					price_str = Integer.toString(spin_int[1]* 15);
	        					price[1] = (spin_int[1]* 15);
	        					fruit_cost.setText(price_str); break;
	        	case "Orange" :  spin_int[2] = (Integer)fruit_spin.getValue();
				 				price_str = Integer.toString(spin_int[2]* 25);
				 				price[2] = (spin_int[2]* 25);
				 				fruit_cost.setText(price_str); break;
        	}
        	switch(veg_combo)
        	{ 
	        	case "Bottle gourd" : spin_int[3] = (Integer)veg_spin.getValue();
							   		  String price_str = Integer.toString(spin_int[3]* 30);
							   		  price[3] = (spin_int[3]* 30);
							   		  veg_cost.setText(price_str); break;
	        	case "Onion" :  spin_int[4] = (Integer)veg_spin.getValue();
	        					price_str = Integer.toString(spin_int[4]* 18);
	        					price[4] = (spin_int[4]* 18);
	        					veg_cost.setText(price_str); break;
	        	case "Potato" :  spin_int[5] = (Integer)veg_spin.getValue();
				 				price_str = Integer.toString(spin_int[5]* 22);
				 				price[5] = (spin_int[5]* 22);
				 				veg_cost.setText(price_str); break;
        	}
        	switch(groce_combo)
        	{ 
	        	case "Bread" : spin_int[6] = (Integer)groce_spin.getValue();
							   price_str = Integer.toString(spin_int[6]* 27);
							   price[6] = (spin_int[6]* 27);
							   groce_cost.setText(price_str); break;
	        	case "Rice" :  spin_int[7] = (Integer)groce_spin.getValue();
	        					price_str = Integer.toString(spin_int[7]* 120);
	        					price[7] = (spin_int[7]* 120);
	        					groce_cost.setText(price_str); break;
	        	case "Wheat" :  spin_int[8] = (Integer)groce_spin.getValue();
				 				price_str = Integer.toString(spin_int[8]* 70);
				 				price[8] = (spin_int[8]* 70);
				 				groce_cost.setText(price_str); break;
        	}
        	switch(cloth_combo)
        	{ 
	        	case "Shirt" : spin_int[9] = (Integer)cloth_spin.getValue();
							   price_str = Integer.toString(spin_int[9]* 400);
							   price[9] = (spin_int[9]* 400);
							   cloth_cost.setText(price_str); break;
	        	case "Pant" :  spin_int[10] = (Integer)cloth_spin.getValue();
	        					price_str = Integer.toString(spin_int[10]* 900);
	        					price[10] = (spin_int[10]* 900);
	        					cloth_cost.setText(price_str); break;
	        	case "T-shirt" :  spin_int[11] = (Integer)cloth_spin.getValue();
				 				price_str = Integer.toString(spin_int[11]* 250);
				 				price[11] = (spin_int[11]* 250);
				 				cloth_cost.setText(price_str); break;
        	}
        	switch(kit_combo)
        	{ 
	        	case "Spoons" : spin_int[12] = (Integer)kit_spin.getValue();
							    price_str = Integer.toString(spin_int[12]* 20);
							   price[12] = (spin_int[12]* 20);
							   kit_cost.setText(price_str); break;
	        	case "Forks" :  spin_int[13] = (Integer)kit_spin.getValue();
	        					price_str = Integer.toString(spin_int[13]* 20);
	        					price[13] = (spin_int[13]* 20);
	        					kit_cost.setText(price_str); break;
	        	case "Plates" :  spin_int[14] = (Integer)kit_spin.getValue();
				 				price_str = Integer.toString(spin_int[14]* 120);
				 				price[14] = (spin_int[14]* 120);
				 				kit_cost.setText(price_str); break;
        	}
        	
        	Thread.sleep(1000);
        	
        	
        }
	
	}			
	
	
	public static void main(String[] args)
	{
		billing_system bill = new billing_system();
		try
		{
			conn = null;
			userName = "root";
			password = "ORacl3MyS@l";
			url = "jdbc:mysql://localhost:3306/test";
			Class.forName ("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,userName,password);
			System.out.println("Database connection established");
			stmt = null;
			rs = null;
			pstmt = null;
			create_tables(stmt,conn);
			bill.bill_ui();
		
		}
		catch(Exception e)
		{
			System.out.println("cannot connect to database " + e);
		}
		finally
		{
			try 
			{
				conn.close();
			}
			catch(Exception err3)
			{
				System.out.println(err3.getMessage());
			}
		}
	}

		
	public static void create_tables(Statement stmt, Connection conn)
	{
		try 
		{
			stmt = conn.createStatement();
			stmt.execute("create table billtesting (Sl Integer, Item Varchar(50), Quantity Integer, Price Integer)");
			
		}
		catch(Exception err2)
		{
			System.out.println(err2.getMessage());
		}
	}

	
	public void actionPerformed(ActionEvent e)
	{
		String qry1;
		try 
		{	

			if(e.getSource() == atc1)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,fruit_combo);
				if(fruit_combo.equals("Apple"))
				{
					pstmt.setInt(3,spin_int[0]);
					pstmt.setInt(4,price[0]);
					pstmt.executeUpdate();
					System.out.println("query registered " + spin_int[0] + " "+ price[0]);
				}
				
				if(fruit_combo.equals("Banana"))
				{
					pstmt.setInt(3,spin_int[1]);
					pstmt.setInt(4,price[1]);
					pstmt.executeUpdate();
					System.out.println("query registered " + spin_int[1] + " "+ price[1]);
				}
				
				if(fruit_combo.equals("Orange"))
				{
					pstmt.setInt(3,spin_int[2]);
					pstmt.setInt(4,price[2]);
					pstmt.executeUpdate();
					System.out.println("query registered " + spin_int[2] + " "+ price[2]);
				}
			}
			if(e.getSource() == atc2)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,veg_combo);
				if(veg_combo.equals("Bottle gourd"))
				{
					pstmt.setInt(3,spin_int[3]);
					pstmt.setInt(4,price[3]);
					pstmt.executeUpdate();
					System.out.println("query registered");	
				}
				if(veg_combo.equals("Onion"))
				{
					pstmt.setInt(3,spin_int[4]);
					pstmt.setInt(4,price[4]);
					pstmt.executeUpdate();
					System.out.println("query registered");	
				}
				if(veg_combo.equals("Potato"))
				{
					pstmt.setInt(3,spin_int[5]);
					pstmt.setInt(4,price[5]);
					pstmt.executeUpdate();
					System.out.println("query registered");	
				}
			}
			if(e.getSource() == atc3)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,groce_combo);
				if(groce_combo.equals("Bread"))
				{
					pstmt.setInt(3,spin_int[6]);
					pstmt.setInt(4,price[6]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(groce_combo.equals("Rice"))
				{
					pstmt.setInt(3,spin_int[7]);
					pstmt.setInt(4,price[7]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(groce_combo.equals("Wheat"))
				{
					pstmt.setInt(3,spin_int[8]);
					pstmt.setInt(4,price[8]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
			}
			if(e.getSource() == atc4)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,cloth_combo);
				if(cloth_combo.equals("Shirt"))
				{	
					pstmt.setInt(3,spin_int[9]);
					pstmt.setInt(4,price[9]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(cloth_combo.equals("Pant"))
				{	
					pstmt.setInt(3,spin_int[10]);
					pstmt.setInt(4,price[10]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(cloth_combo.equals("T-shirt"))
				{	
					pstmt.setInt(3,spin_int[11]);
					pstmt.setInt(4,price[11]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
			}
			if(e.getSource() == atc5)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,kit_combo);
				if(kit_combo.equals("Spoons"))
				{
					pstmt.setInt(3,spin_int[12]);
					pstmt.setInt(4,price[12]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(kit_combo.equals("Forks"))
				{
					pstmt.setInt(3,spin_int[13]);
					pstmt.setInt(4,price[13]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
				if(kit_combo.equals("Plates"))
				{
					pstmt.setInt(3,spin_int[14]);
					pstmt.setInt(4,price[14]);
					pstmt.executeUpdate();
					System.out.println("query registered");
				}
			}

			if(e.getSource() == atc6)
			{
				sl_no++;
				qry1 = "insert into billtesting(Sl,Item,Quantity,Price) values(?,?,?,?)";
				pstmt = conn.prepareStatement(qry1);
				pstmt.setInt(1,sl_no);
				pstmt.setString(2,misc_item);
				pstmt.setInt(3, misc_quan);
				pstmt.setInt(4,misc_price);
				pstmt.executeUpdate();
				System.out.println("query registered");
			}
			
			if(e.getSource() == delete)
			{
				String delete_ele=JOptionPane.showInputDialog(cart_pane,"Enter the Item number to delete:","Alert",JOptionPane.WARNING_MESSAGE);
				stmt = conn.createStatement();
				stmt.execute("DELETE FROM billtesting WHERE Sl="+delete_ele);
				stmt.execute("SELECT * FROM billtesting");
				rs = stmt.getResultSet();
				int k=0;
				while(rs.next()) 
	    		{
	    				tab_sl = rs.getString("Sl");
	        			tab_item = rs.getString("Item");
	        			tab_quan = rs.getString("Quantity");
	        			tab_price = rs.getString("Price");
	        			System.out.println(tab_sl+"\t"+tab_item+"\t"+tab_quan+"\t"+tab_price);
	    				table.setValueAt(tab_sl, k, 0);
	    				table.setValueAt(tab_item, k, 1);
	    				table.setValueAt(tab_quan, k, 2);
	    				table.setValueAt(tab_price, k, 3);
	    				k++;
	    		}
				table.setValueAt("", k, 0);
				table.setValueAt("", k, 1);
				table.setValueAt("", k, 2);
				table.setValueAt("", k, 3);
			}
			
			if(e.getSource() == next)
			{
				stmt = conn.createStatement();
				stmt.execute("SELECT * FROM billtesting");
				rs = stmt.getResultSet();
				int k=0;
				while(rs.next()) 
	    		{
	    				tab_sl = rs.getString("Sl");
	        			tab_item = rs.getString("Item");
	        			tab_quan = rs.getString("Quantity");
	        			tab_price = rs.getString("Price");
	        			System.out.println(tab_sl+"\t"+tab_item+"\t"+tab_quan+"\t"+tab_price);
	    				table.setValueAt(tab_sl, k, 0);
	    				table.setValueAt(tab_item, k, 1);
	    				table.setValueAt(tab_quan, k, 2);
	    				table.setValueAt(tab_price, k, 3);
	    				k++;
	    		}
			}
			
			if(e.getSource() == proceed)
			{
				int total_bill=0;
				double gst=0,gst_price=0;
				stmt = conn.createStatement();
				stmt.execute("SELECT * FROM billtesting");
				rs = stmt.getResultSet();
				int k=0;
				while(rs.next()) 
	    		{
	    				tab_sl = rs.getString("Sl");
	        			tab_item = rs.getString("Item");
	        			tab_quan = rs.getString("Quantity");
	        			tab_price = rs.getString("Price");
	        			total_bill = total_bill + Integer.parseInt(tab_price);
	        			gst = total_bill*0.18;
	        			gst_price = total_bill + gst;
	        			System.out.println(tab_sl+"\t"+tab_item+"\t"+tab_quan+"\t"+tab_price);
	    				bill_table.setValueAt(tab_sl, k, 0);
	    				bill_table.setValueAt(tab_item, k, 1);
	    				bill_table.setValueAt(tab_quan, k, 2);
	    				bill_table.setValueAt(tab_price, k, 3);
	    				k++;
	    		}
				bill_table.setValueAt("Total Bill", 17, 2);
				bill_table.setValueAt(total_bill, 17, 3);
				bill_table.setValueAt("GST (18%)", 18, 2);
				bill_table.setValueAt(gst, 18, 3);
				bill_table.setValueAt("Final Bill", 19, 2);
				bill_table.setValueAt(gst_price, 19, 3);
				
				
			}



		}
		catch(Exception err)
		{
			System.out.println(err.getMessage());
		}

	}

}