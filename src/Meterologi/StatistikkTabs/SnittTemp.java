/*
 * Skrevet av Thomas Nordengen 18 Mai 2011
 */

package Meterologi.StatistikkTabs;


import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;

public class SnittTemp extends Lista implements ActionListener
{
	private Diagram diagram;
	private final int STARTÅR = 1970;

	//fra og til dato bokser
	private JComboBox fraårboks;
	private JComboBox tilårboks;
	private int fra = 0, til =0;

	private JButton oppdater;
	
	//mellomlagring av dag mnd og år
	private int fraår;
	private int tilår;


	public JPanel ByggPanel() //utseende
	{		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel årpanel = new JPanel();
		årpanel.setLayout(new GridLayout(3,0));
		JPanel knappepanel = new JPanel();
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(4,0));

		//innputfeltet for dato
		JPanel datopanel = new JPanel();
		JPanel datopanel2 = new JPanel();
		//fra dato bokser
		datopanel.add(new JLabel("Fra år:"));
		fraårboks = new JComboBox(makeyeararray());
		fraårboks.addActionListener(this);
		datopanel.add(fraårboks);

		datopanel2.add(new JLabel("Til år: "));
		tilårboks = new JComboBox(makeyeararray2());
		tilårboks.addActionListener(this);
		datopanel2.add(tilårboks);
	
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		diagram = new Diagram(0,0);

		årpanel.add(toppanel);
		årpanel.add(oppdater);
		knappepanel.add(årpanel);
		knappepanel.add(diagram);
		
		panel.add(knappepanel,BorderLayout.WEST);
	
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
	private String[] makeyeararray2()
	{
		int til = Calendar.getInstance().get(Calendar.YEAR);
		int fra = STARTÅR;
		
		String[] array = new String[til-fra+1];
		int j = 0;
		for(int i = til; i>=fra; i--)
		{	
			array[j] = i+"";
			j++;
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
		tilår = Integer.parseInt((String)tilårboks.getSelectedItem());

		return true;
	}
	
	public String[] getAkseString()
	{
		String[] mellomlager = makearray(fraår, tilår);

		return mellomlager;
	}
	public int antallÅr()
	{
		int antallÅr = tilår - fraår;
		return antallÅr;
	}
	
	public void tegnGraf(int fra, int til)
	{
		
		int antallÅr = tilår - fraår;
		double gammel = 0;
		double ny;
		
		double[] temp = new double[antallÅr+1];
		
		int[] array = new int[til-fra+1];
		for(int i = fra; i <= til; i++)
		{
			array[i-fra] = i;
			temp[i-fra] = stedliste.getGjennomsnittMaxTempIÅr(array[i]);
			ny = temp[i];
			diagram = new Diagram(gammel,ny);
			gammel = ny;
		}	
	}
	
	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == fraårboks){
			//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			fraår = Integer.parseInt((String)fraårboks.getSelectedItem());
		}
		if(e.getSource() == tilårboks)
		{	
			tilår = Integer.parseInt((String)tilårboks.getSelectedItem());	
		}
		
		else if(e.getSource() == oppdater)
		{
			if(stedliste.tomListe())
			{
				melding("Det finnes ingen data registrert mellom" +fraår +" og " +tilår);
			}
			else
			{
				tegnGraf(fraår, tilår);
			}
		}	
	}	
}
	
