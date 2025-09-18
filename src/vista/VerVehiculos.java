package vista;

import controlador.VehiculoController;
import modelo.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;

public class VerVehiculos extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtPlacas, txtMarca, txtModelo, txtCapacidad;
    private JCheckBox chkDisponible;
    private JButton btnActualizar, btnDesactivar;

    private Vehiculo vehiculoSeleccionado;

    public VerVehiculos() {
        setLayout(new BorderLayout());

        // === Panel de edición arriba ===
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar / Activar - Desactivar Vehículo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int fila = 0;

        // Placas
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Placas:"), gbc);
        txtPlacas = new JTextField(10);
        gbc.gridx = 1;
        panelEdicion.add(txtPlacas, gbc);

        // Marca
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Marca:"), gbc);
        txtMarca = new JTextField(12);
        gbc.gridx = 3;
        panelEdicion.add(txtMarca, gbc);

        // Modelo
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Modelo:"), gbc);
        txtModelo = new JTextField(12);
        gbc.gridx = 1;
        panelEdicion.add(txtModelo, gbc);

        // Capacidad
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Capacidad (kg):"), gbc);
        txtCapacidad = new JTextField(8);
        gbc.gridx = 3;
        panelEdicion.add(txtCapacidad, gbc);

        // Disponible
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Disponible:"), gbc);
        chkDisponible = new JCheckBox("Sí");
        chkDisponible.setBackground(Color.WHITE);
        gbc.gridx = 1;
        panelEdicion.add(chkDisponible, gbc);

        // Botones
        gbc.gridx = 2; gbc.gridy = fila; gbc.gridwidth = 2;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnActualizar = new JButton("Actualizar");
        btnDesactivar = new JButton("Desactivar");
        panelBtns.add(btnActualizar);
        panelBtns.add(btnDesactivar);
        panelEdicion.add(panelBtns, gbc);

        add(panelEdicion, BorderLayout.NORTH);

        // === Tabla de vehículos ===
        String[] columnas = {"Placas", "Marca", "Modelo", "Capacidad", "Disponible", "Activo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // no editable directamente
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

                boolean activo = (boolean) modeloTabla.getValueAt(row, 5);
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
        btnActualizar.addActionListener(e -> actualizarVehiculo());
        btnDesactivar.addActionListener(e -> toggleEstadoVehiculo());

        // Cargar datos iniciales
        cargarVehiculos();
    }

    private void cargarVehiculos() {
        modeloTabla.setRowCount(0);
        VehiculoController vc = new VehiculoController();
        List<Vehiculo> lista = vc.listar();
        for (Vehiculo v : lista) {
            modeloTabla.addRow(new Object[]{
                    v.getPlacas(),
                    v.getMarca(),
                    v.getModelo(),
                    v.getCapacidadCarga(),
                    v.isDisponible(),
                    v.isActivo()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String placas = (String) modeloTabla.getValueAt(fila, 0);
            VehiculoController vc = new VehiculoController();
            vehiculoSeleccionado = vc.listar().stream()
                    .filter(v -> v.getPlacas().equals(placas))
                    .findFirst().orElse(null);

            if (vehiculoSeleccionado != null) {
                txtPlacas.setText(vehiculoSeleccionado.getPlacas());
                txtMarca.setText(vehiculoSeleccionado.getMarca());
                txtModelo.setText(vehiculoSeleccionado.getModelo());
                txtCapacidad.setText(String.valueOf(vehiculoSeleccionado.getCapacidadCarga()));
                chkDisponible.setSelected(vehiculoSeleccionado.isDisponible());

                btnDesactivar.setText(vehiculoSeleccionado.isActivo() ? "Desactivar" : "Activar");
                setCamposEditables(vehiculoSeleccionado.isActivo());
            }
        }
    }

    private void setCamposEditables(boolean editable) {
        txtPlacas.setEditable(editable);
        txtMarca.setEditable(editable);
        txtModelo.setEditable(editable);
        txtCapacidad.setEditable(editable);
        chkDisponible.setEnabled(editable);
        btnActualizar.setEnabled(editable);
    }

    private void actualizarVehiculo() {
        if (vehiculoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo primero.");
            return;
        }

        String placas = txtPlacas.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String capacidadStr = txtCapacidad.getText().trim();
        boolean disponible = chkDisponible.isSelected();

        if (placas.isEmpty() || marca.isEmpty() || modelo.isEmpty() || capacidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (placas.length() != 6) {
            JOptionPane.showMessageDialog(this, "Las placas deben tener exactamente 6 caracteres.");
            return;
        }

        double capacidad;
        try {
            capacidad = Double.parseDouble(capacidadStr);
            if (capacidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser un número positivo.");
            return;
        }

        try {
            vehiculoSeleccionado.setPlacas(placas);
            vehiculoSeleccionado.setMarca(marca);
            vehiculoSeleccionado.setModelo(modelo);
            vehiculoSeleccionado.setCapacidadCarga(capacidad);
            vehiculoSeleccionado.setDisponible(disponible);

            VehiculoController vc = new VehiculoController();
            vc.actualizar(vehiculoSeleccionado);

            JOptionPane.showMessageDialog(this, "Vehículo actualizado correctamente.");
            cargarVehiculos();
            reseleccionarVehiculo(vehiculoSeleccionado.getPlacas());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void toggleEstadoVehiculo() {
        if (vehiculoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo primero.");
            return;
        }
        try {
            vehiculoSeleccionado.setActivo(!vehiculoSeleccionado.isActivo());
            VehiculoController vc = new VehiculoController();
            vc.actualizar(vehiculoSeleccionado);

            JOptionPane.showMessageDialog(this,
                    vehiculoSeleccionado.isActivo() ? "Vehículo activado." : "Vehículo desactivado.");
            cargarVehiculos();
            reseleccionarVehiculo(vehiculoSeleccionado.getPlacas());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
        }
    }

    private void reseleccionarVehiculo(String placas) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(placas)) {
                tabla.setRowSelectionInterval(i, i);
                cargarSeleccion();
                break;
            }
        }
    }
}
