package Airbnb;


import java.sql.Connection;
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
