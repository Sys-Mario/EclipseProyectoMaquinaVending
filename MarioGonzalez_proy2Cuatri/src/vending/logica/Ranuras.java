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
		if (cantidad > getMAX_PRODUCTOS() && cantidad <= 0) {
			do {
				System.out.println("ERROR CON LA CANTIDAD INTRODUCIDA ---> ");
				System.out.println("Introduzcala de nuevo: ");
			} while (cantidad <= getMAX_PRODUCTOS() && cantidad > 0);
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
