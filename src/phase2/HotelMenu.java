package phase2;

public class HotelMenu 
{
	public HotelMenu()
	{
		
	}
	
	void ShowMenu()
	{
		int option = -1;
		
		while(option != 0)
		{
			System.out.println("Choose option for hotel. (type 0 to go back)");
			System.out.println("1) Reserve hotel.");
			System.out.println("2) Visit hotel.");
			System.out.println("3) Favorite hotel.");
			System.out.println("4) Write feedback for hotel.");
			System.out.println("5) See feedback");
			System.out.println("6) See top feedback");
		}
	}

}
