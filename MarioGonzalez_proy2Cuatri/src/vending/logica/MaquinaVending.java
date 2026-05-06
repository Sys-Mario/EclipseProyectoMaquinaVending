package vending.logica;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import vending.productos.*;

public class MaquinaVending {

	private Map <String, Ranuras> stock;
	private Deposito depositoMonedas;
	private BigDecimal creditoCliente;
	private final String PIN_ADMIN = "1234";
	
	public MaquinaVending() {
		this.stock = new HashMap<>();
		this.depositoMonedas = new Deposito (20);
		// this.inicializarEstructura();
		this.inicializarEstructuraPrueba();
		this.creditoCliente = new BigDecimal("0");
	}
	
	public Map<String, Ranuras> getStock() {
		return stock;
	}

	public Deposito getDepositoMonedas() {
		return depositoMonedas;
	}

	public BigDecimal getCreditoCliente() {
		return creditoCliente;
	}

	public void setStock(Map<String, Ranuras> stock) {
		this.stock = stock;
	}

	public void setDepositoMonedas(Deposito depositoMonedas) {
		this.depositoMonedas = depositoMonedas;
	}

	public void setCreditoCliente(BigDecimal creditoCliente) {
		this.creditoCliente = creditoCliente;
	}

	// Inicializa la infraestructura de la web.
	
