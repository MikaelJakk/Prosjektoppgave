package Meterologi.Diagram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiagramVindu extends JFrame
{
	private Diagram diagram;
	private JButton minknapp;
	private JButton maxknapp;
	
	//pekere for Diagram
	private Color sort = Color.black;
	private Color blå = Color.blue;
	private Color grønn = Color.green;
	private Color rød = Color.red;
	
	private int origox = 50;
	private int origoy = 250;

	double[] mintemps = null;
	double[] maxtemps = null;
	int startår = 1970;
	
	public DiagramVindu(int år, double[] mintemparray, double[]maxtemparray)
	{
		super("Diagram");
		startår = år;
		mintemps = mintemparray;
		maxtemps = maxtemparray;
	
		diagram = new Diagram();
		minknapp = new JButton("Vis graf for gjennomsnitts mintemp");
		minknapp.addActionListener(new Knappelytter());
		maxknapp = new JButton("Vis graf for Gjennomsnitts maxtemp");
		maxknapp.addActionListener(new Knappelytter());
		Container c = getContentPane();
		c.add(diagram, BorderLayout.CENTER);
		c.add(minknapp, BorderLayout.SOUTH);
		c.add(maxknapp,BorderLayout.NORTH);
		pack();
		setVisible(true);
		
		diagram.setGrafikk();
	}
	
	public void tegnYAkse()
	{
		diagram.setTegneFarge(Color.red);
		diagram.tegnStrek(origox, 0, origox, 500);//y-akse
		
		int xtall = 50;
		String[] tall = new String[21];
		int ystart = 0;
		int yøkning = 25;
		for(int i = 0; i< 21; i++)
		{
			tall[i] = xtall+"";
			xtall = xtall-5;
			diagram.tegnAkseString(tall[i], origox-20, ystart+5);
			diagram.tegnStrek(origox,ystart,origox+5,ystart);
			ystart+=yøkning;
		}//end of y-akse
	}
	
	public void tegnXAkse(int år)
	{
		diagram.setTegneFarge(Color.red);
		diagram.tegnStrek(origox, origoy, 500,origoy);
		
		int ytall = år++;

		int xstart = 50;
		int xøkning = 45;
		for(int i = 0; i<10;i++)
		{
			diagram.tegnAkseString(ytall+"", xstart, origoy+20);
			diagram.tegnStrek(xstart, origoy-5, xstart, origoy+5);
			ytall++;
			xstart+=xøkning;
		}
		
	}
	
	public void tegnMinGraf(double[] array)
	{
		int startx = 50;
		int starty = 250;

		for(int i=0;i<array.length;i++)
		{
			starty = diagram.konverterMinTemp(array[i]);
			int tily = diagram.konverterMinTemp(array[i+1]);
			if(tily == 0)
				tily = 0;
			diagram.tegnStrek(startx, starty, startx+45,tily);
			startx = startx+45;
		}
	}
	
	public void tegnMaxGraf(double[] array)
	{
		int startx = 50;
		int starty = 250;

		for(int i=0;i<array.length-1;i++)
		{
			starty = diagram.konverterMaxTemp(array[i]);
			int tily = diagram.konverterMaxTemp(array[i+1]);
			if(tily == 0)
				tily = 0;
			diagram.tegnStrek(startx, starty, startx+45,tily);
			startx = startx+45;
		}
	}

	private class Knappelytter implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getSource() == minknapp)
			{
				try{
					tegnYAkse();
					tegnXAkse(startår);
					tegnMinGraf(mintemps);
				}
				catch(Exception ex){}
			}
			else if(e.getSource() == maxknapp)
			{
				try{
					tegnYAkse();
					tegnXAkse(startår);
					tegnMaxGraf(maxtemps);
				}
				catch(Exception ex){}
				
			}
		}
	}
}