package jerarquiaProductos;

import java.math.*;

public abstract class Producto {

	private String id;
	private String nombre;
	private BigDecimal precio;
	
	public Producto(String id, String nombre) {
		setId(id);
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
	
}
