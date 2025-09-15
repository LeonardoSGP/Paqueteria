package vista;

import controlador.ZonaController;
import controlador.TiendaController;
import modelo.Zona;
import modelo.Tienda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ZonaForm extends JPanel {

    private JTextField txtCodigoZona, txtNombre, txtTarifaBase, txtTiempoEntrega;
    private JTextArea txtDescripcion;
    private JComboBox<String> cbTienda;
    private JButton btnGuardar;
    private static int contador = 1; // secuencia simple
    private static String codigoZonaPendiente = null; // mantiene el código actual hasta guardar

    public ZonaForm() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 16);

        int fila = 0;

        // Código de zona (auto)
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Código Zona:"), gbc);

        if (codigoZonaPendiente == null) {
            codigoZonaPendiente = generarCodigoZona();
        }
        txtCodigoZona = new JTextField(codigoZonaPendiente);
        txtCodigoZona.setEditable(false);
        txtCodigoZona.setFont(fuente);
        gbc.gridx = 1;
        add(txtCodigoZona, gbc);

        // Nombre
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Nombre Zona:"), gbc);
        txtNombre = new JTextField();
        txtNombre.setFont(fuente);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        // Descripción
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Descripción:"), gbc);
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setFont(fuente);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        gbc.gridx = 1;
        add(scrollDesc, gbc);

        // Tarifa base
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Tarifa Base:"), gbc);
        txtTarifaBase = new JTextField();
        txtTarifaBase.setFont(fuente);
        gbc.gridx = 1;
        add(txtTarifaBase, gbc);

        // Tiempo entrega
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Tiempo Entrega (min):"), gbc);
        txtTiempoEntrega = new JTextField();
        txtTiempoEntrega.setFont(fuente);
        gbc.gridx = 1;
        add(txtTiempoEntrega, gbc);

        // Tienda Responsable
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        add(new JLabel("Tienda Responsable:"), gbc);
        cbTienda = new JComboBox<>();
        cbTienda.setFont(fuente);
        cargarTiendas();
        gbc.gridx = 1;
        add(cbTienda, gbc);

        // Botón Guardar
        fila++;
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        btnGuardar = new JButton("Guardar Zona");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        add(btnGuardar, gbc);

        btnGuardar.addActionListener(this::guardarZona);
    }

    private String generarCodigoZona() {
        ZonaController zc = new ZonaController();
        String ultimo = zc.obtenerUltimoCodigo();

        if (ultimo == null) {
            return "ZON001"; // primera zona
        }

        try {
            int num = Integer.parseInt(ultimo.substring(3)); // extrae el número
            return String.format("ZON%03d", num + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "ZON001"; // fallback en caso de error
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

    private void guardarZona(ActionEvent evt) {
        String codigo = txtCodigoZona.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String tarifaStr = txtTarifaBase.getText().trim();
        String tiempoStr = txtTiempoEntrega.getText().trim();
        String tiendaSel = (String) cbTienda.getSelectedItem();

        // Validaciones
        if (nombre.isEmpty() || tarifaStr.isEmpty() || tiempoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben estar completos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double tarifa;
        try {
            tarifa = Double.parseDouble(tarifaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La tarifa base debe ser un número válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tiempo;
        try {
            tiempo = Integer.parseInt(tiempoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El tiempo de entrega debe ser un número entero (minutos).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long tiendaId = null;
        if (tiendaSel != null && !tiendaSel.equals("-- Ninguna --")) {
            tiendaId = Long.parseLong(tiendaSel.split(" - ")[0]);
        }

        try {
            Zona z = new Zona();
            z.setCodigoZona(codigo);
            z.setNombreZona(nombre);
            z.setDescripcion(descripcion);
            z.setTarifaBase(tarifa);
            z.setTiempoEntregaEstimado(tiempo);
            if (tiendaId != null) {
                z.setTiendaResponsableId(tiendaId);
            }
            z.setActiva(true);

            ZonaController zc = new ZonaController();
            zc.insertar(z);

            JOptionPane.showMessageDialog(this, "Zona registrada con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar zona: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        // generar nuevo código y guardarlo en estático
        codigoZonaPendiente = generarCodigoZona();
        txtCodigoZona.setText(codigoZonaPendiente);

        txtNombre.setText("");
        txtDescripcion.setText("");
        txtTarifaBase.setText("");
        txtTiempoEntrega.setText("");
        cbTienda.setSelectedIndex(0);
    }
}
