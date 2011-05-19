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
	Calendar n� = Calendar.getInstance();
	Calendar fradato;
	Calendar tildato;
	private double gammelTemp = 1;
	private double nyTemp = 1;
	
	private JComboBox valgt�r;	
	private JButton oppdater;
	
	//mellomlagring av dag mnd og �r
	
	private int �ret;

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
		datopanel.add(new JLabel("Valgt �r:"));
		valgt�r = new JComboBox(makeyeararray());
		valgt�r.addActionListener(this);
		datopanel.add(valgt�r);
	
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		//datopanel2.add(tildagboks);		
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
		�ret = Integer.parseInt((String)valgt�r.getSelectedItem());
		return true;
	}//end of getDatoVerdier()
	
		
	//Setter array verdien til X-Aksen GjennomsnittsTemp-Diagrammet
	/*public int setAkseArray()
	{
		if(!getDatoVerdier())
		{
			melding("Feil i getDAtoverdier!");
		}
		else
		{
			int antall�r = til�r - fra�r;
			
			akseVerdi = antall�r;
			return akseVerdi;
		}
		return 1;
	}*/
	
	public String[] getAkseString()
	{
		String[] mellomlager = new String[1];
		
		String mellomlagera = (String)valgt�r.getSelectedItem();
		
		mellomlager[0] = mellomlagera;
		return mellomlager;
	}
	
	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == valgt�r){
	
			getDatoVerdier();
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			tildato = fradato;
			fradato.set(�ret, 0, 1);
			tildato.set(�ret+1,0,1);
		}
		
		else if(e.getSource() == oppdater)
		{

			if(!stedliste.finnesI�r(�ret))
			{
				melding("Det finnes ingen data registrert p� " + �ret);
			}
			else
			{
					nyTemp = stedliste.getGjennomsnittMinTempI�rVerdi(�ret);
					melding("hentet ny  minTemp");
					if(nyTemp == 0)
					{
						melding("NyTemp returnerer Null!");
					}
					else
					{
						diagram = new Diagram(gammelTemp,nyTemp);
						gammelTemp = nyTemp;
					}
			}	
		}	
	}	
}
	
