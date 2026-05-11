package vending.logica;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static vending.logica.InterfazConsola.*;

import vending.productos.*;

public class MaquinaVending {

	private Stock sistemaStock;
	private Deposito depositoMonedas;
	private BigDecimal creditoCliente;
	private BigDecimal totalesAcumulados;
	private String pinAdmin = "1234";
	
	public MaquinaVending() {
		this.sistemaStock = new Stock();
		this.depositoMonedas = new Deposito (10);
		this.creditoCliente = BigDecimal.ZERO;
		this.totalesAcumulados = BigDecimal.ZERO;
		
	}
	
	public Stock getSistemaStock() { return sistemaStock; }
	public Deposito getDepositoMonedas() { return depositoMonedas; }
	public BigDecimal getCreditoCliente() { return creditoCliente; }
	public void setCreditoCliente(BigDecimal creditoCliente) { this.creditoCliente = creditoCliente; }
	public BigDecimal getTotalesAcumulados() { return totalesAcumulados; }
	public void setTotalesAcumulados(BigDecimal totalesAcumulados) { this.totalesAcumulados = totalesAcumulados; }
	public String getPinAdmin() { return pinAdmin; }
	public void setPinAdmin(String pinAdmin) { this.pinAdmin = pinAdmin; }
	
	public void imprimirBienvenida() {
		System.out.println(AZUL + "  ╔════════════════════════════════════════════════════════╗");

		System.out.println("  ║    " + CIAN + "   __  __      _      ____       _  U  ___  u       " + AZUL + "║");
		System.out.println("  ║    " + CIAN + " U|' \\/ '|uU  / _\\  U |  _ \\ u  | |  \\/'_ \\/        " + AZUL + "║");
		System.out.println("  ║    " + CIAN + " \\| |\\/| |/ \\/ _ \\/  \\| |_) |/  | |  | | | |        " + AZUL + "║");
		System.out.println("  ║    " + CIAN + "  | |  | |  / ___ \\   |  _ <    | |  | |_| |        " + AZUL + "║");
		System.out.println("  ║    " + CIAN + "  |_|  |_| /_/   \\_\\  |_| \\_\\  u|_| u \\___/         " + AZUL + "║");
		System.out.println("  ║    " + CIAN + "  <<,  >>,, \\\\    >>  //   \\\\_  _//,-.  \\\\          " + AZUL + "║");

		System.out.println("  ╠════════════════════════════════════════════════════════╣");

	    int anchoCaja = 56;
	    String subtitulo = centrarTexto("SISTEMA DE VENDING PROFESIONAL", anchoCaja);
	    String version   = centrarTexto("v2026.05.10 - [SISTEMA ACTIVO]", anchoCaja);
	    
	    System.out.println("  ║" + BLANCO + subtitulo + AZUL + "║");
	    System.out.println("  ║" + CIAN   + version   + AZUL + "║");
	    System.out.println("  ╚════════════════════════════════════════════════════════╝" + RESET);

	    System.out.print("\n" + AMARILLO + "  >> Iniciando sistemas" + RESET);
	    for(int i = 0; i < 3; i++) {
	        try { Thread.sleep(300); System.out.print(AMARILLO + "." + RESET); } catch (Exception e) {}
	    }
	    System.out.println(VERDE + " [READY]" + RESET);
	}
	
