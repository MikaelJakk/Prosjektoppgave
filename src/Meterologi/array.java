package Meterologi;

public class array {

public static String getArray()
{
	
	String[] enArray;
	enArray = new String[10];
	int hei = 0;
	
	for(int i = 0; i < enArray.length;i++)
	{
		enArray[i] = "hei"+hei+"\t";
		hei++;
	}
	String tekst = "";
	for(int i = 0;i <enArray.length;i++)
	{
		tekst += enArray[i];
	}
	return tekst;
}
	
	public static void main(String [] args)
	{
		System.out.println(getArray());
	}
	
	
}
