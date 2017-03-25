package phase2;
import java.util.*;
import java.sql.*;
public class Hotel 
{
	public Hotel()
	{
		
	}
	
	static void CreateHotel(Scanner scan, Connector con, User CurrentUser)
	{
		String Category,Name,Address,URL,PhoneNumber,Year;
		System.out.println("Type in category of hotel.");
		Category = scan.next();
		System.out.println("Type in Name of hotel.");
		Name = scan.next();
		System.out.print("Type in Address of hotel.");
		Address = scan.nextLine();
		System.out.print("\nType in URL of hotel.");
		URL = scan.nextLine();
		System.out.print("\nType in phone number of hotel.");
		PhoneNumber = scan.nextLine();
		System.out.print("\nType in year of hotel.");
		Year = scan.next();
		
	}
	
	static void SQLCreateHotel(String Category, String Name, String Address,
			String URL, String PhoneNumber, String Year,
			User CurrentUser, Connector con) throws Exception
	{
		String query;

		query= "INSERT INTO THs (Category, Name, Address, URL, PhoneNumber, Year, Login) "+"VALUES (?, ?, ?, ?, ?,?,?)";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setString (1, Category);
		      preparedStmt.setString (2, Name);
		      preparedStmt.setString (2, Address);
		      preparedStmt.setString(4, URL);
		      preparedStmt.setString(5, PhoneNumber);
		      preparedStmt.setString(6, Year);
		      preparedStmt.setString(6, CurrentUser.UserLogin);
		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
	}
}
