package vending.logica;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
	
	public List<BigDecimal> calcularCambioNecesario (BigDecimal importeCliente){
		
		
		
		return null;
	}
	
	public boolean tieneCambioSuficiente (BigDecimal importeCliente) {
		
		
		
		return false;
	}
	
	public void añadirMonedas (Monedas moneda, int cantidad) {
		int monedasActuales = monedas.getOrDefault(moneda, 0);
		monedas.put(moneda, monedasActuales + cantidad);
	}

	public Map<Monedas, Integer> getMonedas() {
		return monedas;
	}

	public void setMonedas(Map<Monedas, Integer> monedas) {
		this.monedas = monedas;
	}
	
}
