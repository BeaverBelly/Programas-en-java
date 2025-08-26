package Ejercicio6;

public class CajeroAutomatico {
    private double saldo;

    public CajeroAutomatico(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public void retirarDinero(double cantidad) throws FondosInsuficientesException, CantidadInvalidaException {
        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad debe ser mayor que cero.");
        }
        if (cantidad % 10 != 0) {
            throw new CantidadInvalidaException("La cantidad debe ser múltiplo de 10.");
        }


        if (cantidad > saldo) {
            throw new FondosInsuficientesException("Fondos insuficientes. Saldo disponible: " + saldo);
        }

        saldo -= cantidad;
        System.out.println("✅ Retiro exitoso. Ha retirado: " + cantidad + " | Saldo restante: " + saldo);
    }

    public double getSaldo() {
        return saldo;
    }
}

