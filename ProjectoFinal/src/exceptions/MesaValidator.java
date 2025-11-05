package exceptions;

public final class MesaValidator {

    private MesaValidator() {}

    public static void validarNombreMesa(String nombre) throws MesaNoDisponibleException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MesaNoDisponibleException(
                    "Debe ingresar un nombre para la mesa.",
                    MesaNoDisponibleException.Codigo.NOMBRE_MESA_VACIO
            );
        }
        if (nombre.matches("\\d+")) {
            throw new MesaNoDisponibleException(
                    "El nombre de la mesa no puede ser solo un número.",
                    MesaNoDisponibleException.Codigo.NOMBRE_MESA_SOLO_NUMEROS
            );
        }
    }

    public static void validarNombreMozo(String mozo) throws MesaNoDisponibleException {
        if (mozo == null || mozo.isEmpty()) {
            throw new MesaNoDisponibleException(
                    "Debe ingresar un nombre de mozo.",
                    MesaNoDisponibleException.Codigo.NOMBRE_MOZO_VACIO
            );
        }
        if (mozo.matches(".*\\d.*")) {
            throw new MesaNoDisponibleException(
                    "El nombre del mozo no puede contener números.",
                    MesaNoDisponibleException.Codigo.NOMBRE_MOZO_TIENE_NUMEROS
            );
        }
    }

    public static void validarFilaSeleccionada(int fila, String accion) throws MesaNoDisponibleException {
        if (fila == -1) {
            throw new MesaNoDisponibleException(
                    "Debe seleccionar una mesa para " + accion + ".",
                    MesaNoDisponibleException.Codigo.FILA_NO_SELECCIONADA
            );
        }
    }

    public static void validarEstadoMesa(String estadoActual, String nuevoEstado, String accion)
            throws MesaNoDisponibleException {

        if ("OCUPADA".equalsIgnoreCase(estadoActual) && "eliminar".equalsIgnoreCase(accion)) {
            throw new MesaNoDisponibleException(
                    "No puede eliminar una mesa ocupada.",
                    MesaNoDisponibleException.Codigo.NO_ELIMINAR_MESA_OCUPADA
            );
        }
        if ("OCUPADA".equalsIgnoreCase(estadoActual) && "asignar mozo".equalsIgnoreCase(accion)) {
            throw new MesaNoDisponibleException(
                    "No puede asignar mozo a una mesa ocupada.",
                    MesaNoDisponibleException.Codigo.NO_ASIGNAR_MOZO_MESA_OCUPADA
            );
        }
        if (nuevoEstado != null && estadoActual != null
                && estadoActual.equalsIgnoreCase(nuevoEstado)
                && "cambiar estado".equalsIgnoreCase(accion)) {
            throw new MesaNoDisponibleException(
                    "La mesa ya tiene ese estado.",
                    MesaNoDisponibleException.Codigo.ESTADO_SIN_CAMBIO
            );
        }
    }
}
