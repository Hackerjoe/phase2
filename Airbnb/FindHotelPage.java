package Airbnb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class FindHotelPage extends Page
{

    public FindHotelPage(Application app) {
        super(app);
    }

    public void show()
    {


        println("Find a house. (type 0 to go back)");
        println("1)Find by City, State or address.");
        println("2)Find by Name.");
        // need to do some more querrying
        println("3)Find by price range.");
        // need to do some more querrying
        println("4)Find by keywords.");
        // need to do some more querrying
        println("5)Find by average feedback scores");
        // need to do some more querrying
        println("6)Find by trusted users.");
        
        println("7)Go to house with id.");

        int option = Integer.parseInt(getInput());
        
        if (option == 0){
            HomePage homePage = new HomePage(app);
            app.setNextPage(homePage);
        }
        else if(option == 1)
        {
            FindHotelLike("Address");
        }
        else if(option == 2)
        {
            FindHotelLike("Name");
        }

    }
    
    
    private void FindHotelLike(String Col)
    {
        String address;
        println("Type "+ Col +" to find house: ");
        
        String description = getInput();
        
        try{
            String result = House.getHousesLike(Col, description, app.connection);
            println(result);
        } catch (Exception e) {
            
        }
        
    }
    
    

}

