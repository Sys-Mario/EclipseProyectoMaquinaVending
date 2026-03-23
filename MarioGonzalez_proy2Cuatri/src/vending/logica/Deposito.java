package vending.logica;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Deposito {

	private Map<Monedas, Integer> monedas;
	private BigDecimal recaudacionTotal;
	
	public List<BigDecimal> calcularCambioNecesario (BigDecimal importeCliente){
		
		
		
		return null;
	}
	
	public boolean tieneCambioSuficiente (BigDecimal importeCliente) {
		
		
		
		return false;
	}
	
	public void añadirMonedas (Monedas moneda, int cantidad) {
		int monedasActuales = monedas.get(moneda);
		monedas.put(moneda, monedasActuales + cantidad);
	}
	
}
