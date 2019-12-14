import java.util.Scanner;
import java.util.*;

/* This is a small scale e-commerce application allowing merchants to sell unique 
 * items with different offers and customer can buy items easily
 * Customer can also maintain a cart for buying items
 * Rewards are awarded to merchants and customers on conditions defined by company
 * As on now, 5 merchants and 5 customers are pre-defined in the application 
 * 
 */



/*
 * LogIn Interface simplifies the code as both Merchant and customer
 * class implements this display and defines its methods.
 */
interface LogIn
{
	public void enter(Main_Menu menu);
	public void show_menu(LogIn u,Main_Menu menu);
}

/*
 * Display Interface simplifies the code as both Merchant and customer
 * class implements this display and defines its methods.
 */
interface Display
{
	public void print_details(int data,Main_Menu menu);
}

/*
 * Item class creates an object of type Item having all attributes an 
 * Item can possess like name, price, quantity with merchant etc 
 */
class Item
{
	int Unique_id=1;
	int Item_Id;
	String Item_name;
	double Item_price;
	int Item_quantity;
	String Item_category;
	String Item_offer;
	Merchant Item_merchant;
	
	Item(String iname, double iprice, int iqnt, String cat,Merchant mnt,Main_Menu mmenu)
	{
		Item_merchant=mnt;
		Item_name=iname;
		Item_price=iprice;
		Item_quantity=iqnt;
		Item_category=cat;
		Item_offer="None";
		Item_Id=Unique_id;
		Unique_id++;
	}
	
}

class Customer_Item
{
	int CItem_quantity;
	Item CItem_MainItem;
	double CItem_Pay;
	Customer_Item(Item cmi, int ciq)
	{
		CItem_MainItem = cmi;
		CItem_quantity = ciq;
	}
	
}

/*
 * User class have generic methods defined which can be used
 * by both Merchants and Customers ( Polymorphism!) as instance of such objects 
 * can be passed in the method arguments
 */
class User
{
	public User()
	{
		
	}
	
	public void User_logIn(LogIn in,Main_Menu mnm)
	{
		in.enter(mnm);
	}
	public void User_show(LogIn d,Main_Menu mnm)
	{
		d.show_menu(d,mnm);
	}
	
	public void User_display(Display y,int val,Main_Menu mu)
	{
		y.print_details(val,mu);
	}
	
}

/*
 * Merchant class implements Interfaces LogIn and Display allowing the user to execute the 
 * generic methods with merchant object also.
 */
class Merchant implements LogIn,Display
{
	String merchant_name;
	int merchant_id;
	double company_contribution;
	int merchant_reward;
	double merchant_reward_num;
	int how_many_unique;
	int merchant_uniqueitem;
	String merchant_address;
	
	ArrayList<Item> All_Items = new ArrayList<Item>();
			
	
	Merchant() {}
	Merchant(String n,int id,String ggg)
	{
		merchant_address=ggg;
		merchant_name=n;
		merchant_id=id;
		merchant_reward=0;
		how_many_unique=0;
		merchant_uniqueitem=10;
		merchant_reward_num=0;
	}
	
	
	@Override
	public void print_details(int which_id,Main_Menu mm)
	{

		System.out.println(mm.All_Merchants[which_id-1].merchant_name+" "+mm.All_Merchants[which_id-1].merchant_address+" "+mm.All_Merchants[which_id-1].company_contribution);
	}
	public void enter(Main_Menu mu)
	{
		System.out.println("Choose Merchant");
		for(int i=0;i<mu.All_Merchants.length;i++)
		{
			Merchant now = mu.All_Merchants[i];
			System.out.println(now.merchant_id+" "+now.merchant_name);
		}
		
		Scanner t = new Scanner(System.in);
		int next = t.nextInt();
		mu.All_Merchants[next-1].show_menu(mu.All_Merchants[next-1],mu);
	}

	@Override
	public void show_menu(LogIn u,Main_Menu mmenu1)
	{
		
	}
	
