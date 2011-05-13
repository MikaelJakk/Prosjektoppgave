package Meterologi.StatistikkTabs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

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
	
	private JRadioButton snittmintemp;
	private JRadioButton snittmaxtemp;
	private JRadioButton minstnedbør;
	private JRadioButton mestnedbør;
	private ButtonGroup utvalggruppe;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		utskrift = new JTextArea(25,50);
		
		årvalg = new JComboBox( makeYearArray());
		
		snittmintemp = new JRadioButton("Snitt MinTemp");
		snittmintemp.addActionListener(this);
		snittmaxtemp = new JRadioButton("Snitt MaksTemp");
		snittmaxtemp.addActionListener(this);
		minstnedbør = new JRadioButton("Minst Nedbør");
		minstnedbør.addActionListener(this);
		mestnedbør = new JRadioButton("Mest Nedbør");
		mestnedbør.addActionListener(this);
		
		
		utvalggruppe = new ButtonGroup();
		utvalggruppe.add(snittmintemp);
		utvalggruppe.add(snittmaxtemp);
		utvalggruppe.add(minstnedbør);
		utvalggruppe.add(mestnedbør);
		
		JPanel utvalg = new JPanel();
		utvalg.setLayout(new GridLayout(0,1));
		
		JPanel årutvalg = new JPanel();
		
		årutvalg.add(new JLabel("År"));
		årutvalg.add(årvalg);
		
		utvalg.add(årutvalg);
		utvalg.add(snittmintemp);
		utvalg.add(snittmaxtemp);
		utvalg.add(minstnedbør);
		utvalg.add(mestnedbør);
		
		panel.add(utvalg, BorderLayout.WEST);
		
		Tabellmodell modell = new Tabellmodell();
	      JTable tabell = new JTable( modell );
	      tabell.setSize(800,600);
	      
		panel.add(tabell, BorderLayout.CENTER);

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
		
	}
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == snittmintemp)
		{
			//gjør noe kult
		}
		
		else if(e.getSource() == snittmaxtemp)
		{
			//gjør noe annet
		}

		else if(e.getSource() == minstnedbør)
		{
			//gjør noe annet
		}

		else if(e.getSource() == mestnedbør)
		{
			//gjør noe annet
		}
	}
	
	//Tabellmodelloppsett
	class Tabellmodell extends AbstractTableModel
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		private String[] feltnavn =
			{"Fylke","Sted","SnittTemp","Dato"};

		private Object[][] celler = 
		
		{
			{"Akershus","Bærum","-15",sdf.format(Calendar.getInstance().getTime())},
			{"Akershus","Bærum","-30",sdf.format(Calendar.getInstance().getTime())},
			{"Akershus","Bærum","-15",sdf.format(Calendar.getInstance().getTime())},
			{"Akershus","Bærum","-15",sdf.format(Calendar.getInstance().getTime())},
			{"Akershus","Bærum","-15",sdf.format(Calendar.getInstance().getTime())},
			{"Akershus","Bærum","-15",sdf.format(Calendar.getInstance().getTime())}
		};
		
			
		public String getColumnName( int kolonne )
		{
		  return feltnavn[ kolonne ];
		}

		//For å informere tabellmodellen om kolonnenes datatyper.
		public Class getColumnClass( int kolonne )
		{
		  return celler[ 0 ][ kolonne ].getClass();
		}

		public int getColumnCount()
		{
		  return celler[ 0 ].length;
		}

		public int getRowCount()
		{
		  return celler.length;
		}

		public Object getValueAt( int rad, int kolonne )
		{
		  return celler[ rad ][ kolonne ];
		}
	}
}
