
/*
 * 			Points to consider-:	
 * 				1. Report 1 and Report 2 both are output when you run this program
 * 				2. Only 1 scan of the table is made
 * 				3. For 1st Report -::::	
 * 							Data Structures like Arrays and ArrayList are used for storing the values like maximum value of a product on the position corresponding to their product
 * 							meaning if Pepsi is stored in ArrayList prodArray at first position, the maximum value is stored in 1st position of maxValue Array or if Milk is stored in 
 * 							3rd position then all the required information regarding Milk Product will be stored in 3rd Position in other arrays. Similarly for other products, 
 * 						    all info which is only required for result is stored at their corresponding positions.
 * 				4. For 2nd Report -::::
 * 							Only Arrays (1D and 2D) are used to generate the output. Details-: Combination of a Customer and product is stored in 'custpro' which has multiples  
 * 				 			1 Dimension arrays(or whole 2 dimension) of each size 2- i.e only for storing customer name and product name if the customer is from state connecticut, 
 * 							new york or new jersey and then their respective value is stored on the same index but in different 2D array called 'all[][3], it has 3 columns which holds
 *  						max value of connecticut in 1st field,min value of new york in 2nd and min value of new jersey in 3rd. If the next row is of same customer and product then the
 *  						value of that corresponding position similar to their cust name and product name array in custpro, is updated if it suits the conditions.  
 *  			5. Reports are output when this program is compiled. The left justification and right justfication is also set using System.out.printf, and are placed accordingly as
 *  				specified in the specification.
 *  			6. ArrayList is only used in 1st report for storing the product name. One reason to use arraylist is for their dynamic nature and resizable feature. They are more 
 *  				efficient for space complexity.
 *  			7. Although 2nd report could have been done in a lot easier methods using hashmaps and all, but are evaluted using simple data structures such as arrays. 
 *  			8. For both reports, max and min operation is performed based on update. If the current value is higher or lower than the stored value, replace it. For avg, all the values
 *  				are aggregated and then divided by the total number for each product in report 1.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Assign1 {

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/postgres"; 					// Variables declaration and initialization
		String user = "postgres";													// user name
		String pass = "rocknroll";													//password
		String query = "SELECT * FROM Sales";										//SQL Query
		
		/*
		 * Variables for report 1
		 */
		
		int c[] = new int[100], j = 0, k = 0, maxValue[] = new int[500], tempMaxValue = 0, sameProduct = 0;
		int tempMinValue = 0, minValue[] = new int[500];
		int maxDay[] = new int[500], maxYear[] = new int[500], maxMonth[] = new int[500], minDay[] = new int[500],				//Arrays for days month,year for max and min values
				minYear[] = new int[500], minMonth[] = new int[500],avgProductValue[] = new int[500];
		String maxCustName[] = new String[500], maxCustState[] = new String[500], minCustName[] = new String[500],				//Arrays for customer Names who has max/min values
				minCustState[] = new String[500],tempProduct;
		String maxCombinedDate[] = new String[100], minCombinedDate[] = new String[100];										//Combined the date only for report 1
		Arrays.fill(c, 0);
		Arrays.fill(maxValue, 0); 																							  // all values in max Value Array are set as zero
		Arrays.fill(minValue, 1000);				 																		// and 1000 in min Value Array, could set a much larger value,
		Arrays.fill(avgProductValue, 0); 																					// it would be easy to compare in much larger databases
		String heading1 = "PRODUCT", heading2 = "MAX_Q", heading3 = "CUSTOMER", heading4 = "DATE", heading5 = "ST", 		//Headings for report 1 output
				heading6 = "MIN_Q", heading7 = "CUSTOMER", heading8 = "DATE", heading9 = "ST", heading10 = "AVG_Q"; 
		
		ArrayList<String> prodArray = new ArrayList<String>(); 																//Arraylist Product stores product name if the condition satisfies.
		new ArrayList<Integer>();
		new ArrayList<Integer>();
		new ArrayList<String>();
		
		/*
		 * Variables for Report 2
		 */
		
		String custpro[][] = new String[450][3];
		int all[][] = new int[450][3];
		int place1 = 0;
		int month[][] = new int[450][3];
		int day[][] = new int[450][3];
		int year[][] = new int[450][3];
		boolean furtherCheckRequired;
		int setPos = 0, nypos = 0, njpos = 0;
		
		/*
		 * Loading Driver 
		 */
		
		try 																			//Try Catch Block Starts
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Successfully loaded the driver!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Creating Connection
		 */
		
		try {
			Connection con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Created!!");
			
			/*
			 * Statement created and a result set also created through which we
			 * will scan the table in the database
			 */
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next())																		 // rs.next() used for reading values of different 
			{																						// attributes in the row  while helps to read all the 
				/*																					// rows until rs.next() tells it no more rows
				 * Report 1 work here starts			 																		
				 */
				
				tempProduct = rs.getString("prod"); 										// product is stored		
																							// temporary in the
																							// tempProduct variable for
																							// duplicate check
				for (k = 0; k < prodArray.size(); k++) {
					if (tempProduct.equals(prodArray.get(k))) // nothing but
																// simple logic,
																// if there is
																// already an
																// element in
																// the product
																// array
						sameProduct = 1; // with the same product name in the
											// current row, it will not add it
											// in the Array
				} // preventing duplicate products in the array

				if (sameProduct == 0) 
				{																				// well if the above check didn't change the value	
					prodArray.add(tempProduct); 												// of the variable same product
				} 																				// this will add the new product read by result set to the array
																								
				sameProduct = 0;
				for (j = 0; j < prodArray.size(); j++) 													// looping over the products array
				{
					if (prodArray.get(j).equals(rs.getString("prod"))) 									// Check if the product is already in the arraylist	
					{ 																					// the current result set is bigger than the stored in
																										// maxValue at corresponding
						c[j]++; 																		// product position, ultimately finding the max
																										// value of the Product
						tempMaxValue = rs.getInt("quant");
						tempMinValue = rs.getInt("quant");
						if (maxValue[j] < tempMaxValue) {
							maxValue[j] = tempMaxValue; 												// if we do find the max value, we will store
																										// its customer information along with other information
							maxCustName[j] = rs.getString("cust"); 										// as necessary
							maxCustState[j] = rs.getString("state");
							maxYear[j] = rs.getInt("year");
							maxDay[j] = rs.getInt("day");
							maxMonth[j] = rs.getInt("month");
							maxCombinedDate[j] = maxMonth[j] + "/" + maxDay[j] + "/" + maxYear[j];
						}
						if (minValue[j] > tempMinValue) 
						{																						 // same steps as done for finding max
							minValue[j] = tempMinValue;															//  value of a specific product
							minCustName[j] = rs.getString("cust");
							minCustState[j] = rs.getString("state");
							minYear[j] = rs.getInt("year");
							minDay[j] = rs.getInt("day");
							minMonth[j] = rs.getInt("month");
							minCombinedDate[j] = minMonth[j] + "/" + minDay[j] + "/" + minYear[j];
						}
						avgProductValue[j] += rs.getInt("quant");											 // adding all the values of the same product
					}																						// in their corresponding array position
				}
				
				
				/*
				 * Report 2 work here starts
				 */
				
				setPos = 0;																//setPos is for CT max position, nypos is NewYork Min position
				nypos = 0;																//and njpos is for New Jersey min position all are used to set values
				njpos = 0;																//in all[][] array which contains all three values per combination of product
				furtherCheckRequired = false;											//and customer. Further check required boolean value is used if the combination
																						//is already in the custpro array
				for (String sc[] : custpro)												//custpro array is a 2d array which stores the value of each customer and 
				{																		//product combination names		
					
					if (sc[0] != null && sc[0].equals(rs.getString("cust")) && sc[1].equals(rs.getString("prod"))) 
					{
						furtherCheckRequired = true;													//if the combination is already in the array,
						break;																			//set boolean value to true and break the loop
					} 
					else
					{
						setPos++;																		//else increament the positions of all three variables that
						nypos++;																		//we will use to update the max/min values incase the combination
						njpos++;																		//is matched
					}																				
				}
				if (rs.getString("state").equals("CT") && rs.getInt("year") >= 2000 && rs.getInt("year") < 2005) 				//If state is connecticut and year is in between 2000 and 2005
				{
					if (!furtherCheckRequired) 															// If earlier the combination of customer and product was NOT FOUND
					{																					
						custpro[place1][0] = rs.getString("cust");										//Add the values in the arrays like customer name	
						custpro[place1][1] = rs.getString("prod");										//product name	
						all[place1][0] = rs.getInt("quant");											//CT quantity as state is CT
						month[place1][0] = rs.getInt("month");											//Month, Day and Year
						day[place1][0] = rs.getInt("day");
						year[place1][0] = rs.getInt("year");
						place1++;
					}
					else 
					{
						if (rs.getInt("quant") > all[setPos][0]) 										//If they did got caught then compare that value stored to the current 
						{																				//value and update all the information if necessary
							all[setPos][0] = rs.getInt("quant");
							month[setPos][0] = rs.getInt("month");
							day[setPos][0] = rs.getInt("day");
							year[setPos][0] = rs.getInt("year");
						}
					}
				}
				if (rs.getString("state").equals("NY")) 
				{
					if (!furtherCheckRequired) 
					{																					//Similarly in case of New york, add if not found
						custpro[place1][0] = rs.getString("cust");
						custpro[place1][1] = rs.getString("prod");
						all[place1][1] = rs.getInt("quant");
						month[place1][1] = rs.getInt("month");
						day[place1][1] = rs.getInt("day");
						year[place1][1] = rs.getInt("year");
						place1++;
					} 
					else 
					{
						if (all[nypos][1] != 0 && rs.getInt("quant") < all[nypos][1]) 					//update if found, obviously array will initialize automatically
						{																				//zero values, so make another else if, if its zero that means its 
							all[nypos][1] = rs.getInt("quant");											//not updated at any time, so just add the current value			
							month[nypos][1] = rs.getInt("month");
							day[nypos][1] = rs.getInt("day");
							year[nypos][1] = rs.getInt("year");
						} else if (all[nypos][1] == 0) 
								{
									all[nypos][1] = rs.getInt("quant");
									month[nypos][1] = rs.getInt("month");
									day[nypos][1] = rs.getInt("day");
									year[nypos][1] = rs.getInt("year");
								}
					}
				}
				if (rs.getString("state").equals("NJ")) {
					if (!furtherCheckRequired) {
						custpro[place1][0] = rs.getString("cust");											//similar case as of new york
						custpro[place1][1] = rs.getString("prod");
						all[place1][2] = rs.getInt("quant");												//add
						month[place1][2] = rs.getInt("month");
						day[place1][2] = rs.getInt("day");
						year[place1][2] = rs.getInt("year");
						place1++;
					} else {
						if (all[njpos][2] != 0 && rs.getInt("quant") < all[njpos][2]) {
							all[njpos][2] = rs.getInt("quant");
							month[njpos][2] = rs.getInt("month");
							day[njpos][2] = rs.getInt("day");												//or update ..
							year[njpos][2] = rs.getInt("year");
						} else if (all[njpos][2] == 0) {
							all[njpos][2] = rs.getInt("quant");
							month[njpos][2] = rs.getInt("month");
							day[njpos][2] = rs.getInt("day");
							year[njpos][2] = rs.getInt("year");
						}
					}
				}

			} 
			
			
			/*
			 * WHILE LOOP ENDS HERE, All ROWS HAVE BEEN READ!
			 */
			
			/*
			 * Output of Report 1 
			 */
			
			System.out.printf("%-7s %4s %-7s %9s %-5s %3s %-1s %9s %-2s %7s", 
								heading1, heading2, heading3, heading4, heading5, 								//Headings for 1st report
								heading6, heading7, heading8, heading9,	heading10);
			System.out.println("\n=======  ===  ========      ===  ==     ===   ======	   ===  ==    ===\n");
			
			for (int l = 0; l < prodArray.size(); l++) 
			{
				avgProductValue[l] = avgProductValue[l] / c[l];														//calculating the average product value 
				System.out.printf("%-7s %4d %-7s %11s %-5s %4d %-7s %11s %-2s %7s %n", 
						prodArray.get(l), maxValue[l],maxCustName[l], maxCombinedDate[l],maxCustState[l],				//Left Justified String and Right Justified Integer Values
						minValue[l], minCustName[l], minCombinedDate[l], minCustState[l],avgProductValue[l]);			//using System.out.printf command
			}

			/*
			 * Output of Report 2
			 */
			
			System.out.println();
			System.out.printf("%-7s %-7s %7s %7s %7s %7s %7s %7s %7s %7s %7s %7s %7s %7s %n", 
							"Customer", "Product", "CT_MAX", "Month", "Day", "Year", 								//Left Justified String and Right Justified Integer Values
							"NY_MIN", "Month", "Day", "Year", 														//using System.out.printf command
							"NJ_MIN", "Month", "Day", "Year");
			System.out.println(
					"======== =======  ======   =====     ===    ====  ======   =====     ===    ====  ======   =====     ===    ====");
			for (int s3 = 0; s3 < all.length; s3++) {
				if (custpro[s3][0] != null) {
					System.out.printf("%-7s %-7s %7d %7d %7d %7d %7d %7d %7d %7d %7d %7d %7d %7d %n", 
							custpro[s3][0],	custpro[s3][1], all[s3][0], month[s3][0], day[s3][0], year[s3][0], 
							all[s3][1], month[s3][1], day[s3][1], year[s3][1], 
							all[s3][2], month[s3][2], day[s3][2], year[s3][2]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();																							 // Exception Handling
		}
	}

}