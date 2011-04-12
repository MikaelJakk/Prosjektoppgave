/*
 * Skrevet av Mikael Jakhelln, 
 * Oppdatert: 11.4.2011
 * Denne klassen skal bygge gui, samt metoder og lytter for registrering av nyData 
 * og legges til i Tab.java
 */

package Meterologi;

import java.awt.*;
		import java.util.*;
		import java.awt.event.*;
import javax.swing.*;

import Meterologi.Lister.Data;
import Meterologi.Lister.DataListe;
import Meterologi.Lister.Sted;

public class RegistrerData implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JTextArea utskrift;
	
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	private JComboBox dagboks;
	private JComboBox månedboks;
	private JTextField årfelt;
	private JTextField mintempfelt;
	private JTextField maxtempfelt;
	private JTextField nedbørfelt;
	private JButton skrivut;
	private JButton leggtilny;
	
	private int dag;
	private int måned;
	private int år;
	private double min;
	private double max;
	private double ned;
	
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
		datopanel.add(new JLabel("År"));
		årfelt = new JTextField(4);
		datopanel.add(årfelt);
		datopanel.add(new JLabel("Måned"));
		månedboks = new JComboBox(måneder);
		datopanel.add(månedboks);
		datopanel.add(new JLabel("Dag"));
		dagboks = new JComboBox(dager);
		datopanel.add(dagboks);				
		toppanel.add(datopanel);
		//inputfelter for inndata
		JPanel inndatapanel = new JPanel();
		inndatapanel.add(new JLabel("Minimumstemp"));
		mintempfelt = new JTextField(2);
		inndatapanel.add(mintempfelt);
		inndatapanel.add(new JLabel("Maksimumstemp"));
		maxtempfelt = new JTextField(2);
		inndatapanel.add(maxtempfelt);
		inndatapanel.add(new JLabel("Nedbør"));
		nedbørfelt= new JTextField(3);
		inndatapanel.add(nedbørfelt);
		toppanel.add(inndatapanel);
		//knapper
		JPanel knappepanel = new JPanel();
		leggtilny = new JButton("Registrer Ny Data");
		leggtilny.addActionListener(this);
		knappepanel.add(leggtilny);
		skrivut = new JButton("skriv ut");
		skrivut.addActionListener(this);
		knappepanel.add(skrivut);
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
	
	public boolean getVærVerdier()
	{
		try{
		min = Integer.parseInt(mintempfelt.getText());
		}catch(Exception e){melding("ugyldig mintempverdi");return false;}
		try{
		max = Integer.parseInt(maxtempfelt.getText());
		}catch(Exception e){melding("ugyldig maxtempverdi");return false;}
		try{
		ned = Integer.parseInt(nedbørfelt.getText());
		}catch(Exception e){melding("ugyldig nedbørsverdi");return false;}
		
		if(ned < 0 )
		{melding("ugyldig nedbørsverdi"); return false;}
		if(ned > 229.6)
		{melding("Ny nedbørsrekord");}
		if(min < -273.15)
		{melding("minimumstemperaturen som er innskrevet er mindre enn det absolutte nullpunkt!");return false;}
		if(max < min)
		{melding("Innskrevet MaxTemp er mindre en MinTemp!");return false;}
		if(max > 9999)
		{melding("ekstremnedbør");}
		
		return true;
	}//end of getVærVerdier()

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == skrivut)
		{
			if( dataliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else
				utskrift.setText(dataliste.skrivUtListe() );
		}
		if(event.getSource() == leggtilny)
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
				
				if(!getVærVerdier())
					return;
				
				//lager en ny node med dataen
				nydata = new Data(dato, min, max, ned);
				
				//prøver å sette den inn i lista.
				boolean dobbeltregistrering = dataliste.datoEksisterer(nydata);
					
				if(dobbeltregistrering)
				{melding("Det er allerede registrert data på denne datoen");}
				else{
					dataliste.nyData(nydata);
					melding("Data er lagt til");
				}
			}
			catch(Exception ex){melding("Feil ved innsetting av data!");};
		}
	}//end of actionPerformed()
}//End of registrerData