package _3_Conversor;

public class Conversor {
    public double convertir(double metros){

        return metros * 100.0;

    }

    public double convertir(double kilogramos, String tipo){
        if(tipo.equalsIgnoreCase("peso")){

            return kilogramos * 1000.0;
        }
        else{
            throw new IllegalArgumentException("Tipo no reconocido");
        }

    }

    public double convertir(double temperatura, boolean esTempeartura ){
        if(esTempeartura){
            return (temperatura * 9.0 / 5) + 32;
        }
        else{
            return (temperatura - 32) * 5.0 / 9;
        }
    }

}
