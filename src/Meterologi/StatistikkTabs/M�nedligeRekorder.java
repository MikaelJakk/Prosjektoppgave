package Meterologi.StatistikkTabs;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Meterologi.*;

public class M�nedligeRekorder extends Lista implements ActionListener

{
	
	final int start�r = 1970;
	Calendar n� = Calendar.getInstance();
	int slutt�r = n�.get(Calendar.YEAR);
	
	int fra�r,framnd,fradag,til�r,tilmnd,tildag;


	private JRadioButton makstemp, mintemp, nedb�r;
	private ButtonGroup knappegruppe;
	private JTextArea utskrift;

	public JPanel ByggPanel() //utseende
	{
		
		makstemp = new JRadioButton("H�yest Temp");
		mintemp = new JRadioButton("Lavest Temp");
		nedb�r = new JRadioButton("Mest Nedb�r");
		makstemp.addActionListener(this);
		mintemp.addActionListener(this);
		nedb�r.addActionListener(this);
		knappegruppe = new ButtonGroup();
		knappegruppe.add(makstemp);
		knappegruppe.add(mintemp);
		knappegruppe.add(nedb�r);
		
		JPanel venstre = new JPanel();
		venstre.setLayout(new GridLayout(0,1));
		venstre.add(makstemp);
		venstre.add(mintemp);
		venstre.add(nedb�r);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(venstre, BorderLayout.WEST);
		utskrift = new JTextArea(25,60);
		utskrift.setEditable(false);
		panel.add(utskrift,BorderLayout.CENTER);
		
		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[slutt�r-start�r+1];
		for(int i = start�r; i <= slutt�r; i++)
		{
			dagarray[i-start�r] = i + "";
		}
		return dagarray;
	}
	
	public void getMinTempRekorder()
	{/*M�nedlige rekorder, det vil si tabeller som for hver m�ned i �ret viser h�yeste 
	registrerte maksimumstemperatur (i l�pet av alle �r det er m�linger for), verdien for denne, 
	stedet der den er m�lt (inklusive fylke), samt dato og �rstall. 
	(Det kan v�re flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedb�r i l�pet av et d�gn.
	*/
		/*skal returnere en streng med alle �rets m�neder med rekordene for hver m�ned,dato og hvor de er.
		 */
		if(stedliste.tomListe())
		{
			utskrift.setText("ingen steder er registrert");
			return;
		}
		String retur = "M�ned\tFylke\tSted\tMinTemp\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMinTempForM�ned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMinTempForM�ned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMinTempForM�ned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMinTempForM�ned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMinTempForM�ned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMinTempForM�ned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMinTempForM�ned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMinTempForM�ned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMinTempForM�ned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMinTempForM�ned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMinTempForM�ned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMinTempForM�ned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMinTempForM�ned(Calendar.DECEMBER);
		}catch(Exception ex){System.out.println("Feil: ved utregning av m�nedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}
	public void getMaxTempRekorder()
	{/*M�nedlige rekorder, det vil si tabeller som for hver m�ned i �ret viser h�yeste 
	registrerte maksimumstemperatur (i l�pet av alle �r det er m�linger for), verdien for denne, 
	stedet der den er m�lt (inklusive fylke), samt dato og �rstall. 
	(Det kan v�re flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedb�r i l�pet av et d�gn.
	*/
		/*skal returnere en streng med alle �rets m�neder med rekordene for hver m�ned,dato og hvor de er.
		 */
		if(stedliste.tomListe())
		{
			utskrift.setText("ingen steder er registrert");
			return;
		}
		String retur = "M�ned\tFylke\tSted\tMaxTemp\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMaxTempForM�ned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMaxTempForM�ned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMaxTempForM�ned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMaxTempForM�ned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMaxTempForM�ned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMaxTempForM�ned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMaxTempForM�ned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMaxTempForM�ned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMaxTempForM�ned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMaxTempForM�ned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMaxTempForM�ned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMaxTempForM�ned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMaxTempForM�ned(Calendar.DECEMBER);
		}catch(Exception ex){System.out.println("Feil: ved utregning av m�nedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}
	
	public void getNedb�rRekorder()
	{
		if(stedliste.tomListe())
		{
			utskrift.setText("ingen steder er registrert");
			return;
		}
		String retur = "M�ned\tFylke\tSted\tNedb�r\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMestNedb�rForM�ned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMestNedb�rForM�ned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMestNedb�rForM�ned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMestNedb�rForM�ned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMestNedb�rForM�ned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMestNedb�rForM�ned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMestNedb�rForM�ned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMestNedb�rForM�ned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMestNedb�rForM�ned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMestNedb�rForM�ned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMestNedb�rForM�ned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMestNedb�rForM�ned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMestNedb�rForM�ned(Calendar.DECEMBER);
		}catch(Exception ex){System.out.println("Feil: ved utregning av m�nedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == makstemp )
		{
			getMaxTempRekorder();
		}
		
		if(e.getSource() == mintemp )
		{
			getMinTempRekorder();
		}
		
		if(e.getSource() == nedb�r)
		{
			getNedb�rRekorder();
		}
		
	}
}
