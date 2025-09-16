package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal para el rol de SUPERVISOR
 */
public class ISupervisor extends JPanel {

    private JPanel panelMenu, panelContenido;

    public ISupervisor() {
        setLayout(new BorderLayout());

        // ===== Panel lateral (menú) =====
        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(33, 45, 62)); // 🔵 mismo fondo que Repartidor y Almacenista
        panelMenu.setPreferredSize(new Dimension(220, 0));

        JLabel lblTitulo = new JLabel("Supervisor", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelMenu.add(lblTitulo);

        // Botones del menú
        JButton btnEmpleados = crearBotonMenu("👤 Gestionar Empleados");
        JButton btnTiendas = crearBotonMenu("🏬 Gestionar Tiendas");
        JButton btnZonas = crearBotonMenu("🗺 Supervisar Zonas");
        JButton btnReportes = crearBotonMenu("📊 Generar Reportes");

        panelMenu.add(btnEmpleados);
        panelMenu.add(btnTiendas);
        panelMenu.add(btnZonas);
        panelMenu.add(btnReportes);

        // Empujar el botón de cerrar sesión al fondo
        panelMenu.add(Box.createVerticalGlue());

        JButton btnCerrarSesion = crearBotonCerrarSesion();
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        // ===== Panel central con CardLayout =====
        panelContenido = new JPanel(new CardLayout());
        panelContenido.setBackground(Color.WHITE);

        panelContenido.add(new JLabel("Bienvenido Supervisor", SwingConstants.CENTER), "HOME");
        panelContenido.add(new VerEmpleados(), "EMPLEADOS");
        panelContenido.add(new VerTiendas(), "TIENDAS");
        panelContenido.add(new VerZonas(), "ZONAS");
        panelContenido.add(new JLabel("Módulo de Reportes en construcción", SwingConstants.CENTER), "REPORTES");

        add(panelContenido, BorderLayout.CENTER);

        // Acciones de botones
        btnEmpleados.addActionListener(e -> mostrarVista("EMPLEADOS"));
        btnTiendas.addActionListener(e -> mostrarVista("TIENDAS"));
        btnZonas.addActionListener(e -> mostrarVista("ZONAS"));
        btnReportes.addActionListener(e -> mostrarVista("REPORTES"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setBackground(new Color(52, 152, 219)); // 🔵 mismo azul de los otros roles
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton crearBotonCerrarSesion() {
        JButton btn = new JButton("⏻ Cerrar sesión");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 36));
        btn.setBackground(new Color(192, 57, 43)); // 🔴 rojo
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
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
