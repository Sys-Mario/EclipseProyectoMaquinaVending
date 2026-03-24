package vending.productos;

import java.math.*;
import java.util.Objects;

public abstract class Productos {

	private String id;
	private String nombre;
	private BigDecimal precio;
	
	public Productos(String id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public Productos(String nombre) {
		setNombre(nombre);
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public abstract BigDecimal calcularPrecio(BigDecimal precio);

	@Override
	public boolean equals(Object obj) {
		Productos otroPro = (Productos) obj;
		return this.id.equals(otroPro.id);
	}

	@Override
	public String toString() {
		return nombre + "\t\t" + precio;
	}
	
}
