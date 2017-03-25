package Airbnb;

class NewUserPage extends Page {
    
    public NewUserPage(Application app) {
        super(app);
    }
    
    @Override
    public void show() {
        println("Welcome! Enter some credentials to set up your account!");
        
        // GET USER INFO
        print("Full name: ");
        String name = getInput();
        
        print("Username: ");
        String username = getInput();
        
        print("Password: ");
        String password = getInput();
        
        print("Address: ");
        String address = getInput();
        
        print("Phone number: ");
        String number = getInput();
        
        println("Review account details. \n");
        println("Name: " + name);
        println("Username: " + username);
        println("Password: " + password);
        println("Address: " + address);
        println("Phone: " + number);
        
        println("\nGood? (yes/restart)");
        String response = scanner.next();
        
        if (response.equals("yes")) {
            println("We are making you account!");
            try {
                User.InsertUser(name, username, 0, password, address, number, app.connection);
                User user = new User(name, username, 0, password, address, number);
                app.setCurrentUser(user);
                // go to the home page
                HomePage homePage = new HomePage(app);
                app.setNextPage(homePage);
                
            } catch (Exception e) {
                println("could not add user");
            }
            
        }
        else {
            // will go back to beginning of new user page
        }
        
        
    }
}
