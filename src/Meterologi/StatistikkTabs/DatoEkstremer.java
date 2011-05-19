package Meterologi.StatistikkTabs;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import Meterologi.Lista;

public class DatoEkstremer extends Lista implements ActionListener{

	private final int START�R = 1970;
	//fra og til dato bokser
	private JComboBox fradagboks;
	private JComboBox fram�nedboks;
	private JComboBox fra�rboks;
	private JComboBox til�rboks;
	private JComboBox tildagboks;
	private JComboBox tilm�nedboks;
	
	private JTextArea utskrift;
	
	//mellomlagring av dag mnd og �r
	private int fradag;
	private int framnd;
	private int fra�r;
	private int tildag;
	private int tilmnd;
	private int til�r;
	
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
		til�rboks = new JComboBox(makeyeararray2());
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
		return makearray(START�R, Calendar.getInstance().get(Calendar.YEAR));
	}
	
	private String[] makeyeararray2()
	{
		int til = Calendar.getInstance().get(Calendar.YEAR);
		int fra = START�R;
		
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
		framnd = Integer.parseInt((String) fram�nedboks.getSelectedItem());
		fra�r = Integer.parseInt((String)fra�rboks.getSelectedItem());
		til�r = Integer.parseInt((String)til�rboks.getSelectedItem());
		tilmnd = Integer.parseInt((String)tilm�nedboks.getSelectedItem());
		tildag = Integer.parseInt((String)tildagboks.getSelectedItem());
		//m� lage test p� registrering av datoer som ikke har v�rt enn�.
		return true;
	}//end of getDatoVerdier()
	
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

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == fra�rboks || e.getSource() == fram�nedboks){
			//forandrer antall dager i dagboksen s� det blir riktig med tanke p� skudd�r osv.
			settDatoVerdierIBokser(fra�rboks,fram�nedboks,fradagboks);}
		if(e.getSource() == til�rboks || e.getSource() == tilm�nedboks){	
			settDatoVerdierIBokser(til�rboks,tilm�nedboks,tildagboks);}
		
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
				utskrift.setText("Viser H�yeste registrerte maksimumstemperatur mellom"
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
