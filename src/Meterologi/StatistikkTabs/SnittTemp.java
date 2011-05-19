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
	private int akseVerdi;
	
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
		til�rboks = new JComboBox(makeyeararray2());
		til�rboks.addActionListener(this);
		datopanel2.add(til�rboks);
		
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		//diagram = new Diagram(g,n);

		�rpanel.add(toppanel);
		�rpanel.add(oppdater);
		knappepanel.add(�rpanel);
		//knappepanel.add(diagram);
		
		panel.add(knappepanel,BorderLayout.WEST);
	
		return panel;
	}
	
	private String[] makeyeararray()
	{
		return makearray(START�R, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	private String[] makeyeararray2()
	{
		int til = Calendar.getInstance().get(Calendar.YEAR);
		int fra = START�R;
		
		String[] array = new String[til-fra+1];
		int j = 0;
		for(int i = til; i>=fra; i--)
		{	
			array[j] = i+"";
			j++;
		}
		
		return array;
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
	
	//Setter array verdien til X-Aksen GjennomsnittsTemp-Diagrammet
	public int setAkseArray()
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
	}
	
	public String[] getAkseString()
	{
		String[] mellomlager = makearray(fra�r, til�r);
		
		
		return mellomlager;
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == fra�rboks){
			//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
			fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		}
			
		if(e.getSource() == til�rboks)
		{	
			til�r = Integer.parseInt((String)til�rboks.getSelectedItem());	
		}
		
		if(e.getSource() == oppdater)
		{
			if(fra�r > til�r)
			{
				melding("Fra�r er etter til�r, velg riktige �r");
				return;
			}
			
			if(stedliste.tomListe())
				JOptionPane.showMessageDialog(null,"Ingen registrerte steder");
			else
			{	
				nyTemp = 30;//valgtSted.dataliste.getGjennomsnittsMinTempVerdi(fradato, tildato);
				if(nyTemp == 0)
				{
					JOptionPane.showMessageDialog(null,"NyTemp returnerer 0 Grader!");
				}
				else
				{
					//diagram skal f� inn start�r og slutt�r, og to arrayer, 
					//en med minsnittverdier og en med makssnittverdier
					diagram = new Diagram(gammelTemp,nyTemp);
					gammelTemp = nyTemp;
				}
			}
		}	
	}	
}
