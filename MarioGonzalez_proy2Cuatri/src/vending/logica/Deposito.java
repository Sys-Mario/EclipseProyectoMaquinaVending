package vending.logica;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import static vending.logica.InterfazConsola.*;

public class Deposito {

	private Map<Monedas, Integer> monedas;
	
	public Deposito(int cantidadInicial) {
		this.monedas = new EnumMap<>(Monedas.class);
		if (cantidadInicial <= 0) {
			rellenarMap();
		} else {
			rellenarMap(cantidadInicial);
		}
	}

	// Rellenan el mapa de una cantidad asociada de monedas o por defecto de 10.
	
	public void rellenarMap ( ) {
		for (Monedas m : Monedas.values()) {
            monedas.put(m, 10);
        }
	}
	
	public void rellenarMap (int cantidad) {
		for (Monedas m : Monedas.values()) {
            monedas.put(m, cantidad);
        }
	}
	
	public Map<Monedas, Integer> getMonedas() {
		return monedas;
	}

	public void setMonedas(Map<Monedas, Integer> monedas) {
		this.monedas = monedas;
	}
	
	/**
	 * Traduce el Enum a un valor numérico para hacer cálculos.
	 */
	public BigDecimal getValorMoneda(Monedas m) {
	    return switch (m) {
	        case DOS_EUROS      -> new BigDecimal("2.00");
	        case UN_EURO        -> new BigDecimal("1.00");
	        case CINCUENTA_CENT -> new BigDecimal("0.50");
	        case VEINTE_CENT    -> new BigDecimal("0.20");
	        case DIEZ_CENT      -> new BigDecimal("0.10");
	        case CINCO_CENT     -> new BigDecimal("0.05");
	        default             -> BigDecimal.ZERO;
	    };
	}
	
	/**
	 * Calcula el cambio necesario a devolver al cliente.
	 * @param importeADevolver / Credito del cliente que debe devolver la maquina.
	 * @return de un mapa con las monedas a devolver.
	 */
	public Map<Monedas, Integer> calcularCambioNecesario(BigDecimal importeADevolver) {
	    Map<Monedas, Integer> cambioAEntregar = new EnumMap<>(Monedas.class);
	    BigDecimal restante = importeADevolver;

	    Monedas[] todasLasMonedas = Monedas.values();

	    for (Monedas m : todasLasMonedas) {
	        BigDecimal valorM = getValorMoneda(m);
	        int cantidadDisponible = monedas.get(m);
	        int monedasAEntregar = 0;

	        while (restante.compareTo(valorM) >= 0 && cantidadDisponible > 0) {
	            restante = restante.subtract(valorM);
	            cantidadDisponible--;
	            monedasAEntregar++;
	        }

	        if (monedasAEntregar > 0) {
	            cambioAEntregar.put(m, monedasAEntregar);
	            monedas.put(m, cantidadDisponible);
	        }
	    }

	    if (restante.compareTo(BigDecimal.ZERO) > 0) {
	        return null;
	    }

	    return cambioAEntregar;
	}

	/**
	 * Revisa todas las monedas hasta que no tiene un importe, devuelve cada vez que resta una moneda al importe del cliente.
	 * Ademas tiene que revisar que tiene cantidad, si no, pasa a la siguiente moneda.
	 * @param importeADevolver / Credito del cliente que debe devolver la maquina. 
	 * @return un booleano si ha podido devolver todo el dinero del cliente.
	 */
	public boolean tieneCambioSuficiente(BigDecimal importeADevolver) {
	    BigDecimal restante = importeADevolver;
	    for (Monedas m : Monedas.values()) {
	        BigDecimal valorM = getValorMoneda(m);
	        int disponible = monedas.get(m);
	        while (restante.compareTo(valorM) >= 0 && disponible > 0) {
	            restante = restante.subtract(valorM);
	            disponible--;
	        }
	    }
	    return restante.compareTo(BigDecimal.ZERO) == 0;
	}
	
	/**
	 * Añade monedas con una cantidad asignada.
	 * @param moneda el tipo que es.
	 * @param cantidad asignada.
	 */
	public void añadirMonedas (Monedas moneda, int cantidad) {
		int monedasActuales = monedas.getOrDefault(moneda, 0);
		monedas.put(moneda, monedasActuales + cantidad);
	}
	
	public void imprimirDepositoVisual() {

		System.out.println("\n" + AZUL + " ┌────────────────────────────────────────────────────────────┐");
	    System.out.println(" │" + AMARILLO_B + "                ESTADO DE LA CAJA DE MONEDAS               " + AZUL + " │");
	    System.out.println(" ├──────────────────────────────┬─────────────────────────────┤");
	    System.out.println(" │" + BLANCO + "      DENOMINACIÓN            " + AZUL + "│" + BLANCO + "      CANTIDAD / ESTADO      " + AZUL + "│");
	    System.out.println(" ├──────────────────────────────┼─────────────────────────────┤" + RESET);


	    for (Map.Entry<Monedas, Integer> entry : monedas.entrySet()) {
	        Monedas m = entry.getKey();
	        int cantidad = entry.getValue();
	        BigDecimal valor = getValorMoneda(m);

	        String colorCant = (cantidad < 5) ? ROJO : (cantidad < 10) ? AMARILLO : VERDE;
	        String aviso     = (cantidad < 5) ? " [¡BAJO!]" : (cantidad == 0) ? " [VACÍO]" : " [OK]";

	        String barra = "■".repeat(Math.min(cantidad, 5));
	        if (cantidad < 5) barra = ROJO + barra + RESET;
	        else barra = VERDE + barra + RESET;
	        
	        String denominacion = m.name() + " (" + String.format("%.2f", valor) + "€)";
	        
	        System.out.printf(AZUL + " │ " + RESET + "%-28s " + AZUL + "│      " + colorCant + "%-4d " + RESET + "%-10s %-10s" + AZUL + "       │%n",
	                denominacion, 
	                cantidad, 
	                barra,
	                colorCant + aviso + RESET);
	    }

	    System.out.println(AZUL + " └──────────────────────────────┴─────────────────────────────┘" + RESET);
	
	    BigDecimal totalCaja = monedas.entrySet().stream()
	            .map(e -> getValorMoneda(e.getKey()).multiply(new BigDecimal(e.getValue())))
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	            
	    System.out.println(CIAN + "  TOTAL EFECTIVO EN CAJA: " + VERDE_B + String.format("%.2f", totalCaja) + "€" + RESET + "\n");
	}
	
	@Override
	public String toString() {
		return "Monedas: " + monedas;
	}
	
}
