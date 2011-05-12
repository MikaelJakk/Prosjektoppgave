	/*
 * Re-edit av Nam le
 * Oppdatert: 11.4.2011
 * Denne klassen skal bygge gui, samt metoder og lytter for registrering av visData
 * og legges til i Hovedvindu
 * .java
 */

package Meterologi;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
	
import Meterologi.Lister.Data;
import Meterologi.Lister.Sted;


public class VisData extends Lista implements ActionListener{

	private static final long serialVersionUID = 1L;
	private final int START�R = 1970;
	private JTextArea utskrift;
	//valgt fylke bokser
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox fram�nedboks;
	private JComboBox fra�rboks;
	private JComboBox til�rboks;
	private JComboBox tildagboks;
	private JComboBox tilm�nedboks;
	
	//knapper
	private JButton visData;
	private JButton visMaxTemp;
	private JButton visMinTemp;
	private JButton visMaxNedb�r;
	private JButton visTotalNedb�r;
	private JButton visGjennomsnittMinTemp;
	private JButton visGjennomsnittMaxTemp;
	
	//mellomlagring av dag mnd og �r
	private int fradag;
	private int framnd;
	private int fra�r;
	private int tildag;
	private int tilmnd;
	private int til�r;
	
	//pekere til fradato og tildato
	Calendar fradato;
	Calendar tildato;

	//peker til valgtsted
	private Sted valgtSted; 
	//valgtSted skal peke p� stedet man velger i comboboksene.
	//det er dette stedet man skal lagre ny data p� sted.nyData.(Data d);
	
	//array over registrerte fylker og steder. samt pekere til valgt fylke og sted
	private String fylke;
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
	//til � skrive ut datoer
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	 
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
		JPanel datopanel2 = new JPanel();
		//fra dato bokser
		datopanel.add(new JLabel("Fra �r:"));
		fra�rboks = new JComboBox(makeyeararray());
		fra�rboks.addActionListener(this);
		datopanel.add(fra�rboks);
		datopanel.add(new JLabel("M�ned"));
		fram�nedboks = new JComboBox(makearray(1, 12));
		fram�nedboks.addActionListener(this);
		datopanel.add(fram�nedboks);
		datopanel.add(new JLabel("Dag"));
		fradagboks = new JComboBox(makearray(1, 31));
		fradagboks.addActionListener(this);
		datopanel.add(fradagboks);	
		//til dato bokser
		datopanel2.add(new JLabel("Til �r: "));
		til�rboks = new JComboBox(makeyeararray());
		til�rboks.addActionListener(this);
		datopanel2.add(til�rboks);
		datopanel2.add(new JLabel("M�ned"));
		tilm�nedboks = new JComboBox(makearray(1, 12));
		tilm�nedboks.addActionListener(this);
		datopanel2.add(tilm�nedboks);
		datopanel2.add(new JLabel("Dag"));
		tildagboks = new JComboBox(makearray(1, 31));
		tildagboks.addActionListener(this);
		datopanel2.add(tildagboks);		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		//knapper
		JPanel knappepanel = new JPanel();
		visData = new JButton("Vis Data");
		visData.addActionListener(this);
		visMaxTemp = new JButton("Max Temp");
		visMaxTemp.addActionListener(this);
		visMinTemp = new JButton("Min Temp");
		visMinTemp.addActionListener(this);
		visMaxNedb�r = new JButton("Max Nedb�r");
		visMaxNedb�r.addActionListener(this);
		visTotalNedb�r = new JButton("Total Nedb�r");
		visTotalNedb�r.addActionListener(this);
		visGjennomsnittMinTemp= new JButton("Snitt MinTemp");
		visGjennomsnittMinTemp.addActionListener(this);
		visGjennomsnittMaxTemp = new JButton("Snitt MaxTemp");
		visGjennomsnittMaxTemp.addActionListener(this);
		knappepanel.add(visData);
		knappepanel.add(visMinTemp);
		knappepanel.add(visMaxTemp);
		knappepanel.add(visMaxNedb�r);
		knappepanel.add(visTotalNedb�r);
		knappepanel.add(visGjennomsnittMinTemp);
		knappepanel.add(visGjennomsnittMaxTemp);
		toppanel.add(knappepanel);
		
		//legger til toppanelet
		panelet.add(toppanel);
		
