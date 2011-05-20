package Meterologi.Diagram;

import java.awt.*;

import javax.swing.*;

public class Diagram extends JPanel{

	private Graphics2D ark;
	private double engrad = 5;
	
	public Diagram()
	{
		setBackground(Color.black);
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension (500,500);
	}
	
	public void setGrafikk()
	{
		Graphics g = getGraphics();
		ark = (Graphics2D) g;
	}
	
	public void setTegneFarge(Color f)
	{
		if(ark != null)
			ark.setPaint(f);
	}
	
	public void blankUt()
	{
		repaint();
	}
	
	public void tegnStrek(int frax, int fray, int tilx, int tily)
	{
		ark.drawLine( frax, fray, tilx, tily);
	}	
	
	public void tegnAkseString(String år,int x, int y)
	{
			ark.drawString(år, x,y);
	}
	
	public int konverterMinTemp(double y)
	{
		int nyverdi = (int) (y*(engrad));//(int) (y/50*100*(engrad));
		
		return( 250 - nyverdi);
	}
	public int konverterMaxTemp(double y)
	{
		int nyverdi = (int) (y*(engrad));//(y/50*100*(engrad));
		
		return( 250 + nyverdi);
	}
	
	public int konverterVerdi(double y)
	{
		if(y < 0)
			return konverterMinTemp(y);
		else if(y>0)
			return konverterMaxTemp(y);
		
		return 250;
	}
}
