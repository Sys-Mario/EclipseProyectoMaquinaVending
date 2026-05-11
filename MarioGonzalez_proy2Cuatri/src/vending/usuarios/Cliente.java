package vending.usuarios;

import static vending.logica.InterfazConsola.*;
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

	/**
	 * Muestra la logica del cliente, que puede hacer durante el proceso.
	 * @param cl (Cliente creado)
	 * @param ad (Administrador creado)
	 * @return boolean para terminar todo en el main.
	 */
	public boolean clienteLogic (Cliente cl, Administrador ad) {
		int elegir = -1;
		boolean seguirEnLaMaquina = true;
		
		while (seguirEnLaMaquina) {
			cl.getMv().getSistemaStock().imprimirStockCompleto();
			System.out.println("\n" + CIAN + "    ┌──────────────────────────────────────────────────┐");
	        System.out.printf(CIAN + "    │" + BLANCO + "         CRÉDITO ACTUAL: " + VERDE_B + "%10.2f€" + CIAN + "              │%n", 
	                          cl.getMv().getCreditoCliente());
	        System.out.println(CIAN + "    ├──────────────────────────────────────────────────┤");
	        System.out.println(CIAN + "    │" + RESET + "  1. " + AMARILLO + "Insertar Monedas" + "                             " + CIAN + "│");
	        System.out.println(CIAN + "    │" + RESET + "  2. " + AMARILLO + "Seleccionar Producto (Comprar)" + "               " + CIAN + "│");
	        System.out.println(CIAN + "    │" + RESET + "  3. Devolver Crédito                             " + CIAN + "│");
	        System.out.println(CIAN + "    │" + BLANCO + "  4. Acceso Técnico (PIN)" + "                         " + CIAN + "│");
	        System.out.println(CIAN + "    │" + ROJO + "  0. Salir de la Máquina" + "                          " + CIAN + "│");
	        System.out.println(CIAN + "    └──────────────────────────────────────────────────┘" + RESET);
	        System.out.print(CIAN + "     ➤ Seleccione una opción: " + RESET);
			if (ScannerGlobal.sc.hasNextInt()) {
	            elegir = ScannerGlobal.sc.nextInt();
	            ScannerGlobal.sc.nextLine(); 
	            
	            switch (elegir) {
                case 0:
                    seguirEnLaMaquina = false;
                    System.out.println("\n" + AMARILLO + "  Gracias por usar la máquina de " + RESET + "MARIO" + AMARILLO + ". ¡Hasta pronto!" + RESET);
                    break;
                case 4:
                    if (cl.modoAdmin()) {
                        ad.adminLogic(ad);
                    }
                    break;
                case 1, 2, 3:
                    cl.eleccionUsuario(elegir);
                    break;
                default:
                    System.out.println("\n" + ROJO + "  [!] Opción no disponible. Inténtelo de nuevo." + RESET);
            }
	        } else {
	        	System.out.println(ROJO + "  [!] Por favor, introduce un número válido." + RESET);
	            ScannerGlobal.sc.next();
	        }
		}
		return seguirEnLaMaquina;
	}
	
	/**
	 * En base a lo que elija el cliente, mandara a un sitio u otro.
	 * @param elegido el valor introducido por cliente.
	 */
	public void eleccionUsuario (int elegido) {
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
	}
	
	/**
	 * Muestra y pide datos para introducir dinero a la maquina.
	 */
	public void introducir () {
		System.out.println();
	    String eleccion = "";
	    
	    do {
	    	System.out.println(CIAN + "    ┌──────────────────────────────────────────────────┐");
	        System.out.println("    │" + BLANCO + "            SELECTOR DE MONEDAS (CRÉDITO)         " + CIAN + "│");
	        System.out.println("    ├──────────────────────────────────────────────────┤");
	        System.out.println("    │       " + AMARILLO + " A. 2.00€ " + CIAN + " │ " + AMARILLO + " B. 1.00€ " + CIAN + " │ " + AMARILLO + " C. 0.50€ " + CIAN + "       │");
	        System.out.println("    │       " + AMARILLO + " D. 0.20€ " + CIAN + " │ " + AMARILLO + " E. 0.10€ " + CIAN + " │ " + AMARILLO + " F. 0.05€ " + CIAN + "       │");
	        System.out.println("    ├──────────────────────────────────────────────────┤");
	        System.out.println("    │" + ROJO + "              S. FINALIZAR INSERCIÓN              " + CIAN + "│");
	        System.out.println("    └──────────────────────────────────────────────────┘" + RESET);
	        
	        System.out.print(CIAN + "     ➤ Inserte moneda (Letra): " + RESET);
	        
	        eleccion = ScannerGlobal.sc.next().toUpperCase().trim();
	        ScannerGlobal.sc.nextLine();
	        
	        mv.introducirMoneda(eleccion);
	    } while (!eleccion.equals("S"));
	    System.out.println(VERDE + "     [✔] Inserción finalizada correctamente." + RESET);
	}
	
	/**
	 * Muestra y pide datos para comprar un producto.
	 */
	public void comprar () {
		 String eleccion;
		 boolean codigoValido = false;
		 
		do {
			System.out.println("\n" + CIAN + "     ┌──────────────────────────────────────────────────┐");
	        System.out.println("     │" + BLANCO + "                MENÚ DE SELECCIÓN                 " + CIAN + "│");
	        System.out.println("     └──────────────────────────────────────────────────┘" + RESET);
	        System.out.println("      Escriba el " + AMARILLO + "CÓDIGO" + RESET + " del producto (ej: A1, B2...)");
	        System.out.print(CIAN + "      ➤ SELECCIÓN: " + RESET);
	        
	        eleccion = ScannerGlobal.sc.nextLine().toUpperCase().trim();
	        
	        if (!mv.getSistemaStock().getRanuras().containsKey(eleccion)) {
	        	System.out.println("\n" + ROJO + "      [!] ERROR: El código [" + eleccion + "] no existe." + RESET);
	            System.out.println("      Por favor, consulte el catálogo de productos.");
	            System.out.println(CIAN + "     ──────────────────────────────────────────────────" + RESET);
	        } else {
	        	codigoValido = true;
	        }
	    } while (!codigoValido);
		
		System.out.println("\n" + VERDE + "      [OK] Código aceptado. Procesando selección..." + RESET);
	    try { Thread.sleep(500); } catch (Exception e) {}
		
		mv.comprarProducto(eleccion);
	}
	
	/**
	 * Metodo para preguntar la contraseña de Admin.
	 * @return boolean que avisa si ha fallado (false) o consiguio entrar (true).
	 */
	public boolean modoAdmin () {
		String intentoPin;
		int intentosFallidos = 3;
		boolean correcto = false;
		
	    String fondoNegro = "\u001B[40m";
		
	    System.out.println("\n" + fondoNegro + VERDE_B + "      ╔═════════════════════════════════════════════════╗      ");
	    System.out.println("      ║        " + AMARILLO + "[!] ALERTA: ACCESO RESTRINGIDO" + VERDE_B + "           ║      ");
	    System.out.println("      ║       SISTEMA OPERATIVO KALI-VENDING v2.0       ║      ");
	    System.out.println("      ╚═════════════════════════════════════════════════╝      " + RESET);
		
		while (intentosFallidos > 0 && !correcto) {
			System.out.print(VERDE + "      ID_ROOT@MARIO_MACHINE:~$ " + RESET + "ENTER_PIN (" + intentosFallidos + " left): ");
	        intentoPin = ScannerGlobal.sc.next();

	        if (mv.pinCorrecto(intentoPin)) {
	        	System.out.println("\n" + VERDE + "      [OK] HASH_MATCH: " + RESET + "Verificando integridad...");
	        	System.out.print(VERDE + "      CARGANDO: [" + RESET);
	            for(int i=0; i<20; i++) {
	                try { Thread.sleep(50); System.out.print("#"); } catch (Exception e) {}
	            }
	            System.out.println(VERDE + "] 100%" + RESET);
	            
	            System.out.println("\n" + VERDE_B + "      >>> ACCESO CONCEDIDO. BIENVENIDO, ADMIN." + RESET);
	            correcto = true;
	        } else {
	            intentosFallidos--;
	            if (intentosFallidos >= 1) {
	                System.out.println(ROJO_B + "      [ERR] INVALID_TOKEN. Intento fallido." + RESET);
	            } else {
	                System.out.println(ROJO_B + "      [ERR] FATAL_ERROR: La intrusión ha sido reportada a MARIO_SECURITY." + RESET);
	            }
	        }
	    }
		
		if (!correcto) {
			System.out.println("\n" + ROJO_B + fondoNegro + "      ╔══════════════════════════════════════════════════╗ ");
	        System.out.println("      ║ [!!!] SISTEMA BLOQUEADO - CONTACTE CON SOPORTE   ║ ");
	        System.out.println("      ╚══════════════════════════════════════════════════╝ " + RESET);
	    }
		ScannerGlobal.sc.nextLine();
		ScannerGlobal.pulseEnter();
		return correcto;
	}
}
