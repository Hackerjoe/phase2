package phase2;
import java.util.*;
public class Test 
{
	public static void main(String[] args) 
	{
		try{
			Connector con= new Connector();
			System.out.print("CONNECTED");
			con.closeConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
