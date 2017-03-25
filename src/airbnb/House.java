package airbnb;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;




class House {

    public House() {
        
    }
    
    
    public static String getHousesLike(String columnName, String description, Connector con) throws Exception {
        
        String query;
        ResultSet results;
        query = "select * from THs where "+ columnName +" like '%"+ description +"%';";
        try{
            results = con.stmt.executeQuery(query);
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
        }
        
        String resultString = "";
        if(!results.isBeforeFirst())
        {
            return "NO RESULTS!";
        }
        while(results.next())
        {
            resultString += "House id: ";
            resultString += results.getString("hid") + "\n";
            
            resultString += "Name: ";
            resultString += results.getString("Name") + "\n";
            
            resultString += "Address: ";
            resultString += results.getString("Address") + "\n\n";
            
        }

        return resultString;
        
    }
    
	static void GetAvailableDates(Connector con, int HotelId) throws Exception
	{
		String query;
		ResultSet results;
		query = "select A.PPN,P.start,P.end from Available A inner join Period P on P.pid where A.hid = "+ HotelId+";";
		try{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			System.out.println("This hotel has no dates available.");
		}
		else
		{
			System.out.println("--> This Hotel is available:");
			while(results.next())
			{
				
				String start = results.getString("start");
				String end = results.getString("end");
				float PPN = results.getFloat("PPN");
				System.out.println("--> Start date: " + start +" End Date: "+ end +" Price Per Night: $" +PPN);
			}
		}
	}
	
	static float CheckIfDatesAreAvailable(Connector con, int HotelId, String start, String end) throws Exception
	{
		String query;
		ResultSet results;
		query = "select * from Available A inner join Period P on P.pid where A.hid = 1  and start <= '" + start +"'  and end >='" +end +"' ;";
		try{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return -1;
		}
		
		while(results.next())
		{
			float PPN = results.getFloat("PPN");
			return PPN;
		}
		return -1;


	}
	
	static void SQLReserveDate(Connector con, Date Start, Date End, String UserLogin, int HotelId)
	{
		String query;
		query= "INSERT INTO THs (Cost, Login, hid, pid) "+"VALUES (?, ?, ?, ?);";
		
	}
	
	static void CreatePeriod(Connector con, Date Start, Date End)
	{
		String query;
		query= "INSERT INTO THs (Start, End) "+"VALUES (?, ?);";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			 // preparedStmt.setDate(1,Start);
		      //preparedStmt.setString (2, Name);
		} 
		catch(Exception e)
		{
			
		}
		
	}
	
	static void CreateHotel(Scanner scan, Connector con, User CurrentUser) throws Exception
	{
		String Category,Name,Address,URL,PhoneNumber,Year;
		System.out.println("Type in category of hotel.");
		Category = scan.next();
		System.out.println("Type in Name of hotel.");
		Name = scan.next();
		System.out.print("Type in Address of hotel: ");
		Address = scan.next();
		System.out.print("\nType in URL of hotel: ");
		URL = scan.next();
		System.out.print("\nType in phone number of hotel: ");
		PhoneNumber = scan.next();
		System.out.print("\nType in year of hotel: ");
		Year = scan.next();
		try {
			SQLCreateHotel(Category,Name,Address,URL,PhoneNumber,Year,CurrentUser,con);
		} catch (Exception e) {
			throw(e);
		}
		
	}
	
	
	static void SQLCreateHotel(String Category, String Name, String Address,
			String URL, String PhoneNumber, String Year,
			User CurrentUser, Connector con) throws Exception
	{
		String query;

		query= "INSERT INTO THs (Category, Name, Address, URL, PhoneNumber, Year, Login) "+"VALUES (?, ?, ?, ?, ?,?,?);";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setString (1, Category);
		      preparedStmt.setString (2, Name);
		      preparedStmt.setString (3, Address);
		      preparedStmt.setString(4, URL);
		      preparedStmt.setString(5, PhoneNumber);
		      preparedStmt.setString(6, Year);
		      preparedStmt.setString(7, CurrentUser.login);
		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
	}
	
    
    
//    public static House getHouseWithID(int id, Connector con) throws Exception {
//        
//        String query;
//        ResultSet results;
//        query = "select * from THs where hid = " + id + ";";
//        try{
//            results = con.stmt.executeQuery(query);
//        } catch(Exception e) {
//            System.err.println("Unable to execute query:"+query+"\n");
//            System.err.println(e.getMessage());
//            throw(e);
//        }
//        
//        if(!results.isBeforeFirst())
//        {
//            return null;
//        }
//        
//        results.next();
//        int hid = results.getInt("hid");
//        String category = results.getString("Category");
//        String name = results.getString("Name");
//        String address = results.getString("Address");
//        String url = results.getString("URL");
//        String phoneNumber = results.getString("PhoneNumber");
//        String String year =
//
//
//    }
    
    
    
//    public static void SQLHotelLikeAddress(String address,String Col, Connector con, Scanner scan) throws Exception
//    {
//        
//        String query;
//        ResultSet results;
//        query = "select * from THs where "+ Col +" like '%"+ address +"%';";
//        try{
//            results = con.stmt.executeQuery(query);
//        } catch(Exception e) {
//            System.err.println("Unable to execute query:"+query+"\n");
//            System.err.println(e.getMessage());
//            throw(e);
//        }
//        if(!results.isBeforeFirst())
//        {
//            System.out.println("NO RESULTS!");
//        }
//        while(results.next())
//        {
//            String hid = results.getString("hid");
//            String name = results.getString("Name");
//            String haddress = results.getString("Address");
//            System.out.println("----->> ID: " + hid + " Name: " + name + " Address: " + haddress);
//            
//        }
//        
//    }

}
