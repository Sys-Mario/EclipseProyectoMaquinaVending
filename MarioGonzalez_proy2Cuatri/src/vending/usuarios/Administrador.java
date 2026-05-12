package vending.usuarios;

import static vending.logica.InterfazConsola.*;
import vending.logica.MaquinaVending;
import vending.logica.Ranuras;
import vending.logica.ScannerGlobal;
import vending.productos.Bebida;
import vending.productos.Productos;
import vending.productos.Snack;
import vending.productos.TamanioSnacks;

public class Administrador {

private MaquinaVending mv;
	
	public Administrador(MaquinaVending mv) {
		this.mv = mv;
	}

	public MaquinaVending getMv() {
		return mv;
	}
	
	public boolean adminLogic(Administrador ad) {
        int elegir = -1;
        boolean salirMaquina = true;
        String pinAntigua = mv.getPinAdmin();
        
        while (elegir != 0 && elegir != 666 && mv.getPinAdmin().equals(pinAntigua)) {
            
            System.out.println("\n" + CIAN + "     ╔══════════════════════════════════════════════════╗" + RESET);
            System.out.println(CIAN + "     ║ " + ROJO_B + "             PANEL DE ADMINISTRACIÓN            " + CIAN + " ║" + RESET);
            System.out.println(CIAN + "     ╚══════════════════════════════════════════════════╝" + RESET);
        	
        	System.out.println(CIAN + "       1." + RESET + " Reponer Stock (Cargar cantidad)");
            System.out.println(CIAN + "       2." + RESET + " Introducir Nuevo Producto");
            System.out.println(CIAN + "       3." + RESET + " Eliminar Producto");
            System.out.println(CIAN + "       4." + RESET + " Ver Estado de la Caja");
            System.out.println(CIAN + "       5." + RESET + " Ver Recaudación Total");
            System.out.println(CIAN + "       6." + RESET + " Ver Stock Detallado");
            System.out.println(CIAN + "       7." + RESET + " Cambiar PIN de acceso");
            System.out.println(ROJO_B + "       0. Salir al Menú Cliente" + RESET);
            System.out.println(ROJO_B + "       666. [TOCAR EN URGENCIA] Apagar Maquina" + RESET);
            System.out.println(CIAN + "───────────────────────────────────────────────────────────────" + RESET);
            System.out.print(CIAN + "       Seleccione una opción: " + RESET);
            
            if (ScannerGlobal.sc.hasNextInt()) {
                elegir = ScannerGlobal.sc.nextInt();
                ScannerGlobal.sc.nextLine(); 
                
                if (elegir == 666) {
                	System.out.println("\n" + AMARILLO + "  Gracias por usar la máquina de " + RESET + "MARIO" + AMARILLO + ". ¡Adios!" + RESET + " ☺♥");
                	salirMaquina = false;
                } else if (elegir != 0) {
                	ad.eleccionAdmin(elegir);
                }
            } else {
            	System.out.println("\n" + ROJO + "  [!] Error: Por favor, introduce un número." + RESET);
                ScannerGlobal.sc.next();
            }
        }
        return salirMaquina;
    }
	
	public void eleccionAdmin (int elegido) {
		switch (elegido) {
			case 1:
				menuReponer ();
				break;
			case 2:
				menuProductoNuevo();
				break;
			case 3:
				menuEliminarProducto();
				break;
			case 4:
				mv.getDepositoMonedas().imprimirDepositoVisual();
				break;
			case 5:
				menuRecaudacionTotal();
				break;
			case 6:
				mv.getSistemaStock().imprimirStockCompleto();
				break;
			case 7:
				menuCambioPin();
				break;
			default:
				System.out.println("\n" + ROJO + "  [!] Opción no disponible. Inténtelo de nuevo." + RESET);
		}
		ScannerGlobal.pulseEnter();
	}
	
