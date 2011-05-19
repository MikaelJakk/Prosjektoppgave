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
	private final int STARTÅR = 1970;
	Calendar nå = Calendar.getInstance();
	Calendar fradato;
	Calendar tildato;
	private double gammelTemp = 1;
	private double nyTemp = 1;
	
	private Sted valgtSted; 
	//valgtSted skal peke på stedet man velger i comboboksene.
	
	private JComboBox fylkeboks;
	private JComboBox stedboks;
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox framånedboks;
	private JComboBox fraårboks;
	private JComboBox tilårboks;
	private JComboBox tildagboks;
	private JComboBox tilmånedboks;
	
	private JButton oppdater;
	
	//mellomlagring av dag mnd og år
	private int fradag;
	private int framnd;
	private int fraår;
	private int tildag;
	private int tilmnd;
	private int tilår;
	
	private String fylke;
	private String[] fylker = stedliste.getFylkeArray();
	private String sted;
	private String[] steder = stedliste.getStedArray(fylker[0]);
	private double g = 1;
	private double n = 1;

	public JPanel ByggPanel() //utseende
	{		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JPanel årpanel = new JPanel();
		årpanel.setLayout(new GridLayout(4,0));
		JPanel knappepanel = new JPanel();
		diagram = new Diagram(g,n);
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
		tilårboks = new JComboBox(makeyeararray2());
		tilårboks.addActionListener(this);
		datopanel2.add(tilårboks);
		datopanel2.add(new JLabel("Måned"));
		tilmånedboks = new JComboBox(makearray(1, 12));
		tilmånedboks.addActionListener(this);
		datopanel2.add(tilmånedboks);
		datopanel2.add(new JLabel("Dag"));
		tildagboks = new JComboBox(makearray(1, 31));
		tildagboks.addActionListener(this);
		oppdater = new JButton("Oppdater");
		oppdater.addActionListener(this);
		datopanel2.add(tildagboks);		
		toppanel.add(datopanel);
		toppanel.add(datopanel2);
		
		årpanel.add(stedpanel);
		årpanel.add(toppanel);
		årpanel.add(oppdater);
		knappepanel.add(årpanel);
		knappepanel.add(diagram);
		
		panel.add(knappepanel,BorderLayout.NORTH);
	
		return panel;
	}
	
	private String[] makeyeararray()
	{
		return makearray(STARTÅR, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	private String[] makeyeararray2()
	{
		int til = Calendar.getInstance().get(Calendar.YEAR);
		int fra = STARTÅR;
		
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
	
	public boolean getDatoVerdier()
	{
		framnd = Integer.parseInt((String) framånedboks.getSelectedItem());
		fraår = Integer.parseInt((String)fraårboks.getSelectedItem());
		tilår = Integer.parseInt((String)tilårboks.getSelectedItem());
		tilmnd = Integer.parseInt((String)tilmånedboks.getSelectedItem());
		//må lage test på registrering av datoer som ikke har vært ennå.
		return true;
	}//end of getDatoVerdier()
	
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
	
	public void actionPerformed(ActionEvent e) {
		

		if(e.getSource() == fraårboks || e.getSource() == framånedboks){
			//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			settDatoVerdierIBokser(fraårboks,framånedboks,fradagboks);}
		if(e.getSource() == tilårboks || e.getSource() == tilmånedboks){	
			settDatoVerdierIBokser(tilårboks,tilmånedboks,tildagboks);}
		
		if(e.getSource() == fylkeboks)
		{
			steder = stedliste.getStedArray((String)fylkeboks.getSelectedItem());
			stedboks.setModel(new DefaultComboBoxModel(steder));
			
			if(!getStedVerdier())
				return;
			
				
		}
		
		if(e.getSource() == oppdater)
		{
			valgtSted = stedliste.getStedNode(fylke, sted);
			nyTemp = valgtSted.dataliste.getGjennomsnittsMinTempVerdi(fradato, tildato);
			diagram = new Diagram(gammelTemp,nyTemp);
			gammelTemp = nyTemp;
		}	
	}	
}
