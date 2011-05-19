/*
 * Skrevet av Thomas Nordengen 18 Mai 2011
 */

package Meterologi.StatistikkTabs;


import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;
import Meterologi.Lister.Sted;

public class SnittTemp extends Lista implements ActionListener
{
	private Diagram diagram;
	private final int START�R = 1970;

	//fra og til dato bokser
	private JComboBox fra�rboks;
	private JComboBox til�rboks;

	private JButton oppdater;
	
	//mellomlagring av dag mnd og �r
	private int fra�r;
	private int til�r;

	private double g = 1;
	private double n = 1;

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

		datopanel2.add(new JLabel("Til �r: "));
		til�rboks = new JComboBox(makeyeararray());
		til�rboks.addActionListener(this);
		datopanel2.add(til�rboks);
	
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		diagram = new Diagram(g,n);

		�rpanel.add(toppanel);
		�rpanel.add(oppdater);
		knappepanel.add(�rpanel);
		knappepanel.add(diagram);
		
		panel.add(knappepanel,BorderLayout.WEST);
	
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
		til�r = Integer.parseInt((String)til�rboks.getSelectedItem());

		return true;
	}
	
	public String[] getAkseString()
	{
		String[] mellomlager = makearray(fra�r, til�r);

		return mellomlager;
	}
	
	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == fra�rboks){
			//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
			fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		}
		if(e.getSource() == til�rboks)
		{	
			til�r = Integer.parseInt((String)til�rboks.getSelectedItem());	
		}
		
		else if(e.getSource() == oppdater)
		{

			if(stedliste.tomListe())
			{
				melding("Det finnes ingen data registrert mellom" +fra�r +" og " +til�r);
			}
			else
			{
					
			}
		}	
	}	
}
	
