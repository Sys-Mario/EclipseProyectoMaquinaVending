package vending.usuarios;

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

	public static boolean clienteLogic (Cliente cl, Administrador ad) {
		int elegir = 0;
		boolean salirTotal = true;
		
		while (elegir != 4) {
			cl.getMv().getSistemaStock().imprimirStockCompleto();
			System.out.println("====================================================");
			System.out.println("\n CREDITO ACTUAL: "+ cl.getMv().getCreditoCliente() + "€");
			System.out.print("\n------------------------------------"
					+ "\n1. Insertar Monedas"
					+ "\n2. Seleccionar Producto (Comprar)"
					+ "\n3. Devolver Crédito"
					+ "\n4. Modo Administrador (Requiere PIN)"
					+ "\n0. Salir De La MaquinaVending"
					+ "\nSeleccione una opcion: ");
			if (ScannerGlobal.sc.hasNextInt()) {
	            elegir = ScannerGlobal.sc.nextInt();
	            ScannerGlobal.sc.nextLine(); 
	            
	            if (elegir == 4) {
                    if (cl.modo()) {
                        ad.adminLogic(ad);
                        elegir = 0;
                    } else {
                        elegir = 0;
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
		return salirTotal;
	}
	
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
			case 4:
				modo();
				break;
			default:
				System.out.println("Eleccion erronea");
		}
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
		System.out.println(" [SISTEMA DE SEGURIDAD]");
		
		while (intentosFallidos > 0 && !correcto) {
	        System.out.print("Introduzca el PIN (" + intentosFallidos + " intentos restantes): ");
	        intentoPin = ScannerGlobal.sc.next();

	        if (mv.pinCorrecto(intentoPin)) {
	            System.out.println("Acceso correcto. Cargando panel de control...\n");
	            correcto = true;
	        } else {
	            intentosFallidos--;
	            if (intentosFallidos > 0) {
	                System.out.println("PIN incorrecto. Inténtelo de nuevo.");
	            }
	        }
	    }
		
		if (!correcto) {
	        System.out.println("Te has quedado sin intentos, te redirigimos al menú...");
	    }
		
		return correcto;
	}
}
