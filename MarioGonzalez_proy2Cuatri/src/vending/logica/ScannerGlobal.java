package vending.logica;

import java.util.Scanner;

public class ScannerGlobal {
	
	// Variable estática, final y global para todo el proyecto
	public static final Scanner sc = new Scanner (System.in);
	
	public static void cerrar () {
		sc.close();
	}
	
	public static void pulseEnter() {
		String cian = InterfazConsola.CIAN;
	    String amarillo = InterfazConsola.AMARILLO;
	    String reset = InterfazConsola.RESET;
	    
	    System.out.println("\n");
	    
	    System.out.println(cian + "      ┌──────────────────────────────────────────────────┐");
	    System.out.println("      │ " + amarillo + "       PULSE [ENTER] PARA VOLVER AL MENÚ        " + cian + " │");
	    System.out.println("      └──────────────────────────────────────────────────┘" + reset);
	    
	    ScannerGlobal.sc.nextLine();
	}
}
