package vending.productos;

import java.math.*;

public class Bebidas extends Productos {

	private int mililitros;
	private boolean azucarada;
	
	public Bebidas(String id, String nombre, int mililitros, boolean azucarada) {
		super(id, nombre);
		setMililitros(mililitros);
		setAzucarada(azucarada);
		setPrecio(calcularPrecio(new BigDecimal("1")));
	}
	
	public double getMililitros() {
		return mililitros;
	}

	public boolean isAzucarada() {
		return azucarada;
	}

	public void setMililitros(int mililitros) {
		if (mililitros <= 0) {
			mililitros = 100;
		}
		this.mililitros = mililitros;
	}

	public void setAzucarada(boolean azucarada) {
		this.azucarada = azucarada;
	}

	@Override
	public BigDecimal calcularPrecio(BigDecimal precio) {
		BigDecimal diezPorCiento = new BigDecimal("0.1");
		if (isAzucarada()) {
			if (getMililitros() >= 500) {
				precio.multiply(new BigDecimal("0.2"));
			} else {
				precio.multiply(diezPorCiento);
			}
		} else {
			if (getMililitros() < 500) {
				precio.divide(diezPorCiento);
			}
		}
		return precio;
	}

}
