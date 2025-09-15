package vista;

import controlador.EmpleadoController;
import controlador.UsuarioController;
import modelo.Empleado;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReporteEmpleados extends JPanel {

    private JLabel lblTotales, lblSalarios, lblAntiguedad;
    private JTextArea areaRoles;
    private JTextArea areaSalarios;
    private JTextArea areaAntiguedad;
    private JButton btnExportarPDF, btnExportarExcel;

    public ReporteEmpleados() {
        setLayout(new BorderLayout());

        // Panel superior: resumen general
        JPanel panelTotales = new JPanel(new GridLayout(3, 1));
        panelTotales.setBorder(BorderFactory.createTitledBorder("Resumen General"));
        lblTotales = new JLabel();
        lblSalarios = new JLabel();
        lblAntiguedad = new JLabel();
        lblTotales.setFont(new Font("Arial", Font.BOLD, 14));
        lblSalarios.setFont(new Font("Arial", Font.BOLD, 14));
        lblAntiguedad.setFont(new Font("Arial", Font.BOLD, 14));
        panelTotales.add(lblTotales);
        panelTotales.add(lblSalarios);
        panelTotales.add(lblAntiguedad);

        add(panelTotales, BorderLayout.NORTH);

        // Panel central: detalles
        JPanel panelDetalles = new JPanel(new GridLayout(1, 3, 10, 10));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        areaRoles = new JTextArea();
        areaRoles.setEditable(false);
        areaRoles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panelDetalles.add(new JScrollPane(areaRoles));

        areaSalarios = new JTextArea();
        areaSalarios.setEditable(false);
        areaSalarios.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panelDetalles.add(new JScrollPane(areaSalarios));

        areaAntiguedad = new JTextArea();
        areaAntiguedad.setEditable(false);
        areaAntiguedad.setFont(new Font("Monospaced", Font.PLAIN, 13));
        panelDetalles.add(new JScrollPane(areaAntiguedad));

        add(panelDetalles, BorderLayout.CENTER);

        // Panel lateral derecho: botones de exportación
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Exportar"));

        btnExportarPDF = new JButton("📄 Exportar PDF");
        btnExportarExcel = new JButton("📊 Exportar Excel");

        btnExportarPDF.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExportarExcel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnExportarPDF);
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(btnExportarExcel);

        add(panelBotones, BorderLayout.EAST);

        // Eventos
        btnExportarPDF.addActionListener(e -> exportarPDF());
        btnExportarExcel.addActionListener(e -> exportarExcel());

        cargarDatos();
    }

    private void cargarDatos() {
        EmpleadoController ec = new EmpleadoController();
        UsuarioController uc = new UsuarioController();

        List<Empleado> empleados = ec.listar();

        if (empleados.isEmpty()) {
            lblTotales.setText("No hay empleados registrados.");
            areaRoles.setText("");
            areaSalarios.setText("");
            areaAntiguedad.setText("");
            return;
        }

        // Totales
        long activos = empleados.stream().filter(Empleado::isActivo).count();
        long inactivos = empleados.size() - activos;
        lblTotales.setText("Total: " + empleados.size() +
                " | Activos: " + activos +
                " | Inactivos: " + inactivos);

        // ===== Distribución por roles =====
        Map<String, Long> roles = empleados.stream()
                .map(e -> uc.obtenerPorId(e.getUsuarioId()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        u -> (u.getTipoUsuario() == null || u.getTipoUsuario().isBlank()) ? "SIN_ROL" : u.getTipoUsuario(),
                        Collectors.counting()
                ));

        StringBuilder sbRoles = new StringBuilder("Distribución por Roles:\n");
        roles.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> sbRoles.append(String.format("%-15s: %d%n", entry.getKey(), entry.getValue())));
        areaRoles.setText(sbRoles.toString());

        // ===== Salarios =====
        double promedio = redondear(empleados.stream().mapToDouble(Empleado::getSalarioActual).average().orElse(0));
        double max = redondear(empleados.stream().mapToDouble(Empleado::getSalarioActual).max().orElse(0));
        double min = redondear(empleados.stream().mapToDouble(Empleado::getSalarioActual).min().orElse(0));
        lblSalarios.setText("Salarios → Promedio: $" + promedio +
                " | Máximo: $" + max +
                " | Mínimo: $" + min);

        StringBuilder sbSalarios = new StringBuilder("Rangos Salariales:\n");
        long bajos = empleados.stream().filter(e -> e.getSalarioActual() < 5000).count();
        long medios = empleados.stream().filter(e -> e.getSalarioActual() >= 5000 && e.getSalarioActual() < 10000).count();
        long altos = empleados.stream().filter(e -> e.getSalarioActual() >= 10000).count();
        sbSalarios.append(String.format("%-12s: %d%n", "< $5000", bajos));
        sbSalarios.append(String.format("%-12s: %d%n", "$5000-$9999", medios));
        sbSalarios.append(String.format("%-12s: %d%n", ">= $10000", altos));
        areaSalarios.setText(sbSalarios.toString());

        // ===== Antigüedad =====
        List<Empleado> conFecha = empleados.stream()
                .filter(e -> e.getFechaIngreso() != null)
                .toList();

        long promedioMeses = 0;
        Empleado masAntiguo = null;

        if (!conFecha.isEmpty()) {
            promedioMeses = Math.round(conFecha.stream()
                    .mapToLong(e -> {
                        LocalDate ingreso = e.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return ChronoUnit.MONTHS.between(ingreso, LocalDate.now());
                    })
                    .average().orElse(0));

            masAntiguo = conFecha.stream()
                    .min((a, b) -> a.getFechaIngreso().compareTo(b.getFechaIngreso()))
                    .orElse(null);
        }

        lblAntiguedad.setText("Antigüedad → Promedio: " + (promedioMeses / 12) + " años");

        StringBuilder sbAntiguedad = new StringBuilder("Antigüedad de empleados:\n");
        sbAntiguedad.append("Promedio: ").append(promedioMeses).append(" meses\n");
        if (masAntiguo != null) {
            sbAntiguedad.append("Más antiguo: ").append(masAntiguo.getNombre())
                    .append(" desde ").append(masAntiguo.getFechaIngreso());
        } else {
            sbAntiguedad.append("No hay fechas de ingreso registradas.");
        }
        areaAntiguedad.setText(sbAntiguedad.toString());
    }

    private double redondear(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    // === Métodos de exportación ===
    private void exportarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("ReporteEmpleados.pdf"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new java.io.FileOutputStream(fileChooser.getSelectedFile()));
                document.open();
                document.add(new Paragraph("Reporte de Empleados\n\n"));
                document.add(new Paragraph(lblTotales.getText()));
                document.add(new Paragraph(lblSalarios.getText()));
                document.add(new Paragraph(lblAntiguedad.getText()));
                document.add(new Paragraph("\n--- Roles ---\n" + areaRoles.getText()));
                document.add(new Paragraph("\n--- Salarios ---\n" + areaSalarios.getText()));
                document.add(new Paragraph("\n--- Antigüedad ---\n" + areaAntiguedad.getText()));
                document.close();
                JOptionPane.showMessageDialog(this, "PDF exportado con éxito.");
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + ex.getMessage());
            }
        }
    }

    private void exportarExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("ReporteEmpleados.csv"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile())) {
                fw.write("Totales;" + lblTotales.getText() + "\n");
                fw.write("Salarios;" + lblSalarios.getText() + "\n");
                fw.write("Antiguedad;" + lblAntiguedad.getText() + "\n\n");

                fw.write("Roles:\n" + areaRoles.getText() + "\n");
                fw.write("Salarios:\n" + areaSalarios.getText() + "\n");
                fw.write("Antigüedad:\n" + areaAntiguedad.getText() + "\n");

                JOptionPane.showMessageDialog(this, "Excel (CSV) exportado con éxito.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al exportar Excel: " + ex.getMessage());
            }
        }
    }
}
