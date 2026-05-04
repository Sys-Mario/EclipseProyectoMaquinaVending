package vending.logica;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Deposito {

	private Map<Monedas, Integer> monedas;
	
	public Deposito(int cantidadInicial) {
		this.monedas = new EnumMap<>(Monedas.class);
		rellenarMap(cantidadInicial);

	}

	public void rellenarMap (int cantidad) {
		for (Monedas m : Monedas.values()) {
            monedas.put(m, cantidad);
        }
	}
	
	public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Monedas, Integer> entry : monedas.entrySet()) {
            BigDecimal valor = entry.getKey().getValor();
            total = total.add(valor.multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }
	
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

	public Map<Monedas, Integer> getMonedas() {
		return monedas;
	}

	public void setMonedas(Map<Monedas, Integer> monedas) {
		this.monedas = monedas;
	}
	
}
