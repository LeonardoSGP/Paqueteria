package vista;

import controlador.UsuarioController;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

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
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
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
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('\u2022'); // puntitos por defecto
        panel.add(txtPassword, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        btnVerPassword = new JButton("👁");
        btnVerPassword.setMargin(new Insets(2, 5, 2, 5));
        panel.add(btnVerPassword, gbc);

        // Acción para mostrar/ocultar contraseña
        btnVerPassword.addActionListener(e -> {
            if (txtPassword.getEchoChar() == '\u2022') {
                txtPassword.setEchoChar((char) 0); // mostrar
            } else {
                txtPassword.setEchoChar('\u2022'); // ocultar
            }
        });

        // Botón login
        gbc.gridx = 1;
        gbc.gridy = 2;
        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin, gbc);

        // Mensaje
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setForeground(Color.RED);
        panel.add(lblMensaje, gbc);

        add(panel);

        // Acción login
        btnLogin.addActionListener(e -> autenticar());
    }

    private void autenticar() {
        String rol = (String) cbRol.getSelectedItem();
        String password = new String(txtPassword.getPassword());

        UsuarioController uc = new UsuarioController();
        Usuario u = uc.loginPorRol(rol, password);

        if (u != null) {
            // ✅ mostrar el último acceso si lo tenía
            if (u.getUltimoAcceso() != null) {
                JOptionPane.showMessageDialog(this,
                        "Último acceso: " + u.getUltimoAcceso(),
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            // ✅ actualizar último acceso
            uc.actualizarUltimoAcceso(u.getId());

            lblMensaje.setForeground(Color.GREEN);
            lblMensaje.setText("Bienvenido " + u.getTipoUsuario());

            switch (rol.toUpperCase()) {
                case "GERENTE":
                    abrirVistaGerente();
                    break;
                case "CAJERO":
                    abrirVistaCajero();
                    break;
                case "REPARTIDOR":
                    abrirVistaRepartidor();
                    break;
                case "SUPERVISOR":
                    abrirVistaSupervisor();
                    break;
                case "ALMACENISTA":
                    abrirVistaAlmacenista();
                    break;
            }

        } else {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Contraseña incorrecta");
        }
    }

    private void abrirVistaGerente() {
        this.dispose();
        JFrame frameGerente = new JFrame("Panel GERENTE");
        frameGerente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameGerente.setLayout(new BorderLayout());
        IGerente panel = new IGerente();
        frameGerente.add(panel, BorderLayout.CENTER);
        frameGerente.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameGerente.pack();
        frameGerente.setLocationRelativeTo(null);
        frameGerente.setVisible(true);
    }

    private void abrirVistaCajero() {
        this.dispose(); // cerrar login

        JFrame frameCajero = new JFrame("Panel CAJERO");
        frameCajero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCajero.setLayout(new BorderLayout());
        ICajero panel = new ICajero(); 
        frameCajero.add(panel, BorderLayout.CENTER);
        frameCajero.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameCajero.pack();
        frameCajero.setLocationRelativeTo(null);
        frameCajero.setVisible(true);
    }

    private void abrirVistaSupervisor() {
        this.dispose(); 

        JFrame frameSupervisor = new JFrame("Panel SUPERVISOR");
        frameSupervisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameSupervisor.setLayout(new BorderLayout());
        ISupervisor panel = new ISupervisor(); 
        frameSupervisor.add(panel, BorderLayout.CENTER);
        frameSupervisor.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameSupervisor.pack();
        frameSupervisor.setLocationRelativeTo(null);
        frameSupervisor.setVisible(true);
    }

    private void abrirVistaAlmacenista() {
        this.dispose(); // cerrar login

        JFrame frameAlmacenista = new JFrame("Panel Almacenista");
        frameAlmacenista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAlmacenista.setLayout(new BorderLayout());

        IAlmacenista panel = new IAlmacenista();
        frameAlmacenista.add(panel, BorderLayout.CENTER);
        frameAlmacenista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameAlmacenista.pack();
        frameAlmacenista.setLocationRelativeTo(null);
        frameAlmacenista.setVisible(true);
    }

    private void abrirVistaRepartidor() {
        this.dispose(); 

        JFrame frameRepartidor = new JFrame("Panel Repartidor");
        frameRepartidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRepartidor.setLayout(new BorderLayout());

        IRepartidor panel = new IRepartidor(); 
        frameRepartidor.add(panel, BorderLayout.CENTER);
        frameRepartidor.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameRepartidor.pack();
        frameRepartidor.setLocationRelativeTo(null);
        frameRepartidor.setVisible(true);
    }

    public static void main(String[] args) {
        UsuarioController uc = new UsuarioController();
        uc.crearGerentePorDefecto(); // asegura gerente por defecto

        SwingUtilities.invokeLater(() -> {
            new ILogin().setVisible(true);
        });
    }
}