	/**
	 * Permite al usuario insertar monedas actualizando el crédito y el depósito.
	 */
	public void introducirMoneda(String eleccion) {
		Map<String, Monedas> opciones = inicializarOpcionesMonedas();

        if (!eleccion.equals("S")) {
            Monedas moneda = opciones.get(eleccion);

            if (moneda != null) {
                BigDecimal valor = depositoMonedas.getValorMoneda(moneda);
                
                depositoMonedas.añadirMonedas(moneda, 1);
                this.creditoCliente = this.creditoCliente.add(valor);

                System.out.println("\n" + VERDE + "    [+] Moneda de " + valor + "€ aceptada." + RESET);
            } else {
            	System.out.println("\n" + ROJO + "    [!] Opción inválida. La moneda ha sido rechazada." + RESET);
            }
            System.out.println(CIAN + "    ┌────────────────────────────┐");
            System.out.printf(CIAN + "    │  Crédito total: " + AMARILLO_B + "%7s€" + CIAN + "   │%n", this.creditoCliente);
            System.out.println(CIAN + "    └────────────────────────────┘" + RESET + "\n");
        }
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
	
	/**
	 * Pasa varias condiciones para poder llegar a comprar un producto con su codigo.
	 * @param eleccion string del codigo.
	 */
	public void comprarProducto(String eleccion) {
	   
	    Ranuras ranuraSeleccionada = sistemaStock.getRanuras().get(eleccion);
	    
	    if (ranuraSeleccionada != null) {
	    	Productos productoSeleccionado = ranuraSeleccionada.getProducto();
		    System.out.println();
	    	if (productoSeleccionado != null) {
		        System.out.println(CIAN + "      Procesando la compra de: " + AMARILLO + productoSeleccionado.getNombre() + CIAN + "..." + RESET);
		        
		        if (ranuraSeleccionada.hayStock()) {
		            BigDecimal precio = productoSeleccionado.getPrecio();

		            if (creditoCliente.compareTo(precio) >= 0) {
		                BigDecimal cambioADevolver = creditoCliente.subtract(precio);

		                if (depositoMonedas.tieneCambioSuficiente(cambioADevolver)) {
		                    
		                	System.out.print(VERDE + "      Dispensando... [");
		                    for(int i=0; i<15; i++) {
		                        try { Thread.sleep(100); System.out.print("■"); } catch (Exception e) {}
		                    }
		                    System.out.println("] ¡CLACK!" + RESET);
		                	
		                	System.out.println("\n" + VERDE_B + "      ★ COMPRA FINALIZADA CON ÉXITO ★" + RESET);
		                    
		                    creditoCliente = creditoCliente.subtract(precio);
		                    totalesAcumulados = totalesAcumulados.add(precio);
		                    ranuraSeleccionada.reducirStock();
		                    
		                    devolverCambio(); 

		                } else {
		                	System.out.println(ROJO + "      [!] ERROR TÉCNICO: La máquina no dispone de cambio suficiente." + RESET);
		                    System.out.println(AMARILLO + "      Por seguridad, la operación ha sido cancelada." + RESET);
		                    System.out.println("      Recupere su crédito en el menú principal.");
		                    ScannerGlobal.pulseEnter();
		                }

		            } else {
		                System.out.println(ROJO + "      [!] CRÉDITO INSUFICIENTE" + RESET);
		                System.out.println(" Faltan: " + AMARILLO + precio.subtract(creditoCliente) + "€" + RESET);
		                ScannerGlobal.pulseEnter();
		            }
		        } else {
		        	System.out.println(ROJO + "      [X] STOCK AGOTADO: Lo sentimos, no quedan unidades." + RESET);
		            ScannerGlobal.pulseEnter();
		        }
		    } else {
		    	System.out.println(ROJO + "      [!] RANURA VACÍA: No hay producto en la selección " + eleccion + RESET);
		        ScannerGlobal.pulseEnter();
		    }
	    } else {
	    	System.out.println(ROJO + "      [!] ERROR: El código " + eleccion + " no existe." + RESET);
	        ScannerGlobal.pulseEnter();
	    }
	}
	
	/**
	 * Muestra por pantalla el credito de cambio.
	 */
	public void devolverCambio() {
		System.out.println();
		 
	    if (creditoCliente.compareTo(BigDecimal.ZERO) <= 0) {
	    	System.out.println(CIAN + " [i] No hay crédito acumulado para devolver." + RESET);
	    } else {
	    	Map<Monedas, Integer> monedasEntregadas = depositoMonedas.calcularCambioNecesario(creditoCliente);

		    if (monedasEntregadas != null) {
		    	System.out.println(VERDE + "     ┌──────────────────────────────────────────┐");
	            System.out.println("     │            " + BLANCO + "DEVOLVIENDO CRÉDITO" + VERDE + "           │");
	            System.out.println("     ├──────────────────────────────────────────┤" + RESET);
		        for (Map.Entry<Monedas, Integer> entrada : monedasEntregadas.entrySet()) {
		            Monedas m = entrada.getKey();
		            int cant = entrada.getValue();
		            
		            System.out.printf(VERDE + "     │ " + RESET + "        %-3d moneda(s) de %-5s€" + VERDE + "          │%n", 
                            cant, depositoMonedas.getValorMoneda(m));
		        }
		        System.out.println(VERDE + "     └──────────────────────────────────────────┘" + RESET);
	            System.out.println(AMARILLO + "           [¡Recoja su cambio de la bandeja!]" + RESET);
		        
		        creditoCliente = BigDecimal.ZERO;
		    } else {
		    	System.out.println(ROJO_B + "      ╔══════════════════════════════════════════════╗");
	            System.out.println("      ║   ERROR: SIN CAMBIO EN EL DEPÓSITO           ║");
	            System.out.println("      ╚══════════════════════════════════════════════╝" + RESET);
	            System.out.println(AMARILLO + "      Su crédito de " + creditoCliente + "€ se mantiene en la máquina.");
	            System.out.println("      Contacte con el servicio técnico." + RESET);
		    }
	    }
	    ScannerGlobal.pulseEnter();
	}
	
	/**
	 * Verifica si el pin insertado es igual al de la Maquina Vending.
	 * @param pin string del supuesto pin insertado.
	 * @return boolean si es igual o no.
	 */
	public boolean pinCorrecto (String pin) {
		return pin.equals(getPinAdmin());
	}
	
	/**
	 * Para poder cambiar el pin de Administrador si quisiera.
	 * @param pinNuevo el nuevo que quiere poner.
	 * @param pinConfirmacion la copia para verificar.
	 * @return un String si lo ha conseguido.
	 */
	public String cambiarPin(String pinNuevo, String pinConfirmacion) {
	    String frase = "";

	    if (!pinNuevo.equals(pinConfirmacion)) {
	        frase = "Los PINs no coinciden.";
	    } else if (!pinNuevo.matches("\\d{4}")) { 
	        frase = "El PIN debe tener exactamente 4 números (0-9).";
	    } else {
	        this.pinAdmin = pinNuevo;
	        frase = "PIN de administrador actualizado correctamente.";
	    }
	    return frase;
	}
	
}
