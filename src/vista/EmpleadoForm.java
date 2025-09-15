package vista;

import controlador.EmpleadoController;
import controlador.UsuarioController;
import modelo.Empleado;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.regex.Pattern;

public class EmpleadoForm extends JPanel {

    private JTextField txtNumeroEmpleado, txtNombre, txtApellidos, txtTelefono, txtSalario, txtEmail;
    private JComboBox<String> cbRol;
    private JPasswordField txtPassword;
    private JButton btnGuardar, btnVerPassword;

    public EmpleadoForm() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 16);

        // Número de empleado (desde BD)
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Número Empleado:"), gbc);

        EmpleadoController ec = new EmpleadoController();
        txtNumeroEmpleado = new JTextField(ec.generarSiguienteNumeroEmpleado());
        txtNumeroEmpleado.setEditable(false);
        txtNumeroEmpleado.setFont(fuente);
        gbc.gridx = 1;
        add(txtNumeroEmpleado, gbc);

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

        // Salario
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Salario:"), gbc);
        txtSalario = new JTextField();
        txtSalario.setFont(fuente);
        gbc.gridx = 1;
        add(txtSalario, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField();
        txtEmail.setFont(fuente);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        // Contraseña con ojito
        gbc.gridx = 0; gbc.gridy = 6;
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

        // Rol
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Rol:"), gbc);
        cbRol = new JComboBox<>(new String[]{
                "CAJERO", "SUPERVISOR", "REPARTIDOR", "ALMACENISTA"
        });
        cbRol.setFont(fuente);
        gbc.gridx = 1;
        add(cbRol, gbc);

        // Botón Guardar
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        btnGuardar = new JButton("Guardar Empleado");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(this::guardarEmpleado);
    }

    private void guardarEmpleado(ActionEvent evt) {
        String numeroEmpleado = txtNumeroEmpleado.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String salarioStr = txtSalario.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String rol = (String) cbRol.getSelectedItem();

        // Validaciones
        if (nombre.isEmpty() || apellidos.isEmpty()
                || telefono.isEmpty() || salarioStr.isEmpty()
                || email.isEmpty() || password.isEmpty() || rol == null) {
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

        // Contraseña segura
        if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
                .matcher(password).matches()) {
            JOptionPane.showMessageDialog(this,
                    "️La contraseña debe tener mínimo 8 caracteres, " +
                            "una mayúscula, una minúscula, un número y un símbolo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El salario debe ser numérico (ejemplo: 8500.50).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EmpleadoController empCtrl = new EmpleadoController();
        if (empCtrl.existeTelefono(telefono)) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono ya está registrado, ingresa uno diferente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Crear usuario
            UsuarioController uc = new UsuarioController();
            Usuario u = new Usuario();
            u.setUsername(numeroEmpleado);
            u.setPasswordHash(password);
            u.setEmail(email);
            u.setTipoUsuario(rol);
            u.setFechaCreacion(new Date());
            u.setActivo(true);
            uc.insertar(u);

            // Crear empleado
            Empleado e = new Empleado();
            e.setUsuarioId(u.getId());
            e.setNumeroEmpleado(numeroEmpleado);
            e.setNombre(nombre);
            e.setApellidos(apellidos);
            e.setTelefono(telefono);
            e.setSalarioActual(salario);
            e.setFechaIngreso(new Date());
            e.setActivo(true);

            empCtrl.insertar(e);

            JOptionPane.showMessageDialog(this,
                    "Empleado registrado con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al guardar empleado: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        EmpleadoController ec = new EmpleadoController();
        txtNumeroEmpleado.setText(ec.generarSiguienteNumeroEmpleado());
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtSalario.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        cbRol.setSelectedIndex(0);
    }
}
