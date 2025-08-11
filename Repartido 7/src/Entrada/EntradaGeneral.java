package Entrada;

// EntradaGeneral.java
public class EntradaGeneral extends Entrada {
    public EntradaGeneral(String numeroEntrada, int horaAcceso) {
        super(numeroEntrada, horaAcceso);
    }

    @Override
    public boolean validarEntrada() {
        // Válida entre 10 y 22 (inclusive)
        return horaAcceso >= 10 && horaAcceso <= 22;
    }
}
