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
    
    static void getHouseOwnBy(String Login, Connector con) throws Exception
    {
    	  String query;
          ResultSet results;
          query = "select * from THs where Login = '"+Login+"';";
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
              System.out.println("You own zero houses.");
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
          System.out.println(resultString);
    }
    
    
    static String GetHouseNameByHid(int hid, Connector con) throws Exception
    {
    	  String query;
          ResultSet results;
          query = "select * from THs where hid = '"+hid+"';";
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
              return "NAH";
          }
          results.next();
          return results.getString("Name");

    }
    
    
	static void GetAvailableDates(Connector con, int HotelId) throws Exception
	{
		String query;
		ResultSet results;
		query = "select A.PPN,P.start,P.end from Available A, Period P where P.pid = A.pid and A.hid ="+ HotelId+";";
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
		query = "select * from Available A inner join Period P on P.pid where A.hid = "+HotelId+"  and start <= '" + start +"'  and end >='" +end +"' ;";
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
	
	static int getPidOfDates(Connector con, int hid, String start, String end) throws Exception
	{
		String query;
		ResultSet results;
		query = "select * from Available A inner join Period P on P.pid where A.hid = "+hid+"  and start <= '" + start +"'  and end >='" +end +"' ;";
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
			int pid = results.getInt("pid");
			return pid;
		}
		return -1;
	}
	
	static void SQLReserveDate(Connector con, String Start, String End,float cost, String UserLogin, int hid) throws Exception
	{
		int pid = CreatePeriod(con, Start, End);
		int APid = getPidOfDates(con, hid, Start, End);
		String query;
		query= "INSERT INTO Reserve (Cost, Login, hid, pid) "+"VALUES (?, ?, ?, ?);";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setFloat(1,cost);
		      preparedStmt.setString(2,UserLogin);
		      preparedStmt.setInt(3, hid);
		      preparedStmt.setInt(4, pid);
		      preparedStmt.execute();
		      SQLSplitAvailable(APid, hid, Start, End, con);
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
		
		
	}
	
	static void SQLSplitAvailable(int APid,int hid, String start, String end, Connector con) throws Exception
	{
		float PPN = CheckIfDatesAreAvailable(con, hid, start, end);
		Period ParentPeriod = getPeriod(APid,con);
		DeleteAvailable(APid, hid, con);
		if(Period.ConvertCompare(ParentPeriod.start,start)  == 0 && Period.ConvertCompare(ParentPeriod.end,end) == 0)
		{
			// They are equal
			return;
		}
		else if(Period.ConvertCompare(start,ParentPeriod.start) > 0 && Period.ConvertCompare(ParentPeriod.end,end) > 0)
		{
			// Middle case where we need to create two available.
			int newPid = CreatePeriod(con,ParentPeriod.start,start);
			int newPid2 = CreatePeriod(con, end, ParentPeriod.end);
			AddAvailable(newPid, hid,PPN, con);
			AddAvailable(newPid2, hid,PPN, con);
		}
		else if(Period.ConvertCompare(start,ParentPeriod.start) == 0)
		{
			//Starts are the same.
			int newPid = CreatePeriod(con, end, ParentPeriod.end);
			AddAvailable(newPid, hid,PPN, con);
			return;
		}
		else if(Period.ConvertCompare(ParentPeriod.end,end) < 0)
		{
			int newPid = CreatePeriod(con, ParentPeriod.start, start);
			AddAvailable(newPid, hid,PPN, con);
			return;
		}


		
		
	}
	
	
	static Period getPeriod(int pid, Connector con) throws Exception
	{
		//select * from Period where pid = 5;
		String query;
		ResultSet results;
		query = "select * from Period where pid = " +pid+";";
		
		try
		{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return null;
		}
		results.next();
		return new Period(results.getString("start"),results.getString("end"),pid);
	}
	static void DeleteAvailable(int pid,int hid, Connector con) throws Exception
	{
		String query;
		query= "delete from Available where pid = ? and hid = ?;";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setInt(1,pid);
		      preparedStmt.setInt(2,hid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
	}
	
	static void AddAvailable(int pid,int hid,float PPN, Connector con) throws Exception
	{
		String query;
		query= "insert into Available (PPN,hid,pid) values (?,?,?);";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setFloat(1, PPN);
			  preparedStmt.setInt(2,hid);
		      preparedStmt.setInt(3,pid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
	}
	
	static int CreatePeriod(Connector con, String Start, String End) throws Exception
	{
		int pid = CheckIfDateExist(con, Start, End);
		if(pid != -1)
			return pid;
		
		String query;
		query= "INSERT INTO Period (start, end) "+"VALUES (?, ?);";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setDate(1,java.sql.Date.valueOf(Start));
		      preparedStmt.setDate(2, java.sql.Date.valueOf(End));
		      preparedStmt.execute();
		      pid = CheckIfDateExist(con, Start, End);
		      if(pid == -1)
		      {
		    	  Exception e = new Exception("ERROR Could not find period!");
		    	  throw e;
		      }
		      return pid;
		      
		} 
		catch(Exception e)
		{
            throw(e);
		}
		
	}
	
	
	
	static int CheckIfDateExist(Connector con, String Start, String End) throws Exception
	{
		
		String query;
		ResultSet results;
		query = "select * from Period where start = '"+ Start +"' and end='"+End+"';";
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
		else
		{
			results.next();
			return results.getInt("pid");
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
	
	static boolean CheckForFeedback(Connector con, String Login, int Hid) throws Exception
	{
		String query;
		ResultSet results;
		query = "select * from Feedback where Login = '" +Login+"' and hid ="+Hid+";";
		
		try
		{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	static List<FeedBack> getFeedBackForHouse(Connector con, int Hid) throws Exception
	{
		String query;
		ResultSet results;
		List<FeedBack> returnList = new ArrayList<FeedBack>();
		query = "select * from Feedback where hid ="+Hid+";";
		
		try
		{
			results = con.stmt.executeQuery(query);
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
		if(!results.isBeforeFirst())
		{
			return returnList;
		}
		else
		{
			while(results.next())
			{
				FeedBack newFeed = new FeedBack();
				newFeed.date = results.getString("fbdate");
				newFeed.fid = results.getInt("fid");
				newFeed.hid = Hid;
				newFeed.Login = results.getString("Login");
				newFeed.message = results.getString("Text");
				newFeed.score = results.getInt("Score");
				returnList.add(newFeed);
			}
		}
		return returnList;
	}
	
	static void CreateFeedBack(Connector con, String Login, int Hid, String message,int Score) throws Exception
	{
		
		if(CheckForFeedback(con, Login, Hid) == true)
		{
			Exception e = new Exception("Already gave feedback");
			throw(e);
			
		}
			
		//insert into Feedback (hid,Login,fbdate,Text,Score) values ('1','joeyDD','2017-01-01','It was a place',10);
		String query;

		query= "insert into Feedback (hid,Login,fbdate,Text,Score) "+"VALUES (?, ?, CURDATE(), ?, ?);";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setInt (1, Hid);
		      preparedStmt.setString (2, Login);
		      preparedStmt.setString (3, message);
		      preparedStmt.setInt(4, Score);
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
