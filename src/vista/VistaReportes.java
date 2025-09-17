package vista;

import controlador.ReporteController;
import modelo.Reporte;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class VistaReportes extends JPanel {

    private JComboBox<String> cbTipoReporte;
    private JDateChooser fechaInicio, fechaFin;
    private JButton btnGenerar, btnLimpiar, btnExportar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextArea txtResultados;
    private JPanel panelResultados;
    private CardLayout cardLayout;

    private final ReporteController reporteController;
    private final SimpleDateFormat dateFormat;
    private final DecimalFormat decimalFormat;

    public VistaReportes() {
        this.reporteController = new ReporteController();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.decimalFormat = new DecimalFormat("#,##0.00");

        setLayout(new BorderLayout());
        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // === Panel de controles superior ===
        JPanel panelControles = new JPanel(new GridBagLayout());
        panelControles.setBorder(BorderFactory.createTitledBorder("Generación de Reportes"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de reporte
        gbc.gridx = 0; gbc.gridy = 0;
        panelControles.add(new JLabel("Tipo de Reporte:"), gbc);

        cbTipoReporte = new JComboBox<>(new String[]{
            "Seleccionar...",
            "Ingresos por Período",
            "Clientes Frecuentes",
            "Rendimiento Repartidores",
            "Estados de Envíos",
            "Estadísticas Generales"
        });
        gbc.gridx = 1;
        panelControles.add(cbTipoReporte, gbc);

        // Fecha inicio
        gbc.gridx = 2; gbc.gridy = 0;
        panelControles.add(new JLabel("Fecha Inicio:"), gbc);

        fechaInicio = new JDateChooser();
        fechaInicio.setDateFormatString("dd/MM/yyyy");
        fechaInicio.setPreferredSize(new Dimension(120, 25));
        gbc.gridx = 3;
        panelControles.add(fechaInicio, gbc);

        // Fecha fin
        gbc.gridx = 4; gbc.gridy = 0;
        panelControles.add(new JLabel("Fecha Fin:"), gbc);

        fechaFin = new JDateChooser();
        fechaFin.setDateFormatString("dd/MM/yyyy");
        fechaFin.setPreferredSize(new Dimension(120, 25));
        gbc.gridx = 5;
        panelControles.add(fechaFin, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 6;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(51, 153, 255));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFont(new Font("Tahoma", Font.BOLD, 12));

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(new Color(255, 153, 51));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Tahoma", Font.BOLD, 12));

        btnExportar = new JButton("Exportar PDF");
        btnExportar.setBackground(new Color(34, 139, 34));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnExportar.setEnabled(false);

        panelBotones.add(btnGenerar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnExportar);
        panelControles.add(panelBotones, gbc);

        add(panelControles, BorderLayout.NORTH);

        // === Panel de resultados con CardLayout ===
        cardLayout = new CardLayout();
        panelResultados = new JPanel(cardLayout);

        // Tarjeta de tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        String[] columnas = {"Concepto", "Valor", "Detalles"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);

        // Renderer personalizado para la tabla
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(240, 248, 255));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }

                setHorizontalAlignment(column == 1 ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return c;
            }
        });

        tabla.setRowHeight(25);
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados"));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        // Tarjeta de texto
        JPanel panelTexto = new JPanel(new BorderLayout());
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        txtResultados.setBackground(new Color(245, 245, 245));
        JScrollPane scrollTexto = new JScrollPane(txtResultados);
        scrollTexto.setBorder(BorderFactory.createTitledBorder("Resumen del Reporte"));
        panelTexto.add(scrollTexto, BorderLayout.CENTER);

        panelResultados.add(panelTabla, "TABLA");
        panelResultados.add(panelTexto, "TEXTO");

        add(panelResultados, BorderLayout.CENTER);

        // Mostrar panel de tabla por defecto
        cardLayout.show(panelResultados, "TABLA");
    }

    private void configurarEventos() {
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarReporte();
            }
        });
    }

    private void generarReporte() {
        String tipoReporte = (String) cbTipoReporte.getSelectedItem();

        if ("Seleccionar...".equals(tipoReporte)) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de reporte.");
            return;
        }

        Date inicio = fechaInicio.getDate();
        Date fin = fechaFin.getDate();

        // Validar fechas para algunos reportes
        if (requiereFechas(tipoReporte) && (inicio == null || fin == null)) {
            JOptionPane.showMessageDialog(this, "Seleccione las fechas de inicio y fin.");
            return;
        }

        if (inicio != null && fin != null && inicio.after(fin)) {
            JOptionPane.showMessageDialog(this, "La fecha de inicio debe ser anterior a la fecha fin.");
            return;
        }

        try {
            btnGenerar.setEnabled(false);
            btnGenerar.setText("Generando...");

            switch (tipoReporte) {
                case "Ingresos por Período":
                    generarReporteIngresos(inicio, fin);
                    break;
                case "Clientes Frecuentes":
                    generarReporteClientesFrecuentes();
                    break;
                case "Rendimiento Repartidores":
                    generarReporteRepartidores(inicio, fin);
                    break;
                case "Estados de Envíos":
                    generarReporteEstadosEnvios(inicio, fin);
                    break;
                case "Estadísticas Generales":
                    generarEstadisticasGenerales(inicio, fin);
                    break;
            }

            btnExportar.setEnabled(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            btnGenerar.setEnabled(true);
            btnGenerar.setText("Generar Reporte");
        }
    }

    private boolean requiereFechas(String tipoReporte) {
        return !tipoReporte.equals("Clientes Frecuentes");
    }

    private void generarReporteIngresos(Date inicio, Date fin) {
        Map<String, Object> datos = reporteController.generarReporteIngresos(inicio, fin);

        modeloTabla.setRowCount(0);
        modeloTabla.addRow(new Object[]{"Total de Envíos", datos.get("totalEnvios"), "Envíos procesados en el período"});
        modeloTabla.addRow(new Object[]{"Ingresos Totales", "$" + decimalFormat.format(datos.get("totalIngresos")), "Suma de costos finales"});
        modeloTabla.addRow(new Object[]{"Costo Promedio", "$" + decimalFormat.format(datos.get("promedioCosto")), "Promedio por envío"});
        modeloTabla.addRow(new Object[]{"Costo Mínimo", "$" + decimalFormat.format(datos.get("costoMinimo")), "Envío más económico"});
        modeloTabla.addRow(new Object[]{"Costo Máximo", "$" + decimalFormat.format(datos.get("costoMaximo")), "Envío más costoso"});

        cardLayout.show(panelResultados, "TABLA");
    }

    private void generarReporteClientesFrecuentes() {
        List<Map<String, Object>> datos = reporteController.generarReporteClientesFrecuentes(10);

        modeloTabla.setRowCount(0);
        for (Map<String, Object> cliente : datos) {
            String nombre = cliente.get("nombre") + " " + cliente.get("apellidos");
            String codigo = (String) cliente.get("codigoCliente");
            Integer envios = (Integer) cliente.get("totalEnvios");
            Object total = cliente.get("totalGastado");

            modeloTabla.addRow(new Object[]{
                nombre,
                envios + " envíos",
                "Código: " + codigo + " | Total gastado: $" + decimalFormat.format(total)
            });
        }

        cardLayout.show(panelResultados, "TABLA");
    }

    private void generarReporteRepartidores(Date inicio, Date fin) {
        List<Map<String, Object>> datos = reporteController.generarReporteRendimientoRepartidores(inicio, fin);

        modeloTabla.setRowCount(0);
        for (Map<String, Object> repartidor : datos) {
            String nombre = repartidor.get("nombre") + " " + repartidor.get("apellidos");
            String numero = (String) repartidor.get("numeroEmpleado");
            Integer envios = (Integer) repartidor.get("enviosRealizados");
            Integer exitosos = (Integer) repartidor.get("enviosExitosos");
            Object calificacion = repartidor.get("calificacionPromedio");

            modeloTabla.addRow(new Object[]{
                nombre,
                envios + " envíos",
                "Núm: " + numero + " | Exitosos: " + exitosos + " | Calificación: " +
                (calificacion != null ? decimalFormat.format(calificacion) : "N/A")
            });
        }

        cardLayout.show(panelResultados, "TABLA");
    }

    private void generarReporteEstadosEnvios(Date inicio, Date fin) {
        Map<String, Integer> datos = reporteController.generarReporteEstadosEnvios(inicio, fin);

        modeloTabla.setRowCount(0);
        int total = datos.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            String estado = entry.getKey();
            Integer cantidad = entry.getValue();
            double porcentaje = total > 0 ? (cantidad * 100.0 / total) : 0;

            modeloTabla.addRow(new Object[]{
                estado,
                cantidad + " envíos",
                String.format("%.1f%% del total", porcentaje)
            });
        }

        cardLayout.show(panelResultados, "TABLA");
    }

    private void generarEstadisticasGenerales(Date inicio, Date fin) {
        StringBuilder resumen = new StringBuilder();

        // Obtener diferentes estadísticas
        Map<String, Object> ingresos = reporteController.generarReporteIngresos(inicio, fin);
        List<Map<String, Object>> clientesFrecuentes = reporteController.generarReporteClientesFrecuentes(5);
        Map<String, Integer> estados = reporteController.generarReporteEstadosEnvios(inicio, fin);

        resumen.append("=== RESUMEN EJECUTIVO ===\n");
        resumen.append("Período: ").append(dateFormat.format(inicio)).append(" - ").append(dateFormat.format(fin)).append("\n\n");

        resumen.append("💰 INGRESOS:\n");
        resumen.append("   • Total de envíos: ").append(ingresos.get("totalEnvios")).append("\n");
        resumen.append("   • Ingresos totales: $").append(decimalFormat.format(ingresos.get("totalIngresos"))).append("\n");
        resumen.append("   • Costo promedio: $").append(decimalFormat.format(ingresos.get("promedioCosto"))).append("\n\n");

        resumen.append("👥 TOP 5 CLIENTES:\n");
        int i = 1;
        for (Map<String, Object> cliente : clientesFrecuentes) {
            resumen.append(String.format("   %d. %s %s - %d envíos\n",
                i++,
                cliente.get("nombre"),
                cliente.get("apellidos"),
                cliente.get("totalEnvios")));
        }

        resumen.append("\n📊 ESTADOS DE ENVÍOS:\n");
        int totalEnvios = estados.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : estados.entrySet()) {
            double porcentaje = totalEnvios > 0 ? (entry.getValue() * 100.0 / totalEnvios) : 0;
            resumen.append(String.format("   • %s: %d (%.1f%%)\n",
                entry.getKey(), entry.getValue(), porcentaje));
        }

        txtResultados.setText(resumen.toString());
        cardLayout.show(panelResultados, "TEXTO");
    }

    private void limpiarFormulario() {
        cbTipoReporte.setSelectedIndex(0);
        fechaInicio.setDate(null);
        fechaFin.setDate(null);
        modeloTabla.setRowCount(0);
        txtResultados.setText("");
        btnExportar.setEnabled(false);
        cardLayout.show(panelResultados, "TABLA");
    }

    private void exportarReporte() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("reporte_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                // Aquí se implementaría la exportación a PDF usando iText
                // Por ahora solo mostramos un mensaje
                JOptionPane.showMessageDialog(this,
                    "Funcionalidad de exportación a PDF se implementará próximamente.\n" +
                    "Archivo: " + fileChooser.getSelectedFile().getName());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
        }
    }
}