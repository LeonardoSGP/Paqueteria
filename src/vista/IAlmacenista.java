package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal para el rol de ALMACENISTA
 */
public class IAlmacenista extends JPanel {

    private JPanel panelMenu, panelContenido;

    public IAlmacenista() {
        setLayout(new BorderLayout());

        // ===== Panel lateral (menú) =====
        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(new Color(33, 47, 61));
        panelMenu.setPreferredSize(new Dimension(200, 0));

        JLabel lblTitulo = new JLabel("Almacén", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panelMenu.add(lblTitulo);

        // Botones del menú
        JButton btnInventario = crearBotonMenu("📦 Inventario");
        JButton btnRecepcion = crearBotonMenu("📥 Recepción");
        JButton btnMovimientos = crearBotonMenu("🔁 Movimientos");
        JButton btnReportes = crearBotonMenu("📊 Reportes");

        panelMenu.add(btnInventario);
        panelMenu.add(btnRecepcion);
        panelMenu.add(btnMovimientos);
        panelMenu.add(btnReportes);

        // Empujar el botón de cerrar sesión al fondo
        panelMenu.add(Box.createVerticalGlue());

        JButton btnCerrarSesion = crearBotonCerrarSesion();
        panelMenu.add(btnCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        // ===== Panel central con CardLayout =====
        panelContenido = new JPanel(new CardLayout());

        JPanel panelInicio = new JPanel();
        panelInicio.setBackground(Color.WHITE);
        panelInicio.add(new JLabel("Bienvenido, Almacenista"));
        panelContenido.add(panelInicio, "INICIO");

        JPanel panelInventario = new JPanel(new BorderLayout());
        panelInventario.setBackground(Color.WHITE);
        panelInventario.add(new JLabel("Inventario (tabla con existencias y búsqueda)"), BorderLayout.NORTH);
        panelContenido.add(panelInventario, "INVENTARIO");

        JPanel panelRecepcion = new JPanel(new BorderLayout());
        panelRecepcion.setBackground(Color.WHITE);
        panelRecepcion.add(new JLabel("Recepción de paquetes (formulario de escaneo/registro)"), BorderLayout.NORTH);
        panelContenido.add(panelRecepcion, "RECEPCION");

        JPanel panelMovs = new JPanel(new BorderLayout());
        panelMovs.setBackground(Color.WHITE);
        panelMovs.add(new JLabel("Movimientos (entradas/salidas, ajustes)"), BorderLayout.NORTH);
        panelContenido.add(panelMovs, "MOVIMIENTOS");

        JPanel panelReportes = new JPanel(new BorderLayout());
        panelReportes.setBackground(Color.WHITE);
        panelReportes.add(new JLabel("Reportes de almacén (resúmenes/descargas)"), BorderLayout.NORTH);
        panelContenido.add(panelReportes, "REPORTES");

        add(panelContenido, BorderLayout.CENTER);

        // Acciones
        btnInventario.addActionListener(e -> mostrarVista("INVENTARIO"));
        btnRecepcion.addActionListener(e -> mostrarVista("RECEPCION"));
        btnMovimientos.addActionListener(e -> mostrarVista("MOVIMIENTOS"));
        btnReportes.addActionListener(e -> mostrarVista("REPORTES"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));
        btn.setBackground(new Color(46, 134, 193));
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
