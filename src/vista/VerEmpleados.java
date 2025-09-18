package vista;

import controlador.EmpleadoController;
import controlador.UsuarioController;
import controlador.TiendaController;
import modelo.Empleado;
import modelo.Usuario;
import modelo.Tienda;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VerEmpleados extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtNumeroEmpleado, txtNombre, txtApellidos, txtTelefono, txtSalario, txtCorreo;
    private JComboBox<String> cbSupervisor, cbTienda, cbRol;
    private JButton btnActualizar, btnDesactivar;

    private Empleado empleadoSeleccionado;
    private Usuario usuarioSeleccionado;

    public VerEmpleados() {
        setLayout(new BorderLayout());

        // === Panel de edición ARRIBA ===
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar / Activar - Desactivar Empleado"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Número empleado
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Número:"), gbc);
        txtNumeroEmpleado = new JTextField(10);
        txtNumeroEmpleado.setEditable(false);
        gbc.gridx = 1;
        panelEdicion.add(txtNumeroEmpleado, gbc);

        // Nombre
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(12);
        gbc.gridx = 3;
        panelEdicion.add(txtNombre, gbc);

        // Apellidos
        gbc.gridx = 4; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField(12);
        gbc.gridx = 5;
        panelEdicion.add(txtApellidos, gbc);

        // Teléfono
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(10);
        gbc.gridx = 1;
        panelEdicion.add(txtTelefono, gbc);

        // Correo
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField(15);
        gbc.gridx = 3;
        panelEdicion.add(txtCorreo, gbc);

        // Salario
        gbc.gridx = 4; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Salario:"), gbc);
        txtSalario = new JTextField(10);
        gbc.gridx = 5;
        panelEdicion.add(txtSalario, gbc);

        // Rol
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Rol:"), gbc);
        cbRol = new JComboBox<>(new String[]{"CAJERO", "SUPERVISOR", "REPARTIDOR", "ALMACENISTA"});
        gbc.gridx = 1;
        panelEdicion.add(cbRol, gbc);

        // Supervisor
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Supervisor:"), gbc);
        cbSupervisor = new JComboBox<>(new String[]{"-- Ninguno --"});
        gbc.gridx = 3;
        panelEdicion.add(cbSupervisor, gbc);

        // Tienda
        gbc.gridx = 4; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Tienda:"), gbc);
        cbTienda = new JComboBox<>(new String[]{"-- Ninguna --"});
        gbc.gridx = 5;
        panelEdicion.add(cbTienda, gbc);

        // Botones
        fila++;
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 6;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Actualizar");
        btnDesactivar = new JButton("Desactivar");
        panelBtns.add(btnActualizar);
        panelBtns.add(btnDesactivar);
        panelEdicion.add(panelBtns, gbc);

        add(panelEdicion, BorderLayout.NORTH);

        // === Tabla de empleados ===
        String[] columnas = {"Número", "Nombre", "Apellidos", "Teléfono", "Correo", "Rol", "Tienda", "Salario", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);

        // Renderer para inactivos en rojo y tachados
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                boolean activo = (boolean) modeloTabla.getValueAt(row, 8);
                if (!activo) {
                    c.setForeground(Color.RED);
                    Font font = c.getFont();
                    @SuppressWarnings("unchecked")
                    Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
                    attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                    c.setFont(new Font(attributes));
                } else {
                    c.setForeground(Color.BLACK);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Eventos
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
        btnActualizar.addActionListener(e -> actualizarEmpleado());
        btnDesactivar.addActionListener(e -> toggleEstadoEmpleado());

        // Cargar combos y empleados
        cargarEmpleados();
        cargarSupervisores();
        cargarTiendas();
    }

    private void cargarEmpleados() {
        modeloTabla.setRowCount(0);
        EmpleadoController ec = new EmpleadoController();
        UsuarioController uc = new UsuarioController();
        TiendaController tc = new TiendaController();

        List<Empleado> lista = ec.listar();
        for (Empleado e : lista) {
            Usuario u = uc.obtenerPorId(e.getUsuarioId());
            Tienda t = (e.getTiendaId() != null) ? tc.obtenerPorId(e.getTiendaId()) : null;
            modeloTabla.addRow(new Object[]{
                    e.getNumeroEmpleado(),
                    e.getNombre(),
                    e.getApellidos(),
                    e.getTelefono(),
                    (u != null ? u.getEmail() : ""),
                    (u != null ? u.getTipoUsuario() : "SIN ROL"),
                    (t != null ? t.getNombreTienda() : "SIN TIENDA"),
                    e.getSalarioActual(),
                    e.isActivo()
            });
        }
    }

    private void cargarSupervisores() {
        cbSupervisor.removeAllItems();
        cbSupervisor.addItem("-- Ninguno --");

        EmpleadoController ec = new EmpleadoController();
        UsuarioController uc = new UsuarioController();

        List<Empleado> lista = ec.listar();
        for (Empleado e : lista) {
            Usuario u = uc.obtenerPorId(e.getUsuarioId());
            if (u != null && "SUPERVISOR".equalsIgnoreCase(u.getTipoUsuario()) && e.isActivo()) {
                cbSupervisor.addItem(e.getId() + " - " + e.getNombre() + " " + e.getApellidos());
            }
        }
    }

    private void cargarTiendas() {
        cbTienda.removeAllItems();
        cbTienda.addItem("-- Ninguna --");

        TiendaController tc = new TiendaController();
        List<Tienda> lista = tc.listar();
        for (Tienda t : lista) {
            cbTienda.addItem(t.getId() + " - " + t.getNombreTienda());
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String numeroEmpleado = (String) modeloTabla.getValueAt(fila, 0);
            EmpleadoController ec = new EmpleadoController();
            UsuarioController uc = new UsuarioController();

            empleadoSeleccionado = ec.obtenerPorNumero(numeroEmpleado);
            if (empleadoSeleccionado != null) {
                usuarioSeleccionado = uc.obtenerPorId(empleadoSeleccionado.getUsuarioId());

                txtNumeroEmpleado.setText(empleadoSeleccionado.getNumeroEmpleado());
                txtNombre.setText(empleadoSeleccionado.getNombre());
                txtApellidos.setText(empleadoSeleccionado.getApellidos());
                txtTelefono.setText(empleadoSeleccionado.getTelefono());
                txtSalario.setText(String.valueOf(empleadoSeleccionado.getSalarioActual()));
                txtCorreo.setText(usuarioSeleccionado != null ? usuarioSeleccionado.getEmail() : "");

                if (usuarioSeleccionado != null) {
                    cbRol.setSelectedItem(usuarioSeleccionado.getTipoUsuario());
                }

                boolean activo = empleadoSeleccionado.isActivo();
                btnDesactivar.setText(activo ? "Desactivar" : "Activar");

                // Supervisor
                if (empleadoSeleccionado.getSupervisorId() != null) {
                    EmpleadoController ec2 = new EmpleadoController();
                    Empleado supervisor = ec2.obtenerPorId(empleadoSeleccionado.getSupervisorId());
                    if (supervisor != null) {
                        cbSupervisor.setSelectedItem(supervisor.getId() + " - " + supervisor.getNombre() + " " + supervisor.getApellidos());
                    }
                } else {
                    cbSupervisor.setSelectedIndex(0);
                }

                // Tienda
                if (empleadoSeleccionado.getTiendaId() != null) {
                    TiendaController tc = new TiendaController();
                    Tienda tienda = tc.obtenerPorId(empleadoSeleccionado.getTiendaId());
                    if (tienda != null) {
                        cbTienda.setSelectedItem(tienda.getId() + " - " + tienda.getNombreTienda());
                    }
                } else {
                    cbTienda.setSelectedIndex(0);
                }

                setCamposEditables(activo);
            }
        }
    }

    private void setCamposEditables(boolean editable) {
        txtNombre.setEditable(editable);
        txtApellidos.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtSalario.setEditable(editable);
        txtCorreo.setEditable(editable);
        cbRol.setEnabled(editable);
        cbSupervisor.setEnabled(editable);
        cbTienda.setEnabled(editable);
        btnActualizar.setEnabled(editable);
    }

    private void actualizarEmpleado() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado primero.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String salarioStr = txtSalario.getText().trim();
        String correo = txtCorreo.getText().trim();
        String rol = (String) cbRol.getSelectedItem();

        if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || salarioStr.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 10 dígitos.");
            return;
        }

        if (!correo.matches("^[\\w.-]+@(gmail\\.com|hotmail\\.com|outlook\\.com)$")) {
            JOptionPane.showMessageDialog(this, "El correo debe ser válido y terminar en gmail, hotmail o outlook.");
            return;
        }

        try {
            double salario = Double.parseDouble(salarioStr);

            empleadoSeleccionado.setNombre(nombre);
            empleadoSeleccionado.setApellidos(apellidos);
            empleadoSeleccionado.setTelefono(telefono);
            empleadoSeleccionado.setSalarioActual(salario);

            // Supervisor
            String supervisorSel = (String) cbSupervisor.getSelectedItem();
            if (supervisorSel != null && !supervisorSel.equals("-- Ninguno --")) {
                long supervisorId = Long.parseLong(supervisorSel.split(" - ")[0]);
                empleadoSeleccionado.setSupervisorId(supervisorId);
            } else {
                empleadoSeleccionado.setSupervisorId(null);
            }

            // Tienda
            String tiendaSel = (String) cbTienda.getSelectedItem();
            if (tiendaSel != null && !tiendaSel.equals("-- Ninguna --")) {
                long tiendaId = Long.parseLong(tiendaSel.split(" - ")[0]);
                empleadoSeleccionado.setTiendaId(tiendaId);
            } else {
                empleadoSeleccionado.setTiendaId(null);
            }

            // Usuario
            if (usuarioSeleccionado != null) {
                usuarioSeleccionado.setEmail(correo);
                usuarioSeleccionado.setTipoUsuario(rol);
                UsuarioController uc = new UsuarioController();
                uc.actualizar(usuarioSeleccionado);
            }

            EmpleadoController ec = new EmpleadoController();
            ec.actualizar(empleadoSeleccionado);

            JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
            cargarEmpleados();
            reseleccionarEmpleado(empleadoSeleccionado.getNumeroEmpleado());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void toggleEstadoEmpleado() {
        if (empleadoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado primero.");
            return;
        }

        try {
            empleadoSeleccionado.setActivo(!empleadoSeleccionado.isActivo());
            EmpleadoController ec = new EmpleadoController();
            ec.actualizar(empleadoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    empleadoSeleccionado.isActivo() ? "Empleado activado." : "Empleado desactivado.");

            cargarEmpleados();
            reseleccionarEmpleado(empleadoSeleccionado.getNumeroEmpleado());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
        }
    }

    private void reseleccionarEmpleado(String numeroEmpleado) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(numeroEmpleado)) {
                tabla.setRowSelectionInterval(i, i);
                cargarSeleccion();
                break;
            }
        }
    }
}