	/**
	 * Piden todos los datos para poder reponer un producto.
	 */
	public void menuReponer () {
		int cantidad;
		String code = "";
		
		System.out.println("\n" + AMARILLO + "     ╔══════════════════════════════════════════════════╗");
	    System.out.println("     ║" + BLANCO + "            MÓDULO DE REPOSICIÓN STOCK            " + AMARILLO + "║");
	    System.out.println("     ╚══════════════════════════════════════════════════╝" + RESET);
	    
		do {
			code = pedirCodigo(false);
	        
	        if (!mv.getSistemaStock().validarFormatoCodigo(code)) {
	        	System.out.println(ROJO + "      Formato inválido. Reintente." + RESET);
	        }
	    } while (!mv.getSistemaStock().validarFormatoCodigo(code));
		
		ScannerGlobal.sc.nextLine();
		Ranuras pr = mv.getSistemaStock().getRanuras().get(code);
		
		if (pr.getProducto() != null) {
			System.out.println("\n" + BLANCO + "      PRODUCTO SELECCIONADO: " + AMARILLO + pr.getProducto().getNombre() + RESET);
	        System.out.println("      STOCK ACTUAL: " + (pr.getCantidad() < 5 ? ROJO : VERDE) + pr.getCantidad() + " unidades" + RESET);
			if (pr.getCantidad() > 5) {
				System.out.println(AMARILLO + "      El stock es suficiente. ¿Desea continuar?" + RESET);
			}
			System.out.print("\n      ¿Cuántas unidades desea añadir? (Máx. capacidad 10): ");
			if (ScannerGlobal.sc.hasNextInt()) {
	            cantidad = ScannerGlobal.sc.nextInt();
	            ScannerGlobal.sc.nextLine();

	            int totalPrevio = pr.getCantidad();
	            
	            if (totalPrevio + cantidad > 10) {
	                System.out.println(ROJO + "      No cabe tanto stock. Capacidad máxima: 10." + RESET);
	            } else if (cantidad < 0) {
	                System.out.println(ROJO + "      No puede añadir cantidades negativas." + RESET);
	            } else {
	                pr.añadirCantidad(cantidad);
	                System.out.println("\n" + VERDE + "      [OK] Inventario actualizado." + RESET);
	                System.out.println(CIAN + "      Nuevo total en " + code + ": " + BLANCO + pr.getCantidad() + " unidades." + RESET);
	            }
	        } else {
	            System.out.println(ROJO + "      Entrada no válida." + RESET);
	            ScannerGlobal.sc.next();
	        }
	    } else {
	        System.out.println(ROJO + "\n      La ranura " + code + " está vacía o no existe." + RESET);
	    }
	}
	