		//utskriftsvindu
		utskrift = new JTextArea(20, 60);
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
		{melding("det oppstod en feil med valg av fylke og sted");return false;}
		return true;
	}
	public void oppdater()//oppdaterer Jcomboboxene stedboks og fylkeboks
	{
		try
		{
			fylker = stedliste.getFylkeArray();
			fylkeboks.setModel(new DefaultComboBoxModel(fylker));
			steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
			stedboks.setModel(new DefaultComboBoxModel(steder));
		}
		catch(Exception ex){System.out.println("Feil: i oppdateringen av FylkeBox" +ex);}	
	}
	
	public boolean getDatoVerdier()
	{
		fradag = Integer.parseInt((String) fradagboks.getSelectedItem());
		framnd = Integer.parseInt((String) fram�nedboks.getSelectedItem());
		fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		til�r = Integer.parseInt((String)til�rboks.getSelectedItem());
		tilmnd = Integer.parseInt((String)tilm�nedboks.getSelectedItem());
		tildag = Integer.parseInt((String)tildagboks.getSelectedItem());
		//m� lage test p� registrering av datoer som ikke har v�rt enn�.
		return true;
	}//end of getDatoVerdier()
	
	private String[] makeyeararray()
	{
		return makearray(START�R, Calendar.getInstance().get(Calendar.YEAR));
	}
	private String[] makearray(int fra, int til)
	{
		String[] dagarray = new String[til-fra+1];
		for(int i = fra; i <= til; i++)
		{
			dagarray[i-fra] = i + "";
		}
		return dagarray;
	}
