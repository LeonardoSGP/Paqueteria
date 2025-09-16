package vista;

import controlador.ClienteController;
import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;

public class ListarClientes extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtCodigo, txtNombre, txtApellidos, txtTelefono, txtCredito, txtDescuento;
    private JButton btnActualizar, btnDesactivar;

    private Cliente clienteSeleccionado;

    public ListarClientes() {
        setLayout(new BorderLayout());

        // === Panel de edición ARRIBA ===
        JPanel panelEdicion = new JPanel(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createTitledBorder("Editar / Activar - Desactivar Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int fila = 0;

        // Código cliente (solo lectura)
        gbc.gridx = 0; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Código:"), gbc);
        txtCodigo = new JTextField(10);
        txtCodigo.setEditable(false);
        gbc.gridx = 1;
        panelEdicion.add(txtCodigo, gbc);

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

        // Crédito
        gbc.gridx = 2; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Crédito:"), gbc);
        txtCredito = new JTextField(10);
        gbc.gridx = 3;
        panelEdicion.add(txtCredito, gbc);

        // Descuento
        gbc.gridx = 4; gbc.gridy = fila;
        panelEdicion.add(new JLabel("Descuento:"), gbc);
        txtDescuento = new JTextField(10);
        gbc.gridx = 5;
        panelEdicion.add(txtDescuento, gbc);

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

        // === Tabla de clientes ===
        String[] columnas = {"ID", "Código", "Nombre", "Apellidos", "Teléfono", "Crédito", "Descuento", "Activo"};
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
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnDesactivar.addActionListener(e -> toggleEstadoCliente());

        // Cargar clientes
        cargarClientes();
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        ClienteController cc = new ClienteController();

        List<Cliente> lista = cc.listar();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getCodigoCliente(),
                    c.getNombre(),
                    c.getApellidos(),
                    c.getTelefono(),
                    c.getCreditoDisponible(),
                    c.getDescuentoAsignado(),
                    c.isActivo()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            long id = (long) modeloTabla.getValueAt(fila, 0);
            ClienteController cc = new ClienteController();
            clienteSeleccionado = cc.obtenerPorId(id);

            if (clienteSeleccionado != null) {
                txtCodigo.setText(clienteSeleccionado.getCodigoCliente());
                txtNombre.setText(clienteSeleccionado.getNombre());
                txtApellidos.setText(clienteSeleccionado.getApellidos());
                txtTelefono.setText(clienteSeleccionado.getTelefono());
                txtCredito.setText(String.valueOf(clienteSeleccionado.getCreditoDisponible()));
                txtDescuento.setText(String.valueOf(clienteSeleccionado.getDescuentoAsignado()));

                boolean activo = clienteSeleccionado.isActivo();
                btnDesactivar.setText(activo ? "Desactivar" : "Activar");

                setCamposEditables(activo);
            }
        }
    }

    private void setCamposEditables(boolean editable) {
        txtNombre.setEditable(editable);
        txtApellidos.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtCredito.setEditable(editable);
        txtDescuento.setEditable(editable);
        btnActualizar.setEnabled(editable);
    }

    private void actualizarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.");
            return;
        }

        try {
            clienteSeleccionado.setNombre(txtNombre.getText().trim());
            clienteSeleccionado.setApellidos(txtApellidos.getText().trim());
            clienteSeleccionado.setTelefono(txtTelefono.getText().trim());
            clienteSeleccionado.setCreditoDisponible(Double.parseDouble(txtCredito.getText().trim()));
            clienteSeleccionado.setDescuentoAsignado(Double.parseDouble(txtDescuento.getText().trim()));

            ClienteController cc = new ClienteController();
            cc.actualizar(clienteSeleccionado);

            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            cargarClientes();
            reseleccionarCliente(clienteSeleccionado.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void toggleEstadoCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente primero.");
            return;
        }

        try {
            clienteSeleccionado.setActivo(!clienteSeleccionado.isActivo());
            ClienteController cc = new ClienteController();
            cc.actualizar(clienteSeleccionado);

            JOptionPane.showMessageDialog(this,
                    clienteSeleccionado.isActivo() ? "Cliente activado." : "Cliente desactivado.");

            cargarClientes();
            reseleccionarCliente(clienteSeleccionado.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
        }
    }

    private void reseleccionarCliente(long id) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (((long) modeloTabla.getValueAt(i, 0)) == id) {
                tabla.setRowSelectionInterval(i, i);
                cargarSeleccion();
                break;
            }
        }
    }
}
