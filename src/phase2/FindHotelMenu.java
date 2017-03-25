package phase2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class FindHotelMenu 
{
	
	public void ShowMenu(Connector con, Scanner scan)
	{

		int option = -1;
		while(option != 0)
		{
			System.out.println("Find a hotel. (type 0 to go back)");
			System.out.println("1)Find by City, State or address.");
			System.out.println("2)Find by Name.");
			System.out.println("3)Find by price range.");
			System.out.println("4)Find by keywords.");
			System.out.println("5)Find by average feedback scores");
			System.out.println("6)Find by trusted users.");
			System.out.println("7)Go to hotel with id.");
			option = scan.nextInt();

			if(option == 1)
			{
				FindHotelLike(con,scan,"Address");
			}
			if(option == 2)
			{
				FindHotelLike(con,scan,"Name");
			}
		}
	}
	

	void FindHotelLike(Connector con, Scanner scan, String Col)
	{
		String address;
		System.out.println("Type "+ Col +" to find hotel: ");
		address = scan.next();
		try {
			SQLHotelLikeAddress(address,Col,con,scan);
		} catch (Exception e) {
			System.out.println("ERROR in trying to find hotel.");
		}
	}

	
	void SQLHotelLikeAddress(String address,String Col, Connector con, Scanner scan) throws Exception 
	{
		
		String query;
		ResultSet results;
		query = "select * from THs where "+ Col +" like '%"+ address +"%';";
		try{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			System.out.println("NO RESULTS!");
		}
		while(results.next())
		{
			String hid = results.getString("hid");
			String name = results.getString("Name");
			String haddress = results.getString("Address");
			System.out.println("----->> ID: " + hid + " Name: " + name + " Address: " + haddress);
			
		}
	
	}
}

