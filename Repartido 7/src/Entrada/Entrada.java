package Entrada;

// Entrada.java
public abstract class Entrada {
    protected String numeroEntrada;
    protected int horaAcceso; // 0..23

    public Entrada(String numeroEntrada, int horaAcceso) {
        if (horaAcceso < 0 || horaAcceso > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }
        this.numeroEntrada = numeroEntrada;
        this.horaAcceso = horaAcceso;
    }

    public abstract boolean validarEntrada();

    public void mostrarDatos() {
        System.out.println("Entrada %s | Hora de acceso: %02d:00%n" + numeroEntrada + " "+ horaAcceso);
    }
}

