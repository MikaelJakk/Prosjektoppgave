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

import Meterologi.Lister.*;

public class RegistrerData extends Lista implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JTextArea utskrift;
	
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	private JComboBox dagboks;
	private JComboBox m�nedboks;
	private JComboBox �rboks;
	private JTextField mintempfelt;
	private JTextField maxtempfelt;
	private JTextField nedb�rfelt;
	private JButton skrivut;
	private JButton leggtilny;
	
	private int dag;
	private int m�ned;
	private int �r;
	private double min;
	private double max;
	private double ned;
	private Data nydata;
	private Sted valgtSted; 
	//valgtSted skal peke p� stedet man velger i comboboksene.
	//det er dette stedet man skal lagre ny data p� sted.nyData.(Data d);
	
	//array over registrerte fylker og steder. samt pekere til valgt fylke og sted
	private String fylke;
	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
									"Hedmark","Hordaland","M�re og Romsdal",
									"Nordland","Nord-Tr�ndelag","Oppland","Oslo","Rogaland",
									"Sogn og Fjordane","S�r-Tr�ndelag","Telemark",
									"Troms","Vest-Agder","Vestfold","�stfold"};
	private String sted;
	private final String[] steder = {"Sted 1","Sted 2","Sted 3"};
	
	private final int fra�r = 1970;
	//skal egentlig bruke stedsliste.getRegistrerteSteder() og stedsliste.getRegistrerteFylker()
	//som skal returnere en String[] med registrerte fylker, og en annen med steder

	public JPanel ByggPanel()
	{	//bygger GUI p� parameter panelet
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
		datopanel.add(new JLabel("�r"));
		�rboks = new JComboBox(makeyeararray());
		�rboks.addActionListener(this);
		datopanel.add(�rboks);
		datopanel.add(new JLabel("M�ned"));
		m�nedboks = new JComboBox(makearray(1, 12));
		m�nedboks.addActionListener(this);
		datopanel.add(m�nedboks);
		datopanel.add(new JLabel("Dag"));
		dagboks = new JComboBox(makearray(1, 31));
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
		inndatapanel.add(new JLabel("Nedb�r"));
		nedb�rfelt= new JTextField(3);
		inndatapanel.add(nedb�rfelt);
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
		{
			melding("det oppstod en feil med valg av fylke og sted");
			return false;
		}
		return true;
	}
	
	public void getDatoVerdier()
	{
		�r = Integer.parseInt((String) �rboks.getSelectedItem());
		m�ned =Integer.parseInt((String) m�nedboks.getSelectedItem());
		dag = Integer.parseInt((String) dagboks.getSelectedItem());
	}//end of getDatoVerdier()
	
	public boolean getV�rVerdier()
	{
		try{
		min = Integer.parseInt(mintempfelt.getText());
		}catch(Exception e){melding("ugyldig mintempverdi");return false;}
		try{
		max = Integer.parseInt(maxtempfelt.getText());
		}catch(Exception e){melding("ugyldig maxtempverdi");return false;}
		try{
		ned = Integer.parseInt(nedb�rfelt.getText());
		}catch(Exception e){melding("ugyldig nedb�rsverdi");return false;}
		
		if(ned < 0 )
		{melding("ugyldig nedb�rsverdi"); return false;}
		if(ned > 229.6)
		{melding("Ny nedb�rsrekord");}
		if(min < -273.15)
		{melding("minimumstemperaturen som er innskrevet er mindre enn det absolutte nullpunkt!");return false;}
		if(max < min)
		{melding("Innskrevet MaxTemp er mindre en MinTemp!");return false;}
		if(max > 9999)
		{melding("ekstremnedb�r");}
		
		return true;
	}//end of getV�rVerdier()

	public String[] makeyeararray()
	{
		return makearray(fra�r, Calendar.getInstance().get(Calendar.YEAR));
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
		if(event.getSource() == �rboks || event.getSource() == m�nedboks)
		{
			int m�nednr = 1 + m�nedboks.getSelectedIndex();
			int antalldager;
			if (m�nednr == 1 || m�nednr == 3 || m�nednr == 5 || m�nednr == 7 ||
					m�nednr == 8 || m�nednr == 10 || m�nednr == 12) {
				antalldager = 31;
			} else if (m�nednr == 2) {
				int �r = Integer.parseInt((String) �rboks.getSelectedItem());
				//sjekk skudd�r
				if(�r%400 == 0 || (�r%4 == 0 && �r%100 != 0))
				{antalldager = 29;}
				else antalldager = 28;
			} else {
				antalldager = 30;
			}
			String[] dager = makearray(1, antalldager);
			dagboks.setModel(new DefaultComboBoxModel(dager));
		}	
		if(event.getSource() == skrivut)
		{
			if( super.dataliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else
				utskrift.setText(super.dataliste.skrivUtListe() );
		}
		if(event.getSource() == leggtilny)
		{	
			try{
				if(!getStedVerdier())//henter valg fra sted og fylkesinput, returnerer false ved feil
					return;
				//henter dato input
				getDatoVerdier();
				//lagrer dato som calendar objekt
				Calendar dato = Calendar.getInstance();
				dato.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
				dato.set(�r,m�ned-1,dag);/*m�ned-1 fordi Calendar.set() er teit*/
				Calendar n� = Calendar.getInstance();
				if(n�.before(dato))
				{
					melding("innskrevet dato har ikke intruffet enn�");
					return;
				}
				
				if(!getV�rVerdier())
					return;
				
				//lager en ny node med dataen
				nydata = new Data(dato, min, max, ned);
				
				//pr�ver � sette den inn i lista.
				boolean dobbeltregistrering = super.dataliste.datoEksisterer(nydata);
					
				if(dobbeltregistrering)
				{melding("Det er allerede registrert data p� denne datoen");}
				else{
					super.dataliste.nyData(nydata);
					melding("Data er lagt til");
				}
			}
			catch(Exception ex){melding("Feil ved innsetting av data!");};
		}
	}//end of actionPerformed()
}//End of registrerData