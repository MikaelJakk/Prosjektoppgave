package Meterologi;

////////////////////////////////////////
///////HovedVindu-gui skrevet av Nam Le og  Mikael Jakhelln
////// tab1.java "Registrer et nytt sted"
////// tab2.java "Regsitrer ny data"
////// tab3.java "Vis Data"
////// tab4.java "Statistikk"
////////////////////////////////////////

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class HovedVindu extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	RegistrerSted regsted = new RegistrerSted(); //tab 1 = "Registrer et nytt sted"
	RegistrerData regdata = new RegistrerData(); //tab 2 = "Regsitrer ny data"
	VisData visdata = new VisData(); //tab 3 = "Vis Data"
	tab4 t4 = new tab4(); //tab 4 = "Statistikk"

	public HovedVindu()
	{

		JFrame frame = new JFrame("Hovedvindu");

		JTabbedPane tabs = new JTabbedPane(); // tabs

		JPanel p1 = regsted.ByggPanel();
		JPanel p2 = regdata.ByggPanel();
		JPanel p3 = visdata.ByggPanel();
		JPanel p4 = t4.tab4();

		tabs.addTab("Registrer et nytt sted",null,p1);
		tabs.addTab("Registrer ny Data",null,p2);
	    tabs.addTab("Vis Data",null,p3);
	    tabs.addTab("Statistikk",null,p4);
	    
	    //oppdaterer comboboxer i RegistrerData
	    tabs.addChangeListener(new ChangeListener(){
	    								public void stateChanged(ChangeEvent evt){
	    									try{
	    									regdata.oppdater();
	    									visdata.oppdater();
	    									}catch(Exception ex){System.out.println("Feil: ved " +
	    											"oppdatering av comboboxer"+ex);}
	    									
	    								}
	    							});


		setLayout(new FlowLayout());
	    frame.setPreferredSize( new java.awt.Dimension(800,600) );
		frame.add(tabs);
		frame.setSize(800,600);
		frame.setVisible(true);
	}
}

