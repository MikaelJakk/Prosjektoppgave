/*
 * Skrevet av Mikael Jakhelln og endret p� av Thomas Nordengen,
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
	private JButton slett;
	private JButton refresh;
	
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
	
	/*
	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
									"Hedmark","Hordaland","M�re og Romsdal",
									"Nordland","Nord-Tr�ndelag","Oppland","Oslo","Rogaland",
									"Sogn og Fjordane","S�r-Tr�ndelag","Telemark",
									"Troms","Vest-Agder","Vestfold","�stfold"};
	*/
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
	//steder inneholder en liste over alle de stedene som har blitt registrert p� bestemt fylke.
	
	
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
		//new!!---------------------------------------
		stedpanel.add(new JLabel ("Refresh:"));
		refresh = new JButton("Oppdater:");
		refresh.addActionListener(this);
		stedpanel.add(refresh);
		//new!!---------------------------------------
		stedpanel.add(new JLabel("Fylke:"));
		fylkeboks = new JComboBox(fylker);
		fylkeboks.addActionListener(this);
		stedpanel.add(fylkeboks);
		stedpanel.add(new JLabel("Sted:"));
		stedboks = new JComboBox(steder);
		stedboks.addActionListener(this);
		stedpanel.add(stedboks);
		//new!!---------------------------------------
		stedpanel.add(new JLabel("Slett sted:"));
		slett = new JButton("Slett valgt sted");
		slett.addActionListener(this);
		stedpanel.add(slett);
		toppanel.add(stedpanel);
		//new!!!--------------------------------------
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
		utskrift.setEditable(false);
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
		{melding("ugyldig nedb�rsverdi\nSjekk innskrevet verdi p� nedb�r"); return false;}
		if(ned > 229.6)
		{melding("Ny nedb�rsrekord");}
		//m� legg etil yes/no dialog, og ny gamlenedb�r.
		if(min < -273.15)
		{melding("minimumstemperaturen som er innskrevet er mindre enn det absolutte nullpunkt!");return false;}
		if(max < min)
		{melding("Innskrevet MaxTemp er mindre en MinTemp!");return false;}
		if(max > 100)
		{melding("Ekstreme Temperaturer");}
		
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
	
	public void refreshFylke()
	{
		try
		{
			fylker = stedliste.getFylkeArray();
			fylkeboks.setModel(new DefaultComboBoxModel(fylker));
			System.out.println("Sucsess: Oppdatering av FylkeComboBox!");
		}
		catch(Exception ex){System.out.println("feil i oppdateringen av FylkeBox");}	
	}
	
	public void refreshSted()
	{
		try
		{
			steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
			stedboks.setModel(new DefaultComboBoxModel(steder));
			System.out.println("Sucsess: Oppdatering av StedComboBox!");
		}
		catch(Exception ex){System.out.println("feil i oppdateringen av StedBox");}	
	}
	

	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == fylkeboks)
		{
			steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
			stedboks.setModel(new DefaultComboBoxModel(steder));
		}
		
		//actionevent for dato comboboxer
		if(event.getSource() == �rboks || event.getSource() == m�nedboks)
		{//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
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
		}//end of actionlistener for datobokser
		
		if(event.getSource() == skrivut)
		{
			if( stedliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else if(getStedVerdier())
			{	if(stedliste.getStedNode(fylke, sted) == null)
					return;
				else 
				{
					valgtSted = stedliste.getStedNode(fylke, sted);
					utskrift.setText(valgtSted.dataliste.skrivUtListe());
				}
			}
		}//end of ActionListener for skrivut
		if(event.getSource() == leggtilny)
		{	
			try{
				if(stedliste.tomListe())
				{
					melding("Du har ikke registrert noen steder\n" +
							"\nDu m� registrere minst ett sted f�r du kan registrere data.");
					return;
				}
				//henter valg fra sted og fylkesinput, returnerer false ved feil
				if(!getStedVerdier())
					return;
				//henter dato input
				getDatoVerdier();
				//lagrer dato som calendar objekt
				Calendar dato = Calendar.getInstance();
				dato.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
				dato.set(�r,m�ned-1,dag);/*m�ned-1 fordi Calendar.set() er teit*/
				Calendar n� = Calendar.getInstance();
				if(n�.before(dato))
				{melding("innskrevet dato har ikke intruffet enn�");
					return;}
				if(!getV�rVerdier())
					return;
				//lager en ny node med dataen
				nydata = new Data(dato, min, max, ned);
				//pr�ver � sette den inn i lista.
				if(!stedliste.finsStedNode(fylke, sted))
				{	melding("stedet finnes ikke");
					return;
				}
				else{
					try{
					valgtSted = stedliste.getStedNode(fylke, sted);
					}catch(Exception ex){System.out.println("Feil: ved innsetting av data (1)");}
					if(valgtSted == null)return;
					
					else if(valgtSted != null)
					{
						boolean finnesidatalista = false;
						try{
						finnesidatalista = valgtSted.dataliste.datoEksisterer(nydata);
						}catch(Exception ex){System.out.println("Feil: ved innsetting av data (2) "+ex);}
						try{
						if(finnesidatalista)
						{melding("Det er allerede registrert data p� denne datoen");}
						else{
							valgtSted.nyData(nydata);
							lagreLista();//lagrer lista etter hver nye datainput
							melding("Data er lagt til");
						}
						}catch(Exception ex){System.out.println("Feil: ved innsetting av data (3) "+ex);}	
					}
				}
			}
			catch(Exception ex){System.out.println(ex);melding("Feil: ved innsetting av data (main)");};
		}//end of ActionsListener for LeggTilNyKnapp
		if(event.getSource() == slett)
		{
			try
			{
				//Sjekker at fylke og sted er valgt
				try{
				if(stedboks.getSelectedItem() == null)
				{
					melding("Sted har ikke blitt valgt");
					return;
					
				}
				}catch(Exception ex){System.out.println("ingen flyke valgt (1) "+ex);}
			
				try{
				int valg = JOptionPane.showConfirmDialog(null, "Sikker p� at du vil slette: " + stedboks.getSelectedItem()+"?",
						"Slette sted?", JOptionPane.YES_NO_OPTION);
				if(valg == JOptionPane.NO_OPTION || valg == JOptionPane.CLOSED_OPTION)
				return;
				}
				catch(Exception ex){System.out.println("Feil: ved innsetting av data (2) "+ex);}
				
				if(!getStedVerdier())
					return;
				//Bruke metode som sletter n�v�rende node i lisa
				stedliste.slettStedNode(fylke,sted);
				lagreLista();
				melding("Stedet er slettet!");
				System.out.println("Sletting gjennomf�rt!");
				refreshFylke();
				refreshSted();
				//Fikse oppdatering ved registrering av nyt sted i regnyttsted! 
				
			}
			catch(Exception ex)
			{
				melding("Det oppstod en feil under sletting av valgt sted!");
			}
		}//end of slettValgtSted ActionListener
		if(event.getSource() == refresh)
		{
			try
			{
				fylker = stedliste.getFylkeArray();
				fylkeboks.setModel(new DefaultComboBoxModel(fylker));
				steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
				stedboks.setModel(new DefaultComboBoxModel(steder));
				System.out.println("Sucsess: Oppdatering av ComboBoxer!");
			}
			catch(Exception ex){System.out.println("feil i oppdateringen");}	
		}
		
		/*if(fylkeboks == (JComboBox)event.getSource());
		{	
			//Dette gj�r at n�r en velger noe i fylkeComboBoxen blir den automatisk oppdatert.
			//Men det g�r ikke ann � lytte p� JComboBox og samtidig lytte p� JButton hvor JButton har tilknytting til JComboBox.
			try
			{
				fylker = stedliste.getFylkeArray();
				fylkeboks.setModel(new DefaultComboBoxModel(fylker));
				System.out.println("Sucsess: Oppdatering av FylkeComboBox!");
			}
			catch(Exception ex){System.out.println("feil i oppdateringen av FylkeBox");}	
		}*/
		

	}//end of actionPerformed()
}//End of registrerData