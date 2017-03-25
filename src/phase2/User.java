package phase2;
import java.sql.*;
import java.util.*;

public class User 
{
	public String UserLogin;
	public String UserName;
	
	public User()
	{
		UserLogin = "";
		UserName = "";
	}
	
	/*
	* This inserts a new user into the user table.
	* It will throw an exception if the login is the same as another user.
	*/
	static public void InsertUser(String Name, String Login, int type, String Password, String Address, String PhoneNumber, Connector con) throws Exception
	{
		//INSERT INTO Users (Name, Login, userType, Password, Address, PhoneNumber) VALUES ('Joey Despain','Jdlog','0','password','123 street','5556666');
		String query;

		query= "INSERT INTO Users (Name, Login, userType, Password, Address, PhoneNumber) "+"VALUES (?, ?, ?, ?, ?,?)";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setString (1, Name);
		      preparedStmt.setString (2, Login);
		      preparedStmt.setInt(3, type);
		      preparedStmt.setString(4, Password);
		      preparedStmt.setString(5, Address);
		      preparedStmt.setString(6, PhoneNumber);
		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
	}
	
	public void UserLogin(Scanner scan, Connector Con) throws Exception
	{
		String login;
		String password;
		
		System.out.println("Type in your login and return.");
		login = scan.next();
		
		System.out.println("Type in you password and return");
		password = scan.next();
		
		try
		{
			SqlLogin(Con,login,password);
		}
		catch(Exception e)
		{
			throw(e);
		}
		
	}
	
	public void SqlLogin(Connector Con, String Login, String Password) throws Exception
	{
		/* select count(1)
			from table
			where key = value;*/
		String query;
		ResultSet results;
		query = "SELECT * FROM Users WHERE Login = '"+Login+"' AND Password = '"+Password+"';";
		try{
			results = Con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		 
		while(results.next())
		{
			UserName = results.getString("Name");
		}
		
	}
	
	
	public void CreateUser(Scanner scan, Connector con) throws Exception
	{
		System.out.println("Type your name then return.");
		String name = scan.next();
		
		System.out.println("Type your login username then return.");
		String login = scan.next();
		
		System.out.println("Do you own a hotel? Type y or n then return.");
		String stype = scan.next();
		int type;
		if(stype.compareTo("y") == 0)
			type = 1;
		else 
			type = 0;
		
		System.out.println("Type in your password then return.");
		String password = scan.next();
		
		System.out.print("Type in your address then return.");
		String address = scan.next();
		
		System.out.print("\nType in your phonenumber then return\n");
		String number = scan.next();
		
		try
		{
			this.InsertUser(name, login, type, password, address, number, con);
		}
		catch (Exception e)
		{
			throw(e);
			
		}
		
	}
	
	public void CreateReservation(Scanner scan, Connector con)
	{
		
	}
	
	public void SQLCreateReservation()
	{
		
	}
}
