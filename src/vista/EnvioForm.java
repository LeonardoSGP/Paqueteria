package vista;

import controlador.EnvioController;
import controlador.ClienteController;
import controlador.PaqueteController;
import modelo.Envio;
import modelo.Cliente;
import modelo.Paquete;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class EnvioForm extends JPanel {

    private JTextField txtNumeroSeguimiento;
    private JComboBox<Cliente> cbRemitente, cbDestinatario;
    private JTextArea txtDireccionOrigen, txtDireccionDestino;
    private JComboBox<String> cbTipoEnvio, cbPrioridad;
    private JTextField txtCosto, txtDescuento, txtCostoFinal;
    private JSpinner spnFechaEntrega;
    private JTextArea txtObservaciones;

    private JTable tblPaquetes;
    private DefaultTableModel paquetesModel;
    private JButton btnAgregarPaquete, btnQuitarPaquete;
    private JButton btnGuardar, btnLimpiar, btnCalcularCosto;

    private EnvioController envioController;
    private ClienteController clienteController;
    private PaqueteController paqueteController;

    public EnvioForm() {
        this.envioController = new EnvioController();
        this.clienteController = new ClienteController();
        this.paqueteController = new PaqueteController();

        initComponents();
        cargarClientes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 14);

        // ===== Título =====
        JLabel titulo = new JLabel("🚚 Registro de Envíos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        mainPanel.add(titulo, gbc);
        gbc.gridwidth = 1;

        // ===== Número de seguimiento =====
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Número Seguimiento:"), gbc);
        txtNumeroSeguimiento = new JTextField(envioController.generarSiguienteNumeroSeguimiento());
        txtNumeroSeguimiento.setEditable(false);
        txtNumeroSeguimiento.setFont(fuente);
        txtNumeroSeguimiento.setBackground(new Color(240, 240, 240));
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        mainPanel.add(txtNumeroSeguimiento, gbc);
        gbc.gridwidth = 1;

        // ===== Clientes =====
        JPanel panelClientes = new JPanel(new GridBagLayout());
        panelClientes.setBorder(BorderFactory.createTitledBorder("Información de Clientes"));
        panelClientes.setBackground(Color.WHITE);

        GridBagConstraints gbcClientes = new GridBagConstraints();
        gbcClientes.insets = new Insets(5, 5, 5, 5);
        gbcClientes.fill = GridBagConstraints.HORIZONTAL;

        // Remitente
        gbcClientes.gridx = 0;
        gbcClientes.gridy = 0;
        panelClientes.add(new JLabel("Remitente:"), gbcClientes);
        cbRemitente = new JComboBox<>();
        cbRemitente.setFont(fuente);
        cbRemitente.setRenderer(new ClienteComboRenderer());
        gbcClientes.gridx = 1;
        panelClientes.add(cbRemitente, gbcClientes);

        // Destinatario
        gbcClientes.gridx = 0;
        gbcClientes.gridy = 1;
        panelClientes.add(new JLabel("Destinatario:"), gbcClientes);
        cbDestinatario = new JComboBox<>();
        cbDestinatario.setFont(fuente);
        cbDestinatario.setRenderer(new ClienteComboRenderer());
        gbcClientes.gridx = 1;
        panelClientes.add(cbDestinatario, gbcClientes);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        mainPanel.add(panelClientes, gbc);
        gbc.gridwidth = 1;

        // ===== Direcciones =====
        JPanel panelDirecciones = new JPanel(new GridBagLayout());
        panelDirecciones.setBorder(BorderFactory.createTitledBorder("Direcciones"));
        panelDirecciones.setBackground(Color.WHITE);

        GridBagConstraints gbcDir = new GridBagConstraints();
        gbcDir.insets = new Insets(5, 5, 5, 5);
        gbcDir.fill = GridBagConstraints.BOTH;

        // Origen
        gbcDir.gridx = 0;
        gbcDir.gridy = 0;
        panelDirecciones.add(new JLabel("Dirección Origen:"), gbcDir);
        txtDireccionOrigen = new JTextArea(3, 30);
        txtDireccionOrigen.setFont(fuente);
        txtDireccionOrigen.setLineWrap(true);
        txtDireccionOrigen.setWrapStyleWord(true);
        panelDirecciones.add(new JScrollPane(txtDireccionOrigen), gbcDir);

        // Destino
        gbcDir.gridx = 0;
        gbcDir.gridy = 1;
        panelDirecciones.add(new JLabel("Dirección Destino:"), gbcDir);
        txtDireccionDestino = new JTextArea(3, 30);
        txtDireccionDestino.setFont(fuente);
        txtDireccionDestino.setLineWrap(true);
        txtDireccionDestino.setWrapStyleWord(true);
        panelDirecciones.add(new JScrollPane(txtDireccionDestino), gbcDir);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        mainPanel.add(panelDirecciones, gbc);
        gbc.gridwidth = 1;

        // ===== Tipo de envío y prioridad =====
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Tipo de Envío:"), gbc);
        cbTipoEnvio = new JComboBox<>(new String[]{"NORMAL", "EXPRESS", "ECONOMICO"});
        cbTipoEnvio.setFont(fuente);
        cbTipoEnvio.addActionListener(e -> calcularCostoTotal());
        gbc.gridx = 1;
        mainPanel.add(cbTipoEnvio, gbc);

        gbc.gridx = 2;
        mainPanel.add(new JLabel("Prioridad:"), gbc);
        cbPrioridad = new JComboBox<>(new String[]{"1 - Alta", "2 - Media", "3 - Baja"});
        cbPrioridad.setFont(fuente);
        cbPrioridad.setSelectedIndex(1);
        cbPrioridad.addActionListener(e -> calcularCostoTotal());
        gbc.gridx = 3;
        mainPanel.add(cbPrioridad, gbc);

        // ===== Paquetes =====
        JPanel panelPaquetes = new JPanel(new BorderLayout());
        panelPaquetes.setBorder(BorderFactory.createTitledBorder("Paquetes"));
        panelPaquetes.setBackground(Color.WHITE);

        String[] columnas = {"Descripción", "Peso (kg)", "Tipo", "Fragil", "Valor Declarado"};
        paquetesModel = new DefaultTableModel(columnas, 0);
        tblPaquetes = new JTable(paquetesModel);

        panelPaquetes.add(new JScrollPane(tblPaquetes), BorderLayout.CENTER);

        JPanel botonesPaquete = new JPanel(new FlowLayout());
        btnAgregarPaquete = new JButton("➕ Agregar Paquete");
        btnAgregarPaquete.addActionListener(this::agregarPaqueteDialog);
        btnQuitarPaquete = new JButton("❌ Quitar Paquete");
        btnQuitarPaquete.addActionListener(e -> quitarPaquete());

        botonesPaquete.add(btnAgregarPaquete);
        botonesPaquete.add(btnQuitarPaquete);

        panelPaquetes.add(botonesPaquete, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        mainPanel.add(panelPaquetes, gbc);
        gbc.gridwidth = 1;

        // ===== Costos =====
        JPanel panelCostos = new JPanel(new GridBagLayout());
        panelCostos.setBorder(BorderFactory.createTitledBorder("Información de Costos"));
        panelCostos.setBackground(Color.WHITE);

        GridBagConstraints gbcCost = new GridBagConstraints();
        gbcCost.insets = new Insets(5, 5, 5, 5);
        gbcCost.fill = GridBagConstraints.HORIZONTAL;

        gbcCost.gridx = 0;
        gbcCost.gridy = 0;
        panelCostos.add(new JLabel("Costo Total:"), gbcCost);
        txtCostoFinal = new JTextField("0.00");
        txtCostoFinal.setFont(fuente);
        txtCostoFinal.setEditable(false);
        txtCostoFinal.setBackground(new Color(240, 240, 240));
        gbcCost.gridx = 1;
        panelCostos.add(txtCostoFinal, gbcCost);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        mainPanel.add(panelCostos, gbc);
        gbc.gridwidth = 1;

        // ===== Fecha de entrega estimada =====
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Fecha Entrega Estimada:"), gbc);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        spnFechaEntrega = new JSpinner(new SpinnerDateModel(cal.getTime(), new Date(), null, Calendar.DAY_OF_MONTH));
        spnFechaEntrega.setEditor(new JSpinner.DateEditor(spnFechaEntrega, "dd/MM/yyyy"));
        spnFechaEntrega.setFont(fuente);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        mainPanel.add(spnFechaEntrega, gbc);
        gbc.gridwidth = 1;

        // ===== Observaciones =====
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(new JLabel("Observaciones:"), gbc);
        txtObservaciones = new JTextArea(4, 30);
        txtObservaciones.setFont(fuente);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(txtObservaciones), gbc);

        // ===== Botones Guardar/Limpiar =====
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        btnGuardar = new JButton("💾 Crear Envío");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGuardar.setBackground(new Color(0, 123, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        btnGuardar.addActionListener(this::guardarEnvio);

        btnLimpiar = new JButton("🗑️ Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLimpiar.setBackground(new Color(108, 117, 125));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setPreferredSize(new Dimension(120, 40));
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelBotones, gbc);

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
    }

    // ===== Métodos para manejar paquetes =====
    private void agregarPaqueteDialog(ActionEvent evt) {
        JTextField txtDescripcion = new JTextField();
        JTextField txtPeso = new JTextField();
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"DOCUMENTO", "CAJA", "SOBRE"});
        JCheckBox chkFragil = new JCheckBox("Fragil");
        JTextField txtValor = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Peso (kg):"));
        panel.add(txtPeso);
        panel.add(new JLabel("Tipo:"));
        panel.add(cbTipo);
        panel.add(new JLabel("Fragil:"));
        panel.add(chkFragil);
        panel.add(new JLabel("Valor declarado:"));
        panel.add(txtValor);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Paquete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String desc = txtDescripcion.getText();
                double peso = Double.parseDouble(txtPeso.getText());
                String tipo = (String) cbTipo.getSelectedItem();
                boolean fragil = chkFragil.isSelected();
                double valor = Double.parseDouble(txtValor.getText());

                paquetesModel.addRow(new Object[]{desc, peso, tipo, fragil, valor});
                calcularCostoTotal();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Peso y Valor deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void quitarPaquete() {
        int fila = tblPaquetes.getSelectedRow();
        if (fila >= 0) {
            paquetesModel.removeRow(fila);
            calcularCostoTotal();
        }
    }

    // ===== Calcular costo total =====
    private void calcularCostoTotal() {
        double total = 0.0;
        String tipoEnvio = (String) cbTipoEnvio.getSelectedItem();
        String prioridadStr = (String) cbPrioridad.getSelectedItem();
        int prioridad = Integer.parseInt(prioridadStr.substring(0, 1));

        for (int i = 0; i < paquetesModel.getRowCount(); i++) {
            double peso = (double) paquetesModel.getValueAt(i, 1);
            total += envioController.calcularCosto(tipoEnvio, peso, prioridad);
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");
        txtCostoFinal.setText(df.format(total));
    }

    // ===== Guardar envío =====
    private void guardarEnvio(ActionEvent evt) {
        Cliente remitente = (Cliente) cbRemitente.getSelectedItem();
        Cliente destinatario = (Cliente) cbDestinatario.getSelectedItem();
        if (remitente == null || destinatario == null) {
            JOptionPane.showMessageDialog(this, "Seleccione remitente y destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Envio envio = new Envio();
            envio.setNumeroSeguimiento(txtNumeroSeguimiento.getText());
            envio.setRemitenteId(remitente.getId());
            envio.setDestinatarioId(destinatario.getId());
            envio.setDireccionOrigen(txtDireccionOrigen.getText().trim());
            envio.setDireccionDestino(txtDireccionDestino.getText().trim());
            envio.setTipoEnvio((String) cbTipoEnvio.getSelectedItem());
            envio.setEstadoActual("REGISTRADO");
            envio.setFechaCreacion(new Date());
            envio.setFechaEntregaEstimada((Date) spnFechaEntrega.getValue());

            String prioridadStr = (String) cbPrioridad.getSelectedItem();
            envio.setPrioridad(Integer.parseInt(prioridadStr.substring(0, 1)));

            envio.setObservaciones(txtObservaciones.getText());

            double costoTotal = Double.parseDouble(txtCostoFinal.getText().replace(",", ""));
            envio.setCostoFinal(costoTotal);

            // Guardar el envío
            envioController.insertar(envio);

            // Guardar paquetes
            for (int i = 0; i < paquetesModel.getRowCount(); i++) {
                Paquete paquete = new Paquete();
                paquete.setDescripcion((String) paquetesModel.getValueAt(i, 0));
                paquete.setPeso((double) paquetesModel.getValueAt(i, 1));
                paquete.setTipoContenido((String) paquetesModel.getValueAt(i, 2));
                paquete.setFragil((boolean) paquetesModel.getValueAt(i, 3));
                paquete.setValorDeclarado((double) paquetesModel.getValueAt(i, 4));

                // Insertar paquete y vincularlo al envío
                paqueteController.insertarPaqueteEnvio(paquete, envio.getId());
            }

            JOptionPane.showMessageDialog(this, "Envío creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear envío: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNumeroSeguimiento.setText(envioController.generarSiguienteNumeroSeguimiento());
        cbRemitente.setSelectedIndex(0);
        cbDestinatario.setSelectedIndex(0);
        txtDireccionOrigen.setText("");
        txtDireccionDestino.setText("");
        cbTipoEnvio.setSelectedIndex(0);
        cbPrioridad.setSelectedIndex(1);
        paquetesModel.setRowCount(0);
        txtCostoFinal.setText("0.00");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        spnFechaEntrega.setValue(cal.getTime());
        txtObservaciones.setText("");
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteController.listarActivos();
            cbRemitente.removeAllItems();
            cbDestinatario.removeAllItems();
            cbRemitente.addItem(null);
            cbDestinatario.addItem(null);

            for (Cliente c : clientes) {
                cbRemitente.addItem(c);
                cbDestinatario.addItem(c);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Render para mostrar clientes en JComboBox
class ClienteComboRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Cliente) {
            Cliente c = (Cliente) value;
            setText(c.getCodigoCliente() + " - " + c.getNombre() + " " + c.getApellidos());
        } else if (value == null) {
            setText("-- Seleccionar Cliente --");
        }
        setHorizontalAlignment(CENTER); // Centrado
        return this;
    }
}
