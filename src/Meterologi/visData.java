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
	private JComboBox årfelt;
	private JButton visData;
	private JButton visMnd;
	private JButton visÅr;
	
	private int dag;
	private int mnd;
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
		årfelt = new JComboBox(makeyeararray());
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
		visMnd = new JButton("Data for måned");
		visMnd.addActionListener(this);
		visÅr = new JButton("Data for år");
		visÅr.addActionListener(this);
		knappepanel.add(visData);
		knappepanel.add(visMnd);
		knappepanel.add(visÅr);
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
		mnd =Integer.parseInt((String) månedboks.getSelectedItem());
		år = Integer.parseInt((String)årfelt.getSelectedItem());
		if(dag <= 0 || dag > 31)
		{	melding("ugyldig dag");
			return false; 
		}
		if(mnd == 0 || mnd >12 || mnd < 1)
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
	public int telldager() // metode for å skjekke hvor mange dager det er i mnden
	{
		getDatoVerdier();
		if(mnd == 1 || mnd == 3 || mnd == 5 || mnd == 7 ||mnd == 8 || mnd == 10 || mnd == 12 )
			return 31;
		if(mnd == 2) // passer på skuddåret
		{
			if(år%4 == 0 && år%100 != 0 && år%400 == 0 )
				return 29;
			return 28;
		}
			
			
		return 30;
	}
	public String regnUtDag()
	{
		getDatoVerdier();
		int dagløp = 0;
		
		String tekst = "";
		for(int i = 0; i < telldager();i++)
		{
			dagløp++;
			
			
			Calendar dato = Calendar.getInstance();
			dato.setTimeInMillis(0);
			dato.set(år,mnd-1,dagløp);
			nydata = new Data(dato, 0, 0, 0);
				if(super.dataliste.datoEksisterer(nydata))
					if(super.dataliste.getData(nydata).toString() != null)
						tekst += super.dataliste.getData(nydata).toString() + "\n";
					
				
				
				
				
		}
		
		return tekst;
	}
	public String regnUtÅr()
	{
		getDatoVerdier();
		String tekst = "";
		int mnder = 0;
		int dager = 0;
		int  tekst1 = 0;
		
			for(int i = 0;i<13;i++) // i<13 for 12 måneder.
			{
				mnder++;
				for(int j = 0;j<500;j++) // 500 er at sikret tall for at J skal være nok av dager den går gjennom. kunne ha kanskje skrevet 366?
				{
					dager++;
					Calendar dato = Calendar.getInstance();
					dato.setTimeInMillis(0);
					dato.set(år,mnder-1,dager);
					nydata = new Data(dato, 1, 2, 3);
						if(super.dataliste.datoEksisterer(nydata))
							if(super.dataliste.getData(nydata).toString() != null)
								tekst += super.dataliste.getData(nydata).toString() + "\n";
						if(mnder-1 == 13)
							return tekst;
						
				}
			}
			return tekst;			
	}

	public void actionPerformed(ActionEvent event) {
		
			
		if(event.getSource() == visData)
		{
			if( super.dataliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else
			{
				getDatoVerdier();
				//lagrer dato som calendar objekt
				Calendar dato = Calendar.getInstance();
				dato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
				dato.set(år,mnd-1,dag);/*måned-1 fordi Calendar.set() er teit*/
				nydata = new Data(dato, 0, 0, 0);
				if(super.dataliste.datoEksisterer(nydata))
					if(super.dataliste.getData(nydata).toString() != null)
						utskrift.setText("Dato\tMinTemp\tMaxTemp\tNedbør\n\n" + 
												super.dataliste.getData(nydata).toString());
					else
						utskrift.setText("toStringen returnerer 0 ");
				else
					utskrift.setText("Denne datoen er ikke registrert enda");
			}
		}
		else if(event.getSource() == visMnd)
		{
			if(regnUtDag() == "")
				utskrift.setText("Fant ingen data for denne måned");
			else
				utskrift.setText("Dato\tMinTemp\tMaxTemp\tNedbør\n\n" + regnUtDag() + "\n\n\n Dette er alle dataene for valgt måned." );
		}		
		else if(event.getSource() == visÅr)
		{
				String nyttår = "";
				getDatoVerdier();
				Calendar dato1 = Calendar.getInstance(); dato1.setTimeInMillis(0); dato1.set(år, 11, 31);
					nydata = new Data(dato1,0,0,0);
			if(super.dataliste.datoEksisterer(nydata))
			{
					nyttår = super.dataliste.getData(nydata).toString();
					utskrift.setText("Dato\tMinTemp\tMaxTemp\tNedbør\n\n" + regnUtÅr() + nyttår + "\n\n\n Dette er alle dataene for valgt for året." );
			}
						
			else if(regnUtÅr() == "")
				utskrift.setText("Fant ingen data for dette året.");
			else
				utskrift.setText("Dato\tMinTemp\tMaxTemp\tNedbør\n\n" + regnUtÅr()  +  "\n\n\n Dette er alle dataene for valgt for året.");
				
		}
		
	}//end of actionPerformed()
}//End of registrerData