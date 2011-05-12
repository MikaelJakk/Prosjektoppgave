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
	
	private JComboBox årvalg;
	//lager streng[] for årvalg utifra maskinens kalenderår
	int fraår = 1970;
	Calendar nå = Calendar.getInstance();
	int tilår = nå.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JRadioButton enradioknapp;
	private JRadioButton toradioknapp;
	private ButtonGroup enknappegruppe;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		årvalg = new JComboBox( makeYearArray());
		
		enradioknapp = new JRadioButton("løk");
		enradioknapp.addActionListener(this);
		toradioknapp = new JRadioButton("ost");
		toradioknapp.addActionListener(this);
		
		enknappegruppe = new ButtonGroup();
		enknappegruppe.add(enradioknapp);
		enknappegruppe.add(toradioknapp);
		
		JPanel utvalg = new JPanel();
		utvalg.setLayout(new GridLayout(0,1));
		
		JPanel årutvalg = new JPanel();
		
		årutvalg.add(new JLabel("År"));
		årutvalg.add(årvalg);
		
		utvalg.add(årutvalg);
		utvalg.add(enradioknapp);
		utvalg.add(toradioknapp);
		
		panel.add(utvalg);
		panel.add(utskrift);

		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[tilår-fraår+1];
		for(int i = fraår; i <= tilår; i++)
		{
			dagarray[i-fraår] = i + "";
		}
		return dagarray;
	}
	
	public void settDatoer(int år)
	{
		fradato = Calendar.getInstance();
		fradato.setTimeInMillis(0);
		tildato = fradato;
		fradato.set(år, 0, 1);
		år++;
		tildato.set(år,0,-1);
	}
	
	public void visMaxTempRanking()
	{
		settDatoer( Integer.parseInt( (String) årvalg.getSelectedItem() ) );
		String[] ranking = stedliste.getMaxTempRanking(fradato, tildato);
		
		utskrift.setText("Rankingliste for Høyest Temperatur\nPlass\tFylke\tSted\tTemp\tDato\n");
		for(int i=0; i<ranking.length;i++)
		{
			utskrift.append(i+1 +"\t"+ranking[i]+"\n");
		}
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enradioknapp)
		{visMaxTempRanking();}
		
		if(e.getSource() == toradioknapp)
		{
			//gjør noe annet
		}
		
	}
}
