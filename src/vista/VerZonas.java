package vista;

import controlador.ZonaController;
import controlador.TiendaController;
import controlador.EmpleadoController;
import controlador.UsuarioController;
import controlador.ZonaRepartidorController;
import modelo.Zona;
import modelo.Tienda;
import modelo.Empleado;
import modelo.ZonaRepartidor;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class VerZonas extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigoZona, txtNombre, txtTarifaBase, txtTiempoEntrega;
    private JTextArea txtDescripcion;
    private JComboBox<String> cbTienda, cbRepartidor;
    private JButton btnActualizar, btnDesactivar, btnAsignarRepartidor;

    private Zona zonaSeleccionada;

    public VerZonas() {
        setLayout(new BorderLayout());

        // === Panel de edición arriba ===
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar / Activar - Desactivar Zona"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int fila = 0;

        // Código zona (solo lectura)
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Código:"), gbc);
        txtCodigoZona = new JTextField(10);
        txtCodigoZona.setEditable(false);
        gbc.gridx = 1;
        panelEdicion.add(txtCodigoZona, gbc);

        // Nombre
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(12);
        gbc.gridx = 3;
        panelEdicion.add(txtNombre, gbc);

        // Tarifa base
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Tarifa Base:"), gbc);
        txtTarifaBase = new JTextField(10);
        gbc.gridx = 1;
        panelEdicion.add(txtTarifaBase, gbc);

        // Tiempo entrega
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Tiempo Entrega:"), gbc);
        txtTiempoEntrega = new JTextField(10);
        gbc.gridx = 3;
        panelEdicion.add(txtTiempoEntrega, gbc);

        // Tienda
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Tienda:"), gbc);
        cbTienda = new JComboBox<>(new String[]{"-- Ninguna --"});
        gbc.gridx = 1;
        panelEdicion.add(cbTienda, gbc);

        // Repartidor
        gbc.gridx = 2;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Repartidor:"), gbc);
        cbRepartidor = new JComboBox<>(new String[]{"-- Ninguno --"});
        gbc.gridx = 3;
        panelEdicion.add(cbRepartidor, gbc);

        // Descripción
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        panelEdicion.add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextArea(3, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelEdicion.add(scrollDesc, gbc);

        // Botones
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 4;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Actualizar");
        btnDesactivar = new JButton("Desactivar");
        btnAsignarRepartidor = new JButton("Asignar Repartidor");
        panelBtns.add(btnActualizar);
        panelBtns.add(btnDesactivar);
        panelBtns.add(btnAsignarRepartidor);
        panelEdicion.add(panelBtns, gbc);

        add(panelEdicion, BorderLayout.NORTH);

        // === Tabla de zonas ===
        String[] columnas = {"Código", "Nombre", "Descripción", "Tarifa", "Tiempo", "Tienda", "Repartidores", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);

        // Renderer para tachar inactivos
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
        btnActualizar.addActionListener(e -> actualizarZona());
        btnDesactivar.addActionListener(e -> toggleEstadoZona());
        btnAsignarRepartidor.addActionListener(e -> asignarRepartidor());

        // Cargar datos iniciales
        cargarZonas();
        cargarTiendas();
        cargarRepartidores();
    }

    private void cargarZonas() {
        modeloTabla.setRowCount(0);
        ZonaController zc = new ZonaController();
        TiendaController tc = new TiendaController();
        ZonaRepartidorController zrc = new ZonaRepartidorController();
        EmpleadoController ec = new EmpleadoController();

        List<Zona> lista = zc.listar();
        for (Zona z : lista) {
            Tienda t = tc.obtenerPorId(z.getTiendaResponsableId());
            List<ZonaRepartidor> asignaciones = zrc.listarPorZona(z.getId());

            String repartidores = asignaciones.stream()
                    .filter(ZonaRepartidor::isActivo) // solo activos
                    .map(zr -> {
                        Empleado e = ec.obtenerPorId(zr.getRepartidorId()); // aquí guardamos empleado_id directo
                        if (e != null) {
                            UsuarioController uc = new UsuarioController();
                            Usuario u = uc.obtenerPorId(e.getUsuarioId());
                            // ⚡ Solo mostrar si sigue siendo REPARTIDOR
                            if (u != null && "REPARTIDOR".equalsIgnoreCase(u.getTipoUsuario())) {
                                return e.getNombre() + " " + e.getApellidos();
                            }
                        }
                        return null; // si no es repartidor ya no se muestra
                    })
                    .filter(Objects::nonNull) // quitamos los null
                    .collect(Collectors.joining(", "));
            modeloTabla.addRow(new Object[]{
                z.getCodigoZona(),
                z.getNombreZona(),
                z.getDescripcion(),
                z.getTarifaBase(),
                z.getTiempoEntregaEstimado(),
                (t != null ? t.getNombreTienda() : ""),
                repartidores,
                z.isActiva()
            });
        }
    }

    private void cargarTiendas() {
        cbTienda.removeAllItems();
        cbTienda.addItem("-- Ninguna --");
        TiendaController tc = new TiendaController();
        for (Tienda t : tc.listar()) {
            cbTienda.addItem(t.getId() + " - " + t.getNombreTienda());
        }
    }

    private void cargarRepartidores() {
        cbRepartidor.removeAllItems();
        cbRepartidor.addItem("-- Ninguno --");

        UsuarioController uc = new UsuarioController();
        EmpleadoController ec = new EmpleadoController();

        // Trae solo usuarios con rol REPARTIDOR
        List<Usuario> usuariosRepartidores = uc.listarRepartidores();
        for (Usuario u : usuariosRepartidores) {
            Empleado emp = ec.obtenerPorUsuarioId(u.getId());
            if (emp != null && emp.isActivo()) {
                cbRepartidor.addItem(emp.getId() + " - " + emp.getNombre() + " " + emp.getApellidos());
            }
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modeloTabla.getValueAt(fila, 0);
            ZonaController zc = new ZonaController();
            for (Zona z : zc.listar()) {
                if (z.getCodigoZona().equals(codigo)) {
                    zonaSeleccionada = z;
                    break;
                }
            }
            if (zonaSeleccionada != null) {
                txtCodigoZona.setText(zonaSeleccionada.getCodigoZona());
                txtNombre.setText(zonaSeleccionada.getNombreZona());
                txtDescripcion.setText(zonaSeleccionada.getDescripcion());
                txtTarifaBase.setText(String.valueOf(zonaSeleccionada.getTarifaBase()));
                txtTiempoEntrega.setText(String.valueOf(zonaSeleccionada.getTiempoEntregaEstimado()));

                // Seleccionar tienda
                if (zonaSeleccionada.getTiendaResponsableId() != null && zonaSeleccionada.getTiendaResponsableId() != 0) {
                    TiendaController tc = new TiendaController();
                    Tienda t = tc.obtenerPorId(zonaSeleccionada.getTiendaResponsableId());
                    if (t != null) {
                        cbTienda.setSelectedItem(t.getId() + " - " + t.getNombreTienda());
                    }
                } else {
                    cbTienda.setSelectedIndex(0);
                }

                // Seleccionar repartidor (trae de la tabla ZONA_REPARTIDOR)
                ZonaRepartidorController zrc = new ZonaRepartidorController();
                List<ZonaRepartidor> asignados = zrc.listarPorZona(zonaSeleccionada.getId());

                if (!asignados.isEmpty()) {
                    // Tomamos el primero activo y lo seleccionamos
                    ZonaRepartidor activo = asignados.stream()
                            .filter(ZonaRepartidor::isActivo)
                            .findFirst()
                            .orElse(null);

                    if (activo != null) {
                        EmpleadoController ec = new EmpleadoController();
                        Empleado rep = ec.obtenerPorId(activo.getRepartidorId());
                        if (rep != null) {
                            cbRepartidor.setSelectedItem(rep.getId() + " - " + rep.getNombre() + " " + rep.getApellidos());
                        }
                    }
                } else {
                    cbRepartidor.setSelectedIndex(0);
                }

                boolean activo = zonaSeleccionada.isActiva();
                btnDesactivar.setText(activo ? "Desactivar" : "Activar");
                setCamposEditables(activo);
            }
        }
    }

    private void setCamposEditables(boolean editable) {
        txtNombre.setEditable(editable);
        txtDescripcion.setEditable(editable);
        txtTarifaBase.setEditable(editable);
        txtTiempoEntrega.setEditable(editable);
        cbTienda.setEnabled(editable);
        cbRepartidor.setEnabled(editable);
        btnActualizar.setEnabled(editable);
        btnAsignarRepartidor.setEnabled(editable);
    }

    private void actualizarZona() {
        if (zonaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una zona primero.");
            return;
        }
        try {
            zonaSeleccionada.setNombreZona(txtNombre.getText().trim());
            zonaSeleccionada.setDescripcion(txtDescripcion.getText().trim());
            zonaSeleccionada.setTarifaBase(Double.parseDouble(txtTarifaBase.getText().trim()));
            zonaSeleccionada.setTiempoEntregaEstimado(Integer.parseInt(txtTiempoEntrega.getText().trim()));

            String tiendaSel = (String) cbTienda.getSelectedItem();
            if (tiendaSel != null && !tiendaSel.equals("-- Ninguna --")) {
                long tiendaId = Long.parseLong(tiendaSel.split(" - ")[0]);
                zonaSeleccionada.setTiendaResponsableId(tiendaId);
            } else {
                zonaSeleccionada.setTiendaResponsableId(null);
            }

            // Guardar zona
            ZonaController zc = new ZonaController();
            zc.actualizar(zonaSeleccionada);

            // === Nuevo: guardar repartidor seleccionado ===
            String repartidorSel = (String) cbRepartidor.getSelectedItem();
            if (repartidorSel != null && !repartidorSel.equals("-- Ninguno --")) {
                long repartidorId = Long.parseLong(repartidorSel.split(" - ")[0]);
                ZonaRepartidorController zrc = new ZonaRepartidorController();
                zrc.asignar(zonaSeleccionada.getId(), repartidorId);
            }

            JOptionPane.showMessageDialog(this, "Zona actualizada correctamente.");
            cargarZonas();
            reseleccionarZona(zonaSeleccionada.getCodigoZona());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void toggleEstadoZona() {
        if (zonaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una zona primero.");
            return;
        }
        try {
            zonaSeleccionada.setActiva(!zonaSeleccionada.isActiva());
            ZonaController zc = new ZonaController();
            zc.actualizar(zonaSeleccionada);
            JOptionPane.showMessageDialog(this,
                    zonaSeleccionada.isActiva() ? "Zona activada." : "Zona desactivada.");
            cargarZonas();
            reseleccionarZona(zonaSeleccionada.getCodigoZona());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
        }
    }

    private void asignarRepartidor() {
        if (zonaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una zona primero.");
            return;
        }
        String repartidorSel = (String) cbRepartidor.getSelectedItem();
        if (repartidorSel != null && !repartidorSel.equals("-- Ninguno --")) {
            long repartidorId = Long.parseLong(repartidorSel.split(" - ")[0]);
            ZonaRepartidorController zrc = new ZonaRepartidorController();
            zrc.asignar(zonaSeleccionada.getId(), repartidorId);
            JOptionPane.showMessageDialog(this, "Repartidor asignado correctamente.");
            cargarZonas();
            reseleccionarZona(zonaSeleccionada.getCodigoZona());
        }
    }

    private void reseleccionarZona(String codigoZona) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(codigoZona)) {
                tabla.setRowSelectionInterval(i, i);
                cargarSeleccion();
                break;
            }
        }
    }
}
