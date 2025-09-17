package vista;

import controlador.EnvioController;
import controlador.ClienteController;
import controlador.PaqueteController;
import modelo.Envio;
import modelo.Cliente;
import modelo.Paquete;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EnvioForm extends JPanel {

    private JTextField txtNumeroSeguimiento;
    private JComboBox<Cliente> cbRemitente, cbDestinatario;
    private JTextArea txtDireccionOrigen, txtDireccionDestino;
    private JComboBox<String> cbTipoEnvio, cbPrioridad;
    private JTextField txtCosto, txtDescuento, txtCostoFinal;
    private JSpinner spnFechaEntrega;
    private JTextArea txtObservaciones;
    private JButton btnGuardar, btnCalcularCosto, btnLimpiar;

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

        // Panel principal con scroll
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fuente = new Font("Arial", Font.PLAIN, 14);

        // Título
        JLabel titulo = new JLabel("🚚 Registro de Envíos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        mainPanel.add(titulo, gbc);

        gbc.gridwidth = 1; // Reset

        // Número de seguimiento
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

        gbc.gridwidth = 1; // Reset

        // Panel de Clientes
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
        cbRemitente.setRenderer(new ClienteComboRenderer());

        cbRemitente.setFont(fuente);
        gbcClientes.gridx = 1;
        panelClientes.add(cbRemitente, gbcClientes);

        // Destinatario
        gbcClientes.gridx = 0;
        gbcClientes.gridy = 1;
        panelClientes.add(new JLabel("Destinatario:"), gbcClientes);
        cbDestinatario = new JComboBox<>();
        cbDestinatario.setRenderer(new ClienteComboRenderer());
        cbDestinatario.setFont(fuente);
        gbcClientes.gridx = 1;
        panelClientes.add(cbDestinatario, gbcClientes);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        mainPanel.add(panelClientes, gbc);

        gbc.gridwidth = 1; // Reset

        // Panel de Direcciones
        JPanel panelDirecciones = new JPanel(new GridBagLayout());
        panelDirecciones.setBorder(BorderFactory.createTitledBorder("Direcciones"));
        panelDirecciones.setBackground(Color.WHITE);

        GridBagConstraints gbcDir = new GridBagConstraints();
        gbcDir.insets = new Insets(5, 5, 5, 5);
        gbcDir.fill = GridBagConstraints.BOTH;

        // Dirección origen
        gbcDir.gridx = 0;
        gbcDir.gridy = 0;
        panelDirecciones.add(new JLabel("Dirección Origen:"), gbcDir);
        txtDireccionOrigen = new JTextArea(3, 30);
        txtDireccionOrigen.setFont(fuente);
        txtDireccionOrigen.setLineWrap(true);
        txtDireccionOrigen.setWrapStyleWord(true);
        JScrollPane scrollOrigen = new JScrollPane(txtDireccionOrigen);
        gbcDir.gridx = 1;
        panelDirecciones.add(scrollOrigen, gbcDir);

        // Dirección destino
        gbcDir.gridx = 0;
        gbcDir.gridy = 1;
        panelDirecciones.add(new JLabel("Dirección Destino:"), gbcDir);
        txtDireccionDestino = new JTextArea(3, 30);
        txtDireccionDestino.setFont(fuente);
        txtDireccionDestino.setLineWrap(true);
        txtDireccionDestino.setWrapStyleWord(true);
        JScrollPane scrollDestino = new JScrollPane(txtDireccionDestino);
        gbcDir.gridx = 1;
        panelDirecciones.add(scrollDestino, gbcDir);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        mainPanel.add(panelDirecciones, gbc);

        gbc.gridwidth = 1; // Reset

        // Tipo de envío y prioridad
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Tipo de Envío:"), gbc);
        cbTipoEnvio = new JComboBox<>(new String[]{"NORMAL", "EXPRESS", "ECONOMICO"});
        cbTipoEnvio.setFont(fuente);
        cbTipoEnvio.addActionListener(e -> calcularCosto());
        gbc.gridx = 1;
        mainPanel.add(cbTipoEnvio, gbc);

        gbc.gridx = 2;
        mainPanel.add(new JLabel("Prioridad:"), gbc);
        cbPrioridad = new JComboBox<>(new String[]{"1 - Alta", "2 - Media", "3 - Baja"});
        cbPrioridad.setFont(fuente);
        cbPrioridad.setSelectedIndex(1); // Media por defecto
        cbPrioridad.addActionListener(e -> calcularCosto());
        gbc.gridx = 3;
        mainPanel.add(cbPrioridad, gbc);

        // Panel de costos
        JPanel panelCostos = new JPanel(new GridBagLayout());
        panelCostos.setBorder(BorderFactory.createTitledBorder("Información de Costos"));
        panelCostos.setBackground(Color.WHITE);

        GridBagConstraints gbcCost = new GridBagConstraints();
        gbcCost.insets = new Insets(5, 5, 5, 5);
        gbcCost.fill = GridBagConstraints.HORIZONTAL;

        gbcCost.gridx = 0;
        gbcCost.gridy = 0;
        panelCostos.add(new JLabel("Costo Base:"), gbcCost);
        txtCosto = new JTextField("0.00");
        txtCosto.setFont(fuente);
        gbcCost.gridx = 1;
        panelCostos.add(txtCosto, gbcCost);

        btnCalcularCosto = new JButton("💰 Calcular");
        btnCalcularCosto.addActionListener(e -> calcularCosto());
        gbcCost.gridx = 2;
        panelCostos.add(btnCalcularCosto, gbcCost);

        gbcCost.gridx = 0;
        gbcCost.gridy = 1;
        panelCostos.add(new JLabel("Descuento (%):"), gbcCost);
        txtDescuento = new JTextField("0.0");
        txtDescuento.setFont(fuente);
        txtDescuento.addActionListener(e -> calcularCostoFinal());
        gbcCost.gridx = 1;
        panelCostos.add(txtDescuento, gbcCost);

        gbcCost.gridx = 0;
        gbcCost.gridy = 2;
        panelCostos.add(new JLabel("Costo Final:"), gbcCost);
        txtCostoFinal = new JTextField("0.00");
        txtCostoFinal.setFont(new Font("Arial", Font.BOLD, 14));
        txtCosto.setEditable(false);
        txtCosto.setBackground(new Color(240, 240, 240));

        txtCostoFinal.setBackground(new Color(240, 240, 240));
        gbcCost.gridx = 1;
        panelCostos.add(txtCostoFinal, gbcCost);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        mainPanel.add(panelCostos, gbc);

        gbc.gridwidth = 1; // Reset

        // Fecha de entrega estimada
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("Fecha Entrega Estimada:"), gbc);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3); // 3 días por defecto
        spnFechaEntrega = new JSpinner(new SpinnerDateModel(cal.getTime(), new Date(), null, Calendar.DAY_OF_MONTH));
        spnFechaEntrega.setEditor(new JSpinner.DateEditor(spnFechaEntrega, "dd/MM/yyyy"));
        spnFechaEntrega.setFont(fuente);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        mainPanel.add(spnFechaEntrega, gbc);

        gbc.gridwidth = 1; // Reset

        // Observaciones
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(new JLabel("Observaciones:"), gbc);
        txtObservaciones = new JTextArea(4, 30);
        txtObservaciones.setFont(fuente);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollObs, gbc);

        // Panel de botones
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
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelBotones, gbc);

        // Scroll para el panel principal
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteController.listarActivos();

            cbRemitente.removeAllItems();
            cbDestinatario.removeAllItems();

            cbRemitente.addItem(null); // Opción vacía
            cbDestinatario.addItem(null);

            for (Cliente cliente : clientes) {
                cbRemitente.addItem(cliente);
                cbDestinatario.addItem(cliente);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularCosto() {
        try {
            String tipoEnvio = (String) cbTipoEnvio.getSelectedItem();
            String prioridadStr = (String) cbPrioridad.getSelectedItem();
            int prioridad = Integer.parseInt(prioridadStr.substring(0, 1));

            // Para este ejemplo, usamos peso promedio de 2kg
            // En una implementación real, obtendrías esto de los paquetes seleccionados
            double pesoPromedio = 2.0;

            double costo = envioController.calcularCosto(tipoEnvio, pesoPromedio, prioridad);

            DecimalFormat df = new DecimalFormat("0.00");
            df.setDecimalSeparatorAlwaysShown(true);
            txtCosto.setText(df.format(costo));

            calcularCostoFinal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular costo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularCostoFinal() {
        try {
            double costo = Double.parseDouble(txtCosto.getText().replace(",", ""));
            double descuento = Double.parseDouble(txtDescuento.getText());

            double costoFinal = costo - (costo * descuento / 100);

            DecimalFormat df = new DecimalFormat("#,##0.00");
            txtCostoFinal.setText(df.format(costoFinal));

        } catch (NumberFormatException e) {
            txtCostoFinal.setText("0.00");
        }
    }

    private void guardarEnvio(ActionEvent evt) {
        Cliente remitente = (Cliente) cbRemitente.getSelectedItem();
        Cliente destinatario = (Cliente) cbDestinatario.getSelectedItem();
        String direccionOrigen = txtDireccionOrigen.getText().trim();
        String direccionDestino = txtDireccionDestino.getText().trim();
        String tipoEnvio = (String) cbTipoEnvio.getSelectedItem();

        // Validaciones
        if (remitente == null || destinatario == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar remitente y destinatario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (direccionOrigen.isEmpty() || direccionDestino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Las direcciones de origen y destino son obligatorias.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (remitente != null && destinatario != null && remitente.getId() == destinatario.getId()) {
            JOptionPane.showMessageDialog(this, "El remitente y destinatario no pueden ser la misma persona.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Envio envio = new Envio();
            envio.setNumeroSeguimiento(txtNumeroSeguimiento.getText());
            envio.setRemitenteId(remitente.getId());
            envio.setDestinatarioId(destinatario.getId());
            envio.setDireccionOrigen(direccionOrigen);
            envio.setDireccionDestino(direccionDestino);
            envio.setTipoEnvio(tipoEnvio);
            envio.setEstadoActual("REGISTRADO");
            envio.setFechaCreacion(new Date());
            envio.setFechaEntregaEstimada((Date) spnFechaEntrega.getValue());

            double costo = Double.parseDouble(txtCosto.getText().replace(",", ""));
            double descuento = Double.parseDouble(txtDescuento.getText());
            double costoFinal = Double.parseDouble(txtCostoFinal.getText().replace(",", ""));

            envio.setCosto(costo);
            envio.setDescuentoAplicado(descuento);
            envio.setCostoFinal(costoFinal);

            String prioridadStr = (String) cbPrioridad.getSelectedItem();
            int prioridad = Integer.parseInt(prioridadStr.substring(0, 1));
            envio.setPrioridad(prioridad);

            envio.setObservaciones(txtObservaciones.getText());

            envioController.insertar(envio);

            JOptionPane.showMessageDialog(this,
                    "Envío creado exitosamente.\nNúmero de seguimiento: " + envio.getNumeroSeguimiento(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

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
        txtCosto.setText("0.00");
        txtDescuento.setText("0.0");
        txtCostoFinal.setText("0.00");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        spnFechaEntrega.setValue(cal.getTime());

        txtObservaciones.setText("");
    }
}

// Clase interna para mostrar clientes en ComboBox
class ClienteComboRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Cliente) {
            Cliente cliente = (Cliente) value;
            setText(cliente.getCodigoCliente() + " - " + cliente.getNombre() + " " + cliente.getApellidos());
        } else if (value == null) {
            setText("-- Seleccionar Cliente --");
        }

        return this;
    }
}
