package vending.usuarios;

import vending.logica.InterfazConsola;
import vending.logica.MaquinaVending;
import vending.logica.ScannerGlobal;

public class Cliente {

	private MaquinaVending mv;
	
	public Cliente(MaquinaVending mv) {
		this.mv = mv;
	}

	public MaquinaVending getMv() {
		return mv;
	}

	public boolean clienteLogic (Cliente cl, Administrador ad) {
		int elegir = -1;
		boolean seguirEnLaMaquina = true;
		
		String verde = InterfazConsola.VERDE_B;
	    String cian = InterfazConsola.CIAN;
	    String reset = InterfazConsola.RESET;
	    String blanco = InterfazConsola.BLANCO;
		
		while (seguirEnLaMaquina) {
			cl.getMv().getSistemaStock().imprimirStockCompleto();
			System.out.println("\n" + cian + "┌──────────────────────────────────────────────────┐" + reset);
	        System.out.printf(cian + "│" + blanco + "         CRÉDITO ACTUAL: " + verde + "%10.2f €" + cian + "             │%n" + reset, 
	                          cl.getMv().getCreditoCliente());
	        System.out.println(cian + "├──────────────────────────────────────────────────┤" + reset);
	        System.out.println(cian + "│" + reset + "  1. Insertar Monedas                             " + cian + "│" + reset);
	        System.out.println(cian + "│" + reset + "  2. Seleccionar Producto (Comprar)               " + cian + "│" + reset);
	        System.out.println(cian + "│" + reset + "  3. Devolver Crédito                             " + cian + "│" + reset);
	        System.out.println(cian + "│" + blanco + "  4. Modo Administrador (PIN)                     " + cian + "│" + reset);
	        System.out.println(cian + "│" + InterfazConsola.ROJO + "  0. Salir de la Máquina                          " + cian + "│" + reset);
	        System.out.println(cian + "└──────────────────────────────────────────────────┘" + reset);
	        System.out.print(cian + "  Seleccione una opción: " + reset);
			if (ScannerGlobal.sc.hasNextInt()) {
	            elegir = ScannerGlobal.sc.nextInt();
	            ScannerGlobal.sc.nextLine(); 
	            
	            if (elegir == 0) {
	            	seguirEnLaMaquina = false;
	            } else if (elegir == 4) {
                    if (cl.modo()) {
                        ad.adminLogic(ad);
                    }
                } else if (elegir >= 1 && elegir <= 3) {
                    cl.eleccionUsuario(elegir);
                } else {
                    System.out.println("\nElección errónea\n");
                }
	        } else {
	            System.out.println("Por favor, introduce un número.");
	            ScannerGlobal.sc.next();
	        }
		}
		return seguirEnLaMaquina;
	}
	
	public void eleccionUsuario (int elegido) {
		InterfazConsola.limpiarConsola();
		switch (elegido) {
			case 1:
				introducir();
				break;
			case 2:
				comprar();
				break;
			case 3:
				mv.devolverCambio();
				break;
			default:
				System.out.println("Eleccion erronea");
		}
		ScannerGlobal.pulseEnter();
	}
	
	public void introducir () {
		System.out.println();
	    String eleccion = "";
	    
	    do {
	        System.out.println(" ------     MENU DE INSERCIÓN     ------ "
	        		+ "\n A. 2.00€\tB. 1.00€\tC. 0.50€"
	        		+ "\n D. 0.20€ \tE. 0.10€\tF. 0.05€"
	        		+ "\n  -------\tS. Salir\t-------");
	        System.out.print("Introduzca opción: ");
	        
	        eleccion = ScannerGlobal.sc.next().toUpperCase().trim();
	        
	        mv.introducirMoneda(eleccion);
	    } while (!eleccion.equals("S"));
	}
	
	public void comprar () {
		 String eleccion;
		do {
	        System.out.println("");
	        System.out.println(" ------     MENU DE COMPRA     ------ "
	                + "\nDime el código del producto que desea comprar.");
	        System.out.print("Introduzca código: ");
	        
	        eleccion = ScannerGlobal.sc.nextLine().toUpperCase().trim();
	        
	        if (!mv.getSistemaStock().getRanuras().containsKey(eleccion)) {
	            System.out.println("El código es incorrecto, prueba de nuevo...");
	        }
	    } while (!mv.getSistemaStock().getRanuras().containsKey(eleccion));
		
		mv.comprarProducto(eleccion);
	}
	
	public boolean modo () {
		String intentoPin;
		int intentosFallidos = 3;
		boolean correcto = false;
		
		String verde = InterfazConsola.VERDE_B;
	    String rojo = InterfazConsola.ROJO_B;
	    String reset = InterfazConsola.RESET;
	    String fondoNegro = "\u001B[40m";
		
	    System.out.println("\n" + fondoNegro + verde + " ╔═════════════════════════════════════════════════╗ ");
	    System.out.println(" ║        [!] ALERTA: ACCESO RESTRINGIDO           ║ ");
	    System.out.println(" ║       SISTEMA OPERATIVO KALI-VENDING v2.0       ║ ");
	    System.out.println(" ╚═════════════════════════════════════════════════╝ " + reset);
		
		while (intentosFallidos > 0 && !correcto) {
			System.out.print( verde + " \nID_ROOT@MACHINE:~$ " + reset + "ENTER_PIN (" + intentosFallidos + " left): ");
	        intentoPin = ScannerGlobal.sc.next();

	        if (mv.pinCorrecto(intentoPin)) {
	        	System.out.println(" \n" + verde + "[OK] HASH_MATCH: " + reset + "Verificando integridad...");
	            System.out.print(verde + " CARGANDO: [####################] 100%" + reset);
	            System.out.println(" \n" + verde + ">>> ACCESO CONCEDIDO. BIENVENIDO, ADMIN." + reset);
	            correcto = true;
	        } else {
	            intentosFallidos--;
	            if (intentosFallidos < 1) {
	            	System.out.println(rojo + " [ERR] INVALID_TOKEN. La intrusión ha sido reportada." + reset);
	            } else if (intentosFallidos > 0) {
	            	System.out.println(rojo + " [ERR] INVALID_TOKEN." + reset);
	            }
	        }
	    }
		
		if (!correcto) {
			System.out.println("\n" + rojo + "╔══════════════════════════════════════════════════╗");
	        System.out.println("║ [!!!] SISTEMA BLOQUEADO - CONTACTE CON SOPORTE   ║");
	        System.out.println("╚══════════════════════════════════════════════════╝" + reset);
	    }
		ScannerGlobal.sc.nextLine();
		ScannerGlobal.pulseEnter();
		return correcto;
	}
}
