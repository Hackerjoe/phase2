package Airbnb;

class HomePage extends Page {
    
    private User user;

    public HomePage(Application app) {
        super(app);
        user = app.getCurrentUser();
    }
    
    @Override
    public void show() {
        println("   Home Page");
        showOptions();
    }
    
    private void showOptions() {
        println("1 = find house");
        println("2 = show account details\n");
        
        int input = Integer.parseInt(getInput());
        
        if (input == 1) {
            FindHotelPage findHotelPage = new FindHotelPage(app);
            app.setNextPage(findHotelPage);
        }
        else if (input == 2) {
            showAccountDetails();
        }
    }
    
    private void showAccountDetails() {
        println("");
        println("Name: " + user.name);
        println("Login: " + user.login);
        println("Address: " + user.address);
        println("Phone: " + user.phonenumber + "\n");
    }

}
