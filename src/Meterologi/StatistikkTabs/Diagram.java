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
	Random generator = new Random();
	int[] maksGjennomsnitt = new int[2];
	int[] minstGjennomsnitt = new int[2];
	private SnittTemp snittemp;
	int[][] a2 = new int [2][12];
	int[] aa;
	int[] bb;
	int teller;
	int xcord, ycord, oldxcord,oldycord;


	//Setter bkgrunsfarge og størrelse
	public Diagram(double g, double n)
	{
		setBackground (Color.black);
		setPreferredSize(new Dimension (475,430));
		teller = 1;
		//settInnMaxGjennomsnittVerdier(g,n);
	}
	
	//Her vil metodene som setter reelle data inn i diagrammet bli kalt opp.
	public int[] settInnMaxGjennomsnittVerdier(double gammelGrad, double nyGrad)
	{
		//kode for generell insetting av data i tabellen
		//tegneflate.setColor (Color.blue);
		//tegneflate.drawLine(x, y, width, height);
		//repaint(); //Viktig med repaint()! 
		//Det er repaint som tillater progranmmet å kunen tegne nye komponenter inn i programmet!
		maksGjennomsnitt = getMaxPixelVerdi(gammelGrad, nyGrad);
		
		return maksGjennomsnitt;
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
			
			//Setter grafen for verdiene inn i Diagrammet
				tegneflate.setColor (Color.green);
				
				/*int gammel = (int)maksGjennomsnitt[0];
				int ny = (int)maksGjennomsnitt[1];
				int lengde1 = 50;
				int lengde2 = 60;
				
				tegneflate.drawLine(lengde1, gammel, lengde2, ny);
				repaint();*/
				if(teller != 2)
				{
					aa = new int[12];
					bb = new int[12];
					for(int r=0; r < 12; r++ )
					{			
						aa[r] = a2[0][r];
						bb[r] = a2[1][r];	
						
						tegneflate.drawLine(startlengdea, aa[r], startlengdeb, bb[r]);
						repaint();
					}
				}
				else
				{
					teller = 2;
				}
				
				//---------
			//Slutt på generering av graf
	}
	
	public int getArrayStørrelse()
	{
		int størrelse = 12;
		/*try
		{
			størrelse = snittemp.antallÅr();
		}
		catch(Exception e){JOptionPane.showMessageDialog(null,"Null antall år!");}
			*/
		
		return størrelse;
	}
	public void tegnGraf(int fra, int til)
	{	
		double gjennomsnittene[] = new double[til-fra+1];
		double gammel = 0;
		double ny;
		int[] maxGrafVerdi;
		a2 = new int [2][12];
		
		
		double[] temp = new double[til-fra+1];
		
		int[] array = new int[til-fra+1];
		for(int i = fra; i <= til; i++)
		{
			array[i-fra] = i;
			temp[i-fra] = snittemp.stedliste.getGjennomsnittMaxTempIÅr(array[i-fra]);
			
			gjennomsnittene[i-fra] = temp[i-fra];
		}
		
		for(int y = 0; y < gjennomsnittene.length; y++)
		{
			ny = gjennomsnittene[y];
			maxGrafVerdi = getMaxPixelVerdi(gammel,ny);
			
			
			for(int b = 1; b <gjennomsnittene.length; y++)
			{
				a2[0][y] = maxGrafVerdi[y];
				a2[1][b] = maxGrafVerdi[b];
			}
		}	
	}
	
	
	public String[] getAkseString()
	{
		String[] mellomlager = {"1970","1971","1972","1973"};
		
		//mellomlager = snittemp.getAkseString();
		return mellomlager;
	}
	
	//Returnerer minTempGjennomsnittsGrafKoordinaten
	public int[] getMinPixelVerdi(double gammelVerdi, double nyVerdi)
	{//Denne metoden skal generere verdier ved hjelp av en algoritme, som skal sendes til metoden: SettInnVerdier().

		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		int gammelMellom = (int)(gammelVerdi/40*100*2);
		int nyMellom = (int)nyVerdi/40*100*2;
		
		int gammelGrafVerdi = 200 + gammelMellom + 20;
		int nyGrafVerdi = 200 + nyMellom + 20;
		int[] min = new int[2];
		min[0] = gammelGrafVerdi;
		min[1] = nyGrafVerdi;
		
		return min;
	}
	//Returnerer maxTempGjennomsnittsGrafKoordinaten
	public int[] getMaxPixelVerdi(double gammelVerdi, double nyVerdi)
	{
		//Gjør om til prosentandel av 40Grader, som er maxVerdi i Diagrammet
		int gammelMellom = (int)(gammelVerdi/40*100*2);
		int nyMellom = (int)nyVerdi/40*100*2;
		
		int gammelGrafVerdi = 200 + gammelMellom + 20;
		int nyGrafVerdi = 200 + nyMellom + 20;
		int[] max = new int[2];
		max[0] = gammelGrafVerdi;
		max[1] = nyGrafVerdi;
		
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