	public void show_menu(Merchant merch,Main_Menu mmenu)
	{
		System.out.println("Welcome "+merch.merchant_name);
		System.out.println("Merchant Menu");
		System.out.println("1) Add Item");
		System.out.println("2) Edit Item");
		System.out.println("3) Search by category");
		System.out.println("4) Add offer");
		System.out.println("5) Rewards won");
		System.out.println("6) Exit");
		Scanner y = new Scanner(System.in);
		int quer = y.nextInt();
		
		if(quer==1)
		{
			if(merch.how_many_unique<merch.merchant_uniqueitem)
			{
				merch.how_many_unique++;
				merch.AddItem(merch,mmenu);
			}
			else
			{
				System.out.println("Maximum Items limit reached");
			}
		}
		else if(quer==2)
		{
			System.out.println("Chose Item By Code");
			for(int j=0;j<merch.All_Items.size();j++)
			{
				Item ad = All_Items.get(j);
				System.out.println((j+1)+" "+ad.Item_name+" "+ad.Item_price+" "+ad.Item_quantity+" "+ad.Item_offer+" "+ad.Item_category);
			}
			Scanner v = new Scanner(System.in);
			int item_num = v.nextInt();
			
			Item edit = merch.All_Items.get(item_num-1);
			merch.EditItem(edit,item_num,merch,mmenu);
			
		}
		else if(quer==3)
		{
			merch.SearchByCategory(merch,mmenu);
		}
		else if(quer==4)
		{
			System.out.println("Chose Item By Code");
			for(int j=0;j<merch.All_Items.size();j++)
			{
				Item ad = All_Items.get(j);
				System.out.println((j+1)+" "+ad.Item_name+" "+ad.Item_price+" "+ad.Item_quantity+" "+ad.Item_offer+" "+ad.Item_category);
			}
			Scanner v1 = new Scanner(System.in);
			int item_num1 = v1.nextInt();
			
			Item edit1 = merch.All_Items.get(item_num1-1);
			merch.AddOffer(edit1,item_num1,merch,mmenu);
		}
		else if(quer==5)
		{
			System.out.println(merch.merchant_reward);
			merch.show_menu(merch, mmenu);
		}
		else if(quer==6)
		{
			mmenu.Index(mmenu);
		}
		else
		{
			System.out.println("Invalid Query");
			merch.show_menu(merch, mmenu);
			
		}
	}
	
	void AddOffer(Item it,int itnum,Merchant mt,Main_Menu mmenu)
	{
		System.out.println("Choose Offer");
		System.out.println("1) Buy one get one");
		System.out.println("2) 25% off");
		Scanner o = new Scanner(System.in);
		int onum = o.nextInt();
		if(onum==1)
		{
			it.Item_offer="Buy one get one";
		}
		else if(onum==2)
		{
			it.Item_offer="25% off";
		}
		
		System.out.println(itnum+" "+it.Item_name+" "+it.Item_price+" "+it.Item_quantity+" "+it.Item_offer+" "+it.Item_category);
		
		mt.show_menu(mt,mmenu);
		
	}
	void SearchByCategory(Merchant mn,Main_Menu mmenu)
	{
		System.out.println("Choose A Catgeory");
		for(int u=0;u<mmenu.Category.size();u++)
		{
			System.out.println((u+1)+") "+mmenu.Category.get(u));
		}
		Scanner c = new Scanner(System.in);
		int cat_num = c.nextInt();
		String search = mmenu.Category.get(cat_num-1);
		for(int b=0;b<mmenu.Every_Item.size();b++)
		{
			Item am = mmenu.Every_Item.get(b);
			if((am.Item_category).equals(search))
			{
				System.out.println((b+1)+" "+am.Item_name+" "+am.Item_price+" "+am.Item_quantity+" "+am.Item_offer+" "+am.Item_category);
			}
		}
		
		mn.show_menu(mn,mmenu);
	}
	
	void EditItem(Item im,int index,Merchant mr,Main_Menu mmenu)
	{
		Scanner z = new Scanner(System.in);
		System.out.println("Enter Edit Details");
		System.out.println("Enter Price");
		im.Item_price = z.nextDouble();
		
		System.out.println("Enter Quantity");
		im.Item_quantity = z.nextInt();
		System.out.println(index+" "+im.Item_name+" "+im.Item_price+" "+im.Item_quantity+" "+im.Item_offer+" "+im.Item_category);
		
		mr.show_menu(mr,mmenu);
		
	}
	
	void AddItem(Merchant m,Main_Menu mmenu)
	{
		Scanner f = new Scanner(System.in);
		System.out.println("Enter Item Details");
		System.out.println("Item Name");
		String enter_name = f.next();
		System.out.println("Item Price");
		double enter_price = f.nextDouble();
		System.out.println("Item Quantity");
		int enter_qnt = f.nextInt();
		System.out.println("Item Category");
		String enter_cat = f.next();
		
		if(!mmenu.Category.contains(enter_cat))
		{
			mmenu.Category.add(enter_cat);
		}
		Item addition = new Item(enter_name,enter_price,enter_qnt,enter_cat,m,mmenu);
		m.All_Items.add(addition);
		mmenu.Every_Item.add(addition);
		
		System.out.println((m.All_Items.size())+" "+addition.Item_name+" "+addition.Item_price+" "+addition.Item_quantity+" "+addition.Item_offer+" "+addition.Item_category);
		
		m.show_menu(m,mmenu);
		
	}
}


