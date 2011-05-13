package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import Meterologi.Lista;

public class RankingListe extends Lista implements ActionListener {

	private JTextArea utskrift;
	
	private JComboBox �rvalg;
	//lager streng[] for �rvalg utifra maskinens kalender�r
	int fra�r = 1970;
	Calendar n� = Calendar.getInstance();
	int til�r = n�.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JRadioButton snittmintemp;
	private JRadioButton snittmaxtemp;
	private JRadioButton minstnedb�r;
	private JRadioButton mestnedb�r;
	private ButtonGroup utvalggruppe;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		�rvalg = new JComboBox( makeYearArray());
		
		snittmintemp = new JRadioButton("Snitt MinTemp");
		snittmintemp.addActionListener(this);
		snittmaxtemp = new JRadioButton("Snitt MaksTemp");
		snittmaxtemp.addActionListener(this);
		minstnedb�r = new JRadioButton("Minst Nedb�r");
		minstnedb�r.addActionListener(this);
		mestnedb�r = new JRadioButton("Mest Nedb�r");
		mestnedb�r.addActionListener(this);
		
		
		utvalggruppe = new ButtonGroup();
		utvalggruppe.add(snittmintemp);
		utvalggruppe.add(snittmaxtemp);
		utvalggruppe.add(minstnedb�r);
		utvalggruppe.add(mestnedb�r);
		
		JPanel utvalg = new JPanel();
		utvalg.setLayout(new GridLayout(0,1));
		
		JPanel �rutvalg = new JPanel();
		
		�rutvalg.add(new JLabel("�r"));
		�rutvalg.add(�rvalg);
		
		utvalg.add(�rutvalg);
		utvalg.add(snittmintemp);
		utvalg.add(snittmaxtemp);
		utvalg.add(minstnedb�r);
		utvalg.add(mestnedb�r);
		
		panel.add(utvalg);
		panel.add(utskrift);

		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[til�r-fra�r+1];
		for(int i = fra�r; i <= til�r; i++)
		{
			dagarray[i-fra�r] = i + "";
		}
		return dagarray;
	}
	
	public void settDatoer(int �r)
	{
		fradato = Calendar.getInstance();
		fradato.setTimeInMillis(0);
		tildato = fradato;
		fradato.set(�r, 0, 1);
		�r++;
		tildato.set(�r,0,-1);
	}
	
	public void visMaxTempRanking()
	{
		settDatoer( Integer.parseInt( (String) �rvalg.getSelectedItem() ) );
		String[] ranking = stedliste.getMaxTempRanking(fradato, tildato);
		
		utskrift.setText("Rankingliste for H�yest Temperatur\nPlass\tFylke\tSted\tTemp\tDato\n");
		for(int i=0; i<ranking.length;i++)
		{
			utskrift.append(i+1 +"\t"+ranking[i]+"\n");
		}
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == snittmintemp)
		{
			//gj�r noe kult
		}
		
		else if(e.getSource() == snittmaxtemp)
		{
			//gj�r noe annet
		}

		else if(e.getSource() == minstnedb�r)
		{
			//gj�r noe annet
		}

		else if(e.getSource() == mestnedb�r)
		{
			//gj�r noe annet
		}
	}
}
