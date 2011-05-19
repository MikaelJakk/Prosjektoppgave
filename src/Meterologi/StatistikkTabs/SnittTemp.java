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
	private final int STARTÅR = 1970;
	Calendar nå = Calendar.getInstance();
	Calendar fradato;
	Calendar tildato;
	private double gammelTemp = 1;
	private double nyTemp = 1;
	
	private JComboBox valgtår;	
	private JButton oppdater;
	
	//mellomlagring av dag mnd og år
	
	private int året;

	private double g = 1;
	private double n = 1;

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
		datopanel.add(new JLabel("Valgt år:"));
		valgtår = new JComboBox(makeyeararray());
		valgtår.addActionListener(this);
		datopanel.add(valgtår);
	
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		//datopanel2.add(tildagboks);		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		diagram = new Diagram(g,n);
		
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
	
	public void melding(String m)
	{
		JOptionPane.showMessageDialog(null,m, "OBS!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean getDatoVerdier()
	{
		året = Integer.parseInt((String)valgtår.getSelectedItem());
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
			int antallÅr = tilår - fraår;
			
			akseVerdi = antallÅr;
			return akseVerdi;
		}
		return 1;
	}*/
	
	public String[] getAkseString()
	{
		String[] mellomlager = new String[1];
		
		String mellomlagera = (String)valgtår.getSelectedItem();
		
		mellomlager[0] = mellomlagera;
		return mellomlager;
	}
	
	public void actionPerformed(ActionEvent e) 
	{

		if(e.getSource() == valgtår){
	
			getDatoVerdier();
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			tildato = fradato;
			fradato.set(året, 0, 1);
			tildato.set(året+1,0,1);
		}
		
		else if(e.getSource() == oppdater)
		{

			if(!stedliste.finnesIÅr(året))
			{
				melding("Det finnes ingen data registrert på " + året);
			}
			else
			{
					nyTemp = stedliste.getGjennomsnittMinTempIÅrVerdi(året);
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
	
