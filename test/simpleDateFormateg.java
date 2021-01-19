import java.util.*;
import java.text.*;
class simpleDate
{
	public static void main(String gg[])
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		System.out.println(sdf.format(new java.util.Date()));
		
	}
}