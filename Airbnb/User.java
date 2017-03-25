package Airbnb;

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
}