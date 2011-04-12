/*
 * Re-edit av Nam le
 * Oppdatert: 11.4.2011
 * Denne klassen skal bygge gui, samt metoder og lytter for registrering av visData
 * og legges til i Hovedvindu
 * .java
 */

package Meterologi;

import java.awt.*;
import java.awt.List;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import Meterologi.Lister.Data;
import Meterologi.Lister.DataListe;
import Meterologi.Lister.Sted;
import Meterologi.*;


public class VisData extends Lista implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final int fraår = 1970;
	private JTextArea utskrift;
	
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	private JComboBox dagboks;
	private JComboBox månedboks;
	private JTextField årfelt;
	private JButton visData;
	
	private int dag;
	private int måned;
	private int år;

	//lager pekere til dataliste og data, og valgt sted.
	private DataListe dataliste;
	private Data nydata;
	private Sted valgtSted; 
	//valgtSted skal peke på stedet man velger i comboboksene.
	//det er dette stedet man skal lagre ny data på sted.nyData.(Data d);
	
	//array over registrerte fylker og steder. samt pekere til valgt fylke og sted
	private String fylke;
	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
									"Hedmark","Hordaland","Møre og Romsdal",
									"Nordland","Nord-Trøndelag","Oppland","Oslo","Rogaland",
									"Sogn og Fjordane","Sør-Trøndelag","Telemark",
									"Troms","Vest-Agder","Vestfold","Østfold"};
	private String sted;
	private final String[] steder = {"Sted 1","Sted 2","Sted 3"};
	private final String[] dager = {"1","2","3","4","5","6","7","8","9","10",
									"11","12","13","14","15","16","17","18","19",
									"20","21","22","23","24","25","26","27","28","29","30","31"};
	private final String[] måneder= {"1","2","3","4","5","6","7","8","9","10","11","12"};
	//skal egentlig bruke stedsliste.getRegistrerteSteder() og stedsliste.getRegistrerteFylker()
	//som skal returnere en String[] med registrerte fylker, og en annen med steder

	public JPanel ByggPanel()
	{	//bygger GUI på parameter panelet
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());
		
		//panel for alt utenom utksiftsfelt
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(3,0));
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
		datopanel.add(new JLabel("År"));
		årfelt = new JTextField(4);
		datopanel.add(årfelt);
		datopanel.add(new JLabel("Måned"));
		månedboks = new JComboBox(makearray(1, 12));
		datopanel.add(månedboks);
		datopanel.add(new JLabel("Dag"));
		dagboks = new JComboBox(makearray(1, 31));
		datopanel.add(dagboks);				
		toppanel.add(datopanel);
		//knapper
		JPanel knappepanel = new JPanel();
		visData = new JButton("Vis Data");
		visData.addActionListener(this);
		knappepanel.add(visData);
		toppanel.add(knappepanel);
		//legger til toppanelet
		panelet.add(toppanel);
		
		//utskriftsvindu
		utskrift = new JTextArea(20, 50);
		panelet.add(new JScrollPane(utskrift));
		panelet.setVisible(true);
		
		//Initialiserer listen med steder og data  (forøvring bare datalisten)
		dataliste = new DataListe();
		
		return panelet;
	}//end of byggPanel()
	
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
	
	public boolean getDatoVerdier()
	{
		dag = Integer.parseInt((String) dagboks.getSelectedItem());
		måned =Integer.parseInt((String) månedboks.getSelectedItem());
		år = Integer.parseInt(årfelt.getText());
		if(dag <= 0 || dag > 31)
		{	melding("ugyldig dag");
			return false; 
		}
		if(måned == 0 || måned >12 || måned < 1)
		{	melding("ugyldig måned");
			return false;
		}
		if(år < 1970)
		{	melding("ugyldig årstall");
			return false;
		}
		if(år > 3000)
		{	melding("Morsom eller.\nÅr "+år+" :P");
			return false;
		}
		//må lage test på registrering av datoer som ikke har vært ennå.
		return true;
	}//end of getDatoVerdier()
	
	public String[] makeyeararray()
	{
		return makearray(fraår, Calendar.getInstance().get(Calendar.YEAR));
	}
	public String[] makearray(int fra, int til)
	{
		String[] dagarray = new String[til-fra+1];
		for(int i = fra; i <= til; i++)
		{
			dagarray[i-fra] = i + "";
		}
		return dagarray;
	}

	public void actionPerformed(ActionEvent event) {
		
		
		if(årfelt.getText().length() == 0 || årfelt.getText().length() != 4)
		{
			JOptionPane.showMessageDialog(null, "Skriv inn i feltet år igjen!");
			return;
		}
	
		
		if(event.getSource() == visData)
		{
			if( super.dataliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else
				utskrift.setText(super.dataliste.skrivUtListe() );
		}
		if(event.getSource() == visData)
		{	
			try{
				if(!getStedVerdier())//henter valg fra sted og fylkesinput, returnerer false ved feil
					return;
				//henter dato input
				if(!getDatoVerdier())
					return;
				//lagrer dato som calendar objekt
				Calendar dato = Calendar.getInstance(); 
				dato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
				dato.set(år,måned-1,dag);/*måned-1 fordi Calendar.set() er teit*/
				Calendar nå = Calendar.getInstance();
				if(nå.before(dato))
				{
					melding("innskrevet dato har ikke intruffet ennå");
					return;
				}
				
			
			}
			catch(Exception ex){melding("Feil ved innsetting av data!");};
		}
	}//end of actionPerformed()
}//End of registrerData