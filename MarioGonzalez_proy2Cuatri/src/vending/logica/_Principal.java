package vending.logica;

import vending.usuarios.Administrador;
import vending.usuarios.Cliente;

public class _Principal {
	public static void main(String[] args) {
		
		boolean salir = true;
		
		MaquinaVending mv = new MaquinaVending ();
		
		Cliente cl = new Cliente (mv);
		Administrador ad = new Administrador (mv);
		
		System.out.println("****************************************************"
				+ "\n\tBIENVENIDO A EXPO-VENDING"
				+ "\n****************************************************");
		System.out.println();
	
		while (salir) {
			salir = cl.clienteLogic(cl, ad);
		}

		
	}
	
}
