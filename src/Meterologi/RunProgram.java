package Meterologi;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class RunProgram extends Lista{

	public static HovedVindu vindu = null;

	public static void main(String[] args)
	{
		vindu = new HovedVindu();
		lesLista();
	}
}
