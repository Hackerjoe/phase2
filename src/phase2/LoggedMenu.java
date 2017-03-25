package phase2;

import java.sql.Connection;
import java.util.*;
public class LoggedMenu 
{
	User CurrentUser;
	public LoggedMenu(User CurrentUser)
	{
		this.CurrentUser = CurrentUser;
	}
	
	public void ShowMenu(Connector con, Scanner scan)
	{
		int option = -1;
		while(option != 0)
		{
			System.out.println("You are logged in as " + this.CurrentUser.UserName+".");
			System.out.println("--Options-- (Type 0 (zero) to go back)");
			System.out.println("1) Find Hotel");
			System.out.println("2) Create Hotel.");
			
			
			
			option = scan.nextInt();
			
			if(option == 1)
			{
				FindHotelMenu menu = new FindHotelMenu();
				menu.ShowMenu(con, scan);
				
			}
			if(option == 2)
			{
				try {
					Hotel.CreateHotel(scan, con, CurrentUser);
				} catch (Exception e) {
					System.out.println("Unable to create hotel!");
				}
			}
		}
	}
}
