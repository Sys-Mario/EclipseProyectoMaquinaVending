package vending.usuarios;

import vending.logica.MaquinaVending;
import vending.logica.ScannerGlobal;

public class Administrador {

private MaquinaVending mv;
	
	public Administrador(MaquinaVending mv) {
		this.mv = mv;
	}

	public MaquinaVending getMv() {
		return mv;
	}
	
	public static void adminLogic(Administrador ad) {
        int elegir = 0;
        System.out.println("\n****************************************************"
                + "\n\tPANEL DE ADMINISTRACIÓN"
                + "\n****************************************************");
        
        while (elegir != 0) {
            
            System.out.print("\n1. Reponer Stock (Cargar cantidad)"
                    + "\n2. Introducir Nuevo Producto"
                    + "\n3. Eliminar Producto"
                    + "\n4. Ver Estado de la Caja"
                    + "\n5. Ver Recaudación Total"
                    + "\n6. Ver Stock Detallado"
                    + "\n7. Cambiar PIN de acceso"
                    + "\n0. Salir al Menú Cliente"
                    + "\nSeleccione una opcion: ");
            
            if (ScannerGlobal.sc.hasNextInt()) {
                elegir = ScannerGlobal.sc.nextInt();
                ScannerGlobal.sc.nextLine(); 
                
                ad.eleccionAdmin(elegir); 
                
            } else {
                System.out.println("Por favor, introduce un número.");
                ScannerGlobal.sc.next();
            }
        }
    }
	
	public void eleccionAdmin (int elegido) {
		switch (elegido) {
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
							
				break;
			case 6:
				
				break;
			case 7:
				
				break;
			default:
				System.out.println("Eleccion erronea");
		}
	}
	
	public void reponer () {
		
	}
}
