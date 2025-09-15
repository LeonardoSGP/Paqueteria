/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Diego Quiroga
 */
import controlador.ClienteController;
import modelo.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialClienteEnvios extends JPanel {
    
    private JComboBox<String> cbClientes;
    private JTable tablaEnvios;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JButton btnBuscar, btnRefrescar;
    private ClienteController clienteController;
    
    public HistorialClienteEnvios() {
        this.clienteController = new ClienteController();
        initComponents();
        cargarClientes();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Panel superior
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Historial de Envíos de Clientes"));
        
        JLabel lblTitulo = new JLabel("Historial de Envíos por Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout());
        panelFiltros.setBackground(Color.WHITE);
        
        JLabel lblCliente = new JLabel("Seleccionar Cliente:");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        
        cbClientes = new JComboBox<>();
        cbClientes.setPreferredSize(new Dimension(250, 30));
        cbClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscar = new JButton("🔍 Buscar Envíos");
        btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar.setBackground(new Color(0, 123, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.addActionListener(e -> buscarEnvios());
        
        btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefrescar.setBackground(new Color(40, 167, 69));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarClientes());
        
        panelFiltros.add(lblCliente);
        panelFiltros.add(cbClientes);
        panelFiltros.add(btnBuscar);
        panelFiltros.add(btnRefrescar);
        
        panelSuperior.add(lblTitulo);
        
        JPanel panelCompleto = new JPanel(new BorderLayout());
        panelCompleto.setBackground(Color.WHITE);
        panelCompleto.add(lblTitulo, BorderLayout.NORTH);
        panelCompleto.add(panelFiltros, BorderLayout.CENTER);
        
        add(panelCompleto, BorderLayout.NORTH);
        
        // Tabla de envíos
        String[] columnas = {"ID Envío", "Número Seguimiento", "Destinatario", "Fecha Creación", "Estado", "Costo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEnvios = new JTable(modeloTabla);
        tablaEnvios.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaEnvios.setRowHeight(25);
        tablaEnvios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        scrollPane = new JScrollPane(tablaEnvios);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con información
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.WHITE);
        panelInferior.add(new JLabel("💡 Selecciona un cliente para ver su historial de envíos"));
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cargarClientes() {
        cbClientes.removeAllItems();
        cbClientes.addItem("-- Seleccionar Cliente --");
        
        try {
            List<Cliente> clientes = clienteController.listarActivos();
            
            for (Cliente cliente : clientes) {
                String item = cliente.getCodigoCliente() + " - " + 
                             cliente.getNombre() + " " + cliente.getApellidos();
                cbClientes.addItem(item);
            }
            
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay clientes registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar clientes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarEnvios() {
        if (cbClientes.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un cliente", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        String clienteSeleccionado = (String) cbClientes.getSelectedItem();
        String codigoCliente = clienteSeleccionado.split(" - ")[0];
        
        try {
            // Por ahora simulamos datos - aquí irían los envíos reales
            // Necesitarías crear EnvioController y obtener envíos por cliente
            
            // Datos simulados para demostración
            Object[][] datosSimulados = {
                {"001", "ENV001234", "Juan Pérez López", "2025-09-01", "ENTREGADO", "$150.00"},
                {"002", "ENV001235", "María González", "2025-09-10", "EN_TRANSITO", "$200.00"},
                {"003", "ENV001236", "Carlos Ruiz", "2025-09-15", "REGISTRADO", "$75.50"}
            };
            
            for (Object[] fila : datosSimulados) {
                modeloTabla.addRow(fila);
            }
            
            if (datosSimulados.length == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron envíos para este cliente", 
                    "Sin resultados", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Se encontraron " + datosSimulados.length + " envíos", 
                    "Resultados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar envíos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
