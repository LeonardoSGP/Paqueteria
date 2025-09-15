package vista;

import controlador.EmpleadoController;
import controlador.TiendaController;
import controlador.UsuarioController;
import modelo.Empleado;
import modelo.Tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TiendaForm extends JPanel {

    private JTextField txtCodigo, txtNombre, txtDireccion, txtTelefono, txtCorreo, txtCapacidad;
    private JComboBox<String> cbSupervisor;
    private JSpinner spHoraApertura, spHoraCierre;
    private JButton btnGuardar;

    private static int contador = 1;

    public TiendaForm() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 16);

        // Código Tienda
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Código Tienda:"), gbc);
        txtCodigo = new JTextField(generarCodigoTienda());
        txtCodigo.setEditable(false);
        txtCodigo.setFont(fuente);
        gbc.gridx = 1;
        add(txtCodigo, gbc);

        // Nombre Tienda
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField();
        txtNombre.setFont(fuente);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField();
        txtDireccion.setFont(fuente);
        gbc.gridx = 1;
        add(txtDireccion, gbc);

        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField();
        txtTelefono.setFont(fuente);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        // Correo
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField();
        txtCorreo.setFont(fuente);
        gbc.gridx = 1;
        add(txtCorreo, gbc);

        // Supervisor (gerente_id)
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Supervisor:"), gbc);
        cbSupervisor = new JComboBox<>(new String[]{"-- Ninguno --"});
        cbSupervisor.setFont(fuente);
        gbc.gridx = 1;
        add(cbSupervisor, gbc);
        cargarSupervisores();

        // Horario Apertura
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Horario Apertura:"), gbc);
        spHoraApertura = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorA = new JSpinner.DateEditor(spHoraApertura, "HH:mm");
        spHoraApertura.setEditor(editorA);
        gbc.gridx = 1;
        add(spHoraApertura, gbc);

        // Horario Cierre
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Horario Cierre:"), gbc);
        spHoraCierre = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorC = new JSpinner.DateEditor(spHoraCierre, "HH:mm");
        spHoraCierre.setEditor(editorC);
        gbc.gridx = 1;
        add(spHoraCierre, gbc);

        // Capacidad Almacén
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Capacidad Almacén:"), gbc);
        txtCapacidad = new JTextField();
        txtCapacidad.setFont(fuente);
        gbc.gridx = 1;
        add(txtCapacidad, gbc);

        // Botón Guardar
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        btnGuardar = new JButton("Registrar Tienda");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(this::guardarTienda);
    }

    private String generarCodigoTienda() {
        TiendaController tc = new TiendaController();
        String ultimo = tc.obtenerUltimoCodigo();

        if (ultimo == null) {
            return "TND001"; // primera tienda
        }

        try {
            // Extrae el número después del prefijo "TND"
            int num = Integer.parseInt(ultimo.substring(3));
            return String.format("TND%03d", num + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "TND001"; // fallback en caso de error
        }
    }

    private void cargarSupervisores() {
        EmpleadoController ec = new EmpleadoController();
        List<Empleado> empleados = ec.listar();
        cbSupervisor.removeAllItems();
        cbSupervisor.addItem("-- Ninguno --");
        for (Empleado e : empleados) {
            if (e.isActivo()) {
                //  Solo carga si el usuario es SUPERVISOR
                UsuarioController uc = new UsuarioController();
                String rol = uc.obtenerPorId(e.getUsuarioId()).getTipoUsuario();
                if ("SUPERVISOR".equalsIgnoreCase(rol)) {
                    cbSupervisor.addItem(e.getId() + " - " + e.getNombre() + " " + e.getApellidos());
                }
            }
        }
    }

    private void guardarTienda(ActionEvent evt) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()
                || correo.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Teléfono: exactamente 10 dígitos
        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                    "El teléfono debe tener exactamente 10 dígitos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Correo válido
        if (!correo.matches("^[\\w._%+-]+@(gmail\\.com|hotmail\\.com|outlook\\.com)$")) {
            JOptionPane.showMessageDialog(this,
                    "El correo debe ser válido (gmail, hotmail o outlook).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La capacidad debe ser numérica.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tienda t = new Tienda();
        t.setCodigoTienda(codigo);
        t.setNombreTienda(nombre);
        t.setDireccionCompleta(direccion);
        t.setTelefono(telefono);
        t.setEmail(correo);

        if (cbSupervisor.getSelectedIndex() > 0) {
            String[] partes = cbSupervisor.getSelectedItem().toString().split(" - ");
            t.setGerenteId(Long.parseLong(partes[0]));
        }

        t.setHorarioApertura(new Time(((Date) spHoraApertura.getValue()).getTime()));
        t.setHorarioCierre(new Time(((Date) spHoraCierre.getValue()).getTime()));
        t.setCapacidadAlmacen(capacidad);
        t.setActiva(true);

        try {
            TiendaController tc = new TiendaController();
            tc.insertar(t);
            JOptionPane.showMessageDialog(this,
                    "Tienda registrada con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar tienda: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText(generarCodigoTienda());
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCapacidad.setText("");
        cbSupervisor.setSelectedIndex(0);
    }
}
