package vending.productos;

import java.math.*;

public class Snacks extends Productos {

	private TamanioSnacks tamanio;
	
	public Snacks(String id, String nombre, TamanioSnacks tamanio) {
		super(id, nombre);
		setTamanio(tamanio);
		setPrecio(calcularPrecio(new BigDecimal("1.5")));
	}
	
	public TamanioSnacks getTamanio() {
		return tamanio;
	}

	public void setTamanio(TamanioSnacks tamanio) {
		this.tamanio = tamanio;
	}

	@Override
	public BigDecimal calcularPrecio(BigDecimal precio) {
		if (this.getTamanio() == TamanioSnacks.L) {
			precio.multiply(new BigDecimal("0.2"));
		}
		if (this.getTamanio() == TamanioSnacks.S) {
			precio.divide(new BigDecimal("0.1"));
		}
		return precio;
	}

}
