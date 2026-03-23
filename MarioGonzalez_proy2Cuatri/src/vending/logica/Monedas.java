package vending.logica;

import java.math.BigDecimal;

public enum Monedas {
	
	DOS_EUROS("2.00"),
	UN_EURO("1.00"),
	CINCUENTA_CENT("0.50"),
	VEINTE_CENT("0.20"),
	DIEZ_CENT("0.10"),
	CINCO_CENT("0.05");
	
	private final BigDecimal valor;
	
	Monedas(String valorTexto) {
	    this.valor = new BigDecimal(valorTexto);
	}
	
	public BigDecimal getValor() {
	    return valor;
	}
}
