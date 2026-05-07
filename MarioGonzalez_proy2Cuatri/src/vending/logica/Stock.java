package vending.logica;

import java.util.HashMap;
import java.util.Map;

import vending.productos.Bebidas;
import vending.productos.Productos;
import vending.productos.Snacks;
import vending.productos.TamanioSnacks;

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
        
		ranuras.put("A1", new Ranuras(new Bebidas("P01", "Coca", 330, true), 5));
		ranuras.put("A2", new Ranuras(new Bebidas("P02", "Agua", 500, false), 10));
	    
		ranuras.put("B1", new Ranuras(new Snacks("P03", "Chips", TamanioSnacks.M), 8));
		ranuras.put("B2", new Ranuras(new Snacks("P04", "Choco", TamanioSnacks.L), 3));
    	}
    }
    
    // Validar que un código de ranura tiene el formato correcto
    public boolean validarFormatoCodigo(String codigo) {
        return codigo != null && codigo.matches("^[A-D][1-4]$");
    }

    // Añadir un producto en una ranura libre (Validando ID único)
    public String añadirProducto(String codigo, Productos nuevoProducto, int cantidad) {
        String frase = null;
    	
    	if (!validarFormatoCodigo(codigo)) {
    		frase = "Error: Formato de código inválido.";
    	} else {
    		// Comprobar si el ID ya existe en otra ranura
            for (Ranuras r : ranuras.values()) {
                if (!r.estaVacia() && r.getProducto().getId().equals(nuevoProducto.getId())) {
                    frase = "Error: El producto con ID " + nuevoProducto.getId() + " ya existe en la máquina.";
                }
            }

            Ranuras r = ranuras.get(codigo);
            if (!r.estaVacia()) {
            	frase = "Error: La ranura " + codigo + " ya está ocupada.";
            }
        	
            if (frase == null) {
            	r.setProducto(nuevoProducto);
                r.setCantidad(cantidad);
            	frase = "Producto añadido correctamente.";
            }
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
    	System.out.println("******************** PRODUCTOS *********************"
				+ "\n  CODE\tNOMBRE\t\tPRECIO\tSTOCK");
		System.out.println("====================================================");
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				Ranuras ranura = ranuras.get(codigo);
				if (ranura.getProducto() != null) {
					System.out.println("  " + codigo
							+ "\t" + ranura.getProducto().getNombre()
							+ "\t\t" + ranura.getProducto().getPrecio()
							+ "\t" + ranura.getCantidad());
				} else {
					System.out.println("  " + codigo + "\t---\t\t---\tVACÍO");
				}
			}
		}
    }

    public Map<String, Ranuras> getRanuras() {
    	return ranuras; 
    }
}