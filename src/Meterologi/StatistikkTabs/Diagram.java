package Meterologi.StatistikkTabs;
import  javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class Diagram extends JPanel
{
	Random generator = new Random();
	double[] høyest = new double[2];
	double[] minst = new double[2];


	//Setter bkgrunsfarge og størrelse
	public Diagram(double g, double n)
	{
		setBackground (Color.black);
		setPreferredSize(new Dimension (475,430));
		settInnMaxGjennomsnittVerdier(g,n);
	}
	
	//Her vil metodene som setter reelle data inn i diagrammet bli kalt opp.
	public double[] settInnMaxGjennomsnittVerdier(double gammelGrad, double nyGrad)
	{
		//kode for generell insetting av data i tabellen
		//tegneflate.setColor (Color.blue);
		//tegneflate.drawLine(x, y, width, height);
		//repaint(); //Viktig med repaint()! Det er repaint som tillater progranmmet å kunen tegne  nye komponenter inn i programmet!
		høyest = getMaxPixelVerdi(gammelGrad, nyGrad);
		
		return høyest;
	}

	//Diagram GUI-----------------------------------------------
	public void paintComponent (Graphics tegneflate)
	{
			super.paintComponent (tegneflate);
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
			//String[] temp = {"40","35","30","25","20","15","10","5","0","-5","-10","-15","-20","-25","-30","-35","-40"};
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
			
			String[] måned = {"Jan","Feb","Mar","Apr","Mai","Jun","Juli","Aug","Sep","Okt","Nov","Des"};
			for(int a = 0; a < 12; a++)//setter måneder og streker på x-aksen
			{
				tegneflate.setColor (Color.red);
				tegneflate.drawString(måned[a], startgradMånedX, startgradMånedY);
				tegneflate.setColor(Color.blue);
				tegneflate.drawLine(startgradMånedX, 215, startgradMånedX, 215-5);
				repaint();
				startgradMånedX += 35;
			}
			
			//Setter grafen for verdiene inn i Diagrammet
				tegneflate.setColor (Color.green);
				
				int gammel = (int)høyest[0];
				int ny = (int)høyest[1];
				int lengde1 = 50;
				int lengde2 = 60;
				
				tegneflate.drawLine(lengde1, gammel, lengde2, ny);
				repaint();
				//---------
			//Slutt på generering av graf
	}
	
	//Returnerer minTempGjennomsnittsGrafKoordinaten
	public double[] getMinPixelVerdi(double gammelVerdi, double nyVerdi)
	{//Denne metoden skal generere verdier ved hjelp av en algoritme, som skal sendes til metoden: SettInnVerdier().

		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		double minMellom = gammelVerdi/40*100*2;
		double maxMellom = nyVerdi/40*100*2;
		
		double minGrafVerdi = 200 + minMellom + 20;
		double maxGrafVerdi = 200 + maxMellom + 20;
		double[] min = new double[2];
		min[0] = minGrafVerdi;
		min[1] = maxGrafVerdi;
		
		return min;
	}
	//Returnerer maxTempGjennomsnittsGrafKoordinaten
	public double[] getMaxPixelVerdi(double gammelVerdi, double nyVerdi)
	{
		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		double minMellom = gammelVerdi/40*100*2;
		double maxMellom = nyVerdi/40*100*2;
		
		double minGrafVerdi = 200 - minMellom + 20;
		double maxGrafVerdi = 200 - maxMellom + 20;
		double[] max = new double[2];
		max[0] = minGrafVerdi;
		max[1] = maxGrafVerdi;
		
		return max;	
	}

	
		//skal få inn reelle metrologiske data og sette dem som verdier inn i diagrammet
		//midlertidig satt til randomverdier for debugging av GUI!
		private int[] setArrayVerdier()
		{
			int x = generator.nextInt(299) + 1;
			int y = generator.nextInt(299) + 1;
			int width = generator.nextInt(299) + 1;
			int height = generator.nextInt(299) + 1;

			int verdier[] = new int[4];
			verdier[0] = x;
			verdier[1] = y;
			verdier[2] = width;
			verdier[3] = height;

			return verdier;
		}

	public void refresh()
	{
		repaint();
		setArrayVerdier();
	}
}




