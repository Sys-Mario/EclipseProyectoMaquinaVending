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
			precio = precio.multiply(new BigDecimal("1.2"));
		}
		if (this.getTamanio() == TamanioSnacks.S) {
			precio = precio.multiply(new BigDecimal("0.90"));
		}
		return precio.setScale(2, RoundingMode.HALF_UP);
	}

}
