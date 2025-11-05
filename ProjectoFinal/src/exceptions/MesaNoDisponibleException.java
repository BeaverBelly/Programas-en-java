package exceptions;

public class MesaNoDisponibleException extends Exception {

    public enum Codigo {
        NOMBRE_MESA_VACIO,
        NOMBRE_MESA_SOLO_NUMEROS,
        NOMBRE_MOZO_VACIO,
        NOMBRE_MOZO_TIENE_NUMEROS,
        FILA_NO_SELECCIONADA,
        NO_ELIMINAR_MESA_OCUPADA,
        NO_ASIGNAR_MOZO_MESA_OCUPADA,
        ESTADO_SIN_CAMBIO
    }

    private final Codigo codigo;

    public MesaNoDisponibleException(String mensaje, Codigo codigo) {
        super(mensaje);
        this.codigo = codigo;
    }

    public Codigo getCodigo() {
        return codigo;
    }
}
