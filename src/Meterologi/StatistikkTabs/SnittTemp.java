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
	private final int STARTÅR = 1970;

	//fra og til dato bokser
	private JComboBox fraårboks;
	
	//mellomlagring av dag mnd og år
	private int fraår;

	public JPanel ByggPanel() //utseende
	{		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(4,0));

		//fra år boks
		toppanel.add(new JLabel("Fra år:"));
		fraårboks = new JComboBox(makeyeararray());
		fraårboks.addActionListener(this);
		toppanel.add(fraårboks);
		
		panel.add(toppanel,BorderLayout.CENTER);
		panel.setVisible(true);
				
		return panel;
	}
	
	private String[] makeyeararray()
	{
		return makearray(STARTÅR, Calendar.getInstance().get(Calendar.YEAR));
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
		fraår = Integer.parseInt((String)fraårboks.getSelectedItem());
		return true;
	}//end of metoder for standard gui og valg av år 
	
	public double[] makeSnittMinTempArray(int fra, int til)
	{
		double[] array = new double[til-fra-1];
		
		for(int i = 0; i<(til-fra-1); i++)
		{
			array[i] = stedliste.getGjennomsnittMinTempIÅr(fra++);
		}
		
		return array;
	}
	public double[] makeSnittMaxTempArray(int fra, int til)
	{
		double[] array = new double[til-fra-1];
		
		for(int i = 0; i<(til-fra-1); i++)
		{
			array[i] = stedliste.getGjennomsnittMaxTempIÅr(fra++);
		}
		
		return array;
	}

	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == fraårboks){
			//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			fraår = Integer.parseInt((String)fraårboks.getSelectedItem());

			double[] mintemparray = makeSnittMinTempArray(fraår, fraår+20);
			double[] maxtemparray = makeSnittMaxTempArray(fraår, fraår+20);
			
			DiagramVindu diagram = new DiagramVindu(fraår, mintemparray, maxtemparray);
		}
	}	
}
	
