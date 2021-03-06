/*
 * Skrevet at Thomas Nordengen 15.Mai 2011
 */

package Meterologi.StatistikkTabs;
import  javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Diagram extends JPanel
{
	private int xcord, ycord, oldxcord,oldycord;
	private Graphics2D tegneflate;
	private int grafBredde = 50;
	private int graLengde = 10;
	private int x=50,y=10,width=50,height=420;
	private int x2=50,y2=215,width2=470,height2=215;
	private int startgrady = 20;
	private int startgradx = 10;
	private int startgradM�nedY = 255;
	private int startgradM�nedX = 60;
	

	//Setter bkgrunsfarge og st�rrelse
	public Diagram()
	{
		setBackground (Color.black);
		setPreferredSize(new Dimension (475,430));
	}

	//Diagram GUI-----------------------------------------------
	
	 public void setPanelgrafikk()
	  {
	  	Graphics g = getGraphics();
	  	tegneflate = (Graphics2D) g;
	  }
	 
	 public void tegnAkser()
	 {
		//Setter X og Y akse inn i Diagrammet
			setTegnefarge(Color.blue);
			tegneflate.drawLine(x, y, width, height);
			repaint();
			//------------------------------------------------
			tegneflate.setColor(Color.blue);
			tegneflate.drawLine(x2, y2, width2, height2);
			repaint();
			//------------------------------------------------
			
			// Genererer grafen ved hjelp av for-l�kker
			int xtall = 40;
			for(int i = 0; i < 17; i++)//setter tall og streker p� y-aksen
			{				
				tegneflate.setColor (Color.red);
				tegneflate.drawString(xtall+"", startgradx, startgrady);
				tegneflate.setColor (Color.blue);
				tegneflate.drawLine(50, startgrady, 53,startgrady);
				repaint();
				xtall = xtall-5;
				repaint();
				startgrady += 25;
			}
	 }
	 
	public void setAkseString(String[] lb)
	{
		String[] �r = lb;
		for(int a = 0; a < �r.length; a++)//setter m�neder og streker p� x-aksen
		{
			//�rene som kommer som blir vist langs X-Aksen blir hentet ut av String[] �r
			tegneflate.setColor (Color.red);
			tegneflate.drawString(�r[a], startgradM�nedX, startgradM�nedY);
			tegneflate.setColor(Color.blue);
			tegneflate.drawLine(startgradM�nedX, 215, startgradM�nedX, 215-5);
			repaint();
			startgradM�nedX += 35;
		}
	}
	
	public void setTegnefarge( Color farge )
	{
	  	if ( tegneflate != null )
	  		tegneflate.setPaint(farge);
	}
	
	public void tegnMinGraf(int gammel, int ny)
	{
		
		tegneflate.drawLine(50, 50, 50, 50);
		repaint();
	}
	
	public void tegnMaxGraf(int gammel, int ny)
	{
		
		tegneflate.setColor (Color.red);
		tegneflate.drawLine(grafBredde, gammel, graLengde, ny);
		repaint();
	}
	
	/*public void paintComponent()
	{
		Graphics g = getGraphics();
	  	tegneflate = (Graphics2D) g;
	  	setBackground (Color.white);
		setPreferredSize(new Dimension (475,430));
		
			super.paintComponent (tegneflate);
			int x=50,y=10,width=50,height=420;
			int x2=50,y2=215,width2=470,height2=215;
			int startgrady = 20;
			int startgradx = 10;
			int startgradM�nedY = 255;
			int startgradM�nedX = 60;
			int startlengdea = 50;
			int startlengdeb = 10;
			//Setter X og Y akse inn i Diagrammet
			tegneflate.setColor (Color.blue);
			tegneflate.drawLine(x, y, width, height);
			repaint();
			//------------------------------------------------
			tegneflate.setColor (Color.blue);
			tegneflate.drawLine(x2, y2, width2, height2);
			repaint();
			//------------------------------------------------
			
			// Genererer grafen ved hjelp av for-l�kker
			int xtall = 40;
			for(int i = 0; i < 17; i++)//setter tall og streker p� y-aksen
			{				
				tegneflate.setColor (Color.red);
				tegneflate.drawString(xtall+"", startgradx, startgrady);
				tegneflate.setColor (Color.blue);
				tegneflate.drawLine(50, startgrady, 53,startgrady);
				repaint();
				xtall = xtall-5;
				repaint();
				startgrady += 25;
			}
			
			String[] �r = getAkseString();
			for(int a = 0; a < �r.length; a++)//setter m�neder og streker p� x-aksen
			{
				//�rene som kommer som blir vist langs X-Aksen blir hentet ut av String[] �r
				tegneflate.setColor (Color.red);
				tegneflate.drawString(�r[a], startgradM�nedX, startgradM�nedY);
				tegneflate.setColor(Color.blue);
				tegneflate.drawLine(startgradM�nedX, 215, startgradM�nedX, 215-5);
				repaint();
				startgradM�nedX += 35;
			}
				//---------
			//Slutt p� generering av graf
	}*/
	public void tegneMinKoordinater(Graphics g)
	{
		
	}
	
	public String[] getAkseString()
	{
		String[] mellomlager = {"70","71","72","73","74","75","76","77","78","79","80","81","82"};

		return mellomlager;
	}
	
	//Returnerer minTempGjennomsnittsGrafKoordinaten
	public void getMinPixelVerdi(double gammelVerdi, double nyVerdi)
	{//Denne metoden skal generere verdier ved hjelp av en algoritme, som skal sendes til metoden: SettInnVerdier().

		//Gj�r om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		int gammelMellom = (int)(gammelVerdi/40*100*2);
		int nyMellom = (int)nyVerdi/40*100*2;
		
		int gammelGrafVerdi = 200 + gammelMellom + 20;
		int nyGrafVerdi = 200 + nyMellom + 20;
		int[] min = new int[2];
		min[0] = gammelGrafVerdi;
		min[1] = nyGrafVerdi;
		
		tegnMinGraf( min[0], min[1]);	
	}
	
	//Returnerer maxTempGjennomsnittsGrafKoordinaten
	public void getMaxPixelVerdi(double gammelVerdi, double nyVerdi)
	{
		//Gj�r om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		int gammelMellom = (int)(gammelVerdi/40*100*2);
		int nyMellom = (int)nyVerdi/40*100*2;
		
		int gammelGrafVerdi = 200 + gammelMellom + 20;
		int nyGrafVerdi = 200 + nyMellom + 20;
		int[] max = new int[2];
		max[0] = gammelGrafVerdi;
		max[1] = nyGrafVerdi;
		
		tegnMaxGraf( max[0], max[1]);	
	}


	public void refresh()
	{
		repaint();
	}
	
	public void tegnMinGrag(double[] snittMinTemp)
	{
		
	}
}




