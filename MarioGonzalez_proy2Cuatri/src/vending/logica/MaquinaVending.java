package vending.logica;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import vending.productos.*;

public class MaquinaVending {

	private Stock sistemaStock;
	private Deposito depositoMonedas;
	private BigDecimal creditoCliente;
	private String pinAdmin = "1234";
	
	public MaquinaVending() {
		this.sistemaStock = new Stock();
		this.depositoMonedas = new Deposito (20);
		this.creditoCliente = BigDecimal.ZERO;
	}
	
	public Stock getSistemaStock() { return sistemaStock; }
	public Deposito getDepositoMonedas() { return depositoMonedas; }
	public BigDecimal getCreditoCliente() { return creditoCliente; }
	public void setCreditoCliente(BigDecimal creditoCliente) { this.creditoCliente = creditoCliente; }
	public String getPinAdmin() { return pinAdmin; }
	public void setPinAdmin(String pinAdmin) { this.pinAdmin = pinAdmin; }
	
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

                System.out.println("\n(+) Ingresado: " + valor + "€");
            } else {
                System.out.println("\nOpción inválida. Pruebe otra...");
            }
            System.out.println("Crédito actual: " + this.creditoCliente + "€\n");
        }
        System.out.println(depositoMonedas.toString());
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
	
	public void comprarProducto(String eleccion) {
	   
	    Ranuras ranuraSeleccionada = sistemaStock.getRanuras().get(eleccion);
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
	                    ScannerGlobal.pulseEnter();
	                }

	            } else {
	                System.out.println("No tienes crédito suficiente (Faltan: " + precio.subtract(creditoCliente) + "€)");
	                ScannerGlobal.pulseEnter();
	            }
	        } else {
	            System.out.println("Lo sentimos, no queda stock de este producto.");
	            ScannerGlobal.pulseEnter();
	        }
	    } else {
	        System.out.println("En esa ranura no hay ningún producto asociado.");
	        ScannerGlobal.pulseEnter();
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
	    ScannerGlobal.pulseEnter();
	}
	
	public boolean pinCorrecto (String pin) {
		return pin.equals(getPinAdmin());
	}
	
}
