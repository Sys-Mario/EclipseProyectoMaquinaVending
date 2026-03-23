package vending.logica;

import java.util.*;
import vending.productos.Productos;

public class Ranuras {
	
	private Productos producto;
	private int cantidad;
	private final int MAX_PRODUCTOS = 10;
	
	public Ranuras(Productos producto, int cantidad) {
		setProducto(producto);
		setCantidad(cantidad);
	}
	
	public Productos getProducto() {
		return producto;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public int getMAX_PRODUCTOS() {
		return MAX_PRODUCTOS;
	}
	
	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public void setCantidad(int cantidad) {
		if (cantidad > getMAX_PRODUCTOS()) {
			this.cantidad = 10;
		} else if (cantidad >= -10 && cantidad <= 0) {
			if (cantidad == 0) {
				this.cantidad = 1;
			} else {
				this.cantidad = cantidad * -1;
			}
		} else {
			this.cantidad = cantidad;
		}
	}
	
	public boolean hayStock () {
		return getCantidad() > 0;
	}
		
	public void reducirStock () {
		this.cantidad--;
	}

	@Override
	public String toString() {
		return producto + " " + cantidad;
	}
	
	
}
