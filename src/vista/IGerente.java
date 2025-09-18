package vista;

import java.awt.BorderLayout;
import modelo.Button;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class IGerente extends javax.swing.JPanel {

    private JPanel submenuEmpleados;
    private JPanel submenuClientes;
    private JPanel submenuEnvios;
    private JPanel submenuPaquetes;
    private JPanel submenuRepartidores;
    private JPanel submenuVehiculos;
    private JPanel submenuTiendas;
    private JPanel submenuZonas;
    private JPanel submenuReportes;
    private EmpleadoForm empleadoForm;

    public IGerente() {
        initComponents();
        configurarMenu();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP_principal = new javax.swing.JPanel();
        encabezado = new javax.swing.JPanel();
        izq = new javax.swing.JPanel();
        button1 = new modelo.Button();
        der = new javax.swing.JPanel(){

        };
        panel_menu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnEmpleados = new modelo.Button();
        btnClientes = new modelo.Button();
        btnEnvios = new modelo.Button();
        btnPaquetes = new modelo.Button();
        btnRepartidores = new modelo.Button();
        btnVehiculos = new modelo.Button();
        btnTiendas = new modelo.Button();
        btnZonas = new modelo.Button();
        btnReportes = new modelo.Button();
        button2 = new modelo.Button();
        Panel_Principal = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1180, 700));

        JP_principal.setBackground(new java.awt.Color(255, 255, 255));
        JP_principal.setLayout(new java.awt.BorderLayout());

        encabezado.setBackground(new java.awt.Color(255, 153, 51));
        encabezado.setPreferredSize(new java.awt.Dimension(1180, 100));
        encabezado.setLayout(new java.awt.BorderLayout());

        izq.setBackground(new java.awt.Color(255, 153, 51));
        izq.setPreferredSize(new java.awt.Dimension(200, 100));
        izq.setLayout(new java.awt.BorderLayout());

        button1.setBackground(new java.awt.Color(255, 153, 51));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/menu.png"))); // NOI18N
        button1.setColorNormal(new java.awt.Color(255, 153, 51));
        button1.setColorPressed(new java.awt.Color(255, 153, 51));
        button1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
            }
        });
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        izq.add(button1, java.awt.BorderLayout.CENTER);

        encabezado.add(izq, java.awt.BorderLayout.WEST);

        der.setBackground(new java.awt.Color(255, 153, 51));

        javax.swing.GroupLayout derLayout = new javax.swing.GroupLayout(der);
        der.setLayout(derLayout);
        derLayout.setHorizontalGroup(
            derLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        derLayout.setVerticalGroup(
            derLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        encabezado.add(der, java.awt.BorderLayout.CENTER);

        JP_principal.add(encabezado, java.awt.BorderLayout.NORTH);

        panel_menu.setBackground(new java.awt.Color(255, 153, 51));
        panel_menu.setPreferredSize(new java.awt.Dimension(200, 750));
        panel_menu.setLayout(new javax.swing.BoxLayout(panel_menu, javax.swing.BoxLayout.Y_AXIS));

        jLabel1.setBackground(new java.awt.Color(0, 86, 179));
        jLabel1.setFont(new java.awt.Font("Lucida Sans", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gerente");
        panel_menu.add(jLabel1);

        btnEmpleados.setBackground(new java.awt.Color(255, 153, 51));
        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Empleados.png"))); // NOI18N
        btnEmpleados.setText("Empleados");
        btnEmpleados.setAlignmentY(0.0F);
        btnEmpleados.setColorNormal(new java.awt.Color(255, 153, 51));
        btnEmpleados.setColorPressed(new java.awt.Color(255, 153, 51));
        btnEmpleados.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEmpleados.setIconTextGap(20);
        panel_menu.add(btnEmpleados);

        btnClientes.setBackground(new java.awt.Color(255, 153, 51));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setAlignmentY(0.0F);
        btnClientes.setBorderPainted(false);
        btnClientes.setColorNormal(new java.awt.Color(255, 153, 51));
        btnClientes.setColorPressed(new java.awt.Color(255, 153, 51));
        btnClientes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnClientes.setIconTextGap(20);
        panel_menu.add(btnClientes);

        btnEnvios.setBackground(new java.awt.Color(255, 153, 51));
        btnEnvios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/envio.png"))); // NOI18N
        btnEnvios.setText("Envios");
        btnEnvios.setToolTipText("");
        btnEnvios.setColorNormal(new java.awt.Color(255, 153, 51));
        btnEnvios.setColorPressed(new java.awt.Color(255, 153, 51));
        btnEnvios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnEnvios.setIconTextGap(20);
        panel_menu.add(btnEnvios);

        btnPaquetes.setBackground(new java.awt.Color(255, 153, 51));
        btnPaquetes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/paquete.png"))); // NOI18N
        btnPaquetes.setText("Paquetes");
        btnPaquetes.setColorNormal(new java.awt.Color(255, 153, 51));
        btnPaquetes.setColorPressed(new java.awt.Color(255, 153, 51));
        btnPaquetes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnPaquetes.setIconTextGap(20);
        panel_menu.add(btnPaquetes);

        btnRepartidores.setBackground(new java.awt.Color(255, 153, 51));
        btnRepartidores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/repartidor.png"))); // NOI18N
        btnRepartidores.setText("Repartidores");
        btnRepartidores.setColorNormal(new java.awt.Color(255, 153, 51));
        btnRepartidores.setColorPressed(new java.awt.Color(255, 153, 51));
        btnRepartidores.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnRepartidores.setIconTextGap(20);
        panel_menu.add(btnRepartidores);

        btnVehiculos.setBackground(new java.awt.Color(255, 153, 51));
        btnVehiculos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/vehiculo.png"))); // NOI18N
        btnVehiculos.setText("Vehiculos");
        btnVehiculos.setColorNormal(new java.awt.Color(255, 153, 51));
        btnVehiculos.setColorPressed(new java.awt.Color(255, 153, 51));
        btnVehiculos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiculosActionPerformed(evt);
            }
        });
        panel_menu.add(btnVehiculos);

        btnTiendas.setBackground(new java.awt.Color(255, 153, 51));
        btnTiendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tienda.png"))); // NOI18N
        btnTiendas.setText("Tiendas");
        btnTiendas.setColorNormal(new java.awt.Color(255, 153, 51));
        btnTiendas.setColorPressed(new java.awt.Color(255, 153, 51));
        btnTiendas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTiendas.setIconTextGap(20);
        panel_menu.add(btnTiendas);

        btnZonas.setBackground(new java.awt.Color(255, 153, 51));
        btnZonas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/zona.png"))); // NOI18N
        btnZonas.setText("Zonas");
        btnZonas.setToolTipText("");
        btnZonas.setColorNormal(new java.awt.Color(255, 153, 51));
        btnZonas.setColorPressed(new java.awt.Color(255, 153, 51));
        btnZonas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnZonas.setIconTextGap(20);
        panel_menu.add(btnZonas);

        btnReportes.setBackground(new java.awt.Color(255, 153, 51));
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reporte.png"))); // NOI18N
        btnReportes.setText("Reportes");
        btnReportes.setColorNormal(new java.awt.Color(255, 153, 51));
        btnReportes.setColorPressed(new java.awt.Color(255, 153, 51));
        btnReportes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnReportes.setIconTextGap(20);
        panel_menu.add(btnReportes);

        button2.setBackground(new java.awt.Color(255, 153, 51));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar.png"))); // NOI18N
        button2.setText("Cerrar Sesion");
        button2.setAlignmentY(0.0F);
        button2.setBorderPainted(false);
        button2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        button2.setIconTextGap(20);
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        panel_menu.add(button2);

        JP_principal.add(panel_menu, java.awt.BorderLayout.WEST);

        Panel_Principal.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Principal.setMaximumSize(new java.awt.Dimension(980, 508));
        Panel_Principal.setMinimumSize(new java.awt.Dimension(980, 508));
        Panel_Principal.setPreferredSize(new java.awt.Dimension(980, 650));
        Panel_Principal.setLayout(new java.awt.BorderLayout());
        JP_principal.add(Panel_Principal, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JP_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(JP_principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked

        int posicion = panel_menu.getX();
        if (posicion == 0) {
            Animacion.Animacion.mover_izquierda(0, -200, 1, 1, panel_menu);
            new javax.swing.Timer(300, new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    panel_menu.setVisible(false);
                    ((javax.swing.Timer) e.getSource()).stop(); // detener el timer
                }
            }).start();

        } else {
            // Mostrar y mover hacia la derecha
            panel_menu.setVisible(true);
            Animacion.Animacion.mover_derecha(-200, 0, 1, 1, panel_menu);
        }
    }//GEN-LAST:event_button1MouseClicked

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed

    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        ILogin l = new ILogin();
        l.setVisible(true);
        java.awt.Window w = javax.swing.SwingUtilities.getWindowAncestor(IGerente.this);
        if (w != null) {
            w.dispose();
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void btnVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVehiculosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVehiculosActionPerformed

    private void configurarMenu() {
        // === SUBMENÚ DE EMPLEADOS ===
        submenuEmpleados = new JPanel();
        submenuEmpleados.setLayout(new BoxLayout(submenuEmpleados, BoxLayout.Y_AXIS));
        submenuEmpleados.setBackground(new java.awt.Color(255, 153, 51));
        submenuEmpleados.setVisible(false); // Oculto al inicio

        JButton botonCrearEmpleado = crearSubBoton("Registrar Empleado");
        botonCrearEmpleado.addActionListener(e -> {
            if (empleadoForm == null) {
                empleadoForm = new EmpleadoForm(); // se crea solo una vez
            }
            mostrarPanelEnContenido(empleadoForm);
        });
        submenuEmpleados.add(botonCrearEmpleado);

        JButton botonVerEmpleados = crearSubBoton("Ver Empleados");
        botonVerEmpleados.addActionListener(e -> {
            mostrarPanelEnContenido(new VerEmpleados());
        });
        submenuEmpleados.add(botonVerEmpleados);
        JButton botonReportes = crearSubBoton("Reportes");
        botonReportes.addActionListener(e -> {
            mostrarPanelEnContenido(new ReporteEmpleados());
        });
        submenuEmpleados.add(botonReportes);

        // Submenú Clientes
        submenuClientes = new JPanel();
        submenuClientes.setLayout(new BoxLayout(submenuClientes, BoxLayout.Y_AXIS));
        submenuClientes.setBackground(new Color(255, 153, 51));
        submenuClientes.setVisible(false);
        // Submenú Clientes
        submenuClientes = new JPanel();
        submenuClientes.setLayout(new BoxLayout(submenuClientes, BoxLayout.Y_AXIS));
        submenuClientes.setBackground(new Color(255, 153, 51));
        submenuClientes.setVisible(false);

        //Registra el cliente
        JButton botonRegistrarCliente = crearSubBoton("Registrar Cliente");
        botonRegistrarCliente.addActionListener(e -> {
            ClienteForm clienteForm = new ClienteForm(); // Tu formulario de cliente
            mostrarPanelEnContenido(clienteForm);
        });
        submenuClientes.add(botonRegistrarCliente);
        //ver clientes
        JButton botonVerClientes = crearSubBoton("Ver Clientes");
        botonVerClientes.addActionListener(e -> {
            mostrarPanelEnContenido(new ListarClientes());
        });
        submenuClientes.add(botonVerClientes);

        // Historial de clientes
        JButton botonHistorial = crearSubBoton("Historial de Envios");
        botonHistorial.addActionListener(e -> {
            mostrarPanelEnContenido(new HistorialClienteEnvios());
        });
        submenuClientes.add(botonHistorial);

        //Submenú Envios
        submenuEnvios = new JPanel();
        submenuEnvios.setLayout(new BoxLayout(submenuEnvios, BoxLayout.Y_AXIS));
        submenuEnvios.setBackground(new Color(255, 153, 51));
        submenuEnvios.setVisible(false);

        // Crear Envío
        JButton botonCrearEnvio = crearSubBoton("Crear Envio");
        botonCrearEnvio.addActionListener(e -> {
            mostrarPanelEnContenido(new EnvioForm());
        });
        submenuEnvios.add(botonCrearEnvio);

        // Ver Envíos
        JButton botonVerEnvios = crearSubBoton("Ver Envios");
        botonVerEnvios.addActionListener(e -> {
            mostrarPanelEnContenido(new ListaEnvios());
        });
        submenuEnvios.add(botonVerEnvios);

        // Seguimiento
        JButton botonSeguimiento = crearSubBoton("Seguimiento");
        botonSeguimiento.addActionListener(e -> {
            mostrarPanelEnContenido(new SeguimientoEnvio());
        });
        submenuEnvios.add(botonSeguimiento);

 // Submenú Paquetes
        submenuPaquetes = new JPanel();
        submenuPaquetes.setLayout(new BoxLayout(submenuPaquetes, BoxLayout.Y_AXIS));
        submenuPaquetes.setBackground(new Color(255, 153, 51));
        submenuPaquetes.setVisible(false);

// Registrar Paquete
        JButton botonRegistrarPaquete = crearSubBoton("Registrar Paquete");
        botonRegistrarPaquete.addActionListener(e -> {
            mostrarPanelEnContenido(new PaqueteForm());
        });
        submenuPaquetes.add(botonRegistrarPaquete);

        // Ver Paquetes
        JButton botonVerPaquetes = crearSubBoton("Ver Paquetes");
        botonVerPaquetes.addActionListener(e -> {
            mostrarPanelEnContenido(new ListarPaquetes());
        });
        submenuPaquetes.add(botonVerPaquetes);

        // Reporte de Paquetes
        JButton botonReportePaquetes = crearSubBoton("Reporte de Paquetes");
        botonReportePaquetes.addActionListener(e -> {
            mostrarPanelEnContenido(new ReportePaquetes());
        });
        submenuPaquetes.add(botonReportePaquetes);

        // Submenú Repartidores
        submenuRepartidores = new JPanel();
        submenuRepartidores.setLayout(new BoxLayout(submenuRepartidores, BoxLayout.Y_AXIS));
        submenuRepartidores.setBackground(new Color(255, 153, 51));
        submenuRepartidores.setVisible(false);
        JButton botonVerRepartidores = crearSubBoton("Ver Repartidores");
        botonVerRepartidores.addActionListener(e -> {
            mostrarPanelEnContenido(new VerRepartidores());
        });
        submenuRepartidores.add(botonVerRepartidores);

        // Submenú Vehiculos
        submenuVehiculos = new JPanel();
        submenuVehiculos.setLayout(new BoxLayout(submenuVehiculos, BoxLayout.Y_AXIS));
        submenuVehiculos.setBackground(new Color(255, 153, 51));
        submenuVehiculos.setVisible(false);
        JButton botonRegistrarVehiculo = crearSubBoton("Registrar Vehiculo");
        botonRegistrarVehiculo.addActionListener(e -> {
            mostrarPanelEnContenido(new VehiculoForm());
        });
        submenuVehiculos.add(botonRegistrarVehiculo);
        JButton botonVerVehiculos = crearSubBoton("Ver Vehiculos");
        botonVerVehiculos.addActionListener(e -> {
            mostrarPanelEnContenido(new VerVehiculos());
        });
        submenuVehiculos.add(botonVerVehiculos);

        // Submenú Tiendas
        submenuTiendas = new JPanel();
        submenuTiendas.setLayout(new BoxLayout(submenuTiendas, BoxLayout.Y_AXIS));
        submenuTiendas.setBackground(new Color(255, 153, 51));
        submenuTiendas.setVisible(false);
        JButton botonRegistrarTienda = crearSubBoton("Registrar Tienda");
        botonRegistrarTienda.addActionListener(e -> {
            mostrarPanelEnContenido(new TiendaForm());
        });
        submenuTiendas.add(botonRegistrarTienda);
        JButton botonVerTiendas = crearSubBoton("Ver Tiendas");
        botonVerTiendas.addActionListener(e -> {
            mostrarPanelEnContenido(new VerTiendas());
        });
        submenuTiendas.add(botonVerTiendas);

        // Submenú Zonas
        submenuZonas = new JPanel();
        submenuZonas.setLayout(new BoxLayout(submenuZonas, BoxLayout.Y_AXIS));
        submenuZonas.setBackground(new Color(255, 153, 51));
        submenuZonas.setVisible(false);
        JButton botonRegistrarZona = crearSubBoton("Registrar Zona");
        botonRegistrarZona.addActionListener(e -> {
            mostrarPanelEnContenido(new ZonaForm());
        });
        submenuZonas.add(botonRegistrarZona);
        JButton botonVerZonas = crearSubBoton("Ver Zonas");
        botonVerZonas.addActionListener(e -> {
            mostrarPanelEnContenido(new VerZonas());
        });
        submenuZonas.add(botonVerZonas);

        // Submenú Reportes
        submenuReportes = new JPanel();
        submenuReportes.setLayout(new BoxLayout(submenuReportes, BoxLayout.Y_AXIS));
        submenuReportes.setBackground(new Color(255, 153, 51));
        submenuReportes.setVisible(false);

        JButton botonVistaReportes = crearSubBoton("Vista de Reportes");
        botonVistaReportes.addActionListener(e -> {
            mostrarPanelEnContenido(new VistaReportes());
        });
        submenuReportes.add(botonVistaReportes);

        // Inserta los submenus en el panel_menu según corresponda,
        panel_menu.add(submenuEmpleados, 2);
        panel_menu.add(submenuClientes, 4);
        panel_menu.add(submenuEnvios, 6);
        panel_menu.add(submenuPaquetes, 8);
        panel_menu.add(submenuRepartidores, 10);
        panel_menu.add(submenuVehiculos, 12);
        panel_menu.add(submenuTiendas, 14);
        panel_menu.add(submenuZonas, 16);
        panel_menu.add(submenuReportes, 18);

        // Asignar listeners tipo acordeón para mostrar/ocultar submenús
        btnEmpleados.addActionListener(e -> toggleSubmenu(submenuEmpleados));
        btnClientes.addActionListener(e -> toggleSubmenu(submenuClientes));
        btnEnvios.addActionListener(e -> toggleSubmenu(submenuEnvios));
        btnPaquetes.addActionListener(e -> toggleSubmenu(submenuPaquetes));
        btnRepartidores.addActionListener(e -> toggleSubmenu(submenuRepartidores));
        btnVehiculos.addActionListener(e -> toggleSubmenu(submenuVehiculos));
        btnTiendas.addActionListener(e -> toggleSubmenu(submenuTiendas));
        btnZonas.addActionListener(e -> toggleSubmenu(submenuZonas));
        btnReportes.addActionListener(e -> toggleSubmenu(submenuReportes));

    }
    // Método para mostrar/ocultar submenús con cierre tipo acordeón

    private void toggleSubmenu(JPanel target) {
        for (JPanel submenu : List.of(
                submenuEmpleados, submenuClientes, submenuEnvios, submenuPaquetes,
                submenuRepartidores, submenuVehiculos, submenuTiendas, submenuZonas, submenuReportes
        )) {
            if (submenu == target) {
                submenu.setVisible(!submenu.isVisible());
            } else {
                submenu.setVisible(false);
            }
        }
        panel_menu.revalidate();
        panel_menu.repaint();
    }

    // Método ayudante para crear botones de submenú con estilo
    private JButton crearSubBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 16));
        boton.setBackground(new java.awt.Color(255, 180, 80)); // Naranja más claro
        boton.setForeground(java.awt.Color.WHITE);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        // Asegura que el botón ocupe todo el ancho
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getPreferredSize().height));
        return boton;
    }

    private void mostrarPanelEnContenido(JPanel panel) {
        Panel_Principal.removeAll();
        Panel_Principal.add(panel);
        Panel_Principal.revalidate();
        Panel_Principal.repaint();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JP_principal;
    private javax.swing.JPanel Panel_Principal;
    private modelo.Button btnClientes;
    private modelo.Button btnEmpleados;
    private modelo.Button btnEnvios;
    private modelo.Button btnPaquetes;
    private modelo.Button btnRepartidores;
    private modelo.Button btnReportes;
    private modelo.Button btnTiendas;
    private modelo.Button btnVehiculos;
    private modelo.Button btnZonas;
    private modelo.Button button1;
    private modelo.Button button2;
    private javax.swing.JPanel der;
    private javax.swing.JPanel encabezado;
    private javax.swing.JPanel izq;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panel_menu;
    // End of variables declaration//GEN-END:variables
}