	/**
	 * Implementacion de un nuevo producto
	 * Se le piden todos los datos necesarios.
	 */
	public void menuProductoNuevo () {
		
		System.out.println("\n" + CIAN + "     ┌──────────────────────────────────────────────────┐");
	    System.out.println("     │" + AMARILLO_B + "          REGISTRO DE NUEVO PRODUCTO              " + CIAN + "│");
	    System.out.println("     └──────────────────────────────────────────────────┘" + RESET);
	    
		String code = pedirCodigo (true);
		
		int tipo = 0;
	    while (tipo < 1 || tipo > 2) {
	        System.out.print("\n" + CIAN + "     Tipo:" + RESET + " [1] Snack  [2] Bebida: ");
	        if (ScannerGlobal.sc.hasNextInt()) {
	            tipo = ScannerGlobal.sc.nextInt();
	        } else {
	            ScannerGlobal.sc.next();
	        }
	    }
		
		String id = "";
		boolean idValido = false;
		
		do {
			System.out.print(AMARILLO + "     Dime el ID (Ej: P001): " + RESET);
		    id = ScannerGlobal.sc.next().trim().toUpperCase();

		    if (!mv.getSistemaStock().validarFormatoID(id)) {
		        System.out.println(ROJO + "      [!] El formato debe ser una 'P' seguida de 3 números (ej: P005)." + RESET);
		    } else if (mv.getSistemaStock().comprobarId(id)) { 
		    	System.out.println(ROJO + "      [!] El ID ya esta registrado." + RESET);
		    } else {
		        idValido = true;
		    }
		} while (!idValido);
		
		ScannerGlobal.sc.nextLine();
		String nombreFinal = null;

		while (nombreFinal == null) {
		    System.out.print(AMARILLO + "     Nombre del Producto: " + RESET);
		    String entradaAdmin = ScannerGlobal.sc.nextLine(); 
		    
		    nombreFinal = Productos.formatoNombre(entradaAdmin);

		    if (nombreFinal == null) {
		        System.out.println(ROJO + "      [!] El nombre debe contener solo letras y no puede estar vacío." + RESET);
		    }
		}
		
	    Productos pr = null;
	    
		switch (tipo) {
		case 1:
			TamanioSnacks tamanio = null;
			while (tamanio == null) {
				System.out.print(CIAN + "     Introduce el tamaño (S, M, L): " + RESET);
			    try {
			        tamanio = TamanioSnacks.valueOf(ScannerGlobal.sc.next().trim().toUpperCase());
			    } catch (IllegalArgumentException e) {
			    	System.out.println(ROJO + "      [!] Use S, M o L." + RESET);
			    }
			}
			pr = new Snack(id, nombreFinal, tamanio);
			break;
		case 2:
			
			System.out.print(CIAN + "     Mililitros: " + RESET);
	        int mililitros = ScannerGlobal.sc.hasNextInt() ? ScannerGlobal.sc.nextInt() : 330;
			
			ScannerGlobal.sc.nextLine();
			
			boolean azucarada = pedirSiNo("     Es azucarada?");
			pr = new Bebida(id, nombreFinal, mililitros, azucarada);
			
			break;
		default:
			System.out.println(ROJO + "     Eleccion erronea"+ RESET);
		}
		
		if (pr != null) {
			System.out.print(AMARILLO + "     Cantidad inicial: " + RESET);
			int cantidad = ScannerGlobal.sc.hasNextInt() ? ScannerGlobal.sc.nextInt() : 0;
			
			String fraseResultado = mv.getSistemaStock().añadirProducto(code, pr, cantidad);
			System.out.println("\n" + VERDE_B + "   >>> " + fraseResultado + RESET);
		}
		
	}
	
	/**
	 * Eliminacion de un producto por codigo.
	 */
	public void menuEliminarProducto () {
		
		System.out.println("\n" + ROJO + "     ╔══════════════════════════════════════════════════╗");
	    System.out.println("     ║              ELIMINAR PRODUCTO                   ║");
	    System.out.println("     ╚══════════════════════════════════════════════════╝" + RESET);
		
		String code = pedirCodigo (false);
		
		System.out.print("\n" + AMARILLO + "      ¿Está seguro de que desea eliminar el producto en " + code + "? " + RESET);
	    if (pedirSiNo(" ")) {
	        
	    	if (!mv.getSistemaStock().getRanuras().get(code).estaVacia()) {
	    		if (mv.getSistemaStock().eliminarProducto(code)) {
	    			System.out.print(ROJO + "\n      Eliminando datos de la ranura " + code + "..." + RESET);
	    	        
	    	        try { Thread.sleep(600); } catch (Exception e) {}
	    			
	    			System.out.println("\n" + VERDE_B + "      [✔] El producto fue eliminado correctamente." + RESET);
		        } 
	    	} else {
	            System.out.println("\n" + ROJO + "      [!] La ranura ya estaba vacía o no se pudo acceder." + RESET);
	        }
	    } else {
	        System.out.println("\n" + CIAN + "      [i] Operación cancelada por el administrador." + RESET);
	    }
	    ScannerGlobal.sc.nextLine();
	}
	
	/**
	 * Metodo para pedir si o no.
	 * @param mensaje para saber a que se refiere.
	 * @return boolean de true (Si) o false (No).
	 */
	public boolean pedirSiNo(String mensaje) {
	    boolean resultado = false;
	    boolean entradaValida = false;
	    
	    do {
	    	System.out.print(AMARILLO + mensaje + CIAN + " (S/N): " + RESET);
	        String entrada = ScannerGlobal.sc.next().trim().toUpperCase();
	        
	        if (entrada.equals("S")) {
	            resultado = true;
	            entradaValida = true;
	        } else if (entrada.equals("N")) {
	            resultado = false;
	            entradaValida = true;
	        } else {
	        	System.out.print("\n" + ROJO + "      [!] Por favor, introduce 'S' para sí o 'N' para no." + RESET);
	        }
	    } while (!entradaValida);
	    
	    return resultado;
	}
	