/*
 * Customer class can create objects of type customer which have attributes
 * required by a customer like: name, id, reward etc
 * It also have important methods used by customer for easy transactions and buying Items.
 * Customer class implements Interfaces LogIn and Display allowing the user to execute the 
 * generic methods with customer object also.
 * 
 */

class Customer implements LogIn,Display
{
	int order_num;
	int customer_reward;
	int customer_reward_num;
	int start=0;
	int latest_ten=0;
	String customer_address;
	
	double customer_mainacc;
	double customer_rewardacc;
	
	String customer_name;
	int customer_id;
	
	double bill;
	
	ArrayList<Customer_Item> customer_bill = new ArrayList<Customer_Item>();
	
	ArrayList<Customer_Item> customer_transactions = new ArrayList<Customer_Item>();
	
	Customer(){}
	Customer(String nm,int custid,String fff)
	{
		customer_address=fff;
		customer_name=nm;
		customer_id=custid;
		customer_mainacc=100;
		customer_reward=0;
		customer_rewardacc=0;
		customer_reward_num=0;
		bill = 0;
		
	}
	
	// Using Interface
	
	@Override
	public void print_details(int which_id,Main_Menu mm)
	{
		System.out.println(mm.All_Customer[which_id-1].customer_name+" "+mm.All_Customer[which_id-1].customer_address+" "+mm.All_Customer[which_id-1].order_num);
	}
	public void enter(Main_Menu mnu)
	{
		System.out.println("Choose Customer");
		for(int i=0;i<mnu.All_Customer.length;i++)
		{
			Customer now = mnu.All_Customer[i];
			System.out.println(now.customer_id+" "+now.customer_name);
		}
		
		Scanner t = new Scanner(System.in);
		int next = t.nextInt();
		mnu.All_Customer[next-1].show_menu(mnu.All_Customer[next-1],mnu);
	}
	public void show_menu(LogIn cust1,Main_Menu Mmenu1)
	{
		
	}
	public void show_menu(Customer cust,Main_Menu Mmenu)
	{
		System.out.println("Welcome "+cust.customer_name);
		System.out.println("Customer Menu");
		System.out.println("1) Search Item");
		System.out.println("2) Checkout Cart");
		System.out.println("3) Reward Won");
		System.out.println("4) Print Latest Orders");
		System.out.println("5) Exit");
		Scanner y = new Scanner(System.in);
		int cquery = y.nextInt();
		
		if(cquery==1)
		{
			cust.SearchByCat(cust,Mmenu);
		}
		else if(cquery==2)
		{
			cust.CheckOutCart(cust, Mmenu);
		}
		else if(cquery==3)
		{
			System.out.println(cust.customer_reward);
			cust.show_menu(cust, Mmenu);
		}
		else if(cquery==4)
		{
			cust.RecentOrders(cust, Mmenu);
		}
		else if(cquery==5)
		{
			Mmenu.Index(Mmenu);
		}
		else
		{
			System.out.println("Invalid Query");
			cust.show_menu(cust, Mmenu);
		}
	}
	
