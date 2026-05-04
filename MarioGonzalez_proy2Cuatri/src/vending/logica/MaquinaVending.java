package vending.logica;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import vending.productos.*;

public class MaquinaVending {

	private Map <String, Ranuras> stock;
	private Deposito depositoMonedas;
	private BigDecimal creditoCliente;
	private final String PIN_ADMIN = "1234";
	
	public MaquinaVending() {
		this.stock = new HashMap<>();
		this.inicializarEstructura();
		this.depositoMonedas = new Deposito (20);
		this.creditoCliente = new BigDecimal("0");
	}
	
	public Map<String, Ranuras> getStock() {
		return stock;
	}

	public Deposito getDepositoMonedas() {
		return depositoMonedas;
	}

	public BigDecimal getCreditoCliente() {
		return creditoCliente;
	}

	public void setStock(Map<String, Ranuras> stock) {
		this.stock = stock;
	}

	public void setDepositoMonedas(Deposito depositoMonedas) {
		this.depositoMonedas = depositoMonedas;
	}

	public void setCreditoCliente(BigDecimal creditoCliente) {
		this.creditoCliente = creditoCliente;
	}

	public void inicializarEstructura () {
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				stock.put(codigo, new Ranuras(null, 0));
			}
		}
		stock.put("A1", new Ranuras(new Bebidas("P01", "Coca", 330, true), 5));
	    stock.put("A2", new Ranuras(new Bebidas("P02", "Agua", 500, false), 10));
	    
	    stock.put("B1", new Ranuras(new Snacks("P03", "Chips", TamanioSnacks.M), 8));
	    stock.put("B2", new Ranuras(new Snacks("P04", "Choco", TamanioSnacks.L), 3));
	}
	
	public void comprarProducto (String codigo) {
		if (stock.containsKey(codigo)) {
			depositoMonedas.tieneCambioSuficiente(creditoCliente);
			
		} else {
			System.out.println("Esa ranura no tiene stock!");
		}
	}
	
	public void introducirMoneda () {
		System.out.println("Que moneda te interesa meter? "
				+ "\nA. 2€\tB. 1€\tC. 0.5€"
				+ "\nD. 0.20€\tE. 0.1€\t 0.05€");
		String eleccion = ScannerGlobal.sc.next();

		
	//	depositoMonedas.añadirMonedas(, 1);
	}
	
	public void mostrarTodo () {
		System.out.println("****************************************************"
				+ "\n\tBIENVENIDO A EXPO-VENDING"
				+ "\n****************************************************");
		System.out.println();
		System.out.println("******************** PRODUCTOS *********************"
				+ "\n  POS\tNOMBRE\t\tPRECIO\tSTOCK");
		System.out.println("====================================================");
		for (char i = 'A'; i <= 'D'; i++) {
			for (int j = 1; j <= 4; j++) {
				String codigo = String.valueOf(i) + j;
				Ranuras ranura = stock.get(codigo);
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
		System.out.println("====================================================");
		System.out.println(); System.out.println();
		System.out.println(" CREDITO ACTUAL: "+ this.creditoCliente
				+ "\n------------------------------------"
				+ "\n1. Insertar Monedas"
				+ "\n2. Seleccionar Producto (Comprar)"
				+ "\n3. Devolver Crédito"
				+ "\n4. Modo Administrador (Requiere PIN)");
		int elegir;
		do {
			System.out.println("Seleccione una opcion: ");
			elegir = ScannerGlobal.sc.nextInt();
			if (elegir < 1 && elegir > 4) {
				System.out.println();
				System.out.println("Eleccion erronea");
				System.out.println();
			}
		} while (elegir < 1 && elegir > 4);
		
		eleccionUsuario(elegir);
	}
	
	private void eleccionUsuario (int elegido) {
		switch (elegido) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			default:
				System.out.println("Eleccion erronea");
				mostrarTodo ();
		}
			
	}
	
	
}
