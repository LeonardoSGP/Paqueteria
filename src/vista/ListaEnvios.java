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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaEnvios extends JPanel {
    
    private JTable tablaEnvios;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JButton btnRefrescar, btnBuscar, btnSeguimiento;
    private JComboBox<String> cbFiltroEstado;
    private JTextField txtBuscarNumero;
    private EnvioController envioController;
    private ClienteController clienteController;
    
    public ListaEnvios() {
        this.envioController = new EnvioController();
        this.clienteController = new ClienteController();
        initComponents();
        cargarEnvios();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel superior con título y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel lblTitulo = new JLabel("🚚 Lista de Envíos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 123, 255));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar por número:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscarNumero = new JTextField(15);
        txtBuscarNumero.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblFiltro = new JLabel("Filtrar por estado:");
        lblFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
        cbFiltroEstado = new JComboBox<>(new String[]{
            "TODOS", "REGISTRADO", "EN_PROCESO", "EN_TRANSITO", 
            "EN_REPARTO", "ENTREGADO", "DEVUELTO", "CANCELADO"
        });
        cbFiltroEstado.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar.setBackground(new Color(23, 162, 184));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> filtrarEnvios());
        
        panelFiltros.add(lblBuscar);
        panelFiltros.add(txtBuscarNumero);
        panelFiltros.add(Box.createHorizontalStrut(20));
        panelFiltros.add(lblFiltro);
        panelFiltros.add(cbFiltroEstado);
        panelFiltros.add(btnBuscar);
        
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla de envíos
        String[] columnas = {"ID", "Núm. Seguimiento", "Remitente", "Destinatario", "Tipo", "Estado", "Fecha Creación", "Fecha Estimada", "Costo", "Prioridad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEnvios = new JTable(modeloTabla);
        tablaEnvios.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaEnvios.setRowHeight(25);
        tablaEnvios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEnvios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar anchos de columnas
        tablaEnvios.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tablaEnvios.getColumnModel().getColumn(1).setPreferredWidth(120);  // Núm. Seguimiento
        tablaEnvios.getColumnModel().getColumn(2).setPreferredWidth(150);  // Remitente
        tablaEnvios.getColumnModel().getColumn(3).setPreferredWidth(150);  // Destinatario
        tablaEnvios.getColumnModel().getColumn(4).setPreferredWidth(80);   // Tipo
        tablaEnvios.getColumnModel().getColumn(5).setPreferredWidth(100);  // Estado
        tablaEnvios.getColumnModel().getColumn(6).setPreferredWidth(100);  // Fecha Creación
        tablaEnvios.getColumnModel().getColumn(7).setPreferredWidth(100);  // Fecha Estimada
        tablaEnvios.getColumnModel().getColumn(8).setPreferredWidth(80);   // Costo
        tablaEnvios.getColumnModel().getColumn(9).setPreferredWidth(70);   // Prioridad
        
        scrollPane = new JScrollPane(tablaEnvios);
        scrollPane.setPreferredSize(new Dimension(1200, 400));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefrescar.setBackground(new Color(40, 167, 69));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarEnvios());
        
        btnSeguimiento = new JButton("📍 Ver Seguimiento");
        btnSeguimiento.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSeguimiento.setBackground(new Color(255, 193, 7));
        btnSeguimiento.setForeground(Color.BLACK);
        btnSeguimiento.addActionListener(e -> verSeguimiento());
        
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnSeguimiento);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarEnvios() {
        modeloTabla.setRowCount(0);
        
        try {
            List<Envio> envios = envioController.listar();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (Envio envio : envios) {
                // Obtener datos de clientes
                Cliente remitente = clienteController.obtenerPorId(envio.getRemitenteId());
                Cliente destinatario = clienteController.obtenerPorId(envio.getDestinatarioId());
                
                String nombreRemitente = remitente != null ? 
                    remitente.getNombre() + " " + remitente.getApellidos() : "Cliente no encontrado";
                String nombreDestinatario = destinatario != null ? 
                    destinatario.getNombre() + " " + destinatario.getApellidos() : "Cliente no encontrado";
                
                String prioridadStr = switch (envio.getPrioridad()) {
                    case 1 -> "Alta";
                    case 2 -> "Media";
                    case 3 -> "Baja";
                    default -> "N/A";
                };
                
                Object[] fila = {
                    envio.getId(),
                    envio.getNumeroSeguimiento(),
                    nombreRemitente,
                    nombreDestinatario,
                    envio.getTipoEnvio(),
                    envio.getEstadoActual(),
                    envio.getFechaCreacion() != null ? sdf.format(envio.getFechaCreacion()) : "N/A",
                    envio.getFechaEntregaEstimada() != null ? sdf.format(envio.getFechaEntregaEstimada()) : "N/A",
                    "$" + df.format(envio.getCostoFinal()),
                    prioridadStr
                };
                
                modeloTabla.addRow(fila);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar envíos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarEnvios() {
        String numeroSeguimiento = txtBuscarNumero.getText().trim();
        String estadoSeleccionado = (String) cbFiltroEstado.getSelectedItem();
        
        modeloTabla.setRowCount(0);
        
        try {
            List<Envio> envios;
            
            if (!numeroSeguimiento.isEmpty()) {
                // Buscar por número específico
                Envio envio = envioController.obtenerPorNumeroSeguimiento(numeroSeguimiento);
                envios = envio != null ? List.of(envio) : List.of();
            } else if (!"TODOS".equals(estadoSeleccionado)) {
                // Filtrar por estado
                envios = envioController.listarPorEstado(estadoSeleccionado);
            } else {
                // Mostrar todos
                envios = envioController.listar();
            }
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (Envio envio : envios) {
                Cliente remitente = clienteController.obtenerPorId(envio.getRemitenteId());
                Cliente destinatario = clienteController.obtenerPorId(envio.getDestinatarioId());
                
                String nombreRemitente = remitente != null ? 
                    remitente.getNombre() + " " + remitente.getApellidos() : "Cliente no encontrado";
                String nombreDestinatario = destinatario != null ? 
                    destinatario.getNombre() + " " + destinatario.getApellidos() : "Cliente no encontrado";
                
                String prioridadStr = switch (envio.getPrioridad()) {
                    case 1 -> "Alta";
                    case 2 -> "Media";
                    case 3 -> "Baja";
                    default -> "N/A";
                };
                
                Object[] fila = {
                    envio.getId(),
                    envio.getNumeroSeguimiento(),
                    nombreRemitente,
                    nombreDestinatario,
                    envio.getTipoEnvio(),
                    envio.getEstadoActual(),
                    envio.getFechaCreacion() != null ? sdf.format(envio.getFechaCreacion()) : "N/A",
                    envio.getFechaEntregaEstimada() != null ? sdf.format(envio.getFechaEntregaEstimada()) : "N/A",
                    "$" + df.format(envio.getCostoFinal()),
                    prioridadStr
                };
                
                modeloTabla.addRow(fila);
            }
            
            if (envios.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron envíos con los criterios especificados.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al filtrar envíos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void verSeguimiento() {
        int filaSeleccionada = tablaEnvios.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecciona un envío para ver su seguimiento.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String numeroSeguimiento = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        // Aquí puedes abrir una ventana de seguimiento o mostrar información adicional
        JOptionPane.showMessageDialog(this,
            "Funcionalidad de seguimiento para envío " + numeroSeguimiento + " en desarrollo.",
            "Seguimiento", JOptionPane.INFORMATION_MESSAGE);
    }
}