package MetodoDePago;

public class PagoBilletera extends MetodoPago {
    private String email;

    public PagoBilletera(String titular, String email) {
        super(titular);
        this.email = email;
    }

    @Override
    public void procesarPago(double monto) {
        System.out.println("📱 Procesando pago con billetera electrónica...");
        System.out.println("Titular: " + titular);
        System.out.println("Email: " + email);
        System.out.println("Monto: $" + monto);
    }
}
