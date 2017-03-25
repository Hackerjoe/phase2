package Airbnb;

class LogInPage extends Page {
    
    public LogInPage(Application app) {
        super(app);
    }
    
    @Override
    public void show() {
        println("Welcome back! Please log in.");
        print("Username: ");
        String username = getInput();
        print("Password: ");
        String password = getInput();
        
        try {
            User user = User.AuthenticateUser(username, password, app.connection);
            if (user != null) {
                println("Welcome back, " + user.name + ". You are logged in.");
                app.setCurrentUser(user);
                // go to the home page
                HomePage homePage = new HomePage(app);
                app.setNextPage(homePage);
            }
        } catch (Exception e) {
            println("Was not able to log in");
        }
            
    }
}
