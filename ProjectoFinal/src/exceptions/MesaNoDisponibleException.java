package exceptions;

import javax.swing.JOptionPane;

/**
 * Clase que maneja todas las validaciones relacionadas con mesas
 * y muestra automáticamente los mensajes de error.
 */
public class MesaNoDisponibleException {

    public static boolean validarNombreMesa(String nombre) {
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para la mesa.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nombre.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "El nombre de la mesa no puede ser solo un número.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validarNombreMozo(String mozo) {
        if (mozo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de mozo.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (mozo.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "El nombre del mozo no puede contener números.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validarFilaSeleccionada(int fila, String accion) {
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una mesa para " + accion + ".", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validarEstadoMesa(String estadoActual, String nuevoEstado, String accion) {
        if ("OCUPADA".equalsIgnoreCase(estadoActual) && "eliminar".equalsIgnoreCase(accion)) {
            JOptionPane.showMessageDialog(null, "No puede eliminar una mesa ocupada.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if ("OCUPADA".equalsIgnoreCase(estadoActual) && "asignar mozo".equalsIgnoreCase(accion)) {
            JOptionPane.showMessageDialog(null, "No puede asignar mozo a una mesa ocupada.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nuevoEstado != null && estadoActual.equalsIgnoreCase(nuevoEstado) && "cambiar estado".equalsIgnoreCase(accion)) {
            JOptionPane.showMessageDialog(null, "La mesa ya tiene ese estado.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
