/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Diego Quiroga
 */
import controlador.EnvioController;
import controlador.ClienteController;
import modelo.Envio;
import modelo.Cliente;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class SeguimientoEnvio extends JPanel {
    
    private JTextField txtNumeroSeguimiento;
    private JButton btnBuscar;
    private JPanel panelInformacion;
    private EnvioController envioController;
    private ClienteController clienteController;
    
    public SeguimientoEnvio() {
        this.envioController = new EnvioController();
        this.clienteController = new ClienteController();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel lblTitulo = new JLabel("📍 Seguimiento de Envío", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 123, 255));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel lblNumero = new JLabel("Número de Seguimiento:");
        lblNumero.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtNumeroSeguimiento = new JTextField(20);
        txtNumeroSeguimiento.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(0, 123, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarEnvio());
        
        panelBusqueda.add(lblNumero);
        panelBusqueda.add(txtNumeroSeguimiento);
        panelBusqueda.add(btnBuscar);
        
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel de información
        panelInformacion = new JPanel();
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
        panelInformacion.setBackground(Color.WHITE);
        panelInformacion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Mensaje inicial
        JLabel lblMensaje = new JLabel("Ingrese un número de seguimiento para buscar el envío.", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMensaje.setForeground(Color.GRAY);
        panelInformacion.add(lblMensaje);
        
        JScrollPane scrollInfo = new JScrollPane(panelInformacion);
        add(scrollInfo, BorderLayout.CENTER);
    }
    
    private void buscarEnvio() {
        String numeroSeguimiento = txtNumeroSeguimiento.getText().trim();
        
        if (numeroSeguimiento.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese un número de seguimiento.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Envio envio = envioController.obtenerPorNumeroSeguimiento(numeroSeguimiento);
            
            if (envio == null) {
                panelInformacion.removeAll();
                JLabel lblNoEncontrado = new JLabel("❌ No se encontró ningún envío con ese número.", SwingConstants.CENTER);
                lblNoEncontrado.setFont(new Font("Arial", Font.BOLD, 16));
                lblNoEncontrado.setForeground(Color.RED);
                panelInformacion.add(lblNoEncontrado);
                panelInformacion.revalidate();
                panelInformacion.repaint();
                return;
            }
            
            // Mostrar información del envío
            mostrarInformacionEnvio(envio);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al buscar el envío: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarInformacionEnvio(Envio envio) {
        panelInformacion.removeAll();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        // Obtener clientes
        Cliente remitente = clienteController.obtenerPorId(envio.getRemitenteId());
        Cliente destinatario = clienteController.obtenerPorId(envio.getDestinatarioId());
        
        // Título del envío
        JLabel lblEnvioTitulo = new JLabel("📦 Información del Envío: " + envio.getNumeroSeguimiento(), SwingConstants.CENTER);
        lblEnvioTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblEnvioTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelInformacion.add(lblEnvioTitulo);
        
        // Estado actual
        JLabel lblEstado = new JLabel("🔄 Estado Actual: " + envio.getEstadoActual(), SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstado.setForeground(obtenerColorEstado(envio.getEstadoActual()));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelInformacion.add(lblEstado);
        
        // Panel de detalles
        JPanel panelDetalles = new JPanel(new GridBagLayout());
        panelDetalles.setBackground(Color.WHITE);
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Envío"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Información básica
        agregarDetalle(panelDetalles, gbc, 0, "Tipo de Envío:", envio.getTipoEnvio());
        agregarDetalle(panelDetalles, gbc, 1, "Prioridad:", obtenerPrioridadTexto(envio.getPrioridad()));
        agregarDetalle(panelDetalles, gbc, 2, "Costo Final:", String.format("$%.2f", envio.getCostoFinal()));
        agregarDetalle(panelDetalles, gbc, 3, "Fecha Creación:", 
                       envio.getFechaCreacion() != null ? sdf.format(envio.getFechaCreacion()) : "N/A");
        agregarDetalle(panelDetalles, gbc, 4, "Fecha Est. Entrega:", 
                       envio.getFechaEntregaEstimada() != null ? sdf.format(envio.getFechaEntregaEstimada()) : "N/A");
        
        if (envio.getFechaEntregaReal() != null) {
            agregarDetalle(panelDetalles, gbc, 5, "Fecha Real Entrega:", sdf.format(envio.getFechaEntregaReal()));
        }
        
        panelInformacion.add(panelDetalles);
        
        // Panel de clientes
        JPanel panelClientes = new JPanel(new GridBagLayout());
        panelClientes.setBackground(Color.WHITE);
        panelClientes.setBorder(BorderFactory.createTitledBorder("Información de Clientes"));
        
        GridBagConstraints gbcClientes = new GridBagConstraints();
        gbcClientes.anchor = GridBagConstraints.WEST;
        gbcClientes.insets = new Insets(5, 10, 5, 10);
        
        String nombreRemitente = remitente != null ? 
            remitente.getNombre() + " " + remitente.getApellidos() : "Cliente no encontrado";
        String nombreDestinatario = destinatario != null ? 
            destinatario.getNombre() + " " + destinatario.getApellidos() : "Cliente no encontrado";
        
        agregarDetalle(panelClientes, gbcClientes, 0, "Remitente:", nombreRemitente);
        agregarDetalle(panelClientes, gbcClientes, 1, "Destinatario:", nombreDestinatario);
        
        panelInformacion.add(panelClientes);
        
        // Panel de direcciones
        JPanel panelDirecciones = new JPanel(new BorderLayout());
        panelDirecciones.setBackground(Color.WHITE);
        panelDirecciones.setBorder(BorderFactory.createTitledBorder("Direcciones"));
        
        JTextArea txtDireccionOrigen = new JTextArea("Origen:\n" + envio.getDireccionOrigen());
        txtDireccionOrigen.setEditable(false);
        txtDireccionOrigen.setBackground(new Color(248, 249, 250));
        txtDireccionOrigen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea txtDireccionDestino = new JTextArea("Destino:\n" + envio.getDireccionDestino());
        txtDireccionDestino.setEditable(false);
        txtDireccionDestino.setBackground(new Color(248, 249, 250));
        txtDireccionDestino.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelDirecciones.add(txtDireccionOrigen, BorderLayout.WEST);
        panelDirecciones.add(txtDireccionDestino, BorderLayout.EAST);
        
        panelInformacion.add(panelDirecciones);
        
        // Observaciones si las hay
        if (envio.getObservaciones() != null && !envio.getObservaciones().trim().isEmpty()) {
            JPanel panelObs = new JPanel(new BorderLayout());
            panelObs.setBackground(Color.WHITE);
            panelObs.setBorder(BorderFactory.createTitledBorder("Observaciones"));
            
            JTextArea txtObservaciones = new JTextArea(envio.getObservaciones());
            txtObservaciones.setEditable(false);
            txtObservaciones.setLineWrap(true);
            txtObservaciones.setWrapStyleWord(true);
            txtObservaciones.setBackground(new Color(248, 249, 250));
            txtObservaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            panelObs.add(txtObservaciones, BorderLayout.CENTER);
            panelInformacion.add(panelObs);
        }
        
        panelInformacion.revalidate();
        panelInformacion.repaint();
    }
    
    private void agregarDetalle(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, String valor) {
        gbc.gridx = 0; gbc.gridy = fila;
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblEtiqueta, gbc);
        
        gbc.gridx = 1;
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblValor, gbc);
    }
    
    private Color obtenerColorEstado(String estado) {
        return switch (estado) {
            case "ENTREGADO" -> new Color(40, 167, 69);
            case "EN_TRANSITO", "EN_REPARTO" -> new Color(255, 193, 7);
            case "CANCELADO", "DEVUELTO" -> new Color(220, 53, 69);
            default -> new Color(0, 123, 255);
        };
    }
    
    private String obtenerPrioridadTexto(int prioridad) {
        return switch (prioridad) {
            case 1 -> "Alta";
            case 2 -> "Media";
            case 3 -> "Baja";
            default -> "N/A";
        };
    }
}
