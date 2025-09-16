package vista;

import controlador.ZonaController;
import modelo.Zona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ICajero extends JPanel {

    private JTextField txtRemitente, txtDestinatario, txtDireccion, txtCosto;
    private JComboBox<String> cbZona;
    private JButton btnGuardar, btnTicket;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JPanel panelContenido;

    public ICajero() {
        setLayout(new BorderLayout());

        // ===== Panel lateral =====
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(33, 45, 62)); // 🔵 Azul oscuro
        panelMenu.setPreferredSize(new Dimension(220, 0));

        JLabel lblTitulo = new JLabel("Cajero", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelMenu.add(lblTitulo);

        JButton btnRegistrar = crearBotonMenu("📝 Registrar Pedido");
        JButton btnPedidos = crearBotonMenu("📋 Ver Pedidos");

        panelMenu.add(btnRegistrar);
        panelMenu.add(btnPedidos);

        // Empujar el botón de cerrar sesión hacia abajo
        panelMenu.add(Box.createVerticalGlue());

        JButton btnCerrarSesion = crearBotonCerrarSesion();
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        // ===== Panel central =====
        panelContenido = new JPanel(new CardLayout());
        panelContenido.setBackground(Color.WHITE);

        panelContenido.add(crearPanelRegistro(), "REGISTRO");
        panelContenido.add(crearPanelPedidos(), "PEDIDOS");

        add(panelContenido, BorderLayout.CENTER);

        // === Eventos ===
        btnRegistrar.addActionListener(e -> mostrarVista("REGISTRO"));
        btnPedidos.addActionListener(e -> mostrarVista("PEDIDOS"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    // ===== Panel Registro de Pedido =====
    private JPanel crearPanelRegistro() {
        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int fila = 0;

        // Remitente
        gbc.gridx = 0; gbc.gridy = fila;
        panelRegistro.add(new JLabel("Remitente:"), gbc);
        txtRemitente = new JTextField(15);
        gbc.gridx = 1;
        panelRegistro.add(txtRemitente, gbc);

        // Destinatario
        gbc.gridx = 2;
        panelRegistro.add(new JLabel("Destinatario:"), gbc);
        txtDestinatario = new JTextField(15);
        gbc.gridx = 3;
        panelRegistro.add(txtDestinatario, gbc);

        // Dirección
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelRegistro.add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField(20);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panelRegistro.add(txtDireccion, gbc);
        gbc.gridwidth = 1;

        // Zona
        fila++;
        gbc.gridx = 0; gbc.gridy = fila;
        panelRegistro.add(new JLabel("Zona:"), gbc);
        cbZona = new JComboBox<>(new String[]{"-- Seleccione --"});
        gbc.gridx = 1;
        panelRegistro.add(cbZona, gbc);

        // Costo
        gbc.gridx = 2;
        panelRegistro.add(new JLabel("Costo:"), gbc);
        txtCosto = new JTextField(10);
        txtCosto.setEditable(false);
        gbc.gridx = 3;
        panelRegistro.add(txtCosto, gbc);

        // Botones
        fila++;
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 4;
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Pedido");
        btnTicket = new JButton("Generar Ticket");
        panelBtns.add(btnGuardar);
        panelBtns.add(btnTicket);
        panelRegistro.add(panelBtns, gbc);

        // Eventos
        cbZona.addActionListener(e -> calcularCostoZona());
        btnGuardar.addActionListener(this::guardarPedido);
        btnTicket.addActionListener(this::generarTicket);

        // Cargar zonas
        cargarZonas();

        return panelRegistro;
    }

    // ===== Panel de Pedidos (Tabla) =====
    private JPanel crearPanelPedidos() {
        JPanel panelPedidos = new JPanel(new BorderLayout());
        panelPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos Registrados"));

        String[] columnas = {"Remitente", "Destinatario", "Dirección", "Zona", "Costo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPedidos = new JTable(modeloTabla);

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        panelPedidos.add(scroll, BorderLayout.CENTER);

        return panelPedidos;
    }

    // ===== Funciones de lógica =====
    private void cargarZonas() {
        cbZona.removeAllItems();
        cbZona.addItem("-- Seleccione --");
        ZonaController zc = new ZonaController();
        List<Zona> lista = zc.listar();
        for (Zona z : lista) {
            if (z.isActiva()) {
                cbZona.addItem(z.getId() + " - " + z.getNombreZona() + " (Tarifa: $" + z.getTarifaBase() + ")");
            }
        }
    }

    private void calcularCostoZona() {
        String zonaSel = (String) cbZona.getSelectedItem();
        if (zonaSel != null && !zonaSel.equals("-- Seleccione --")) {
            String tarifaStr = zonaSel.substring(zonaSel.indexOf("$") + 1, zonaSel.indexOf(")"));
            txtCosto.setText(tarifaStr);
        } else {
            txtCosto.setText("");
        }
    }

    private void guardarPedido(ActionEvent evt) {
        String remitente = txtRemitente.getText().trim();
        String destinatario = txtDestinatario.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String zonaSel = (String) cbZona.getSelectedItem();
        String costo = txtCosto.getText().trim();

        if (remitente.isEmpty() || destinatario.isEmpty() || direccion.isEmpty()
                || zonaSel == null || zonaSel.equals("-- Seleccione --")) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modeloTabla.addRow(new Object[]{remitente, destinatario, direccion, zonaSel, costo});

        JOptionPane.showMessageDialog(this, "Pedido registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    private void generarTicket(ActionEvent evt) {
        int fila = tablaPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String remitente = (String) modeloTabla.getValueAt(fila, 0);
        String destinatario = (String) modeloTabla.getValueAt(fila, 1);
        String direccion = (String) modeloTabla.getValueAt(fila, 2);
        String zona = (String) modeloTabla.getValueAt(fila, 3);
        String costo = (String) modeloTabla.getValueAt(fila, 4);

        String ticket = """
                === Ticket de Envío ===
                Remitente: %s
                Destinatario: %s
                Dirección: %s
                Zona: %s
                Costo: $%s
                """.formatted(remitente, destinatario, direccion, zona, costo);

        JOptionPane.showMessageDialog(this, ticket, "Ticket", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFormulario() {
        txtRemitente.setText("");
        txtDestinatario.setText("");
        txtDireccion.setText("");
        cbZona.setSelectedIndex(0);
        txtCosto.setText("");
    }

    // ===== Helpers estilo =====
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setBackground(new Color(52, 152, 219)); // 🔵 Azul
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton crearBotonCerrarSesion() {
        JButton btn = new JButton("⏻ Cerrar sesión");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 36));
        btn.setBackground(new Color(192, 57, 43)); // 🔴 Rojo
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void mostrarVista(String nombre) {
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, nombre);
    }

    private void cerrarSesion() {
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) w.dispose();
        SwingUtilities.invokeLater(() -> {
            ILogin login = new ILogin();
            login.setVisible(true);
        });
    }

}
