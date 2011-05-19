/*
 * Skrevet av Mikael Jakhelln den 8 april 2011
 */

package Meterologi.Lister;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Data{
	
	private Calendar dato;
	private double mintemp;
	private double maxtemp;
	private double nedb�r;
	
	public Data neste;
	
	public Data(Calendar d, double min, double max, double ned)
	{
		dato = d;
		mintemp = min;
		maxtemp = max;
		nedb�r = ned;
	}
	
	public double getMinTemp()
	{
		return mintemp;
	}
	
	public double getMaxTemp()
	{
		return maxtemp;
	}
	
	public double getNedb�r()
	{
		return nedb�r;
	}
	
	public Calendar getDato()
	{
		return dato;
	}
	
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(dato.getTime())+"\t"+mintemp+"�C"+"\t"+maxtemp+"�C"+"\t"+nedb�r+" mm";
	}
	public String getDatoString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(dato.getTime())+"";
	}
}
