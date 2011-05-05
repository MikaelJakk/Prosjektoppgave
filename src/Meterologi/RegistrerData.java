/*
 * Skrevet av Mikael Jakhelln og endret på av Thomas Nordengen,
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
	private JComboBox månedboks;
	private JComboBox årboks;
	private JTextField mintempfelt;
	private JTextField maxtempfelt;
	private JTextField nedbørfelt;
	private JButton skrivut;
	private JButton leggtilny;
	private JButton slett;
	private JButton refresh;
	
	private int dag;
	private int måned;
	private int år;
	private double min;
	private double max;
	private double ned;
	private Data nydata;
	private Sted valgtSted; 
	//valgtSted skal peke på stedet man velger i comboboksene.
	//det er dette stedet man skal lagre ny data på sted.nyData.(Data d);
	
	//array over registrerte fylker og steder. samt pekere til valgt fylke og sted
	private String fylke;
	
	/*
	private final String[] fylker = {"Akershus", "Aust-Agder", "Buskerud", "Finnmark",
									"Hedmark","Hordaland","Møre og Romsdal",
									"Nordland","Nord-Trøndelag","Oppland","Oslo","Rogaland",
									"Sogn og Fjordane","Sør-Trøndelag","Telemark",
									"Troms","Vest-Agder","Vestfold","Østfold"};
	*/
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
	//steder inneholder en liste over alle de stedene som har blitt registrert på bestemt fylke.
	
	
	private final int fraår = 1970;
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
		datopanel.add(new JLabel("År"));
		årboks = new JComboBox(makeyeararray());
		årboks.addActionListener(this);
		datopanel.add(årboks);
		datopanel.add(new JLabel("Måned"));
		månedboks = new JComboBox(makearray(1, 12));
		månedboks.addActionListener(this);
		datopanel.add(månedboks);
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
		år = Integer.parseInt((String) årboks.getSelectedItem());
		måned =Integer.parseInt((String) månedboks.getSelectedItem());
		dag = Integer.parseInt((String) dagboks.getSelectedItem());
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
		{melding("ugyldig nedbørsverdi\nSjekk innskrevet verdi på nedbør"); return false;}
		if(ned > 229.6)
		{melding("Ny nedbørsrekord");}
		//må legg etil yes/no dialog, og ny gamlenedbør.
		if(min < -273.15)
		{melding("minimumstemperaturen som er innskrevet er mindre enn det absolutte nullpunkt!");return false;}
		if(max < min)
		{melding("Innskrevet MaxTemp er mindre en MinTemp!");return false;}
		if(max > 100)
		{melding("Ekstreme Temperaturer");}
		
		return true;
	}//end of getVærVerdier()

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
		if(event.getSource() == årboks || event.getSource() == månedboks)
		{//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			int månednr = 1 + månedboks.getSelectedIndex();
			int antalldager;
			if (månednr == 1 || månednr == 3 || månednr == 5 || månednr == 7 ||
					månednr == 8 || månednr == 10 || månednr == 12) {
				antalldager = 31;
			} else if (månednr == 2) {
				int år = Integer.parseInt((String) årboks.getSelectedItem());
				//sjekk skuddår
				if(år%400 == 0 || (år%4 == 0 && år%100 != 0))
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
							"\nDu må registrere minst ett sted før du kan registrere data.");
					return;
				}
				//henter valg fra sted og fylkesinput, returnerer false ved feil
				if(!getStedVerdier())
					return;
				//henter dato input
				getDatoVerdier();
				//lagrer dato som calendar objekt
				Calendar dato = Calendar.getInstance();
				dato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
				dato.set(år,måned-1,dag);/*måned-1 fordi Calendar.set() er teit*/
				Calendar nå = Calendar.getInstance();
				if(nå.before(dato))
				{melding("innskrevet dato har ikke intruffet ennå");
					return;}
				if(!getVærVerdier())
					return;
				//lager en ny node med dataen
				nydata = new Data(dato, min, max, ned);
				//prøver å sette den inn i lista.
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
						{melding("Det er allerede registrert data på denne datoen");}
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
				int valg = JOptionPane.showConfirmDialog(null, "Sikker på at du vil slette: " + stedboks.getSelectedItem()+"?",
						"Slette sted?", JOptionPane.YES_NO_OPTION);
				if(valg == JOptionPane.NO_OPTION || valg == JOptionPane.CLOSED_OPTION)
				return;
				}
				catch(Exception ex){System.out.println("Feil: ved innsetting av data (2) "+ex);}
				
				if(!getStedVerdier())
					return;
				//Bruke metode som sletter nåværende node i lisa
				stedliste.slettStedNode(fylke,sted);
				lagreLista();
				melding("Stedet er slettet!");
				System.out.println("Sletting gjennomført!");
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
			//Dette gjør at når en velger noe i fylkeComboBoxen blir den automatisk oppdatert.
			//Men det går ikke ann å lytte på JComboBox og samtidig lytte på JButton hvor JButton har tilknytting til JComboBox.
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