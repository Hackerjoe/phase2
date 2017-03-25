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
		System.out.println("You are logged in as " + this.CurrentUser.UserName+".");
		System.out.println("--Options--");
		System.out.println("1) Make Reservation.");
		System.out.println("2) Create Hotel.");
		
		int option = scan.nextInt();
		
		if(option == 1)
		{
			
		}
		if(option == 2)
		{
			Hotel.CreateHotel(scan, con, CurrentUser);
		}
	}
}
