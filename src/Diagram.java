import  javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class Diagram extends JPanel
{
	Random generator = new Random();


	//Setter bkgrunsfarge og størrelse
	public Diagram()
	{
		setBackground (Color.black);
		setPreferredSize(new Dimension (500,500));
	}

	//Diagram GUI-----------------------------------------------
	public void paintComponent (Graphics tegneflate)
	{
			super.paintComponent (tegneflate);


			int x=50,y=20,width=50,height=430;

			int x2=50,y2=230,width2=470,height2=230;

			int startgrady = 30;
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
			String[] temp = {"40","35","30","25","20","15","10","5","0","-5","-10","-15","-20","-25","-30","-35","-40"};
			for(int i = 0; i < 17; i++)
			{				
				tegneflate.setColor (Color.red);
				tegneflate.drawString(temp[i], startgradx, startgrady);
				repaint();
				startgrady += 25;
			}
			
			String[] måned = {"Jan","Feb","Mar","Apr","Mai","Jun","Juli","Aug","Sep","Okt","Nov","Des"};
			for(int a = 0; a < 12; a++)
			{
				tegneflate.setColor (Color.red);
				tegneflate.drawString(måned[a], startgradMånedX, startgradMånedY);
				repaint();
				startgradMånedX += 35;
			}
			//Slutt på generering av graf
			
			
			
			/*//Her vil metodene som setter reelle data inn i diagrammet bli kalt opp.
			public void settInnVerdier(int x, int y, int width, int heigth)
			{

				//kode for generell insetting av data i tabellen
				egneflate.setColor (Color.blue);
				tegneflate.drawLine(x, y, width, height);
				repaint(); //Viktig med repaint()! Det er repaint som tillater progranmmet å kunen tegne  nye komponenter inn i programmet!

			}

			public void tegnDiagramGraf(int x; int y; int wdth; int heigth)
			{//Denne metoden skal generere verdier ved hjelp av en algoritme, som skal sendes til metoden: SettInnVerdier().

				//koordinater
				int x;
				int y;
				int width;
				int heigth;
				//verdier fra minTempSnitt[] og maxTempSnitt[]
				int minTemp;
				int maxTemp;

				//Oversikt over X koordinatene til de forskjellige temperaturene:
				/
					60 = 450, -50 = 420, -40 = 390, -30 = 360, -20 = 330, -10 = 300, -5   = 270
					0    = 240
					5    = 210, 10  = 180 , 15  = 150, 20  = 120, 25  = 90, 30  = 60, 40  = 30


				årstall;//henter årstallet fra riktig metode i stedliste.
				minTempSnitt; //får inn en double[] som inneholder minTempSnitt for ett sted det valgte året
				maxTempSnitt; //får inn en double[] som inneholder maxTempSnitt for ett sted det valgte året

				//Første graf sine koordinaterr:
				//eksempel parameter: int x=50,y=20,width=50,height=460;
				for(int i = 0; i < 13; i++)
				{


					//får innputt!

				}


				//Andre graf sine koordinater:

			}*/


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




