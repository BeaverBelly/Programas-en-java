package MetodoDePago;

public class PagoTarjeta extends MetodoPago {
    private String numeroTarjeta;

    public PagoTarjeta(String titular, String numeroTarjeta) {
        super(titular);
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public void procesarPago(double monto) {
        System.out.println("Procesando pago con tarjeta...");
        System.out.println("Titular: " + titular);
        System.out.println("Número de tarjeta: " + numeroTarjeta);
        System.out.println("Monto: $" + monto);
    }
}