	void SearchByCat(Customer custom,Main_Menu mmenu)
	{
		System.out.println("Choose a Catgeory");
		for(int u=0;u<mmenu.Category.size();u++)
		{
			System.out.println((u+1)+") "+mmenu.Category.get(u));
		}
		Scanner c = new Scanner(System.in);
		int cat_num = c.nextInt();
		String search = mmenu.Category.get(cat_num-1);
		
		System.out.println("Choose Item By Code");
		
		for(int b=0;b<mmenu.Every_Item.size();b++)
		{
			Item am = mmenu.Every_Item.get(b);
			if((am.Item_category).equals(search))
			{
				System.out.println((b+1)+" "+am.Item_name+" "+am.Item_price+" "+am.Item_quantity+" "+am.Item_offer+" "+am.Item_category);
			}
		}
		
		System.out.println("Enter Item Code");
		int code1 = c.nextInt();
		
		System.out.println("Enter Item Quantity");
		int quant1 = c.nextInt();
		Item need = mmenu.Every_Item.get(code1-1);
		
		System.out.println("Choose method of transaction");
		System.out.println("1) Buy Item");
		System.out.println("2) Add Item To Cart");
		System.out.println("3) Exit");
		
		int ToDo = c.nextInt();
		if(ToDo==3)
		{
			custom.show_menu(custom, mmenu);
		}
		else if(ToDo==2)
		{
			double ReqCost = custom.Total_Cost(custom,mmenu,code1,quant1);
			int ReqQuantity = custom.Total_Quantity(custom, mmenu, code1, quant1);
			if(ReqQuantity>need.Item_quantity)
			{
				System.out.println("Out Of Stock");
				custom.show_menu(custom, mmenu);
			}
			else
			{
				Customer_Item AddToCart = new Customer_Item(need,ReqQuantity);
				AddToCart.CItem_Pay = ReqCost;
				custom.customer_bill.add(AddToCart);
				System.out.println("Added To Cart");
				custom.show_menu(custom, mmenu);

			}
		}
		else if(ToDo==1)
		{
			double ReqCost1 = custom.Total_Cost(custom,mmenu,code1,quant1);
			int ReqQuantity1 = custom.Total_Quantity(custom, mmenu, code1, quant1);
			if(ReqQuantity1>need.Item_quantity)
			{
				System.out.println("Out Of Stock");
				custom.show_menu(custom, mmenu);
			}
			else
			{
				double temp = ((ReqCost1)*(0.5))/100;
				double main_cost = ReqCost1;
				if((main_cost+temp)<=(custom.customer_mainacc+custom.customer_rewardacc))
				{
					Customer_Item want = new Customer_Item(need,ReqQuantity1);
					want.CItem_Pay = ReqCost1;
					custom.customer_bill.add(want);
					
					custom.PayBill(custom, mmenu, want,ReqCost1);
					custom.show_menu(custom, mmenu);
				}
				else
				{
					System.out.println("Out Of Money");
					custom.show_menu(custom, mmenu);
				}
			}
		}
	}
	
	// Function for paying bill
	void PayBill(Customer you,Main_Menu mmenu,Customer_Item buynow,double ReqCost)
	{
		Item MainItem = buynow.CItem_MainItem;
			
			if(buynow.CItem_quantity>MainItem.Item_quantity)
			{
				System.out.println(MainItem.Item_name+" is Out Of Stock");
				you.show_menu(you, mmenu);
			}
			else
			{
				double temp = ((ReqCost)*(0.5))/100;
				double main_cost = ReqCost+temp;
				Merchant main_merchant = MainItem.Item_merchant;
				if((main_cost)<=(you.customer_mainacc+you.customer_rewardacc))
				{
					mmenu.Account_balance += (2*temp);
					main_merchant.company_contribution+=temp;
					MainItem.Item_quantity = MainItem.Item_quantity - buynow.CItem_quantity;
					main_merchant.merchant_reward_num+=temp;
					
					you.order_num++;
					you.customer_reward_num++;
					if(you.customer_reward_num>=5)
					{
						you.customer_reward+=10;
						you.customer_rewardacc+=10;
						you.customer_reward_num-=5;
					}
					if(main_merchant.merchant_reward_num>=(double)100)
					{
						main_merchant.merchant_reward+=1;
						main_merchant.merchant_uniqueitem+=1;
						main_merchant.merchant_reward_num-=(double)100;
					}
					
					if(main_cost>you.customer_mainacc)
					{
						double remain = main_cost-you.customer_mainacc;
						you.customer_mainacc=0;
						you.customer_rewardacc = you.customer_rewardacc - remain;
					}
					else
					{
						you.customer_mainacc = you.customer_mainacc - main_cost;
					}
					
					System.out.println(MainItem.Item_name+" Successfully bought");
					
					
					you.customer_transactions.add(buynow);
					
					// to print latest 10 orders
					you.latest_ten++;
					if(you.latest_ten>10)
					{
						you.start++;
						you.latest_ten-=10;
					}
				}
				else
				{
					System.out.println("Out Of Money");
				}
			}
	}
	
	void CheckOutCart(Customer you,Main_Menu mmenu)
	{
		for(int h=0;h<you.customer_bill.size();h++)
		{
			Customer_Item buynow = you.customer_bill.get(h);
			you.PayBill(you, mmenu, buynow,buynow.CItem_Pay);
			
		}
		

		you.bill=0;
		you.customer_bill = new ArrayList<Customer_Item>();
		
		you.show_menu(you, mmenu);
		
	}
	
