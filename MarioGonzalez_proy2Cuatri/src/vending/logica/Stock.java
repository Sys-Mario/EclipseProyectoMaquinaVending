package vending.logica;

import java.util.HashMap;
import java.util.Map;

import vending.productos.Bebida;
import vending.productos.Productos;
import vending.productos.Snack;
import vending.productos.TamanioSnacks;
import static vending.logica.InterfazConsola.*;

public class Stock {
    private Map<String, Ranuras> ranuras;

    public Stock() {
        this.ranuras = new HashMap<>();
        //inicializarEstructura();
        inicializarEstructuraPrueba ();
    }

    // Inicializa la infraestructura de la web.
    
    private void inicializarEstructura() {
        for (char i = 'A'; i <= 'D'; i++) {
            for (int j = 1; j <= 4; j++) {
            	ranuras.put(i + String.valueOf(j), new Ranuras(null, 0));
            }
        }
    }
    
    public void inicializarEstructuraPrueba () {
    	for (char i = 'A'; i <= 'D'; i++) {
            for (int j = 1; j <= 4; j++) {
            	ranuras.put(i + String.valueOf(j), new Ranuras(null, 0));
            }
        
		ranuras.put("A1", new Ranuras(new Bebida("P01", "Coca", 330, true), 5));
		ranuras.put("A2", new Ranuras(new Bebida("P02", "Agua", 500, false), 10));
	    
		ranuras.put("B1", new Ranuras(new Snack("P03", "Chips", TamanioSnacks.M), 8));
		ranuras.put("B2", new Ranuras(new Snack("P04", "Choco", TamanioSnacks.L), 3));
    	}
    }
    
    // Validar que un código de ranura tiene el formato correcto
    public boolean validarFormatoCodigo(String codigo) {
        return codigo != null && codigo.matches("^[A-D][1-4]$");
    }
    public boolean validarFormatoID(String id) {
        return id != null && id.matches("^P[0-9]{3}$");
    }
    
    public boolean comprobarId (String id) {
    	boolean comprobar = false;
    	for (Ranuras r : ranuras.values()) {
            if (!r.estaVacia() && r.getProducto().getId().equals(id)) {
            	comprobar = true;
            }
        }
    	return comprobar;
    }
    
    // Añadir un producto en una ranura libre (Validando ID único)
    public String añadirProducto(String codigo, Productos nuevoProducto, int cantidad) {
        String frase = "";
        Ranuras r = ranuras.get(codigo);
    	
    	if (!validarFormatoCodigo(codigo)) {
    		frase = "El formato del código [" + codigo + "] es inválido.";
    	} else if (comprobarId(nuevoProducto.getId())) {
			frase = "El ID " + nuevoProducto.getId() + " ya existe.";
		} else if (r == null) {
        	frase = "El código " + codigo + " no existe.";
        } else if (!r.estaVacia()) {
        	frase = "La ranura " + codigo + " ya está ocupada por " + r.getProducto().getNombre() +". ";
        } else {
        	r.setProducto(nuevoProducto);
            r.setCantidad(cantidad);
        	frase = "ÉXITO: Producto añadido correctamente a la ranura \" + codigo";
        }
    	
        return frase;
    }

    // Eliminar un producto de una ranura
    public boolean eliminarProducto(String codigo) {
        boolean fueEliminado = false;
    	if (validarFormatoCodigo(codigo) && ranuras.containsKey(codigo)) {
            ranuras.get(codigo).vaciar();
            fueEliminado = true;
        }
        return fueEliminado;
    }

    // Mostrar el listado completo (Incluyendo vacías)
    public void imprimirStockCompleto() {
        System.out.println("\n" + CIAN + " ╔════════════════════════════════════════════════════════╗");
        System.out.println(" ║ " + RESET + "                 CATÁLOGO DE PRODUCTOS                " + CIAN + " ║");
        System.out.println(CIAN + " ╠══════╦══════════════════════════╦════════════╦═════════╣");
        System.out.println(" ║ " + BLANCO + "CODE" + CIAN + " ║ " + BLANCO + "       PRODUCTO          " + CIAN + "║ " + BLANCO + "  PRECIO   " + CIAN + "║ " + BLANCO + " STOCK  " + CIAN + "║");
        System.out.println(" ╠══════╬══════════════════════════╬════════════╬═════════╣" + RESET);

		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				Ranuras ranura = ranuras.get(codigo);
				
				if (ranura.getProducto() != null) {
					String colorStock = (ranura.getCantidad() > 0) ? VERDE : ROJO;
	                String indicadorStock = (ranura.getCantidad() > 0) ? String.valueOf(ranura.getCantidad()) : "X";

	                System.out.printf(CIAN + " ║ " + AMARILLO + "%-4s " + CIAN + "║ " + RESET + "%-24s " + CIAN + "║ " + VERDE + " %6.2f€   " + CIAN + "║ " + colorStock + "   %-2s   " + CIAN + "║%n",
	                        codigo,
	                        ranura.getProducto().getNombre(),
	                        ranura.getProducto().getPrecio(),
	                        indicadorStock);
				} else {
					System.out.printf(CIAN + " ║ " + AMARILLO + "%-4s " + CIAN + "║ " + ROJO + "%-24s " + CIAN + "║ " + ROJO + "   -----   " + CIAN + "║ " + ROJO + "  ---   " + CIAN + "║%n",
	                        codigo,
	                        "(Vacío)");
				}
			}
			if (i < 'D') {
				System.out.println(CIAN + " ╠══════╬══════════════════════════╬════════════╬═════════╣" + RESET);
	        }
		}
		System.out.println(CIAN + " ╚══════╩══════════════════════════╩════════════╩═════════╝" + RESET);
    }

    public Map<String, Ranuras> getRanuras() {
    	return ranuras; 
    }
}