package MetodoDePago;

public abstract class MetodoPago {
    protected String titular;

    public MetodoPago(String titular) {
        this.titular = titular;
    }

    public abstract void procesarPago(double monto);
}