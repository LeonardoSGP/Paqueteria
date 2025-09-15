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

public class ListarClientes extends JPanel {
    
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JButton btnRefrescar, btnEditar, btnEliminar;
    private ClienteController clienteController;
    
    public ListarClientes() {
        this.clienteController = new ClienteController();
        initComponents();
        cargarClientes();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Lista de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Código", "Nombre", "Apellidos", "Teléfono", "Tipo Cliente", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefrescar.setBackground(new Color(40, 167, 69));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarClientes());
        
        btnEditar = new JButton("✏️ Editar");
        btnEditar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.addActionListener(e -> editarCliente());
        
        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarCliente());
        
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarClientes() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        try {
            List<Cliente> clientes = clienteController.listarActivos();
            
            for (Cliente cliente : clientes) {
                String tipoCliente = obtenerNombreTipoCliente(cliente.getTipoClienteId());
                String estado = cliente.isActivo() ? "Activo" : "Inactivo";
                
                Object[] fila = {
                    cliente.getId(),
                    cliente.getCodigoCliente(),
                    cliente.getNombre(),
                    cliente.getApellidos(),
                    cliente.getTelefono(),
                    tipoCliente,
                    estado
                };
                
                modeloTabla.addRow(fila);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Se cargaron " + clientes.size() + " clientes", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar clientes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String obtenerNombreTipoCliente(Integer tipoId) {
        if (tipoId == null) return "Sin tipo";
        
        switch (tipoId) {
            case 1: return "Regular";
            case 2: return "Premium";
            case 3: return "Corporativo";
            case 4: return "Mayorista";
            default: return "Desconocido";
        }
    }
    
    private void editarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un cliente para editar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long clienteId = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        String codigoCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad de edición para cliente " + codigoCliente + " en desarrollo", 
            "En desarrollo", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, selecciona un cliente para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigoCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar el cliente " + codigoCliente + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Long clienteId = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                clienteController.desactivar(clienteId);
                
                JOptionPane.showMessageDialog(this, 
                    "Cliente eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                cargarClientes(); // Refrescar tabla
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar cliente: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
