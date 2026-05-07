package vending.logica;

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
	
	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public void setCantidad(int cantidad) {
	    if (cantidad >= 0 && cantidad <= MAX_PRODUCTOS) {
	        this.cantidad = cantidad;
	    } else {
	        System.out.println("Error: Cantidad fuera de rango (0-" + MAX_PRODUCTOS + ")");
	    }
	}
	
	/*
	public void setCantidad(int cantidad) {
		if (cantidad > getMAX_PRODUCTOS() && cantidad <= 0) {
			do {
				System.out.println("ERROR CON LA CANTIDAD INTRODUCIDA ---> ");
				System.out.println("Introduzcala de nuevo: ");
				cantidad = ScannerGlobal.sc.nextInt();
			} while (cantidad <= getMAX_PRODUCTOS() && cantidad > 0);
		} else {
			this.cantidad = cantidad;
		}
	}
	*/
	public boolean hayStock () {
		return getCantidad() > 0;
	}
		
	public void reducirStock () {
		this.cantidad--;
	}

	public boolean estaVacia() {
		return getProducto() == null;
	}
	
	public void vaciar () {
		this.producto = null;
		this.cantidad = 0;
	}
	
	@Override
	public String toString() {
		return producto + " " + cantidad;
	}
	
	
}
