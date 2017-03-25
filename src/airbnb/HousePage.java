package airbnb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

		option = scanner.nextInt();
		
		if(option == 1)
		{
			
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
			}
			else
			{
				System.out.println("The dates you have selected are not Available.");
			}
		} catch (Exception e) {
			System.out.println("Unable to reserve dates. =(");
		}
	}
	
	public void ReserveDates(String Start, String End)
	{
		
	}

}
