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

	/**
	 * Pone un formato al nombre, cuando intenta introducirlo a la maquina el administrador
	 * Para que esten en mayus las primeras letras de las palabras.
	 * @param entrada
	 * @return
	 */
	public static String formatoNombre (String entrada) {
		String resultado = null;
		
		if (entrada != null && !entrada.trim().isEmpty() && entrada.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
	        
	        String textoLimpio = entrada.toLowerCase().trim();
	        String[] palabras = textoLimpio.split(" ");
	        String acumulador = "";
	        
	        for (int i = 0; i < palabras.length; i++) {
	            String p = palabras[i];
	            if (!p.isEmpty()) {
	                String primeraMayus = p.substring(0, 1).toUpperCase();
	                String restoMinus = p.substring(1);
	                acumulador = acumulador + primeraMayus + restoMinus + " ";
	            }
	        }
	        
	        if (!acumulador.isEmpty()) {
	            resultado = acumulador.trim();
	        }
	    }
	    return resultado;
	}
	
	@Override
	public boolean equals(Object obj) {
		Productos otroPro = (Productos) obj;
		return this.id.equals(otroPro.id);
	}

	@Override
	public String toString() {
		return nombre + " " + precio;
	}
	
}
