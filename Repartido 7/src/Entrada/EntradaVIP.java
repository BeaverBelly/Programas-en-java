package Entrada;

public class EntradaVIP extends Entrada {
    public EntradaVIP(String numeroEntrada, int horaAcceso) {
        super(numeroEntrada, horaAcceso);
    }

    @Override
    public boolean validarEntrada() {
        return true; // acceso libre a cualquier hora
    }
}

