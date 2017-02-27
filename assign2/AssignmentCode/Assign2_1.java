/*
 * REPORT 1
 * 				Points to consider-: 	(This compiles succesfully- my run configuration is JRE- jdk1.8.0_101)
 * 						1. There are total of 3 seperate reports or programs. The detail report is only in this program.
 * 						2. Report 1 and Report 2 are done using one scan of the table sales, but report 3 is done using 2 scans
 * 						3. For 1st Report -::::	
 * 									Class contains one public data structure called info which is a multi dimensional array list. The main array list info, contains multiple
 * 									arraylists, and each one of them contains the information about each group of customer and product. Information like, average value 
 * 									of that product, average value of the other products bought by the current customer, and average value of the same product but for the other customers.
 * 						4. For 2nd Report -::::
 * 									The overall functionality is same as the first report, said above in 3rd point. The difference is, now we are grouping not only customer
 * 									product, but also the month, for each combination of customer, product and month, we are evaluating the average value of the same 
 * 									customer, product but for the previous month and also for the following month.
 * 						5. For 3rd Report -::::
 * 									This time, our job was to find how many sales for the previous month and also for the following month are between the average sale 
 * 									and the maximum sale for the current month and for current product. In this report we are grouping the product and month, since
 * 									grouping them with customer was not producing interesting result. This task was not possible or I just couldn't find a way to do it
 * 									in only one scan of the table, so I did it in 2 scans. 
 * 						6. Since, in last assignment, I used the basic data structures such as arrays, the program was messy and I lost 5 points for that, So
 * 						   this time I'm using the concept of multi dimensional arraylists which can solve this program pretty easily without any mess.
 * 						   The reason I use arraylist is for their dynamic nature and resizable feature. They are more efficient for space complexity.
 * 						7. For the sake of simplicity, if the month is january/december, the previous month/following month value is not considered and is set to null.
 *  							
 * 					
 * 										NAME- YUGAANK ARUN SHARMA
 * 										CWID- 10419077
 * 
 */

package edu.stevens.cs561;

import java.sql.*;
import java.util.ArrayList;

public class Assign2_1 {

	public ArrayList<ArrayList<String>> info;
	Assign2_1() {
		info=new ArrayList<ArrayList<String>>();										//the only data structure object that will hold the result
	}

	public boolean check(String a, String b) {
		int exists = 0;																	//checks if the customer and product already exists in the arraylist
		for (int i = 0; i < info.size(); i++) {											//a= customer, b= product
			if (info.get(i).get(0).equals(a) && info.get(i).get(1).equals(b)) {
				exists = 1;
				break;
			}
		}
		if (exists == 1) {
			return false;																//returns false if yes otherwise true
		} else
			return true;
	}

	public void add(String a, String b, String c) {										//temporary arraylist "xd"
		ArrayList<String> xd = new ArrayList<String>();									//numbers below are the positions
		xd.add(a);																		//0. cust
		xd.add(b);																		//1. prod
		xd.add(c);																		//2. quant --------- //3. count(next line)
		xd.add(Integer.toString(1));  //initializing the count row; this value will increment by 1 in the update function when the customer and product are present already in list;
		info.add(xd);				  //adding the arraylist into the main array list
	}

	public void update(String a, String b, String c) {									//updates the quant variable if a match is confirmed by check function
		int temp;
		for (ArrayList<String> e : info) {
			if (e.get(0).equals(a) && e.get(1).equals(b)) {
				temp = Integer.parseInt(e.get(2)) + Integer.parseInt(c);				// adds the quant value to the current value
				e.set(2, Integer.toString(temp));
				temp = Integer.parseInt(e.get(3)) + 1;
				e.set(3, Integer.toString(temp));										//increments the count value by one
			}
		}
	}

	public void calculateAvg() {
		Float temp;																		//calculates the average of each member of the main arraylist
		for (ArrayList<String> e : info) {
			temp = Float.parseFloat(e.get(2)) / (Float.parseFloat(e.get(3)));			//dividing the sum of quant value by count value
			e.add(Float.toString(temp));												//4. average of current product
		}
	}

	public void calculateOtherProductAvg() {											//calculates the average of each customer for other products but not the current one 
		int count;
		Float temp;
		String name, product;
		for (ArrayList<String> e : info) {
			count = 0;
			temp = 0f;
			name = e.get(0);
			product = e.get(1);
			for (ArrayList<String> f : info) {
				if (f.get(0).equals(name) && !(f.get(1).equals(product))) {
					temp = temp + Float.parseFloat(f.get(2));
					count=count+Integer.parseInt(f.get(3));
				}
			}
			temp = temp / count;
			e.add(Float.toString(temp));												//5. average of this customer but other products
		}
	}

	public void calculateOtherCustomerAvg() {
		int count;																		//calculates the average of product for sales done by other customer except the current one
		Float temp;
		String name, product;
		for (ArrayList<String> e : info) {
			count = 0;
			temp = 0f;
			name = e.get(0);
			product = e.get(1);
			for (ArrayList<String> f : info) {
				if (!(f.get(0).equals(name)) && f.get(1).equals(product)) {
					temp = temp + Float.parseFloat(f.get(2));
					count=count+Integer.parseInt(f.get(3));
				}
			}
			temp = temp / count;
			e.add(Float.toString(temp));												//6. average of other customer for this product
		}
	}

	public void formatDisplay(String a, String b, String c, String d, String e) {
		System.out.printf("%-7s %-7s %15s %15s %15s %n", a, b, c, d, e);				//formats the display
	}

	public void displayResult() {														//display function
		
		System.out.printf("%-7s %-7s %13s %18s %15s %n", "CUSTOMER", "PRODUCT", "THE_AVG", "OTHER_PROD_AVG", "OTHER_CUST_AVG");
		System.out.printf("%-7s %-7s %13s %18s %15s %n", "========", "=======", "=======", "==============", "==============");
		for (ArrayList<String> e : info) {
			formatDisplay(e.get(0), e.get(1), e.get(4), e.get(5), e.get(6));				
			
		}
		
	}

	public static void main(String[] args) {
		Assign2_1 cvar = new Assign2_1();													// class object
		String url = "jdbc:postgresql://localhost:5432/postgres"; 							// url
		String user = "postgres"; 															// user name
		String pass = "rocknroll"; 															// password
		String query = "SELECT * FROM Sales";												 // SQL Query

		/*															 /*
		 * Loading Driver-------------------------Creating Connection *
		/*															  */

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Successfully loaded the driver!");
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Created!!");

			/*
			 * Statement created and a result set also created through which we
			 * will scan the table in the database
			 */

			Statement s = con.createStatement();											//statement created
			ResultSet rs = s.executeQuery(query);											//resultset -query scans the table
			while (rs.next()) 																// rs.next() - each row at a time
			{
				if (cvar.check(rs.getString("cust"), rs.getString("prod"))) {							//check if present
					cvar.add(rs.getString("cust"), rs.getString("prod"), rs.getString("quant"));		//add if not	
				} else
					cvar.update(rs.getString("cust"), rs.getString("prod"), rs.getString("quant"));		//update if present
			}
			cvar.calculateAvg();															//calculates the average of each product
			cvar.calculateOtherProductAvg();												//calculates the other products of same customer average		
			cvar.calculateOtherCustomerAvg();												//calculates the other customers of the same product average
			cvar.displayResult();															//displays the output to screen

		} catch (Exception e) {
			e.printStackTrace();															//catches the exception
		}
	}
}
