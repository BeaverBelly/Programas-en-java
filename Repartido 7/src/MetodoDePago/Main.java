package MetodoDePago;

public class Main {
    public static void main(String[] args) {
        MetodoPago pago1 = new PagoTarjeta("Juan Pérez", "1234-5678-9012-3456");
        MetodoPago pago2 = new PagoBilletera("Ana Gómez", "ana.gomez@email.com");

        pago1.procesarPago(1500.50);
        pago2.procesarPago(750.75);
    }
}
