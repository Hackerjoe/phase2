package airbnb;

import java.util.ArrayList;
import java.util.List;

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
        println("2 = show account details");
        println("3 = create house.");
        println("4 = show house you own.");
        println("5 = Show your reserves");
        println("6 = Visit you reserved houses");
        println("7 = Show your Visits");
        
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
        else if(input == 4)
        {
        	try {
				House.getHouseOwnBy(app.getCurrentUser().login, app.connection);
			} catch (Exception e) {
				println("Unable to get your houses.");
			}
        }
        else if(input == 5)
        {
        	
        	try {
				List<Reserve>rList = User.getReserves(app.getCurrentUser().login, app.connection);
				for(Reserve r : rList)
				{
					println("--> hid: "+ r.hid + " Cost: "+r.cost);
					Period p = House.getPeriod(r.pid, app.connection);
					println("--> From: "+p.start+ " To: "+p.end);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(input == 6)
        {
        	try {
				this.addVisits();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(input == 7)
        {
        	try {
				User.printVisits(app.getCurrentUser().login, app.connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
    
    private void addVisits() throws Exception
    {
    	List<Reserve>rList = User.getReserves(app.getCurrentUser().login, app.connection);
    	int count = 1;
    	for(Reserve r : rList)
		{
    		println("----- "+ count +" -----");
			println("--> hid: "+ r.hid + " Cost: "+r.cost);
			Period p = House.getPeriod(r.pid, app.connection);
			println("--> From: "+p.start+ " To: "+p.end);
			count ++;
		}
    	println("Type the item number you have visted/stayed.");
    	println("Type 0 to confirm you stays.");
    	int option = -1;
    	List<Integer> options = new ArrayList<Integer>();
    	while(option != 0)
    	{
    		option = scanner.nextInt();
    		if(option == 0)
    			break;
    		if(!options.contains(option) && option != -1)
    		{
    			options.add(option);
    		}
    		println("You have choosen: ");
    		for(Integer number : options)
    		{
    			System.out.print(" "+number+",");
    		}
    		
    	}
    	
    	for(Integer number : options)
		{
    		Reserve res = rList.get(number-1);
    		try
    		{
    			User.addVisit(res.cost, app.getCurrentUser().login, res.hid, res.pid, app.connection);
    		}
    		catch(Exception e)
    		{
    			println("Unable to add visit. You may have already visited.");
    		}
		}
		
    	
    }

}
