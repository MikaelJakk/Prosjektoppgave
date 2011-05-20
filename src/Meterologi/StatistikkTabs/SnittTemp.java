/*
 * Skrevet av Thomas Nordengen 18 Mai 2011
 */

package Meterologi.StatistikkTabs;


import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;
import Meterologi.Diagram.*;

public class SnittTemp extends Lista implements ActionListener
{
	private final int START�R = 1970;

	//fra og til dato bokser
	private JComboBox fra�rboks;
	
	//mellomlagring av dag mnd og �r
	private int fra�r;

	public JPanel ByggPanel() //utseende
	{		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel �rpanel = new JPanel();
		�rpanel.setLayout(new GridLayout(3,0));
		JPanel knappepanel = new JPanel();
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(4,0));

		//innputfeltet for dato
		JPanel datopanel = new JPanel();
		JPanel datopanel2 = new JPanel();
		//fra dato bokser
		datopanel.add(new JLabel("Fra �r:"));
		fra�rboks = new JComboBox(makeyeararray());
		fra�rboks.addActionListener(this);
		datopanel.add(fra�rboks);
		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);

		�rpanel.add(toppanel);
		knappepanel.add(�rpanel);
		
		panel.add(knappepanel,BorderLayout.WEST);
		panel.setVisible(true);
				
		return panel;
	}
	
	private String[] makeyeararray()
	{
		return makearray(START�R, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	private String[] makearray(int fra, int til)
	{
		String[] array = new String[til-fra+1];
		for(int i = fra; i <= til; i++)
		{
			array[i-fra] = i + "";
		}
		return array;
	}
	
	public void melding(String m)
	{
		JOptionPane.showMessageDialog(null,m, "OBS!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean getDatoVerdier()
	{
		fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		return true;
	}//end of metoder for standard gui og valg av �r 
	
	public double[] makeSnittMinTempArray(int fra, int til)
	{
		double[] array = new double[til-fra-1];
		
		for(int i = 0; i<(til-fra-1); i++)
		{
			array[i] = stedliste.getGjennomsnittMinTempI�r(fra++);
		}
		
		return array;
	}
	public double[] makeSnittMaxTempArray(int fra, int til)
	{
		double[] array = new double[til-fra-1];
		
		for(int i = 0; i<(til-fra-1); i++)
		{
			array[i] = stedliste.getGjennomsnittMaxTempI�r(fra++);
		}
		
		return array;
	}

	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == fra�rboks){
			//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
			fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
			
			double[] maxtemparray = makeSnittMaxTempArray(fra�r, fra�r+9);
			double[] mintemparray = makeSnittMinTempArray(fra�r, fra�r+9);
			
			DiagramVindu diagram = new DiagramVindu(fra�r, maxtemparray, mintemparray);
			
		}
	}	
}
	
