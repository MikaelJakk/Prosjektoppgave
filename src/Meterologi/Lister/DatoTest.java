package Meterologi.Lister;

import java.util.*;

import javax.swing.JOptionPane;

public class DatoTest {
	
	public static void main(String[] args)
	{
		Calendar en = Calendar.getInstance();
		en.setTimeInMillis(3600);
		en.set(2000,7,1);
		
		Calendar to = Calendar.getInstance();
		to.setTimeInMillis(3600);
		to.set(2000,7,4);
		
		String s = "en : 1.6.2000\nto: 4.6.2000\nHvilken er før den andre?\nSvar: ";
					if(en.before(to))
							s+="en er først";
					else
						s+="to er først";
		JOptionPane.showMessageDialog(null,s);
	}
	
}