	// Function to display recent orders by a customer
	void RecentOrders(Customer cr,Main_Menu meu)
	{
		for(int e=cr.start;e<cr.customer_transactions.size();e++)
		{
			Customer_Item imp = cr.customer_transactions.get(e);
			Item wow_main = imp.CItem_MainItem;
			System.out.println("Bought Item: "+wow_main.Item_name+" Quantity: "+imp.CItem_quantity+" for Rs "+imp.CItem_Pay+" from Merchant "+wow_main.Item_merchant.merchant_name);
			
		}
		cr.show_menu(cr, meu);
	}
	double Total_Cost(Customer custom,Main_Menu mmenu,int code,int quant )
	{
		Item buy = mmenu.Every_Item.get(code-1);
		double value = buy.Item_price;
		
		Item bought;
		
		if((buy.Item_offer).equals("25% off"))
		{
			double discount = (25)*value;
			discount = discount/100;
			value = value - discount;
		}

		value = value*quant;
		
		return value;
	}
	
	int Total_Quantity(Customer custom,Main_Menu mmenu,int code,int quant)
	{
		Item buy = mmenu.Every_Item.get(code-1);
		int extra=0;
		if((buy.Item_offer).equals("Buy one get one"))
		{
			extra=1;
		}
		return (quant+extra);
		

	}

}

class Main_Menu
{

	Merchant All_Merchants[] = new Merchant[5];
	Customer All_Customer[] = new Customer[5];
	
	double Account_balance;
	
	ArrayList<Item> Every_Item = new ArrayList<Item>();
	
	ArrayList<String> Category = new ArrayList<String>();
	
	Main_Menu()
	{
		Account_balance=0;
	}
	
	void Index(Main_Menu mm)
	{

		System.out.println("Welcome To Mercury");
		System.out.println("1) Enter as Merchant");
		System.out.println("2) Enter as Customer");
		System.out.println("3) See user details");
		System.out.println("4) Company account balance");
		System.out.println("5) Exit");
		Scanner s = new Scanner(System.in);
		int query = s.nextInt();
		
		User Current = new User();
		if(query==1)
		{
			Current.User_logIn(new Merchant(),mm);
		}
		else if(query==2)
		{
			Current.User_logIn(new Customer(), mm);
		}
		else if(query==3)
		{
			String which = s.next();
			int which_id = s.nextInt();
			if((which).equals("M"))
			{
				
				Current.User_display(new Merchant(), which_id, mm);
				//System.out.println(mm.All_Merchants[which_id-1].merchant_name+" "+which_id+" "+mm.All_Merchants[which_id-1].company_contribution);
			}
			else if((which).equals("C"))
			{
				Current.User_display(new Customer(), which_id, mm);
			//	System.out.println(mm.All_Customer[which_id-1].customer_name+" "+which_id+" "+mm.All_Customer[which_id-1].order_num);
			}
			else
			{
				System.out.println("Invalid Query");
			}
			mm.Index(mm);
		}
		else if(query==4)
		{
			System.out.println("Account Balance Of Company: "+mm.Account_balance);
			mm.Index(mm);
		}
		else if(query==5)
		{
			System.out.println("Thank You");
			return;
		}
	}
}

public class menu {

	public static void main(String[] args) 
	{

		Main_Menu form = new Main_Menu();
		
		Merchant jack = new Merchant("Jack",1,"Address of jack");
		form.All_Merchants[0] = jack;
		Merchant john = new Merchant("John",2,"Address of john");
		form.All_Merchants[1] = john;
		Merchant james = new Merchant("James",3,"Address of james");
		form.All_Merchants[2] = james;
		Merchant jeff = new Merchant("Jeff",4,"Address of jeff");
		form.All_Merchants[3] = jeff;
		Merchant joseph = new Merchant("Joseph",5,"Address of joseph");
		form.All_Merchants[4] = joseph;
		
		Customer A = new Customer("A",1,"Address of A");
		Customer B = new Customer("B",2,"Address of B");
		Customer C = new Customer("C",3,"Address of C");
		Customer D = new Customer("D",4,"Address of D");
		Customer E = new Customer("E",5,"Address of E");
		form.All_Customer[0]=A;
		form.All_Customer[1]=B;
		form.All_Customer[2]=C;
		form.All_Customer[3]=D;
		form.All_Customer[4]=E;

		form.Index(form);

	}

}
