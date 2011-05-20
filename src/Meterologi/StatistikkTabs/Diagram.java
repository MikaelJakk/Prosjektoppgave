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
	int xcord, ycord, oldxcord,oldycord;
	int[] minPixelVerdier;
	int[] maxPixelVerdier;
	private Graphics2D panelgrafikk;
	private Graphics2D tegneflate;
	int grafBredde = 50;
	int graLengde = 10;
	

	//Setter bkgrunsfarge og størrelse
	public Diagram()
	{
		setBackground (Color.black);
		setPreferredSize(new Dimension (475,430));
		
		
		//settInnMaxGjennomsnittVerdier(g,n);
	}
	

	//Diagram GUI-----------------------------------------------
	
	 public void setPanelgrafikk()
	  {
	  	Graphics g = getGraphics();
	  	tegneflate = (Graphics2D) g;
	  	
	  	int x=50,y=10,width=50,height=420;
		int x2=50,y2=215,width2=470,height2=215;
		int startgrady = 20;
		int startgradx = 10;
		int startgradMånedY = 255;
		int startgradMånedX = 60;
		//Setter X og Y akse inn i Diagrammet
		tegneflate.setColor (Color.blue);
		tegneflate.drawLine(x, y, width, height);
		repaint();
		//------------------------------------------------
		tegneflate.setColor (Color.blue);
		tegneflate.drawLine(x2, y2, width2, height2);
		repaint();
		//------------------------------------------------
		
		// Genererer grafen ved hjelp av for-løkker
		int xtall = 40;
		for(int i = 0; i < 17; i++)//setter tall og streker på y-aksen
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
		
		String[] år = getAkseString();
		for(int a = 0; a < år.length; a++)//setter måneder og streker på x-aksen
		{
			//årene som kommer som blir vist langs X-Aksen blir hentet ut av String[] år
			tegneflate.setColor (Color.red);
			tegneflate.drawString(år[a], startgradMånedX, startgradMånedY);
			tegneflate.setColor(Color.blue);
			tegneflate.drawLine(startgradMånedX, 215, startgradMånedX, 215-5);
			repaint();
			startgradMånedX += 35;
		}
	  }
	
	public void setTegnefarge( Color farge )
	{
	  	if ( panelgrafikk != null )
	  		panelgrafikk.setPaint(farge);
	}
	
	public void blankUtTegning()
	{
	    repaint();
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
			int startgradMånedY = 255;
			int startgradMånedX = 60;
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
			
			// Genererer grafen ved hjelp av for-løkker
			int xtall = 40;
			for(int i = 0; i < 17; i++)//setter tall og streker på y-aksen
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
			
			String[] år = getAkseString();
			for(int a = 0; a < år.length; a++)//setter måneder og streker på x-aksen
			{
				//årene som kommer som blir vist langs X-Aksen blir hentet ut av String[] år
				tegneflate.setColor (Color.red);
				tegneflate.drawString(år[a], startgradMånedX, startgradMånedY);
				tegneflate.setColor(Color.blue);
				tegneflate.drawLine(startgradMånedX, 215, startgradMånedX, 215-5);
				repaint();
				startgradMånedX += 35;
			}
				//---------
			//Slutt på generering av graf
	}*/
	public void tegneMinKoordinater(Graphics g)
	{
		
	}
	
	
	public void tegMinGraf(Graphics g)
	{
		super.paintComponent (g);
		setBackground (Color.black);
		setPreferredSize(new Dimension (475,430));
		
		int a = minPixelVerdier[0];
		int b = minPixelVerdier[1];
			
			g.drawLine(50, a, 53, b);
	}
	
	
	public String[] getAkseString()
	{
		String[] mellomlager = {"1970","1971","1972","1973"};
		
		//mellomlager = snittemp.getAkseString();
		return mellomlager;
	}
	
	//Returnerer minTempGjennomsnittsGrafKoordinaten
	public void getMinPixelVerdi(double gammelVerdi, double nyVerdi)
	{//Denne metoden skal generere verdier ved hjelp av en algoritme, som skal sendes til metoden: SettInnVerdier().

		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
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
		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
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
}




