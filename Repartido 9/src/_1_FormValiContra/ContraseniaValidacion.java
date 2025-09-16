package _1_FormValiContra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ContraseniaValidacion extends JFrame {

    private JTextField txtContrasenia;
    private JTextField txtconfcontrasenia;
    private JLabel lblcontrasenia;
    private JLabel lblconfcontrasenia;
    private JPanel contentPane;
    private JLabel lbltextocontra;
    private JLabel lbltextoconfcontra;


    public ContraseniaValidacion() {
        setContentPane(contentPane);
        setBounds(0, 0, 700,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtContrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(txtContrasenia.getText().length() < 8){
                    lbltextocontra.setText("La contraseña debe tener al menos 8 caracteres");
                    txtContrasenia.setBackground(Color.YELLOW);
                }
                else if (!txtContrasenia.getText().matches(".*\\d.*")) {
                    lbltextocontra.setText("La contraseña debe incluir al menos un número");
                    txtContrasenia.setBackground(Color.RED);
                }
                else{
                    lbltextocontra.setText("Ta' correcto");
                    txtContrasenia.setBackground(Color.GREEN);
                }
            }
        });

        txtconfcontrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (!txtconfcontrasenia.getText().equals(txtContrasenia.getText())){
                    txtconfcontrasenia.setBackground(Color.ORANGE);
                }
                else{
                    txtconfcontrasenia.setBackground(Color.GREEN);
                }
            }
        });
    }

    public static void main(String[] args) {
        ContraseniaValidacion contra = new ContraseniaValidacion();
    }
}
