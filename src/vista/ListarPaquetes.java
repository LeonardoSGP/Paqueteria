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
import controlador.PaqueteController;
import modelo.Paquete;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import modelo.Cliente;

public class ListarPaquetes extends JPanel {

    private JTable tablaPaquetes;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JButton btnRefrescar, btnEditar, btnEliminar, btnFiltrar;
    private JComboBox<String> cbFiltroTipo;
    private JTextField txtBuscarCodigo;
    private PaqueteController paqueteController;

    public ListarPaquetes() {
        this.paqueteController = new PaqueteController();
        initComponents();
        cargarPaquetes();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel superior con título y filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblTitulo = new JLabel("📦 Lista de Paquetes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 123, 255));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);

        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);

        JLabel lblBuscar = new JLabel("Buscar por código:");
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscarCodigo = new JTextField(15);
        txtBuscarCodigo.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setFont(new Font("Arial", Font.PLAIN, 14));
        cbFiltroTipo = new JComboBox<>(new String[]{
            "TODOS", "DOCUMENTOS", "ROPA", "ELECTRODOMESTICOS",
            "JUGUETES", "LIBROS", "MEDICAMENTOS", "ALIMENTOS", "OTROS"
        });
        cbFiltroTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        btnFiltrar = new JButton("🔍 Buscar");
        btnFiltrar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFiltrar.setBackground(new Color(23, 162, 184));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.addActionListener(e -> filtrarPaquetes());

        panelFiltros.add(lblBuscar);
        panelFiltros.add(txtBuscarCodigo);
        panelFiltros.add(Box.createHorizontalStrut(20));
        panelFiltros.add(lblFiltro);
        panelFiltros.add(cbFiltroTipo);
        panelFiltros.add(btnFiltrar);

        panelSuperior.add(panelFiltros, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de paquetes
        String[] columnas = {"ID", "Código", "Descripción", "Tipo", "Peso (kg)", "Dimensiones", "Volumen (cm³)", "Frágil", "Seguro", "Valor ($)", "Fecha Creación", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPaquetes = new JTable(modeloTabla);
        tablaPaquetes.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPaquetes.setRowHeight(25);
        tablaPaquetes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaPaquetes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajustar anchos de columnas
        tablaPaquetes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaPaquetes.getColumnModel().getColumn(1).setPreferredWidth(80);  // Código
        tablaPaquetes.getColumnModel().getColumn(2).setPreferredWidth(150); // Descripción
        tablaPaquetes.getColumnModel().getColumn(3).setPreferredWidth(120); // Tipo
        tablaPaquetes.getColumnModel().getColumn(4).setPreferredWidth(80);  // Peso
        tablaPaquetes.getColumnModel().getColumn(5).setPreferredWidth(100); // Dimensiones
        tablaPaquetes.getColumnModel().getColumn(6).setPreferredWidth(100); // Volumen
        tablaPaquetes.getColumnModel().getColumn(7).setPreferredWidth(60);  // Frágil
        tablaPaquetes.getColumnModel().getColumn(8).setPreferredWidth(60);  // Seguro
        tablaPaquetes.getColumnModel().getColumn(9).setPreferredWidth(80);  // Valor
        tablaPaquetes.getColumnModel().getColumn(10).setPreferredWidth(120); // Fecha
        tablaPaquetes.getColumnModel().getColumn(11).setPreferredWidth(80); // Estado

        scrollPane = new JScrollPane(tablaPaquetes);
        scrollPane.setPreferredSize(new Dimension(1200, 400));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);

        btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRefrescar.setBackground(new Color(40, 167, 69));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarPaquetes());

        btnEditar = new JButton("✏️ Editar");
        btnEditar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setForeground(Color.BLACK);
        btnEditar.addActionListener(e -> editarPaquete());

        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarPaquete());

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarPaquetes() {
        modeloTabla.setRowCount(0);

        try {
            List<Paquete> paquetes = paqueteController.listarActivos();
            DecimalFormat df = new DecimalFormat("#,##0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (Paquete paquete : paquetes) {
                String dimensiones = String.format("%.0f×%.0f×%.0f",
                        paquete.getLargo(), paquete.getAncho(), paquete.getAlto());

                double volumen = paquete.calcularVolumen();
                String estado = paquete.isActivo() ? "Activo" : "Inactivo";

                Object[] fila = {
                    paquete.getId(),
                    paquete.getCodigoPaquete(),
                    paquete.getDescripcion(),
                    paquete.getTipoContenido(),
                    df.format(paquete.getPeso()),
                    dimensiones,
                    df.format(volumen),
                    paquete.isFragil() ? "Sí" : "No",
                    paquete.isRequiereSeguro() ? "Sí" : "No",
                    df.format(paquete.getValorDeclarado()),
                    paquete.getFechaCreacion() != null ? sdf.format(paquete.getFechaCreacion()) : "N/A",
                    estado
                };

                modeloTabla.addRow(fila);
            }

            JLabel lblResultados = new JLabel("Total de paquetes: " + paquetes.size());
            lblResultados.setFont(new Font("Arial", Font.BOLD, 14));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar paquetes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarPaquetes() {
        String codigo = txtBuscarCodigo.getText().trim();
        String tipoSeleccionado = (String) cbFiltroTipo.getSelectedItem();

        modeloTabla.setRowCount(0);

        try {
            List<Paquete> paquetes;

            if (!codigo.isEmpty()) {
                // Buscar por código específico
                Paquete paquete = paqueteController.obtenerPorCodigo(codigo);
                paquetes = paquete != null ? List.of(paquete) : List.of();
            } else if (!"TODOS".equals(tipoSeleccionado)) {
                // Filtrar por tipo
                paquetes = paqueteController.buscarPorTipoContenido(tipoSeleccionado);
            } else {
                // Mostrar todos
                paquetes = paqueteController.listarActivos();
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (Paquete paquete : paquetes) {
                String dimensiones = String.format("%.0f×%.0f×%.0f",
                        paquete.getLargo(), paquete.getAncho(), paquete.getAlto());

                double volumen = paquete.calcularVolumen();
                String estado = paquete.isActivo() ? "Activo" : "Inactivo";

                Object[] fila = {
                    paquete.getId(),
                    paquete.getCodigoPaquete(),
                    paquete.getDescripcion(),
                    paquete.getTipoContenido(),
                    df.format(paquete.getPeso()),
                    dimensiones,
                    df.format(volumen),
                    paquete.isFragil() ? "Sí" : "No",
                    paquete.isRequiereSeguro() ? "Sí" : "No",
                    df.format(paquete.getValorDeclarado()),
                    paquete.getFechaCreacion() != null ? sdf.format(paquete.getFechaCreacion()) : "N/A",
                    estado
                };

                modeloTabla.addRow(fila);
            }

            if (paquetes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron paquetes con los criterios especificados.",
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al filtrar paquetes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPaquete() {
        int filaSeleccionada = tablaPaquetes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona un paquete para editar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoPaquete = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        JOptionPane.showMessageDialog(this,
                "Funcionalidad de edición para paquete " + codigoPaquete + " en desarrollo.",
                "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarPaquete() {
        int filaSeleccionada = tablaPaquetes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona un paquete para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoPaquete = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el paquete " + codigoPaquete + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                Long paqueteId = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
                paqueteController.desactivar(paqueteId);

                JOptionPane.showMessageDialog(this,
                        "Paquete eliminado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                cargarPaquetes(); // Refrescar tabla

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar paquete: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }    
}
