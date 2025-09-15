/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Diego Quiroga
 */

import controlador.PaqueteController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ReportePaquetes extends JPanel {
    
    private PaqueteController paqueteController;
    private JTabbedPane tabbedPane;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public ReportePaquetes() {
        this.paqueteController = new PaqueteController();
        initComponents();
        cargarTodosLosReportes();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Título principal
        JLabel lblTitulo = new JLabel("📊 Reportes de Paquetes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 123, 255));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Crear pestañas para diferentes reportes
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Pestaña 1: Estadísticas Generales
        tabbedPane.addTab("📈 Estadísticas Generales", crearPanelEstadisticas());
        
        // Pestaña 2: Por Tipo de Contenido
        tabbedPane.addTab("📦 Por Tipo de Contenido", crearPanelPorTipo());
        
        // Pestaña 3: Estados y Características
        tabbedPane.addTab("🔄 Estados y Características", crearPanelEstados());
        
        // Pestaña 4: Reporte Mensual
        tabbedPane.addTab("📅 Reporte Mensual", crearPanelMensual());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel de botones inferior
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnRefrescar = new JButton("🔄 Actualizar Reportes");
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefrescar.setBackground(new Color(40, 167, 69));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.addActionListener(e -> cargarTodosLosReportes());
        
        JButton btnExportar = new JButton("📄 Exportar");
        btnExportar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExportar.setBackground(new Color(108, 117, 125));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de exportación en desarrollo", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnExportar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Crear etiquetas para mostrar estadísticas
        JLabel[] labels = new JLabel[10];
        String[] textos = {
            "Total de Paquetes:", "Peso Total (kg):", "Peso Promedio (kg):", 
            "Peso Máximo (kg):", "Peso Mínimo (kg):", "Valor Total Declarado:", 
            "Valor Promedio:", "Volumen Total (cm³):", "Paquetes Frágiles:", "Paquetes con Seguro:"
        };
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel lblTexto = new JLabel(textos[i]);
            lblTexto.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(lblTexto, gbc);
            
            gbc.gridx = 1;
            labels[i] = new JLabel("Cargando...");
            labels[i].setFont(new Font("Arial", Font.BOLD, 16));
            labels[i].setForeground(new Color(0, 123, 255));
            panel.add(labels[i], gbc);
        }
        
        // Guardar referencias para actualizar
        panel.putClientProperty("labels", labels);
        
        return panel;
    }
    
    private JPanel crearPanelPorTipo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Distribución de Paquetes por Tipo de Contenido");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Tabla para mostrar datos por tipo
        String[] columnas = {"Tipo de Contenido", "Cantidad", "Peso Total (kg)", "Peso Promedio (kg)", "Valor Total", "Volumen Total (cm³)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Guardar referencia para actualizar
        panel.putClientProperty("tabla", tabla);
        
        return panel;
    }
    
    private JPanel crearPanelEstados() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Panel para paquetes activos/inactivos
        JPanel panelActivos = crearPanelEstadisticaSimple("Estado de Paquetes", new String[]{"Activos", "Inactivos"});
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(panelActivos, gbc);
        
        // Panel para paquetes frágiles/normales
        JPanel panelFragiles = crearPanelEstadisticaSimple("Tipo de Paquetes", new String[]{"Frágiles", "Normales"});
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(panelFragiles, gbc);
        
        // Guardar referencias
        panel.putClientProperty("panelActivos", panelActivos);
        panel.putClientProperty("panelFragiles", panelFragiles);
        
        return panel;
    }
    
    private JPanel crearPanelEstadisticaSimple(String titulo, String[] categorias) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.setBackground(Color.WHITE);
        
        JPanel contenido = new JPanel(new GridLayout(categorias.length, 2, 5, 5));
        contenido.setBackground(Color.WHITE);
        
        JLabel[] labels = new JLabel[categorias.length];
        for (int i = 0; i < categorias.length; i++) {
            JLabel lblCategoria = new JLabel(categorias[i] + ":");
            lblCategoria.setFont(new Font("Arial", Font.PLAIN, 14));
            contenido.add(lblCategoria);
            
            labels[i] = new JLabel("0");
            labels[i].setFont(new Font("Arial", Font.BOLD, 14));
            labels[i].setForeground(new Color(0, 123, 255));
            contenido.add(labels[i]);
        }
        
        panel.add(contenido, BorderLayout.CENTER);
        panel.putClientProperty("labels", labels);
        
        return panel;
    }
    
    private JPanel crearPanelMensual() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titulo = new JLabel("Reporte Mensual - Últimos 12 Meses");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        String[] columnas = {"Mes", "Cantidad", "Peso Total (kg)", "Valor Total"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scroll = new JScrollPane(tabla);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Guardar referencia
        panel.putClientProperty("tabla", tabla);
        
        return panel;
    }
    
    private void cargarTodosLosReportes() {
        try {
            cargarEstadisticasGenerales();
            cargarReportePorTipo();
            cargarReporteEstados();
            cargarReporteMensual();
            
            JOptionPane.showMessageDialog(this, "Reportes actualizados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar reportes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarEstadisticasGenerales() {
        Map<String, Object> stats = paqueteController.reporteEstadisticasGenerales();
        
        JPanel panel = (JPanel) tabbedPane.getComponentAt(0);
        JLabel[] labels = (JLabel[]) panel.getClientProperty("labels");
        
        if (labels != null && !stats.isEmpty()) {
            labels[0].setText(stats.get("total_paquetes").toString());
            labels[1].setText(df.format((Double) stats.get("peso_total")));
            labels[2].setText(df.format((Double) stats.get("peso_promedio")));
            labels[3].setText(df.format((Double) stats.get("peso_maximo")));
            labels[4].setText(df.format((Double) stats.get("peso_minimo")));
            labels[5].setText("$" + df.format((Double) stats.get("valor_total")));
            labels[6].setText("$" + df.format((Double) stats.get("valor_promedio")));
            labels[7].setText(df.format((Double) stats.get("volumen_total")));
            labels[8].setText(stats.get("total_fragiles").toString());
            labels[9].setText(stats.get("total_con_seguro").toString());
        }
    }
    
    private void cargarReportePorTipo() {
        List<Map<String, Object>> datos = paqueteController.reportePorTipoContenido();
        
        JPanel panel = (JPanel) tabbedPane.getComponentAt(1);
        JTable tabla = (JTable) panel.getClientProperty("tabla");
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        modelo.setRowCount(0);
        
        for (Map<String, Object> fila : datos) {
            Object[] row = {
                fila.get("tipo_contenido"),
                fila.get("cantidad"),
                df.format((Double) fila.get("peso_total")),
                df.format((Double) fila.get("peso_promedio")),
                "$" + df.format((Double) fila.get("valor_total")),
                df.format((Double) fila.get("volumen_total"))
            };
            modelo.addRow(row);
        }
    }
    
    private void cargarReporteEstados() {
        Map<String, Integer> activos = paqueteController.reportePorEstado();
        Map<String, Integer> fragiles = paqueteController.reportePorFragilidad();
        
        JPanel panel = (JPanel) tabbedPane.getComponentAt(2);
        
        // Actualizar panel de activos
        JPanel panelActivos = (JPanel) panel.getClientProperty("panelActivos");
        JLabel[] labelsActivos = (JLabel[]) panelActivos.getClientProperty("labels");
        if (labelsActivos != null) {
            labelsActivos[0].setText(activos.getOrDefault("Activos", 0).toString());
            labelsActivos[1].setText(activos.getOrDefault("Inactivos", 0).toString());
        }
        
        // Actualizar panel de frágiles
        JPanel panelFragiles = (JPanel) panel.getClientProperty("panelFragiles");
        JLabel[] labelsFragiles = (JLabel[]) panelFragiles.getClientProperty("labels");
        if (labelsFragiles != null) {
            labelsFragiles[0].setText(fragiles.getOrDefault("Frágiles", 0).toString());
            labelsFragiles[1].setText(fragiles.getOrDefault("Normales", 0).toString());
        }
    }
    
    private void cargarReporteMensual() {
        List<Map<String, Object>> datos = paqueteController.reportePorMes();
        
        JPanel panel = (JPanel) tabbedPane.getComponentAt(3);
        JTable tabla = (JTable) panel.getClientProperty("tabla");
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        
        modelo.setRowCount(0);
        
        for (Map<String, Object> fila : datos) {
            Object[] row = {
                fila.get("mes"),
                fila.get("cantidad"),
                df.format((Double) fila.get("peso_total")),
                "$" + df.format((Double) fila.get("valor_total"))
            };
            modelo.addRow(row);
        }
    }
}

