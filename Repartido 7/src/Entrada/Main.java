package Entrada;

public class Main {
    public static void main(String[] args) {
            Entrada[] entradas = new Entrada[] {
                    new EntradaVIP("VIP-001", 3),
                    new EntradaVIP("VIP-002", 23),
                    new EntradaGeneral("GEN-101", 9),
                    new EntradaGeneral("GEN-102", 10),
                    new EntradaGeneral("GEN-103", 22),
                    new EntradaGeneral("GEN-104", 23)
            };

            for (Entrada e : entradas) {
                e.mostrarDatos();
                boolean ok = e.validarEntrada();
                System.out.println(ok ? "Acceso PERMITIDO" : "Acceso DENEGADO");
            }
        }
    }