/*
	public int telldager() // metode for � skjekke hvor mange dager det er i mnden
	{
		getDatoVerdier();
		if(mnd == 1 || mnd == 3 || mnd == 5 || mnd == 7 ||mnd == 8 || mnd == 10 || mnd == 12 )
			return 31;
		if(mnd == 2) // passer p� skudd�ret
		{
			if(�r%4 == 0 && �r%100 != 0 && �r%400 == 0 )
				return 29;
			return 28;
		}	
		return 30;
	}
	public String regnUtDag()
	{
		getDatoVerdier();
		getStedVerdier();
		
		int dagl�p = 0;
		valgtSted = stedliste.getStedNode(fylke, sted);
		
		String tekst = "";
		for(int i = 0; i < telldager();i++)  //telldager() er en metode som skjekker hvilke mnd vi er og hvor mange dager den har.
		{
			dagl�p++;

			Calendar dato = Calendar.getInstance();
			dato.setTimeInMillis(0);
			dato.set(�r,mnd-1,dagl�p);
			nydata = new Data(dato, 0, 0, 0);
			
				if(valgtSted.dataliste.datoEksisterer(nydata))
					if(valgtSted.dataliste.getData(nydata).toString() != null)
						tekst += valgtSted.dataliste.getData(nydata).toString() + "\n";		
		}
		
		return tekst;
	}
	public String regnUt�r()
	{
		getStedVerdier();
			
		
		getDatoVerdier();
		String tekst = "";
		int mnder = 0;
		int dager = 0;
		valgtSted = stedliste.getStedNode(fylke, sted);
			for(int i = 0;i<13;i++) // i<13 for 12 m�neder.
			{
				mnder++;
				for(int j = 0;j<366;j++)// j < 366 for ant dager i �ret.
				{
					dager++;
					Calendar dato = Calendar.getInstance();
					dato.setTimeInMillis(0);
					dato.set(�r,mnder-1,dager);
					nydata = new Data(dato, 1, 2, 3);
						if(valgtSted.dataliste.datoEksisterer(nydata))
							if(valgtSted.dataliste.getData(nydata).toString() != null)
								tekst += valgtSted.dataliste.getData(nydata).toString() + "\n";
						if(mnder-1 == 13)
							return tekst;
				}
			}
			return tekst;			
	}
*/
	private void settDatoVerdierIBokser(JComboBox �, JComboBox m, JComboBox d)
	{
		int m�nednr = 1 + m.getSelectedIndex();
		int antalldager;
		if (m�nednr == 1 || m�nednr == 3 || m�nednr == 5 || m�nednr == 7 ||
				m�nednr == 8 || m�nednr == 10 || m�nednr == 12) {
			antalldager = 31;
		} else if (m�nednr == 2) {
			int �r = Integer.parseInt((String) �.getSelectedItem());
			//sjekk skudd�r
			if(�r%400 == 0 || (�r%4 == 0 && �r%100 != 0))
			{antalldager = 29;}
			else antalldager = 28;
		} else {
			antalldager = 30;
		}
		String[] dager = makearray(1, antalldager);
		d.setModel(new DefaultComboBoxModel(dager));
	}
	
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
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == fylkeboks)
		{oppdater();}
		
		if(event.getSource() == fra�rboks || event.getSource() == fram�nedboks){
			//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
			settDatoVerdierIBokser(fra�rboks,fram�nedboks,fradagboks);}
		if(event.getSource() == til�rboks || event.getSource() == tilm�nedboks){	
			settDatoVerdierIBokser(til�rboks,tilm�nedboks,tildagboks);}
		
		if(event.getSource() == visMaxTemp)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;
				
				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedH�yesteTemp(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret p� valgt sted");
					else
						utskrift.setText("Viser h�yeste tempteratur "
										+"registrert p� "+fylke+", "+sted +" mellom " 
										+sdf.format(fradato.getTime())+" og "
										+sdf.format(tildato.getTime())+"\n"
										+"Dato\tMinTemp\tMaxTemp\tNedb�r\n"
										+data.toString());
				}
			}
		}
		if(event.getSource() == visMinTemp)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;

				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedLavesteTemp(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret p� valgt sted");
					else
						utskrift.setText("Viser laveste tempteratur "
								+"registrert p� "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+"Dato\tMinTemp\tMaxTemp\tNedb�r\n"
								+data.toString());
				}
			}
		}
		if(event.getSource() == visMaxNedb�r)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;

				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedMestNedb�r(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret p� valgt sted");
					else
						utskrift.setText("Viser dagen med mest nedb�r"
								+"registrert p� "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+"Dato\tMinTemp\tMaxTemp\tNedb�r\n"
								+data.toString());
				}
			}
		}
		if(event.getSource() == visTotalNedb�r)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;

				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					
					utskrift.setText("Viser total nedb�r "
									+"registrert p� "+fylke+", "+sted +" mellom " 
									+sdf.format(fradato.getTime())+" og "
									+sdf.format(tildato.getTime())+"\n"
									+"Fradato\tTildato\tSum Nedb�r\n"
									+sdf.format(fradato.getTime())+"\t"
									+sdf.format(tildato.getTime())+"\t"
									+valgtSted.dataliste.summerNedb�r(fradato,tildato)+" mm");
				}
			}
		}
		if(event.getSource() == visGjennomsnittMinTemp)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;

				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					
					utskrift.setText("Viser gjennomsnitts mintemperatur "
									+"registrert p� "+fylke+", "+sted +" mellom " 
									+sdf.format(fradato.getTime())+" og "
									+sdf.format(tildato.getTime())+"\n"
									+"Fradato\tTildato\tSnitt Minimumstemperatur\n"
									+sdf.format(fradato.getTime())+"\t"
									+sdf.format(tildato.getTime())+"\t"
									+valgtSted.dataliste.getGjennomsnittsMinTemp(fradato,tildato));
				}
			}
		}
		if(event.getSource() == visGjennomsnittMaxTemp)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;

				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(valgtSted.dataliste.tomListe())
				utskrift.setText("Ingen lagret p� valgt sted");
				else
				{	
					utskrift.setText("Viser gjennomsnitts makstemperatur "
									+"registrert p� "+fylke+", "+sted +" mellom " 
									+sdf.format(fradato.getTime())+" og "
									+sdf.format(tildato.getTime())+"\n"
									+"Fradato\tTildato\tSnitt Maksimumstemperatur\n"
									+sdf.format(fradato.getTime())+"\t"
									+sdf.format(tildato.getTime())+"\t"
									+valgtSted.dataliste.getGjennomsnittsMaksTemp(fradato,tildato));
				}
			}
		}
		if(event.getSource() == visData)
		{
			utskrift.setText("");
			if( stedliste.tomListe() )
				utskrift.setText("ingen data i systemet!");
			else
			{
				if(!getStedVerdier())
					return;
				
				valgtSted = stedliste.getStedNode(fylke, sted);
				
				if(!makeFraTilDato())
				{
					melding("fradato er etter startdato, velg riktig dato");
					return;
				}
				
				if(!valgtSted.dataliste.tomListe())
					
						utskrift.setText("Viser all data registrert p� "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+valgtSted.dataliste.visData(fradato,tildato));
				else
					utskrift.setText("Denne datoen er ikke registrert enda");
			}
		}
	}//end of actionPerformed()
}//End of registrerData