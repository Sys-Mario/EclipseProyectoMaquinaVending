package vending.productos;

import java.math.*;

public class Snacks extends Productos {

	private TamañoSnacks tamaño;
	
	public Snacks(String id, String nombre, TamañoSnacks tamaño) {
		super(id, nombre);
		setTamaño(tamaño);
		setPrecio(calcularPrecio(new BigDecimal("1.5")));
	}
	
	public TamañoSnacks getTamaño() {
		return tamaño;
	}

	public void setTamaño(TamañoSnacks tamaño) {
		this.tamaño = tamaño;
	}

	@Override
	public BigDecimal calcularPrecio(BigDecimal precio) {
		if (this.getTamaño() == TamañoSnacks.L) {
			precio.multiply(new BigDecimal("0.2"));
		}
		if (this.getTamaño() == TamañoSnacks.S) {
			precio.divide(new BigDecimal("0.1"));
		}
		return precio;
	}

}
