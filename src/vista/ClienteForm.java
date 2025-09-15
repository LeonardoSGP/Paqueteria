/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Diego Quiroga
 */
import controlador.ClienteController;
import controlador.UsuarioController;
import modelo.Cliente;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class ClienteForm extends JPanel {

    private JTextField txtCodigoCliente, txtNombre, txtApellidos, txtTelefono, txtEmail;
    private JComboBox<String> cbTipoCliente;
    private JPasswordField txtPassword;
    private JButton btnGuardar, btnVerPassword;

    public ClienteForm() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 16);

        // Código de cliente (desde BD)
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Código Cliente:"), gbc);

        ClienteController cc = new ClienteController();
        txtCodigoCliente = new JTextField(cc.generarSiguienteCodigoCliente());
        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setFont(fuente);
        gbc.gridx = 1;
        add(txtCodigoCliente, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField();
        txtNombre.setFont(fuente);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        // Apellidos
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField();
        txtApellidos.setFont(fuente);
        gbc.gridx = 1;
        add(txtApellidos, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField();
        txtTelefono.setFont(fuente);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField();
        txtEmail.setFont(fuente);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        // Contraseña con ojito
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField();
        txtPassword.setFont(fuente);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        btnVerPassword = new JButton("👁");
        btnVerPassword.setMargin(new Insets(2, 5, 2, 5));
        btnVerPassword.addActionListener(e -> {
            if (txtPassword.getEchoChar() == '\u2022') {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('\u2022');
            }
        });
        gbc.gridx = 2;
        add(btnVerPassword, gbc);

        // Tipo Cliente
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Tipo Cliente:"), gbc);
        cbTipoCliente = new JComboBox<>(new String[]{
                "REGULAR", "PREMIUM", "CORPORATIVO", "MAYORISTA"
        });
        cbTipoCliente.setFont(fuente);
        gbc.gridx = 1;
        add(cbTipoCliente, gbc);

        // Botón Guardar
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        btnGuardar = new JButton("Guardar Cliente");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(this::guardarCliente);
    }

    private void guardarCliente(ActionEvent evt) {
        String codigoCliente = txtCodigoCliente.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String tipoCliente = (String) cbTipoCliente.getSelectedItem();

        // Validaciones
        if (nombre.isEmpty() || apellidos.isEmpty()
                || telefono.isEmpty() || email.isEmpty() 
                || password.isEmpty() || tipoCliente == null) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Teléfono exactamente 10 dígitos
        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono debe tener exactamente 10 dígitos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Email válido
        if (!email.matches("^[\\w._%+-]+@(gmail\\.com|hotmail\\.com|outlook\\.com)$")) {
            JOptionPane.showMessageDialog(this,
                    "El correo debe ser válido (gmail, hotmail o outlook).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Contraseña segura (mínima validación)
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 6 caracteres.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteController clienteCtrl = new ClienteController();
        if (clienteCtrl.existeTelefono(telefono)) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono ya está registrado, ingresa uno diferente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (clienteCtrl.existeEmail(email)) {
            JOptionPane.showMessageDialog(this,
                    "El email ya está registrado, ingresa uno diferente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Crear usuario
            UsuarioController uc = new UsuarioController();
            Usuario u = new Usuario();
            u.setUsername(codigoCliente);
            u.setPasswordHash(password);
            u.setEmail(email);
            u.setTipoUsuario("CLIENTE");
            u.setFechaCreacion(new Date());
            u.setActivo(true);
            uc.insertar(u);

            // Crear cliente
            Cliente c = new Cliente();
            c.setUsuarioId(u.getId());
            c.setCodigoCliente(codigoCliente);
            c.setNombre(nombre);
            c.setApellidos(apellidos);
            c.setTelefono(telefono);
            
            // Mapear tipo cliente a ID
            switch (tipoCliente) {
                case "REGULAR": c.setTipoClienteId(1); break;
                case "PREMIUM": c.setTipoClienteId(2); break;
                case "CORPORATIVO": c.setTipoClienteId(3); break;
                case "MAYORISTA": c.setTipoClienteId(4); break;
                default: c.setTipoClienteId(1); break;
            }
            
            c.setCreditoDisponible(0.0);
            c.setDescuentoAsignado(0.0);
            c.setFechaRegistro(new Date());
            c.setActivo(true);

            clienteCtrl.insertar(c);

            JOptionPane.showMessageDialog(this,
                    "Cliente registrado con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al guardar cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        ClienteController cc = new ClienteController();
        txtCodigoCliente.setText(cc.generarSiguienteCodigoCliente());
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        cbTipoCliente.setSelectedIndex(0);
    }
}
