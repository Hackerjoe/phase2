package airbnb;


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
        println("3 = create house.");
        
        int input = Integer.parseInt(getInput());
        
        if (input == 1) {
            FindHotelPage findHotelPage = new FindHotelPage(app);
            app.setNextPage(findHotelPage);
        }
        else if (input == 2) {
            showAccountDetails();
        }
        else if(input == 3)
        {
        	try {
				House.CreateHotel(this.scanner, app.connection, app.getCurrentUser());
			} catch (Exception e) {
				println("Unable to create house.");
			}
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
