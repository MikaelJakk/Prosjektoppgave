package Meterologi;


public class RunProgram extends Lista
{

	public static HovedVindu vindu = null;

	public static void main(String[] args)
	{
		lesLista();
		vindu = new HovedVindu();
		vindu.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
}