	/**
	 * Para pedir codigos y poder validar las cosas.
	 * @return el codigo.
	 */
	public String pedirCodigo (boolean debeEstarVacia) {
		String code = "";
		boolean codeValido = false;
		do {
			System.out.print(AMARILLO + "     ➤ Introduzca el código de ranura (ej. A1): " + RESET);
			code = ScannerGlobal.sc.next().trim().toUpperCase();

			if (!mv.getSistemaStock().validarFormatoCodigo(code)) {
				System.out.println(ROJO + "      [!] Formato de código inválido. Use Letra + Número [A-D+1-4]." + RESET);
	        } else {
				Ranuras r = mv.getSistemaStock().getRanuras().get(code);
				
				if (r == null) {
					System.out.println(ROJO + "      [!] El código " + code + " no existe en el sistema." + RESET);
				} else {
					if (debeEstarVacia && !r.estaVacia()) {
						System.out.println(ROJO + "      [!] La ranura " + code + " ya está ocupada por: " + CIAN + r.getProducto().getNombre() + RESET);
					} else {
					    codeValido = true;
					}
				}	
	        }
			if (!codeValido) {
				System.out.println(CIAN + "────────────────────────────────────────────────────" + RESET);
			}
			
		} while (!codeValido);
		return code;
	}
	
	/**
	 * Vista de toda la recaudacion en total de los productos.
	 */
	public void menuRecaudacionTotal () {
		System.out.println("\n" + CIAN + "      ╔══════════════════════════════════════════════════╗");
	    System.out.println("      ║" + BLANCO + "           REPORTE DE VENTAS DIARIAS              " + CIAN + "║");
	    System.out.println("      ╚══════════════════════════════════════════════════╝" + RESET);
	
	    System.out.print(AMARILLO + "       >> Calculando totales de caja... " + RESET);
	    try { Thread.sleep(1000); } catch (Exception e) {} // Pausa dramática para el cierre de caja
	    System.out.println(VERDE + "[COMPLETADO]" + RESET);
	    
	    System.out.println("\n" + VERDE + "       ┌────────────────────────────────────────────────┐");
	    System.out.printf(VERDE + "       │      " + BLANCO + " TOTAL RECAUDADO HOY: " + VERDE_B + " %13s€ " + VERDE + "    │%n", 
	                      String.format("%.2f", mv.getDepositoMonedas().getTotalesAcumulados()));
	    System.out.println(VERDE + "       └────────────────────────────────────────────────┘" + RESET);
	    
	    System.out.println(CIAN + "       Informe generado con éxito el " + java.time.LocalDate.now() + RESET);
	}
	
	/**
	 * El menu para el cambio de Pin del administrador.
	 */
	public void menuCambioPin() {
	    System.out.println("\n" + AMARILLO + "      ╔══════════════════════════════════════════════════╗");
		System.out.println("      ║" + BLANCO + "          CAMBIAR CONTRASEÑA DE ACCESO            " + AMARILLO + "║");
		System.out.println("      ╚══════════════════════════════════════════════════╝" + RESET);
		
		System.out.print(CIAN + "       ➤ Ingrese PIN antiguo: " + RESET);
		String passAntigua = ScannerGlobal.sc.next();
		
		System.out.print(CIAN + "       ➤ Ingrese nuevo PIN (4 números): " + RESET);
		String pass1 = ScannerGlobal.sc.next();
		
		System.out.print(CIAN + "       ➤ Confirme nuevo PIN: " + RESET);
		String pass2 = ScannerGlobal.sc.next();
		
		String resultado = mv.cambiarPin(pass1, pass2);
		
		if (resultado.startsWith("PIN")) {
		    System.out.println("\n" + VERDE + "       [✔] " + resultado + RESET);
		} else {
		    System.out.println("\n" + ROJO + "       [!] " + resultado + RESET);
	    }
	    
	    ScannerGlobal.sc.nextLine();
	}
	
}