	public void inicializarEstructura () {
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				stock.put(codigo, new Ranuras(null, 0));
			}
		}
	}
	
	public void inicializarEstructuraPrueba () {
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				stock.put(codigo, new Ranuras(null, 0));
			}
		}
		stock.put("A1", new Ranuras(new Bebidas("P01", "Coca", 330, true), 5));
	    stock.put("A2", new Ranuras(new Bebidas("P02", "Agua", 500, false), 10));
	    
	    stock.put("B1", new Ranuras(new Snacks("P03", "Chips", TamanioSnacks.M), 8));
	    stock.put("B2", new Ranuras(new Snacks("P04", "Choco", TamanioSnacks.L), 3));
	    
	    depositoMonedas.rellenarMap(10);
	}
	
	public void imprimirEstadoMaquina () {
		System.out.println("****************************************************"
				+ "\n\tBIENVENIDO A EXPO-VENDING"
				+ "\n****************************************************");
		System.out.println();
		System.out.println("******************** PRODUCTOS *********************"
				+ "\n  CODE\tNOMBRE\t\tPRECIO\tSTOCK");
		System.out.println("====================================================");
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				Ranuras ranura = stock.get(codigo);
				if (ranura.getProducto() != null) {
					System.out.println("  " + codigo
							+ "\t" + ranura.getProducto().getNombre()
							+ "\t\t" + ranura.getProducto().getPrecio()
							+ "\t" + ranura.getCantidad());
				} else {
					System.out.println("  " + codigo + "\t---\t\t---\tVACÍO");
				}
			}
		}
		System.out.println("====================================================");
		System.out.println();
		System.out.println(" CREDITO ACTUAL: "+ this.creditoCliente);
			
	}
	
	public void iniciar () {
		int elegir = 0;
		
		while (elegir != 4) {
			imprimirEstadoMaquina ();
			System.out.print("\n------------------------------------"
					+ "\n1. Insertar Monedas"
					+ "\n2. Seleccionar Producto (Comprar)"
					+ "\n3. Devolver Crédito"
					+ "\n4. Modo Administrador (Requiere PIN)"
					+ "\nSeleccione una opcion: ");
			if (ScannerGlobal.sc.hasNextInt()) {
	            elegir = ScannerGlobal.sc.nextInt();
	            ScannerGlobal.sc.nextLine(); 
	            
	            if (elegir < 1 || elegir > 4) {
	                System.out.println("\nEleccion erronea\n");
	            } else {
	                eleccionUsuario (elegir);
	            }
	        } else {
	            System.out.println("Por favor, introduce un número.");
	            ScannerGlobal.sc.next();
	        }
		}		
	}
	
	private void eleccionUsuario (int elegido) {
		switch (elegido) {
			case 1:
				introducirMoneda();
				break;
			case 2:
				comprarProducto();
				break;
			case 3:
				devolverCambio();
				break;
			case 4:
				
				break;
			default:
				System.out.println("Eleccion erronea");
		}
			
	}
	
	/**
	 * Permite al usuario insertar monedas actualizando el crédito y el depósito.
	 */
	public void introducirMoneda() {
		System.out.println();
	    String eleccion = "";
	    
	    Map<String, Monedas> opciones = inicializarOpcionesMonedas();

	    do {
	        System.out.println(" ------     MENU DE INSERCIÓN     ------ "
	        		+ "\n A. 2.00€\tB. 1.00€\tC. 0.50€"
	        		+ "\n D. 0.20€ \tE. 0.10€\tF. 0.05€"
	        		+ "\n  -------\tS. Salir\t-------");
	        System.out.print("Introduzca opción: ");
	        
	        eleccion = ScannerGlobal.sc.next().toUpperCase().trim();

	        if (!eleccion.equals("S")) {
	            Monedas moneda = opciones.get(eleccion);

	            if (moneda != null) {
	                BigDecimal valor = depositoMonedas.getValorMoneda(moneda);
	                
	                depositoMonedas.añadirMonedas(moneda, 1);
	                this.creditoCliente = this.creditoCliente.add(valor);

	                System.out.println("\n(+) Ingresado: " + valor + "€");
	            } else {
	                System.out.println("\nOpción inválida. Pruebe otra...");
	            }
	            System.out.println("Crédito actual: " + this.creditoCliente + "€\n");
	        }
	        // System.out.println(depositoMonedas.toString());
	    } while (!eleccion.equals("S"));
	    
	}

	/**
	 * Método privado auxiliar para no repetir la creación del mapa.
	 */
	private Map<String, Monedas> inicializarOpcionesMonedas() {
	    Map<String, Monedas> mapa = new HashMap<>();
	    mapa.put("A", Monedas.DOS_EUROS);
	    mapa.put("B", Monedas.UN_EURO);
	    mapa.put("C", Monedas.CINCUENTA_CENT);
	    mapa.put("D", Monedas.VEINTE_CENT);
	    mapa.put("E", Monedas.DIEZ_CENT);
	    mapa.put("F", Monedas.CINCO_CENT);
	    return mapa;
	}
	
	public void comprarProducto() {
	    String eleccion;
	    
	    
	    
	    do {
	        System.out.println("");
	        System.out.println(" ------     MENU DE COMPRA     ------ "
	                + "\nDime el código del producto que desea comprar.");
	        System.out.print("Introduzca código: ");
	        
	        eleccion = ScannerGlobal.sc.nextLine().toUpperCase().trim();
	        
	        if (!stock.containsKey(eleccion)) {
	            System.out.println("El código es incorrecto, prueba de nuevo...");
	        }
	    } while (!stock.containsKey(eleccion));
	    
	    Ranuras ranuraSeleccionada = stock.get(eleccion);
	    Productos productoSeleccionado = ranuraSeleccionada.getProducto();
	    System.out.println();
	    
	    if (productoSeleccionado != null) {
	        System.out.println("Procesando la compra de " + productoSeleccionado.getNombre() + "...");
	        
	        if (ranuraSeleccionada.hayStock()) {
	            BigDecimal precio = productoSeleccionado.getPrecio();

	            if (creditoCliente.compareTo(precio) >= 0) {

	                BigDecimal cambioADevolver = creditoCliente.subtract(precio);

	                if (depositoMonedas.tieneCambioSuficiente(cambioADevolver)) {
	                    
	                    System.out.println("La compra ha sido un éxito.");
	                    
	                    creditoCliente = creditoCliente.subtract(precio);
	                    ranuraSeleccionada.reducirStock();
	                    
	                    devolverCambio(); 

	                } else {
	                    System.out.println("ERROR: La máquina no dispone de cambio suficiente.");
	                    System.out.println("Operación cancelada. Recupere su crédito en el menú principal.");
	                }

	            } else {
	                System.out.println("No tienes crédito suficiente (Faltan: " + precio.subtract(creditoCliente) + "€)");
	            }
	        } else {
	            System.out.println("Lo sentimos, no queda stock de este producto.");
	        }
	    } else {
	        System.out.println("En esa ranura no hay ningún producto asociado.");
	    }

	}
	
	public void devolverCambio() {
		System.out.println();
		 
	    if (creditoCliente.compareTo(BigDecimal.ZERO) <= 0) {
	        System.out.println("No hay crédito que devolver.");
	    } else {
	    	Map<Monedas, Integer> monedasEntregadas = depositoMonedas.calcularCambioNecesario(creditoCliente);

		    if (monedasEntregadas != null) {
		        System.out.println("--- DEVOLVIENDO CRÉDITO ---");
		        for (Map.Entry<Monedas, Integer> entrada : monedasEntregadas.entrySet()) {
		            Monedas m = entrada.getKey();
		            int cant = entrada.getValue();
		            
		            System.out.println("  " + cant + " moneda(s) de " + depositoMonedas.getValorMoneda(m) + "€");
		        }
		        creditoCliente = BigDecimal.ZERO;
		    } else {
		        System.out.println("ERROR CRÍTICO: La máquina no tiene cambio suficiente.");
		        System.out.println("Por favor, llame al servicio técnico. Su crédito de " + creditoCliente + "€ se mantiene.");
		    }
	    }
	    System.out.println("\nPulse Enter para volver al menú...");
	    ScannerGlobal.sc.nextLine();
	}
}
