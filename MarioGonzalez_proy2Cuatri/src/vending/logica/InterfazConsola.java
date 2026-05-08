package vending.logica;

public class InterfazConsola {
    // --- COLORES BÁSICOS ---
    public static final String RESET = "\u001B[0m";
    public static final String NEGRO = "\u001B[30m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String PURPURA = "\u001B[35m";
    public static final String CIAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";

    // --- COLORES BRILLANTES (Negrita) ---
    public static final String ROJO_B = "\u001B[1;31m";
    public static final String VERDE_B = "\u001B[1;32m";
    public static final String AMARILLO_B = "\u001B[1;33m";
    public static final String AZUL_B = "\u001B[1;34m";

    // --- FONDOS ---
    public static final String FONDO_ROJO = "\u001B[41m";
    public static final String FONDO_VERDE = "\u001B[42m";

    // --- CARACTERES DE MARCO (Para tus cuadros) ---
    public static final String MARCO_UL = "┌"; // Upper Left
    public static final String MARCO_UR = "┐"; // Upper Right
    public static final String MARCO_DL = "└"; // Down Left
    public static final String MARCO_DR = "┘"; // Down Right
    public static final String MARCO_H  = "─"; // Horizontal
    public static final String MARCO_V  = "│"; // Vertical
    public static final String MARCO_T  = "┬"; 
    public static final String MARCO_X  = "┼";

    // Método de utilidad para limpiar la pantalla (funciona en algunas consolas)
    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static String centrarTexto(String texto, int ancho) {
        if (texto == null || texto.length() >= ancho) {
            return texto;
        }
        int espaciosTotal = ancho - texto.length();
        int espaciosIzquierda = espaciosTotal / 2;
        int espaciosDerecha = espaciosTotal - espaciosIzquierda;

        return " ".repeat(espaciosIzquierda) + texto + " ".repeat(espaciosDerecha);
    }
}