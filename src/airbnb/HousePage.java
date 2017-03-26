package airbnb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class HousePage extends Page {

	public int Hid;
	public HousePage(Application app,int Hid) {
		super(app);
		this.Hid = Hid;
	}
	
    public void show()
    {
		int option = -1;
    	System.out.println("Choose option for hotel. (type 0 to go back)");
		System.out.println("1) Reserve hotel.");
		System.out.println("2) Check dates.");
		System.out.println("3) Give feedback.");
		println("4) See feedback on this hotel");
		println("5) Rate feedback");
		println("6) set as favorite.");
		

		option = scanner.nextInt();
		
		if(option == 1)
		{
			ReserveHotel(app.connection,this.scanner);
		}
		if(option == 2)
		{
			try 
			{
				House.GetAvailableDates(app.connection, this.Hid);
			} catch (Exception e) {
				System.out.println("Unable to find dates.");
			}
		}
		if(option == 3)
		{
			this.CreateFeedback();
		}
		if(option == 4)
		{
			this.seeFeeback();
		}
		if(option == 5)
		{
			scoreFeedback();
		}
		if(option == 6)
		{
			try {
				User.setHouseAsFavorite(app.connection, this.Hid,app.getCurrentUser().login);
				println("This hotel has been set as one of your favorites");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public void scoreFeedback()
    {
    	println("Enter the feedback id (fid)");
    	int fid = scanner.nextInt();
    	int score = -1;
    	while(score < 0 || score > 2)
    	{
    		println("Please enter a score for this feeback between 0-2");
    		score = scanner.nextInt();
    	}
    	try {
			User.RateFeedback(app.connection, fid, app.getCurrentUser().login, score);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void seeFeeback()
    {
    	List<FeedBack> feeds= new ArrayList<FeedBack>();
    	try {
			feeds = House.getFeedBackForHouse(app.connection, this.Hid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(FeedBack feed : feeds)
    	{
    		println("--> FID: "+feed.fid+" User: "+feed.Login+" Score: " +feed.score);
    		println("Message: " + feed.message);
    	}
    }
    
    public void CreateFeedback()
    {
    	int score = -1;
    	while(score < 0 || score > 10)
    	{
    		println("Give a score from 0 - 10");
    		score = scanner.nextInt();
    	}
    	System.out.print("Write an optional message for the house : ");
    	String message = scanner.next();
    	try {
			House.CreateFeedBack(app.connection, app.getCurrentUser().login, this.Hid, message, score);
		} catch (Exception e) {
			println("-----------You have already created feedback.-----------------");
		}
    }
    
    public void ReserveHotel(Connector con, Scanner scan)
	{
		String StartDate,EndDate;
		System.out.println("Input start date of reservation.");
		StartDate = scan.next();
		System.out.println("Input end date of reservation.");
		EndDate = scan.next();
		
		try {
			float PPN = House.CheckIfDatesAreAvailable(con, this.Hid, StartDate, EndDate);
			if(PPN != -1)
			{
				
				DateFormat formatter ; 
				Date sd,ed; 
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				sd =  formatter.parse(StartDate);
				ed = formatter.parse(EndDate);
				int daysBetween = (int)( (ed.getTime() - sd.getTime()) / (1000 * 60 * 60 * 24) );
				if(daysBetween <= 0)
					System.out.println("Those dates do not work.");
				float cost = PPN * daysBetween;
				println("Cost would be: $"+cost);
				House.SQLReserveDate(con, StartDate, EndDate, cost, this.app.getCurrentUser().login, this.Hid);
			}
			else
			{
				System.out.println("The dates you have selected are not Available.");
			}
		} catch (Exception e) {
			System.out.println("Unable to reserve dates. =(");
	           System.err.println(e.getMessage());
		}
	}
	
	public void ReserveDates(String Start, String End)
	{
		
	}

}
