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
		setPreferredSize(new Dimension (550,500));
	}
	
	//Diagram GUI-----------------------------------------------
	public void paintComponent (Graphics tegneflate)
	{
			super.paintComponent (tegneflate);
			
			//Initsialiserer Diagram verdier
			//AKSE verdier-------------------
			int x=50,y=20,width=50,height=460;
			int x2=50,y2=460,width2=525,height2=460;
			//Y-Akse verdier-----------------
			String texty1 = "-60.0";
			int stringx1 = 10, stringy1 = 450;
			//-------------------------------
			String texty2 = "-50.0";
			int stringx2 = 10, stringy2 = 420;
			//-------------------------------
			String texty3 = "-40.0";
			int stringx3 = 10, stringy3 = 390;
			//-------------------------------
			String texty4 = "-30.0";
			int stringx4 = 10, stringy4 = 360;
			//-------------------------------
			String texty5 = "-20.0";
			int stringx5 = 10, stringy5 = 330;
			//-------------------------------
			String texty6 = "-10.0";
			int stringx6 = 10, stringy6 = 300;
			//-------------------------------
			String texty7 = "-5.0";
			int stringx7 = 10, stringy7 = 270;
			//-------------------------------
			String texty8 = "0.0";
			int stringx8 = 10, stringy8 = 240;
			//-------------------------------
			String texty9 = "5.0";
			int stringx9 = 10, stringy9 = 210;
			//-------------------------------
			String texty10 = "10.0";
			int stringx10 = 10, stringy10 = 180;
			//-------------------------------
			String texty11 = "15.0";
			int stringx11 = 10, stringy11 = 150;
			//-------------------------------
			String texty12 = "20.0";
			int stringx12 = 10, stringy12 = 120;
			//-------------------------------
			String texty13 = "25.0";
			int stringx13 = 10, stringy13 = 90;
			//-------------------------------
			String texty14 = "30.0";
			int stringx14 = 10, stringy14 = 60;
			//-------------------------------
			String texty15 = "40.0";
			int stringx15 = 10, stringy15 = 30;
			//Slutt på Y-Akse verider----------
			
			//X-Akse Verdier-----------------
			String textx1 = "Jan";
			int stringxx1 = 60, stringyy1 = 485;
			//-------------------------------
			String textx2 = "Feb";
			int stringxx2 = 100, stringyy2 = 485;
			//-------------------------------
			String textx3 = "Mar";
			int stringxx3 = 140, stringyy3 = 485;
			//-------------------------------
			String textx4 = "Apr";
			int stringxx4 = 180, stringyy4 = 485;
			//-------------------------------
			String textx5 = "Mai";
			int stringxx5 = 220, stringyy5 = 485;
			//-------------------------------
			String textx6 = "Jun";
			int stringxx6 = 260, stringyy6 = 485;
			//-------------------------------
			String textx7 = "Jul";
			int stringxx7 = 300, stringyy7 = 485;
			//-------------------------------
			String textx8 = "Aug";
			int stringxx8 = 340, stringyy8 = 485;
			//-------------------------------
			String textx9 = "Sep";
			int stringxx9 = 380, stringyy9 = 485;
			//-------------------------------
			String textx10 = "Okt";
			int stringxx10 = 420, stringyy10 = 485;
			//-------------------------------
			String textx11 = "Nov";
			int stringxx11 = 460, stringyy11 = 485;
			//-------------------------------
			String textx12 = "Des";
			int stringxx12 = 500, stringyy12 = 485;
			//Slutt på X-Akse Verdier--------------
			
			//Setter X og Y akse inn i Diagrammet
			tegneflate.setColor (Color.green);
			tegneflate.drawLine(x, y, width, height);
			repaint();
			//------------------------------------------------
			tegneflate.setColor (Color.blue);
			tegneflate.drawLine(x2, y2, width2, height2);
			repaint();
			//Setter Y-Akse verdier inn i Diagrammet----------
			//----------------------EN------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty1, stringx1, stringy1);
			repaint();
			//----------------------TO------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty2, stringx2, stringy2);
			repaint();
			//----------------------TRE-----------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty3, stringx3, stringy3);
			repaint();
			//----------------------FIRE-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty4, stringx4, stringy4);
			repaint();
			//----------------------FEM--------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty5, stringx5, stringy5);
			repaint();
			//----------------------SEKS-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty6, stringx6, stringy6);
			repaint();
			//----------------------SYV--------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty7, stringx7, stringy7);
			repaint();
			//----------------------ÅTTE-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty8, stringx8, stringy8);
			repaint();
			//----------------------NI---------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty9, stringx9, stringy9);
			repaint();
			//----------------------TI---------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty10, stringx10, stringy10);
			repaint();
			//----------------------ELEVE------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty11, stringx11, stringy11);
			repaint();
			//----------------------TOLV-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty12, stringx12, stringy12);
			repaint();
			//----------------------TRETTEN----------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty13, stringx13, stringy13);
			repaint();
			//----------------------FJORTEN----------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty14, stringx14, stringy14);
			repaint();
			//----------------------FEMTEN-----------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(texty15, stringx15, stringy15);
			repaint();
			//Slutt Y-Akse Verdier inn i Diagrammet--------------
			
			//Setter X-Akse Verdier inn i Diagrammet----------
			//----------------------EN------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx1, stringxx1, stringyy1);
			repaint();
			//----------------------TO------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx2, stringxx2, stringyy2);
			repaint();
			//----------------------TRE-----------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx3, stringxx3, stringyy3);
			repaint();
			//----------------------FIRE-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx4, stringxx4, stringyy4);
			repaint();
			//----------------------FEM--------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx5, stringxx5, stringyy5);
			repaint();
			//----------------------SEKS-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx6, stringxx6, stringyy6);
			repaint();
			//----------------------SYV--------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx7, stringxx7, stringyy7);
			repaint();
			//----------------------ÅTTE-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx8, stringxx8, stringyy8);
			repaint();
			//----------------------NI---------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx9, stringxx9, stringyy9);
			repaint();
			//----------------------TI---------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx10, stringxx10, stringyy10);
			repaint();
			//----------------------ELEVE------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx11, stringxx11, stringyy11);
			repaint();
			//----------------------TOLV-------------------------
			tegneflate.setColor (Color.red);
			tegneflate.drawString(textx12, stringxx12, stringyy12);
			repaint();
			//Endt of Diagram GUI---------------------------------
			
			
			//Her vil metodene som setter reelle data inn i diagrammet bli kalt opp.
			/*
			 * 
			 * 
			 */
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




