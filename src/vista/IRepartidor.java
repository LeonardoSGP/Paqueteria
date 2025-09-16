package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal para el rol de REPARTIDOR
 */
public class IRepartidor extends JPanel {

    private JPanel panelMenu, panelContenido;

    public IRepartidor() {
        setLayout(new BorderLayout());

        // ===== Panel lateral (menú) =====
        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(44, 62, 80));
        panelMenu.setPreferredSize(new Dimension(200, 0));

        JLabel lblTitulo = new JLabel("Repartidor", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelMenu.add(lblTitulo);

        // Botones del menú
        JButton btnEnvios = crearBotonMenu("🚚 Mis Envíos");
        JButton btnHistorial = crearBotonMenu("📜 Historial");
        JButton btnIncidencias = crearBotonMenu("⚠ Reportar Incidencias");

        panelMenu.add(btnEnvios);
        panelMenu.add(btnHistorial);
        panelMenu.add(btnIncidencias);

        // Empujar el botón de cerrar sesión al fondo
        panelMenu.add(Box.createVerticalGlue());

        JButton btnCerrarSesion = crearBotonCerrarSesion();
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        // ===== Panel central =====
        panelContenido = new JPanel(new CardLayout());

        JPanel panelInicio = new JPanel();
        panelInicio.setBackground(Color.WHITE);
        panelInicio.add(new JLabel("Bienvenido, Repartidor"));
        panelContenido.add(panelInicio, "INICIO");

        JPanel panelEnvios = new JPanel(new BorderLayout());
        panelEnvios.setBackground(Color.WHITE);
        panelEnvios.add(new JLabel("Lista de Envíos asignados (aquí va tabla con envíos)"), BorderLayout.NORTH);
        panelContenido.add(panelEnvios, "ENVIOS");

        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBackground(Color.WHITE);
        panelHistorial.add(new JLabel("Historial de entregas (tabla con estados pasados)"), BorderLayout.NORTH);
        panelContenido.add(panelHistorial, "HISTORIAL");

        JPanel panelIncidencias = new JPanel(new BorderLayout());
        panelIncidencias.setBackground(Color.WHITE);
        panelIncidencias.add(new JLabel("Reporte de incidencias (formulario con descripción)"), BorderLayout.NORTH);
        panelContenido.add(panelIncidencias, "INCIDENCIAS");

        add(panelContenido, BorderLayout.CENTER);

        // Acciones
        btnEnvios.addActionListener(e -> mostrarVista("ENVIOS"));
        btnHistorial.addActionListener(e -> mostrarVista("HISTORIAL"));
        btnIncidencias.addActionListener(e -> mostrarVista("INCIDENCIAS"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton crearBotonCerrarSesion() {
        JButton btn = new JButton("⏻ Cerrar sesión");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 36));
        btn.setBackground(new Color(192, 57, 43));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void mostrarVista(String nombre) {
        CardLayout cl = (CardLayout) panelContenido.getLayout();
        cl.show(panelContenido, nombre);
    }

    private void cerrarSesion() {
        Window w = SwingUtilities.getWindowAncestor(this);
        if (w != null) w.dispose();
        SwingUtilities.invokeLater(() -> {
            ILogin login = new ILogin();
            login.setVisible(true);
        });
    }

}
