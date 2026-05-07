package vending.logica;

import java.util.Scanner;

public class ScannerGlobal {
	
	// Variable estática, final y global para todo el proyecto
	public static final Scanner sc = new Scanner (System.in);
	
	public static void cerrar () {
		sc.close();
	}
	
	public static void pulseEnter() {
		System.out.println("\nPulse Enter para volver al menú...");
	    ScannerGlobal.sc.nextLine();
	}
}
