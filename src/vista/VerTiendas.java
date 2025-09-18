package vista;

import controlador.EmpleadoController;
import controlador.TiendaController;
import controlador.UsuarioController;
import modelo.Empleado;
import modelo.Tienda;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.sql.Time;
import java.util.List;
import java.util.Map;

public class VerTiendas extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo, txtNombre, txtDireccion, txtTelefono, txtCorreo, txtCapacidad;
    private JComboBox<String> cbSupervisor;
    private JSpinner spApertura, spCierre;
    private JButton btnActualizar, btnDesactivar;

    private Tienda tiendaSeleccionada;

    public VerTiendas() {
        setLayout(new BorderLayout());

        // === Panel de edición ARRIBA ===
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar / Activar - Desactivar Tienda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Código
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Código:"), gbc);
        txtCodigo = new JTextField(8);
        txtCodigo.setEditable(false);
        gbc.gridx = 1;
        panelEdicion.add(txtCodigo, gbc);

        // Nombre
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(12);
        gbc.gridx = 3;
        panelEdicion.add(txtNombre, gbc);

        // Dirección
        gbc.gridx = 4;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField(15);
        gbc.gridx = 5;
        panelEdicion.add(txtDireccion, gbc);

        // Teléfono
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(10);
        gbc.gridx = 1;
        panelEdicion.add(txtTelefono, gbc);

        // Correo
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField(15);
        gbc.gridx = 3;
        panelEdicion.add(txtCorreo, gbc);

        // Capacidad
        gbc.gridx = 4;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Capacidad:"), gbc);
        txtCapacidad = new JTextField(6);
        gbc.gridx = 5;
        panelEdicion.add(txtCapacidad, gbc);

        // Supervisor
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Supervisor:"), gbc);
        cbSupervisor = new JComboBox<>(new String[]{"-- Ninguno --"});
        gbc.gridx = 1;
        panelEdicion.add(cbSupervisor, gbc);

        // Horarios
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Apertura:"), gbc);
        spApertura = new JSpinner(new SpinnerDateModel());
        spApertura.setEditor(new JSpinner.DateEditor(spApertura, "HH:mm"));
        gbc.gridx = 3;
        panelEdicion.add(spApertura, gbc);

        gbc.gridx = 4;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Cierre:"), gbc);
        spCierre = new JSpinner(new SpinnerDateModel());
        spCierre.setEditor(new JSpinner.DateEditor(spCierre, "HH:mm"));
        gbc.gridx = 5;
        panelEdicion.add(spCierre, gbc);

        // Botones
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 6;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Actualizar");
        btnDesactivar = new JButton("Desactivar");
        panelBtns.add(btnActualizar);
        panelBtns.add(btnDesactivar);
        panelEdicion.add(panelBtns, gbc);

        add(panelEdicion, BorderLayout.NORTH);

        // === Tabla de tiendas ===
        String[] columnas = {"Código", "Nombre", "Dirección", "Teléfono", "Correo", "Capacidad", "Supervisor", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);

        // Renderer para marcar inactivos en rojo y tachados
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                boolean activo = (boolean) modeloTabla.getValueAt(row, 7);
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
        btnActualizar.addActionListener(e -> actualizarTienda());
        btnDesactivar.addActionListener(e -> toggleEstadoTienda());

        // Cargar tiendas y supervisores
        cargarSupervisores();
        cargarTiendas();
    }

    private void cargarTiendas() {
        modeloTabla.setRowCount(0);
        TiendaController tc = new TiendaController();
        EmpleadoController ec = new EmpleadoController();
        UsuarioController uc = new UsuarioController();

        List<Tienda> lista = tc.listar();
        for (Tienda t : lista) {
            String supervisorNombre = "";
            if (t.getGerenteId() != 0) {
                Empleado e = ec.obtenerPorId(t.getGerenteId());
                if (e != null) {
                    Usuario u = uc.obtenerPorId(e.getUsuarioId());
                    if (u != null && "SUPERVISOR".equalsIgnoreCase(u.getTipoUsuario())) {
                        supervisorNombre = e.getNombre() + " " + e.getApellidos();
                    }
                }
            }

            modeloTabla.addRow(new Object[]{
                t.getCodigoTienda(),
                t.getNombreTienda(),
                t.getDireccionCompleta(),
                t.getTelefono(),
                t.getEmail(),
                t.getCapacidadAlmacen(),
                supervisorNombre, // ✅ ahora solo aparecerán supervisores
                t.isActiva()
            });
        }
    }

    private void cargarSupervisores() {
        cbSupervisor.removeAllItems();
        cbSupervisor.addItem("-- Ninguno --");

        EmpleadoController ec = new EmpleadoController();
        UsuarioController uc = new UsuarioController();

        List<Empleado> empleados = ec.listar();
        for (Empleado e : empleados) {
            Usuario u = uc.obtenerPorId(e.getUsuarioId());
            if (e.isActivo() && u != null && "SUPERVISOR".equalsIgnoreCase(u.getTipoUsuario())) {
                cbSupervisor.addItem(e.getId() + " - " + e.getNombre() + " " + e.getApellidos());
            }
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            TiendaController tc = new TiendaController();
            tiendaSeleccionada = tc.listar().stream()
                    .filter(t -> t.getCodigoTienda().equals(codigo))
                    .findFirst().orElse(null);

            if (tiendaSeleccionada != null) {
                txtCodigo.setText(tiendaSeleccionada.getCodigoTienda());
                txtNombre.setText(tiendaSeleccionada.getNombreTienda());
                txtDireccion.setText(tiendaSeleccionada.getDireccionCompleta());
                txtTelefono.setText(tiendaSeleccionada.getTelefono());
                txtCorreo.setText(tiendaSeleccionada.getEmail());
                txtCapacidad.setText(String.valueOf(tiendaSeleccionada.getCapacidadAlmacen()));

                seleccionarSupervisorEnCombo(tiendaSeleccionada.getGerenteId());

                btnDesactivar.setText(tiendaSeleccionada.isActiva() ? "Desactivar" : "Activar");
                setCamposEditables(tiendaSeleccionada.isActiva());
            }
        }
    }

    private void setCamposEditables(boolean editable) {
        txtNombre.setEditable(editable);
        txtDireccion.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtCorreo.setEditable(editable);
        txtCapacidad.setEditable(editable);
        cbSupervisor.setEnabled(editable);
        spApertura.setEnabled(editable);
        spCierre.setEnabled(editable);
        btnActualizar.setEnabled(editable);
    }

    private void seleccionarSupervisorEnCombo(long gerenteId) {
        if (gerenteId == 0) {
            cbSupervisor.setSelectedIndex(0);
            return;
        }
        for (int i = 0; i < cbSupervisor.getItemCount(); i++) {
            String item = cbSupervisor.getItemAt(i);
            if (item != null && item.startsWith(gerenteId + " -")) {
                cbSupervisor.setSelectedIndex(i);
                return;
            }
        }
        cbSupervisor.setSelectedIndex(0);
    }

    private long getSupervisorIdSeleccionado() {
        Object sel = cbSupervisor.getSelectedItem();
        if (sel == null) {
            return 0;
        }
        String s = sel.toString();
        if (s.startsWith("--")) {
            return 0;
        }
        try {
            return Long.parseLong(s.split(" - ")[0].trim());
        } catch (Exception ex) {
            return 0;
        }
    }

    private void actualizarTienda() {
        if (tiendaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tienda primero.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos.");
            return;
        }

        if (!correo.matches("^[\\w.-]+@(gmail\\.com|hotmail\\.com|outlook\\.com)$")) {
            JOptionPane.showMessageDialog(this, "El correo debe ser válido.");
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidad debe ser numérica.");
            return;
        }

        try {
            tiendaSeleccionada.setNombreTienda(nombre);
            tiendaSeleccionada.setDireccionCompleta(direccion);
            tiendaSeleccionada.setTelefono(telefono);
            tiendaSeleccionada.setEmail(correo);
            tiendaSeleccionada.setCapacidadAlmacen(capacidad);
            tiendaSeleccionada.setGerenteId(getSupervisorIdSeleccionado());

            if (spApertura.getValue() instanceof java.util.Date d1) {
                tiendaSeleccionada.setHorarioApertura(new Time(d1.getTime()));
            }
            if (spCierre.getValue() instanceof java.util.Date d2) {
                tiendaSeleccionada.setHorarioCierre(new Time(d2.getTime()));
            }

            TiendaController tc = new TiendaController();
            tc.actualizar(tiendaSeleccionada);

            JOptionPane.showMessageDialog(this, "Tienda actualizada correctamente.");

            cargarTiendas();
            reseleccionarTienda(tiendaSeleccionada.getCodigoTienda()); // ⭐ vuelve a marcar la fila editada
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void toggleEstadoTienda() {
        if (tiendaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tienda primero.");
            return;
        }

        try {
            tiendaSeleccionada.setActiva(!tiendaSeleccionada.isActiva());
            TiendaController tc = new TiendaController();
            tc.actualizar(tiendaSeleccionada);

            JOptionPane.showMessageDialog(this,
                    tiendaSeleccionada.isActiva() ? "Tienda activada." : "Tienda desactivada.");

            cargarTiendas();
            cargarSeleccion();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
        }
    }

    private void reseleccionarTienda(String codigoTienda) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(codigoTienda)) {
                tabla.setRowSelectionInterval(i, i);
                cargarSeleccion();
                break;
            }
        }
    }

}
