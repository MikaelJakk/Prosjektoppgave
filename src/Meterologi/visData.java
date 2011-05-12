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
	private final int STARTÅR = 1970;
	private JTextArea utskrift;
	//valgt fylke bokser
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox framånedboks;
	private JComboBox fraårboks;
	private JComboBox tilårboks;
	private JComboBox tildagboks;
	private JComboBox tilmånedboks;
	
	//knapper
	private JButton visData;
	private JButton visMaxTemp;
	private JButton visMinTemp;
	private JButton visMaxNedbør;
	private JButton visTotalNedbør;
	private JButton visGjennomsnittMinTemp;
	private JButton visGjennomsnittMaxTemp;
	
	//mellomlagring av dag mnd og år
	private int fradag;
	private int framnd;
	private int fraår;
	private int tildag;
	private int tilmnd;
	private int tilår;
	
	//pekere til fradato og tildato
	Calendar fradato;
	Calendar tildato;

	//peker til valgtsted
	private Sted valgtSted; 
	//valgtSted skal peke på stedet man velger i comboboksene.
	//det er dette stedet man skal lagre ny data på sted.nyData.(Data d);
	
	//array over registrerte fylker og steder. samt pekere til valgt fylke og sted
	private String fylke;
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
	//til å skrive ut datoer
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	 
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
		JPanel datopanel2 = new JPanel();
		//fra dato bokser
		datopanel.add(new JLabel("Fra år:"));
		fraårboks = new JComboBox(makeyeararray());
		fraårboks.addActionListener(this);
		datopanel.add(fraårboks);
		datopanel.add(new JLabel("Måned"));
		framånedboks = new JComboBox(makearray(1, 12));
		framånedboks.addActionListener(this);
		datopanel.add(framånedboks);
		datopanel.add(new JLabel("Dag"));
		fradagboks = new JComboBox(makearray(1, 31));
		fradagboks.addActionListener(this);
		datopanel.add(fradagboks);	
		//til dato bokser
		datopanel2.add(new JLabel("Til år: "));
		tilårboks = new JComboBox(makeyeararray());
		tilårboks.addActionListener(this);
		datopanel2.add(tilårboks);
		datopanel2.add(new JLabel("Måned"));
		tilmånedboks = new JComboBox(makearray(1, 12));
		tilmånedboks.addActionListener(this);
		datopanel2.add(tilmånedboks);
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
		visMaxNedbør = new JButton("Max Nedbør");
		visMaxNedbør.addActionListener(this);
		visTotalNedbør = new JButton("Total Nedbør");
		visTotalNedbør.addActionListener(this);
		visGjennomsnittMinTemp= new JButton("Snitt MinTemp");
		visGjennomsnittMinTemp.addActionListener(this);
		visGjennomsnittMaxTemp = new JButton("Snitt MaxTemp");
		visGjennomsnittMaxTemp.addActionListener(this);
		knappepanel.add(visData);
		knappepanel.add(visMinTemp);
		knappepanel.add(visMaxTemp);
		knappepanel.add(visMaxNedbør);
		knappepanel.add(visTotalNedbør);
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
		framnd = Integer.parseInt((String) framånedboks.getSelectedItem());
		fraår = Integer.parseInt((String)fraårboks.getSelectedItem());
		tilår = Integer.parseInt((String)tilårboks.getSelectedItem());
		tilmnd = Integer.parseInt((String)tilmånedboks.getSelectedItem());
		tildag = Integer.parseInt((String)tildagboks.getSelectedItem());
		//må lage test på registrering av datoer som ikke har vært ennå.
		return true;
	}//end of getDatoVerdier()
	
	private String[] makeyeararray()
	{
		return makearray(STARTÅR, Calendar.getInstance().get(Calendar.YEAR));
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
		getStedVerdier();
		
		int dagløp = 0;
		valgtSted = stedliste.getStedNode(fylke, sted);
		
		String tekst = "";
		for(int i = 0; i < telldager();i++)  //telldager() er en metode som skjekker hvilke mnd vi er og hvor mange dager den har.
		{
			dagløp++;

			Calendar dato = Calendar.getInstance();
			dato.setTimeInMillis(0);
			dato.set(år,mnd-1,dagløp);
			nydata = new Data(dato, 0, 0, 0);
			
				if(valgtSted.dataliste.datoEksisterer(nydata))
					if(valgtSted.dataliste.getData(nydata).toString() != null)
						tekst += valgtSted.dataliste.getData(nydata).toString() + "\n";		
		}
		
		return tekst;
	}
	public String regnUtÅr()
	{
		getStedVerdier();
			
		
		getDatoVerdier();
		String tekst = "";
		int mnder = 0;
		int dager = 0;
		valgtSted = stedliste.getStedNode(fylke, sted);
			for(int i = 0;i<13;i++) // i<13 for 12 måneder.
			{
				mnder++;
				for(int j = 0;j<366;j++)// j < 366 for ant dager i året.
				{
					dager++;
					Calendar dato = Calendar.getInstance();
					dato.setTimeInMillis(0);
					dato.set(år,mnder-1,dager);
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
	private void settDatoVerdierIBokser(JComboBox å, JComboBox m, JComboBox d)
	{
		int månednr = 1 + m.getSelectedIndex();
		int antalldager;
		if (månednr == 1 || månednr == 3 || månednr == 5 || månednr == 7 ||
				månednr == 8 || månednr == 10 || månednr == 12) {
			antalldager = 31;
		} else if (månednr == 2) {
			int år = Integer.parseInt((String) å.getSelectedItem());
			//sjekk skuddår
			if(år%400 == 0 || (år%4 == 0 && år%100 != 0))
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
		fradato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
		fradato.set(fraår,framnd-1,fradag);/*måned-1 fordi Calendar.set() er teit*/
		
		tildato = Calendar.getInstance();
		tildato.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
		tildato.set(tilår,tilmnd-1,tildag);/*måned-1 fordi Calendar.set() er teit*/
		
		if(fradato.after(tildato))
			return false;
		else return true;
		
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if(event.getSource() == fylkeboks)
		{oppdater();}
		
		if(event.getSource() == fraårboks || event.getSource() == framånedboks){
			//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			settDatoVerdierIBokser(fraårboks,framånedboks,fradagboks);}
		if(event.getSource() == tilårboks || event.getSource() == tilmånedboks){	
			settDatoVerdierIBokser(tilårboks,tilmånedboks,tildagboks);}
		
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedHøyesteTemp(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret på valgt sted");
					else
						utskrift.setText("Viser høyeste tempteratur "
										+"registrert på "+fylke+", "+sted +" mellom " 
										+sdf.format(fradato.getTime())+" og "
										+sdf.format(tildato.getTime())+"\n"
										+"Dato\tMinTemp\tMaxTemp\tNedbør\n"
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedLavesteTemp(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret på valgt sted");
					else
						utskrift.setText("Viser laveste tempteratur "
								+"registrert på "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+"Dato\tMinTemp\tMaxTemp\tNedbør\n"
								+data.toString());
				}
			}
		}
		if(event.getSource() == visMaxNedbør)
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					Data data = valgtSted.dataliste.getDenMedMestNedbør(fradato,tildato);
					if(data == null)
						utskrift.setText("Ingen lagret på valgt sted");
					else
						utskrift.setText("Viser dagen med mest nedbør"
								+"registrert på "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+"Dato\tMinTemp\tMaxTemp\tNedbør\n"
								+data.toString());
				}
			}
		}
		if(event.getSource() == visTotalNedbør)
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					
					utskrift.setText("Viser total nedbør "
									+"registrert på "+fylke+", "+sted +" mellom " 
									+sdf.format(fradato.getTime())+" og "
									+sdf.format(tildato.getTime())+"\n"
									+"Fradato\tTildato\tSum Nedbør\n"
									+sdf.format(fradato.getTime())+"\t"
									+sdf.format(tildato.getTime())+"\t"
									+valgtSted.dataliste.summerNedbør(fradato,tildato)+" mm");
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
					
					utskrift.setText("Viser gjennomsnitts mintemperatur "
									+"registrert på "+fylke+", "+sted +" mellom " 
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
				utskrift.setText("Ingen lagret på valgt sted");
				else
				{	
					utskrift.setText("Viser gjennomsnitts makstemperatur "
									+"registrert på "+fylke+", "+sted +" mellom " 
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
					
						utskrift.setText("Viser all data registrert på "+fylke+", "+sted +" mellom " 
								+sdf.format(fradato.getTime())+" og "
								+sdf.format(tildato.getTime())+"\n"
								+valgtSted.dataliste.visData(fradato,tildato));
				else
					utskrift.setText("Denne datoen er ikke registrert enda");
			}
		}
	}//end of actionPerformed()
}//End of registrerData