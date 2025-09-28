package _3_torneo_padel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Torneo_Padel extends JFrame {



    private JPanel contentPane;
    private JTextField txtCorreo;
    private JPasswordField txtPIN;
    private JPasswordField txtConfirmarPIN;
    private JCheckBox chkLunes;
    private JCheckBox chkManana;
    private JButton btnInscribirse;
    private JButton btnLimpiar;
    private JCheckBox chkJueves;
    private JCheckBox chkMiercoles;
    private JCheckBox chkMartes;
    private JCheckBox chkViernes;
    private JCheckBox chkSabado;
    private JCheckBox chkDomingo;
    private JCheckBox chkTarde;
    private JCheckBox chkNoche;
    private JCheckBox chkTerminos;
    private JRadioButton rbtMasculino;
    private JRadioButton rbtFemenino;
    private JRadioButton rbtMixto;
    private JLabel lblCategoria;
    private JLabel lblDisponibilidad;
    private JLabel lblFranjas;
    private JLabel lblConfirmarPIN;
    private JLabel lblPIN;
    private JLabel lblCorreo;
    private JLabel lblTerminos;

    public Torneo_Padel(){
        setContentPane(contentPane);
        setBounds(0, 0, 500, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnInscribirse.setEnabled(false);

        txtCorreo.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                emailValido();
                actualizarMensajesUI();
                actualizarEstadoInscribirse();
            }
        });

        txtPIN.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                pinValido();
                confirmacionPinValida();
                actualizarMensajesUI();
                actualizarEstadoInscribirse();
            }
        });

        txtConfirmarPIN.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                confirmacionPinValida();
                actualizarMensajesUI();
                actualizarEstadoInscribirse();
            }
        });

        ItemListener recalc = e -> {
            actualizarMensajesUI();
            actualizarEstadoInscribirse();
        };

        rbtMasculino.addItemListener(recalc);
        rbtFemenino.addItemListener(recalc);
        rbtMixto.addItemListener(recalc);

        chkLunes.addItemListener(recalc);
        chkMartes.addItemListener(recalc);
        chkMiercoles.addItemListener(recalc);
        chkJueves.addItemListener(recalc);
        chkViernes.addItemListener(recalc);
        chkSabado.addItemListener(recalc);
        chkDomingo.addItemListener(recalc);

        chkManana.addItemListener(recalc);
        chkTarde.addItemListener(recalc);
        chkNoche.addItemListener(recalc);

        chkTerminos.addItemListener(recalc);

        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnInscribirse.addActionListener(e -> mostrarResumen());
    }

    private boolean emailValido() {
        String email = txtCorreo.getText().trim();
        boolean ok = email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

        if (ok) {
            txtCorreo.setBackground(Color.GREEN);
            lblCorreo.setText("Correo Valido");
        } else {
            txtCorreo.setBackground(Color.RED);
            lblCorreo.setText("Correo inválido");
        }
        return ok;
    }

    private boolean pinValido() {
        String pin = new String(txtPIN.getPassword());
        boolean ok = pin.matches("\\d{4,6}");
        if (ok) {
            txtPIN.setBackground(Color.GREEN);
            lblPIN.setText("PIN Correcto");
        } else {
            txtPIN.setBackground(Color.YELLOW);
            lblPIN.setText("El PIN debe tener 4–6 dígitos");
        }
        return ok;
    }

    private boolean confirmacionPinValida() {
        String pin  = new String(txtPIN.getPassword());
        String pin2 = new String(txtConfirmarPIN.getPassword());

        if (!pin.matches("\\d{4,6}")) {
            txtConfirmarPIN.setBackground(Color.YELLOW);
            lblConfirmarPIN.setText("El PIN debe tener 4–6 dígitos");
            return false;
        }
        if (!pin.equals(pin2)) {
            txtConfirmarPIN.setBackground(Color.ORANGE);
            lblConfirmarPIN.setText("El PIN y su confirmación no coinciden");
            return false;
        }
        txtConfirmarPIN.setBackground(Color.GREEN);
        lblConfirmarPIN.setText("PIN confirmado");
        return true;
    }

    private boolean categoriaSeleccionada() {
        return rbtMasculino.isSelected() || rbtFemenino.isSelected() || rbtMixto.isSelected();
    }

    private boolean hayAlMenosUnDia() {
        return chkLunes.isSelected() || chkMartes.isSelected() || chkMiercoles.isSelected()
                || chkJueves.isSelected() || chkViernes.isSelected()
                || chkSabado.isSelected() || chkDomingo.isSelected();
    }

    private boolean hayAlMenosUnaFranja() {
        return chkManana.isSelected() || chkTarde.isSelected() || chkNoche.isSelected();
    }

    private void actualizarMensajesUI() {
        if (categoriaSeleccionada()) {
            lblCategoria.setText("Categoría");
        } else {
            lblCategoria.setText("Seleccioná una categoría");
        }

        if (hayAlMenosUnDia()) {
            lblDisponibilidad.setText("Selección de días");
        } else {
            lblDisponibilidad.setText("Selecciona al menos un días");
        }

        if (hayAlMenosUnaFranja()) {
            lblFranjas.setText("Selecciona franjas");
        } else {
            lblFranjas.setText("Selecciona franjas");
        }

        if (chkTerminos.isSelected()) {
            lblTerminos.setText("Términos");
        } else {
            lblTerminos.setText("Debés aceptar las bases del torneo");
        }
    }


    private void actualizarEstadoInscribirse() {
        boolean ok = emailValido() && pinValido() && confirmacionPinValida()  && categoriaSeleccionada() && hayAlMenosUnDia() && hayAlMenosUnaFranja() && chkTerminos.isSelected();

        btnInscribirse.setEnabled(ok);
    }

    private void limpiarFormulario() {
        txtCorreo.setText("");
        txtPIN.setText("");
        txtConfirmarPIN.setText("");

        rbtMasculino.setSelected(false);
        rbtFemenino.setSelected(false);
        rbtMixto.setSelected(false);

        chkLunes.setSelected(false);
        chkMartes.setSelected(false);
        chkMiercoles.setSelected(false);
        chkJueves.setSelected(false);
        chkViernes.setSelected(false);
        chkSabado.setSelected(false);
        chkDomingo.setSelected(false);

        chkManana.setSelected(false);
        chkTarde.setSelected(false);
        chkNoche.setSelected(false);

        chkTerminos.setSelected(false);

        Color def = UIManager.getColor("TextField.background");
        txtCorreo.setBackground(def);
        txtPIN.setBackground(def);
        txtConfirmarPIN.setBackground(def);

        lblCorreo.setText("Correo");
        lblPIN.setText("PIN");
        lblConfirmarPIN.setText("Confirmar PIN");
        lblCategoria.setText("Categoria");
        lblDisponibilidad.setText("Disponibilidad");
        lblFranjas.setText("Disponibilidad - Franjas");
        lblTerminos.setText("Terminos");

        btnInscribirse.setEnabled(false);
    }

    private void mostrarResumen() {
        String categoria = "-";
        if (rbtMasculino.isSelected()) {
            categoria = "Masculina";
        } else if (rbtFemenino.isSelected()) {
            categoria = "Femenina";
        } else if (rbtMixto.isSelected()) {
            categoria = "Mixta";
        }

        StringBuilder dias = new StringBuilder();
        if (chkLunes.isSelected()) dias.append("Lun ");
        if (chkMartes.isSelected()) dias.append("Mar ");
        if (chkMiercoles.isSelected()) dias.append("Mié ");
        if (chkJueves.isSelected()) dias.append("Jue ");
        if (chkViernes.isSelected()) dias.append("Vie ");
        if (chkSabado.isSelected()) dias.append("Sáb ");
        if (chkDomingo.isSelected()) dias.append("Dom ");

        StringBuilder franjas = new StringBuilder();
        if (chkManana.isSelected()) franjas.append("Mañana ");
        if (chkTarde.isSelected())  franjas.append("Tarde ");
        if (chkNoche.isSelected())  franjas.append("Noche ");

        String diasStr;
        if (dias.length() == 0) {
            diasStr = "-";
        } else {
            diasStr = dias.toString();
        }

        String franjasStr;
        if (franjas.length() == 0) {
            franjasStr = "-";
        } else {
            franjasStr = franjas.toString();
        }

        JOptionPane.showMessageDialog(
                this,
                "Correo: " + txtCorreo.getText().trim()
                        + "\nCategoría: " + categoria
                        + "\nDías: " + diasStr
                        + "\nFranjas: " + franjasStr,
                "Inscripción",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        Torneo_Padel PadelTorneo = new Torneo_Padel();
    }
}
