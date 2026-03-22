package jerarquiaProductos;

import java.math.*;

public class Bebidas extends Producto {

	private double tamaño;
	private boolean azucarada;
	
	public Bebidas(String id, String nombre, double tamaño, boolean azucarada) {
		super(id, nombre);
		setTamaño(tamaño);
		setAzucarada(azucarada);
		setPrecio(calcularPrecio(new BigDecimal("1")));
	}
	
	public double getTamaño() {
		return tamaño;
	}

	public boolean isAzucarada() {
		return azucarada;
	}

	public void setTamaño(double tamaño) {
		if (tamaño <= 0) {
			tamaño = 100;
		}
		this.tamaño = tamaño;
	}

	public void setAzucarada(boolean azucarada) {
		this.azucarada = azucarada;
	}

	@Override
	public BigDecimal calcularPrecio(BigDecimal precio) {

		if (isAzucarada()) {
			if (getTamaño() >= 500) {
				precio.multiply(new BigDecimal("0.2"));
			} else {
				precio.multiply(new BigDecimal("0.1"));
			}
		} else {
			if (getTamaño() < 500) {
				precio.divide(new BigDecimal("0.1"));
			}
		}
		return precio;
	}

}
