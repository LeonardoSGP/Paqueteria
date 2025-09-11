package vista;

import controlador.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ILogin extends JFrame {

    private JComboBox<String> cbRol;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMensaje;
    private JButton btnVerPassword;

    public ILogin() {
        setTitle("Login - Sistema Envios");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de usuario (combo arriba)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Usuario:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        cbRol = new JComboBox<>(new String[]{
                "GERENTE",
                "CAJERO",
                "REPARTIDOR",
                "SUPERVISOR",
                "ALMACENISTA"
        });
        panel.add(cbRol, gbc);
        gbc.gridwidth = 1;

        // Contraseña con botón de ver
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        btnVerPassword = new JButton("👁"); // 👁 icono simple
        btnVerPassword.setMargin(new Insets(2, 5, 2, 5));
        panel.add(btnVerPassword, gbc);

        // Acción para mostrar/ocultar contraseña
        btnVerPassword.addActionListener(e -> {
            if (txtPassword.getEchoChar() == '\u2022') { // por defecto es •
                txtPassword.setEchoChar((char) 0); // mostrar texto
            } else {
                txtPassword.setEchoChar('\u2022'); // ocultar
            }
        });

        // Botón login
        gbc.gridx = 1; gbc.gridy = 2;
        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin, gbc);

        // Mensaje
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        panel.add(lblMensaje, gbc);

        add(panel);

        // Acción login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
    }
private void autenticar() {
    String rol = (String) cbRol.getSelectedItem();
    String password = new String(txtPassword.getPassword());

    UsuarioController uc = new UsuarioController();
    Usuario u = uc.loginPorRol(rol, password);

    if (u != null) {
        lblMensaje.setForeground(Color.GREEN);
        lblMensaje.setText("Bienvenido " + u.getTipoUsuario());

        switch (rol.toUpperCase()) {
            case "GERENTE":
                abrirVistaGerente();
                break;
            case "CAJERO":
                JOptionPane.showMessageDialog(this, "Menú de CAJERO");
                break;
            case "REPARTIDOR":
                JOptionPane.showMessageDialog(this, "Menú de REPARTIDOR");
                break;
            case "SUPERVISOR":
                JOptionPane.showMessageDialog(this, "Menú de SUPERVISOR");
                break;
            case "ALMACENISTA":
                JOptionPane.showMessageDialog(this, "Menú de ALMACENISTA");
                break;
        }

    } else {
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setText("Contraseña incorrecta");
    }
}

private void abrirVistaGerente() {
    // Cierra el login
    this.dispose();

    // Crea un nuevo JFrame para contener tu panel IGerente
    JFrame frameGerente = new JFrame("Panel GERENTE");
    frameGerente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frameGerente.setSize(600, 400);
    frameGerente.setLocationRelativeTo(null);

    // Agregamos tu JPanel IGerente dentro del frame
    frameGerente.setContentPane(new IGerente());

    // Mostrar
    frameGerente.setVisible(true);
}


    public static void main(String[] args) {
        UsuarioController uc = new UsuarioController();
        uc.crearGerentePorDefecto(); // asegura gerente por defecto

        SwingUtilities.invokeLater(() -> {
            new ILogin().setVisible(true);
        });
    }
}
