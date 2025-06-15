package _4_Animales;

public class Main {
    public static void main(String[] args){
        Animal[] Animales = {new Perro(), new Gato()};

        for (Animal animalitos : Animales) {
            animalitos.hacersonido();
        }
    }
}
