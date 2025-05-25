public class Cuenta {

    private String numero;
    private String titular;
    private double saldo;

    public Cuenta(String numero, String titular){
        this.numero = numero;
        this.titular = titular;
        this.saldo  = 0.0;
    }

    public void depositar(double monto){
        saldo += monto;
    }

    public void retirar(double monto){
        if (monto > saldo){
            throw new IllegalArgumentException("No puedes retirar más dinero del que tienes.");
        }
        else{
            saldo -= monto;
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }
}
