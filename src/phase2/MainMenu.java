package phase2;
import java.util.*;


public class MainMenu 
{
	public static void main(String[] args) 
	{
		
		try
		{
			Connector con= new Connector();
			System.out.println("-- Connected to database. --");
			System.out.println("--- Main Menu ---");
			System.out.println("- Options -");
			System.out.println("- Type the number of the option you want. -");
			System.out.println("1) Create new user.");
			System.out.println("2) Login to a user.");
			Scanner scan = new Scanner(System.in);
			scan.useDelimiter("\\n");
			int option = scan.nextInt();
			
			if(option == 1)
			{
				try
				{
					User newUser = new User();
					newUser.CreateUser(scan, con);
				}
				catch(Exception e)
				{
					System.out.println("Unable to create new user.");
					e.printStackTrace();
				}
			}
			else if(option == 2)
			{
				try
				{
					User loginUser = new User();
					loginUser.UserLogin(scan, con);
					System.out.println("Welcome "+loginUser.UserName+".");
				}
				catch(Exception e)
				{
					System.out.println("Unable to Login.");
					e.printStackTrace();
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("-- Unable to connect. --");
			e.printStackTrace();
		}
		
	
		
	}
}
