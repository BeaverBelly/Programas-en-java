package Prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//fieldname(lbl), textfield (txt), buttons (btn)

public class PruebaForm extends JFrame{
    private JPanel contentPane;
    private JLabel lblTitulo;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JButton btnValidacion;
    private JLabel lblNombre;
    private JLabel lblEdad;

    public PruebaForm(){
        setContentPane(contentPane);
        setBounds(0,0,300,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnValidacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtNombre.getText().length() >= 4 && Integer.parseInt(txtEdad.getText()) >= 18){
                    JOptionPane.showMessageDialog(btnValidacion, ("Bienvenido: " + txtNombre.getText()));
                }
                else{
                    JOptionPane.showMessageDialog(btnValidacion, "El registro no es valido");
                }
            }
        });

        //Validar que el nombre tenga una longitud mayor a 4
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(txtNombre.getText().length() < 4){
                    txtNombre.setBackground(Color.RED);
                }
                else{
                    txtNombre.setBackground(Color.GREEN);
                }
            }



        });

        txtEdad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Integer.parseInt(txtEdad.getText()) >= 18){
                    txtEdad.setBackground(Color.GREEN);
                }
                else{
                    txtEdad.setBackground(Color.RED);
                }
            }
        });
    }

    public static void main(String[] args){
        PruebaForm form = new PruebaForm();
    }


}
