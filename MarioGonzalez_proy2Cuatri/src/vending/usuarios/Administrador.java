package vending.usuarios;

import java.math.BigDecimal;

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
	
	public static void adminLogic(Administrador ad) {
        int elegir = -1;
        System.out.println("\n****************************************************"
                + "\n\tPANEL DE ADMINISTRACIÓN"
                + "\n****************************************************");
        
        while (elegir != 0) {
            
            System.out.print("\n1. Reponer Stock (Cargar cantidad)"
                    + "\n2. Introducir Nuevo Producto"
                    + "\n3. Eliminar Producto"
                    + "\n4. Ver Estado de la Caja"
                    + "\n5. Ver Recaudación Total"
                    + "\n6. Ver Stock Detallado"
                    + "\n7. Cambiar PIN de acceso"
                    + "\n0. Salir al Menú Cliente"
                    + "\nSeleccione una opcion: ");
            
            if (ScannerGlobal.sc.hasNextInt()) {
                elegir = ScannerGlobal.sc.nextInt();
                ScannerGlobal.sc.nextLine(); 
                
                if (elegir != 0) {
                	ad.eleccionAdmin(elegir);
                }                
            } else {
                System.out.println("Por favor, introduce un número.");
                ScannerGlobal.sc.next();
            }
        }
    }
	
	public void eleccionAdmin (int elegido) {
		switch (elegido) {
			case 1:
				reponer ();
				break;
			case 2:
				productoNuevo();
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
							
				break;
			case 6:
				
				break;
			case 7:
				
				break;
			default:
				System.out.println("Eleccion erronea");
		}
	}
	
	public void reponer () {
		int cantidad;
		String code = "";
		do {
	        System.out.print("Dime el código del producto: ");
	        code = ScannerGlobal.sc.next().trim().toUpperCase();
	        
	        if (!mv.getSistemaStock().validarFormatoCodigo(code)) {
	        	System.out.println("Codigo invalido, pruebe otro.");
	        }
	    } while (!mv.getSistemaStock().validarFormatoCodigo(code));
		
		ScannerGlobal.sc.nextLine();
		Ranuras pr = mv.getSistemaStock().getRanuras().get(code);
		
		if (pr != null) {
			System.out.println("Quieres reponer el producto "+ pr.getProducto().getNombre());
			if (pr.getCantidad() > 5) {
				System.out.println("Seguro que quieres reponerlo? Le queda bastante stock...");
			}
			System.out.print("\nDime cuanto stock le quieres poner a mayores - MAX 10."
					+ "\nTiene esta cantidad: "+ pr.getCantidad() + ". Cuanto: ");
			cantidad = ScannerGlobal.sc.nextInt();
			ScannerGlobal.sc.nextLine();
			int cantidadVieja = pr.getCantidad();
			pr.añadirCantidad(cantidad);
			if (pr.getCantidad() > cantidadVieja) {
				System.out.println("Stock modificado.");
			}
		}
	}
	
	public void productoNuevo () {
		String code = "";
		int eleccion = 0;
		boolean entradaValida = false, codeValido = false;
		
		do {
			System.out.print("Dime el código, donde quieres meter el nuevo prodcuto: ");
			code = ScannerGlobal.sc.next().trim().toUpperCase();

			if (!mv.getSistemaStock().validarFormatoCodigo(code)) {
	            System.out.println("Codigo invalido, pruebe otro.");
	        } else {
				Ranuras r = mv.getSistemaStock().getRanuras().get(code);
				
				if (r == null) {
				    System.out.println("El código " + code + " no existe en la máquina.");
				} else if (!r.estaVacia()) {
				    System.out.println("La ranura " + code + " ya tiene el producto: " + r.getProducto().getNombre());
				} else {
				    codeValido = true;
				}
	        }
		} while (!codeValido);
		
		ScannerGlobal.sc.nextLine();
		if (mv.getSistemaStock().getRanuras().containsKey(code) ) {
			
		}
		do {
			System.out.println("Que tipo de producto será? "
					+ "\n 1. Snacks"
					+ "\n 2. Bebidas");
			if (ScannerGlobal.sc.hasNextInt()) {
	            eleccion = ScannerGlobal.sc.nextInt();
	            
	            if (eleccion == 1 || eleccion == 2) {
	                entradaValida = true; 
	            } else {
	                System.out.println("Por favor, selecciona 1 o 2.");
	            }
			} else {
				System.out.println("Esto no es el valor númerico requerido...");
				ScannerGlobal.sc.next();
			}
		} while (!entradaValida);
		
		
		Productos pr = null;
		String id = "";
		boolean idValido = false;
		
		do {
		    System.out.print("Dime el Id del producto (Formato P000, ej: P001): ");
		    id = ScannerGlobal.sc.next().trim().toUpperCase();

		    if (!mv.getSistemaStock().validarFormatoID(id)) {
		        System.out.println("El formato debe ser una 'P' seguida de 3 números (ej: P005).");
		    } else if (mv.getSistemaStock().comprobarId(id)) { 
		        System.out.println("El ID " + id + " ya está registrado en otra ranura.");
		    } else {
		        idValido = true;
		    }
		} while (!idValido);
		
		System.out.println("Nombre del Producto: ");
		String nombre = ScannerGlobal.sc.next();
		
		switch (eleccion) {
		case 1:
			TamanioSnacks tamanio = null;
			while (tamanio == null) {
			    System.out.print("Introduce el tamaño del Snack (S, M, L): ");
			    String entradaTamanio = ScannerGlobal.sc.next().trim().toUpperCase();

			    try {
			        tamanio = TamanioSnacks.valueOf(entradaTamanio);
			    } catch (IllegalArgumentException e) {
			        System.out.println("Tamaño no válido. Por favor, usa S, M o L.");
			    }
			}
			pr = new Snack(id, nombre, tamanio);
			break;
		case 2:
			int mililitros = 0;
			do {
				System.out.println("Introduce los mililitros de la Bebida: ");
				if (ScannerGlobal.sc.hasNextInt()) {
					mililitros = ScannerGlobal.sc.nextInt();
				}
			} while (mililitros == 0);
			
			ScannerGlobal.sc.nextLine();
			boolean azucarada = pedirSiNo("Es azucarada?");
			pr = new Bebida(id, nombre, mililitros, azucarada);
			
			break;
		default:
			System.out.println("Eleccion erronea");
		}
		
		if (pr != null) {
			System.out.println("Cantidad del producto deseado: ");
			int cantidad = ScannerGlobal.sc.nextInt();
			
			String fraseResultado = mv.getSistemaStock().añadirProducto(code, pr, cantidad);
			System.out.println(fraseResultado);
		}
		
	}
	
	public boolean pedirSiNo(String mensaje) {
	    boolean resultado = false;
	    boolean entradaValida = false;
	    
	    do {
	        System.out.print(mensaje + " (S/N): ");
	        String entrada = ScannerGlobal.sc.next().trim().toUpperCase();
	        
	        if (entrada.equals("S")) {
	            resultado = true;
	            entradaValida = true;
	        } else if (entrada.equals("N")) {
	            resultado = false;
	            entradaValida = true;
	        } else {
	            System.out.println("Error: Por favor, introduce 'S' para sí o 'N' para no.");
	        }
	    } while (!entradaValida);
	    
	    return resultado;
	}
	
}
