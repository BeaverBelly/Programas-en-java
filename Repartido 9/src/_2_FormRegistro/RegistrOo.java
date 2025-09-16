package _2_FormRegistro;

import Prueba.PruebaForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegistrOo extends JFrame {
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JPasswordField txtContrasenia;
    private JPasswordField txtConfirmarContrasenia;
    private JTextField txtTelefono;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JLabel lblTelefono;
    private JLabel lblConfirmarContrasenia;
    private JLabel lblContrasenia;
    private JLabel lblUsuario;
    private JLabel lblCorreo;
    private JLabel lblApellido;
    private JLabel lblNombre;
    private JTextField txtUsuario;

    public RegistrOo() {
        setContentPane(contentPane);
        setBounds(0, 0, 500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtNombre.setToolTipText("Ingrese su nombre sin números (mín. 3 letras)");
        txtApellido.setToolTipText("Ingrese su apellido sin números (min. 3 letras)");
        txtCorreo.setToolTipText("Formato: usuario@dominio.com");
        txtUsuario.setToolTipText("Mínimo 5 caracteres");
        txtContrasenia.setToolTipText("Mínimo 8, al menos 1 letra y 1 número");
        txtTelefono.setToolTipText("Opcional: 8 a 12 dígitos");

        // Nombre (mínimo 3 letras, solo texto)
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = txtNombre.getText().trim();
                if (valor.isEmpty()) {
                    txtNombre.setBackground(Color.YELLOW);
                } else if (valor.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,}")) {
                    txtNombre.setBackground(Color.GREEN);
                } else {
                    txtNombre.setBackground(Color.RED);
                }
            }
        });

        // Apellido
        txtApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = txtApellido.getText().trim();
                if (valor.isEmpty()) {
                    txtApellido.setBackground(Color.YELLOW);
                } else if (valor.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,}")) {
                    txtApellido.setBackground(Color.GREEN);
                } else {
                    txtApellido.setBackground(Color.RED);
                }
            }
        });

        // Correo
        txtCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = txtCorreo.getText().trim();
                if (valor.isEmpty()) {
                    txtCorreo.setBackground(Color.YELLOW);
                } else if (valor.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    txtCorreo.setBackground(Color.GREEN);
                } else {
                    txtCorreo.setBackground(Color.RED);
                }
            }
        });

        // Usuario (mínimo 5 caracteres)
        txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = txtUsuario.getText().trim();
                if (valor.isEmpty()) {
                    txtUsuario.setBackground(Color.YELLOW);
                } else if (valor.length() >= 5) {
                    txtUsuario.setBackground(Color.GREEN);
                } else {
                    txtUsuario.setBackground(Color.RED);
                }
            }
        });

        // Contraseña (mínimo 8, al menos una letra y un número)
        txtContrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = new String(txtContrasenia.getPassword());
                if (valor.isEmpty()) {
                    txtContrasenia.setBackground(Color.YELLOW);
                } else if (valor.matches("(?=.*[A-Za-z])(?=.*\\d).{8,}")) {
                    txtContrasenia.setBackground(Color.GREEN);
                } else {
                    txtContrasenia.setBackground(Color.RED);
                }
            }
        });

        // Confirmar contraseña
        txtConfirmarContrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pass1 = new String(txtContrasenia.getPassword());
                String pass2 = new String(txtConfirmarContrasenia.getPassword());
                if (pass2.isEmpty()) {
                    txtConfirmarContrasenia.setBackground(Color.YELLOW);
                } else if (pass2.equals(pass1)) {
                    txtConfirmarContrasenia.setBackground(Color.GREEN);
                } else {
                    txtConfirmarContrasenia.setBackground(Color.RED);
                }
            }
        });

        // Teléfono (opcional)
        txtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valor = txtTelefono.getText().trim();
                if (valor.isEmpty()) {
                    txtTelefono.setBackground(Color.YELLOW);
                } else if (valor.matches("\\d{8,12}")) {
                    txtTelefono.setBackground(Color.GREEN);
                } else {
                    txtTelefono.setBackground(Color.RED);
                }
            }
        });

        btnRegistrar.setEnabled(false); // al inicio deshabilitado

        btnLimpiar.addActionListener(e -> {
            txtNombre.setText("");
            txtApellido.setText("");
            txtCorreo.setText("");
            txtUsuario.setText("");
            txtContrasenia.setText("");
            txtConfirmarContrasenia.setText("");
            txtTelefono.setText("");

            // Resetear colores (mejor usar el color por defecto del LAF)
            Color def = UIManager.getColor("TextField.background");
            txtNombre.setBackground(def);
            txtApellido.setBackground(def);
            txtCorreo.setBackground(def);
            txtUsuario.setBackground(def);
            txtContrasenia.setBackground(def);
            txtConfirmarContrasenia.setBackground(def);
            txtTelefono.setBackground(def);

            btnRegistrar.setEnabled(false);
        });

        // === BOTÓN REGISTRAR ===
        btnRegistrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Registro exitoso para el usuario: " + txtUsuario.getText(),
                    "Registro",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Escucha global para habilitar/deshabilitar Registrar
        addGlobalValidation();
    }

    // Listener global para actualizar el estado del botón Registrar
    private void addGlobalValidation() {
        KeyAdapter validador = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnRegistrar.setEnabled(validarCampos());
            }
        };

        txtNombre.addKeyListener(validador);
        txtApellido.addKeyListener(validador);
        txtCorreo.addKeyListener(validador);
        txtUsuario.addKeyListener(validador);
        txtContrasenia.addKeyListener(validador);
        txtConfirmarContrasenia.addKeyListener(validador);
        // teléfono es opcional: no participa en la habilitación
    }

    // Comprueba si todos los obligatorios están en estado "verde"
    private boolean validarCampos() {
        boolean nombreOk   = txtNombre.getBackground() == Color.GREEN;
        boolean apellidoOk = txtApellido.getBackground() == Color.GREEN;
        boolean correoOk   = txtCorreo.getBackground() == Color.GREEN;
        boolean usuarioOk  = txtUsuario.getBackground() == Color.GREEN;
        boolean contraOk   = txtContrasenia.getBackground() == Color.GREEN;
        boolean confirmOk  = txtConfirmarContrasenia.getBackground() == Color.GREEN;

        return nombreOk && apellidoOk && correoOk && usuarioOk && contraOk && confirmOk;
    }

    public static void main(String[] args) {
        RegistrOo Registro = new RegistrOo();
    }
}
