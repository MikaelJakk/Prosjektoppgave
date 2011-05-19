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
	
	private Sted valgtSted; 
	//valgtSted skal peke p� stedet man velger i comboboksene.
	
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox fram�nedboks;
	private JComboBox fra�rboks;
	private JComboBox til�rboks;
	private JComboBox tildagboks;
	private JComboBox tilm�nedboks;
	
	private JButton oppdater;
	
	//mellomlagring av dag mnd og �r
	private int fradag;
	private int framnd;
	private int fra�r;
	private int tildag;
	private int tilmnd;
	private int til�r;
	
	private String fylke;
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
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
		//dropdown for valg av fylke og sted
		JPanel stedpanel = new JPanel();
		stedpanel.add(new JLabel("Fylke:"));
		fylkeboks = new JComboBox(fylker);
		fylkeboks.addActionListener(this);
		stedpanel.add(fylkeboks);
		stedpanel.add(new JLabel("Sted:"));
		stedboks = new JComboBox(steder);
		stedboks.addActionListener(this);
		stedpanel.add(stedboks);
		toppanel.add(stedpanel);
		//innputfeltet for dato
		JPanel datopanel = new JPanel();
		JPanel datopanel2 = new JPanel();
		//fra dato bokser
		datopanel.add(new JLabel("Fra �r:"));
		fra�rboks = new JComboBox(makeyeararray());
		fra�rboks.addActionListener(this);
		datopanel.add(fra�rboks);
		//datopanel.add(new JLabel("M�ned"));
		//fram�nedboks = new JComboBox(makearray(1, 12));
		//fram�nedboks.addActionListener(this);
		//datopanel.add(fram�nedboks);
		//datopanel.add(new JLabel("Dag"));
		//fradagboks = new JComboBox(makearray(1, 31));
		//fradagboks.addActionListener(this);
		//datopanel.add(fradagboks);	
		//til dato bokser
		datopanel2.add(new JLabel("Til �r: "));
		til�rboks = new JComboBox(makeyeararray2());
		til�rboks.addActionListener(this);
		datopanel2.add(til�rboks);
		//datopanel2.add(new JLabel("M�ned"));
		//tilm�nedboks = new JComboBox(makearray(1, 12));
		//tilm�nedboks.addActionListener(this);
		//datopanel2.add(tilm�nedboks);
		//datopanel2.add(new JLabel("Dag"));
		//tildagboks = new JComboBox(makearray(1, 31));
		//tildagboks.addActionListener(this);
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		//datopanel2.add(tildagboks);		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		//diagram = new Diagram(g,n);
		
		�rpanel.add(stedpanel);
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
	
	public boolean getStedVerdier()
	{
		try{
		fylke = (String) fylkeboks.getSelectedItem();
		sted = (String) stedboks.getSelectedItem();
		}catch(Exception e)
		{melding("det oppstod en feil med valg av fylke og sted");return false;}
		return true;
	}
	private void settDatoVerdierIBokser(JComboBox �)
	{
		
			int �r = Integer.parseInt((String) �.getSelectedItem());
			
	}
	
	public boolean getDatoVerdier()
	{
		//framnd = Integer.parseInt((String) fram�nedboks.getSelectedItem());
		fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		til�r = Integer.parseInt((String)til�rboks.getSelectedItem());
		//tilmnd = Integer.parseInt((String)tilm�nedboks.getSelectedItem());
		//m� lage test p� registrering av datoer som ikke har v�rt enn�.
		return true;
	}//end of getDatoVerdier()
	
	private boolean makeFraTilDato()
	{
		getDatoVerdier();
		
		fradato = Calendar.getInstance();
		fradato.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
		fradato.set(fra�r,framnd-1,fradag);/*m�ned-1 fordi Calendar.set() er teit*/
		
		tildato = Calendar.getInstance();
		tildato.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
		tildato.set(til�r,tilmnd-1,tildag);/*m�ned-1 fordi Calendar.set() er teit*/
		
		if(fradato.after(tildato))
			return false;
		else return true;	
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
		int mellomlagerFra�r;
		int mellomlagerTil�r;
		
		mellomlagerFra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		mellomlagerTil�r = Integer.parseInt((String)til�rboks.getSelectedItem());
		
		String[] mellomlager = makearray(mellomlagerFra�r, mellomlagerTil�r);
		
		
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
		
		if(e.getSource() == fylkeboks)
		{
			steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
			stedboks.setModel(new DefaultComboBoxModel(steder));
			
			if(!getStedVerdier())
				return;			
			
			if(!makeFraTilDato())
			{
				melding("Fradato er etter tildato, velg riktig dato");
				return;
			}
		}
		
		if(e.getSource() == oppdater)
		{
			if(!getStedVerdier() )
					return;
			if(!makeFraTilDato())
			{
				melding("Fradato er etter tildato, velg riktig dato");
				return;
			}
			valgtSted = stedliste.getStedNode(fylke, sted);
			
			if(valgtSted.dataliste.tomListe())
				JOptionPane.showMessageDialog(null,"Ingen lagret p� valgt sted");
			else
			{	
				makeFraTilDato();
				nyTemp = 30;//valgtSted.dataliste.getGjennomsnittsMinTempVerdi(fradato, tildato);
				if(nyTemp == 0)
				{
					JOptionPane.showMessageDialog(null,"NyTemp returnerer 0 Grader!");
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
