package airbnb;

import java.lang.*;
import java.sql.*;
import java.io.*;

class Application {
    
    private Page currentPage;
    private User currentUser;
    public Connector connection;
    
    public Application() {
        
        try {
            connection = new Connector();
            System.out.println("Succefully connected to the database!");
        }
        catch (Exception e) {
            
        }
        
        
    }
    
    public void run() {
        currentPage = new WelcomePage(this);
        
        while(currentPage != null) {
            currentPage.show();
        }
    }
    
    public void setNextPage(Page nextPage) {
        currentPage = nextPage;
    }
    
    
    public void setCurrentUser(User user) {
        currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    
    
    
    public static void main(String[] args) {
        
        Application app = new Application();
        app.run();
        
    }

}
