package Meterologi;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Meterologi.Lister.*;

public class RegistrerSted implements ActionListener{
	
	StedListe stedliste;

	public JPanel ByggPanel()
	{
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());
		//her lager du panelet sånn du vil ha det
		//feks: panelet.add(blablab);
		
		
		return panelet;
	}
	
	public void melding(String m)
	{
		JOptionPane.showMessageDialog(null,m, "OBS!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
