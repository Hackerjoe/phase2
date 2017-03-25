package airbnb;

class WelcomePage extends Page {
    
    public WelcomePage(Application app) {
        super(app);
    }
    
    @Override
    public void show() {
        println("       Welcome to Airbnb!");
        println("Are you a new user (yes/no)");
        
        String response = getInput();
        
        if (response.equals("yes")) {
            NewUserPage newUserPage = new NewUserPage(app);
            app.setNextPage(newUserPage);
        }
        else if (response.equals("no")) {
            LogInPage logInPage = new LogInPage(app);
            app.setNextPage(logInPage);
        }
    }


}
