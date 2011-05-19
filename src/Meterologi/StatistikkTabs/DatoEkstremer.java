package Meterologi.StatistikkTabs;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import Meterologi.Lista;

public class DatoEkstremer extends Lista implements ActionListener{

	private final int STARTÅR = 1970;
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox framånedboks;
	private JComboBox fraårboks;
	private JComboBox tilårboks;
	private JComboBox tildagboks;
	private JComboBox tilmånedboks;
	
	private JTextArea utskrift;
	
	//mellomlagring av dag mnd og år
	private int fradag;
	private int framnd;
	private int fraår;
	private int tildag;
	private int tilmnd;
	private int tilår;
	
	//pekere til fra og tildato som skal initialiseres senere
	Calendar fradato;
	Calendar tildato;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	private JButton maxknappen;
	private JButton minknappen;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//lager et panel for datovalg
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
		datopanel2.add(tildagboks);
		
		JPanel fratildatopanel = new JPanel();
		fratildatopanel.setLayout(new GridLayout(2,0));
		fratildatopanel.add(datopanel);
		fratildatopanel.add(datopanel2);
		
		//lager ett panel for knapper
		JPanel knappepanel= new JPanel();
		
		maxknappen = new JButton("Vis Makstemp");
		maxknappen.addActionListener(this);
		minknappen = new JButton("Vis Mintemp");
		minknappen.addActionListener(this);
		
		knappepanel.add(maxknappen);
		knappepanel.add(minknappen);
		
		JPanel utvalgspanel = new JPanel();
		utvalgspanel.setLayout(new GridLayout(2,0));
		
		utvalgspanel.add(fratildatopanel);
		utvalgspanel.add(knappepanel);
		
		panel.add(utvalgspanel, BorderLayout.NORTH);
		utskrift = new JTextArea(25,50);
		panel.add(utskrift, BorderLayout.CENTER);
		
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

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == fraårboks || e.getSource() == framånedboks){
			//forandrer antall dager i dagboksen så det blir riktig med tanke på skuddår osv.
			settDatoVerdierIBokser(fraårboks,framånedboks,fradagboks);}
		if(e.getSource() == tilårboks || e.getSource() == tilmånedboks){	
			settDatoVerdierIBokser(tilårboks,tilmånedboks,tildagboks);}
		
		if(e.getSource() == maxknappen)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
			{
				JOptionPane.showMessageDialog(null, "ingen data i systemed");
				return;
			}
			else{
				if(makeFraTilDato())
				utskrift.setText("Viser Høyeste registrerte maksimumstemperatur mellom"
						+sdf.format(fradato.getTime())+" og "
						+sdf.format(tildato.getTime())+"\n"
						+"\n"+stedliste.getMaxTempSted(fradato,tildato));
			}
		}
		
		else if(e.getSource() == minknappen)
		{
			utskrift.setText("");
			if(stedliste.tomListe())
			{
				JOptionPane.showMessageDialog(null, "ingen data i systemed");
				return;
			}
			else{
				if(makeFraTilDato())
				utskrift.setText("Viser laveste registrerte minimumstemperatur mellom"
						+sdf.format(fradato.getTime())+" og "
						+sdf.format(tildato.getTime())+"\n"
						+"\n"+stedliste.getMinTempSted(fradato,tildato));
			}
		}
	
	}
}//end of class
