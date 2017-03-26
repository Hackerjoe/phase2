package airbnb;

import java.sql.*;
import java.util.*;

public class User
{
    
    public String name;
    public String login;
    public int type;
    private String password;
    public String address;
    public String phonenumber;
    
    public User(String Name, String Login, int type, String Password, String Address, String PhoneNumber)
    {
        this.name = Name;
        this.login = Login;
        this.type = type;
        this.password = Password;
        this.address = Address;
        this.phonenumber = PhoneNumber;
    }
    
    /*
     * This inserts a new user into the user table.
     * It will throw an exception if the login is the same as another user.
     */
    public static void InsertUser(String Name, String Login, int type, String Password, String Address, String PhoneNumber, Connector con) throws Exception
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
    
    public static User AuthenticateUser(String username, String password, Connector con) throws Exception {
        String query;
        query = "SELECT * FROM Users WHERE Login = ?;";
        ResultSet rs = null;
        
        try {
            // set up querry
            PreparedStatement preparedStmt = con.con.prepareStatement(query);
            preparedStmt.setString(1, username);
            rs = preparedStmt.executeQuery();
            
            // get the password
            rs.next();
            String dbPassword = rs.getString("Password");
            
            // check validity
            if (password.equals(dbPassword)) {
                String dbName = rs.getString("Name");
                String dbLogin = rs.getString("Login");
                int dbType = rs.getInt("userType");
                String dbAddress = rs.getString("Address");
                String dbPhoneNumebr = rs.getString("PhoneNumber");
                User user = new User(dbName, dbLogin, dbType, dbPassword, dbAddress, dbPhoneNumebr);
                return user;
            }
            else
                return null;
            
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
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
    
    public static List<Reserve> getReserves(String Login,Connector con) throws Exception
    {
    	//select * from Reserve where Login = 'joeyDD';
    	String query;
        ResultSet results;
        query = "select * from Reserve where Login = '"+Login+"';";
        try{
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
        List<Reserve> newList = new ArrayList<Reserve>();
        while(results.next())
        {
        	Reserve res = new Reserve();
        	res.cost = results.getFloat("cost");
        	res.hid = results.getInt("hid");
        	res.pid = results.getInt("pid");
        	newList.add(res);
        }
        
    	return newList;
    }
    
    public static void addVisit(float cost, String Login, int hid, int pid, Connector con) throws Exception
    {
    	String query;
		query= "insert into Visits (cost,Login,hid,pid) values (?,?,?,?);";
		try
		{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setFloat(1, cost);
			  preparedStmt.setString(2,Login);
		      preparedStmt.setInt(3,hid);
		      preparedStmt.setInt(4,pid);
		      preparedStmt.execute();
		}
		catch(Exception e)
		{
			System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
		}
    }
    
    public static void printVisits(String Login, Connector con) throws Exception
    {
    	String query;
        ResultSet results;
        query = "select * from Visits where Login = '"+Login+"';";
        try{
            results = con.stmt.executeQuery(query);
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
        }
        if(!results.isBeforeFirst())
        {
            System.out.println("No Visits.");
        }
        List<Visit> visits = new ArrayList<Visit>();
        while(results.next())
        {
            int hid = results.getInt("hid");
        	float cost = results.getFloat("cost");
        	int pid = results.getInt("pid");
        	Visit newVisit = new Visit();
        	newVisit.cost = cost;
        	newVisit.hid = hid;
        	newVisit.pid = pid;
        	newVisit.Login = Login;
        	visits.add(newVisit);
        }
        
        for(Visit v : visits)
        {
        	String hidName = House.GetHouseNameByHid(v.hid, con);
	    	System.out.println("--> Hid: "+v.hid + " Name: " + hidName +" Cost: " + v.cost);
	    	Period period = House.getPeriod(v.pid, con);
	    	System.out.println("From: "+period.start+" To:" + period.end);
        }
    }
    
    
    static public void RateFeedback(Connector con, int fid, String Login, int score) throws Exception
    {
    	//insert into Rates (fid,Login,rating) values (1,'joeyDD','2');
    	String query;

		query= "INSERT INTO Rates (fid, Login, rating) "+"VALUES (?, ?, ?);";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setInt(1, fid);
		      preparedStmt.setString (2, Login);
		      preparedStmt.setInt (3, score);

		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
    }
    
    static public void setHouseAsFavorite(Connector con, int hid, String Login) throws Exception
    {
    	String query;

		query= "INSERT INTO Favorites (hid, Login, fvdate) "+"VALUES (?, ?, CURDATE());";
		
		try{
			  PreparedStatement preparedStmt = con.con.prepareStatement(query);
			  preparedStmt.setInt(1, hid);
		      preparedStmt.setString (2, Login);

		      preparedStmt.execute();
			 
        } catch(Exception e) {
			System.err.println("Unable to execute query:"+query+"\n");
	                System.err.println(e.getMessage());
			throw(e);
		}
    }
    
    static public void getFavorites(Connector con, String Login) throws Exception
    {
    	//select fvdate, hid from Favorites where Login = 'joeyDD';
    	String query;
        ResultSet results;
        query = "select fvdate, hid from Favorites where Login = '"+Login+"';";
        try{
            results = con.stmt.executeQuery(query);
        } catch(Exception e) {
            System.err.println("Unable to execute query:"+query+"\n");
            System.err.println(e.getMessage());
            throw(e);
        }
        if(!results.isBeforeFirst())
        {
            System.out.println("No Favorites.");
        }
        List<Favorite> favs = new ArrayList<Favorite>();
        while(results.next())
        {
        	Favorite fav = new Favorite();
        	fav.date = results.getString("fvdate");
        	fav.hid = results.getInt("hid");
        	favs.add(fav);
        	
        }
        for(Favorite fav : favs)
        {
	    	String name = House.GetHouseNameByHid(fav.hid, con);
	    	System.out.println("hid: "+ fav.hid+ " Name: " + name + " Date Favorited: "+ fav.date);
        }

    }
    
    
}
