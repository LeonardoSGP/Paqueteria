package vista;

import controlador.EmpleadoController;
import controlador.UsuarioController;
import controlador.VehiculoController;
import controlador.RepartidorVehiculoController;
import modelo.Empleado;
import modelo.Usuario;
import modelo.Vehiculo;
import modelo.RepartidorVehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class VerRepartidores extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JComboBox<String> cbRepartidor, cbVehiculo;
    private JButton btnAsignar, btnFinalizar;

    public VerRepartidores() {
        setLayout(new BorderLayout());

        // === Panel arriba ===
        JPanel panelTop = new JPanel(new FlowLayout());
        panelTop.add(new JLabel("Repartidor:"));
        cbRepartidor = new JComboBox<>();
        panelTop.add(cbRepartidor);

        panelTop.add(new JLabel("Vehículo:"));
        cbVehiculo = new JComboBox<>();
        panelTop.add(cbVehiculo);

        btnAsignar = new JButton("Asignar Vehículo");
        btnFinalizar = new JButton("Finalizar Asignación");
        panelTop.add(btnAsignar);
        panelTop.add(btnFinalizar);

        add(panelTop, BorderLayout.NORTH);

        // === Tabla abajo ===
        String[] columnas = {"Repartidor", "Vehículo", "Placas", "Fecha Asignación", "Fecha Fin"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnAsignar.addActionListener(e -> asignarVehiculo());
        btnFinalizar.addActionListener(e -> finalizarAsignacion());

        // Cargar combos
        cargarRepartidores();
        cargarVehiculos();
        cargarAsignaciones();
    }

    private void cargarRepartidores() {
        cbRepartidor.removeAllItems();
        cbRepartidor.addItem("-- Seleccione --");

        UsuarioController uc = new UsuarioController();
        EmpleadoController ec = new EmpleadoController();

        // Traer solo usuarios REPARTIDOR
        List<Usuario> usuarios = uc.listarRepartidores();
        for (Usuario u : usuarios) {
            Empleado e = ec.obtenerPorUsuarioId(u.getId());
            if (e != null && e.isActivo()) {
                cbRepartidor.addItem(e.getId() + " - " + e.getNombre() + " " + e.getApellidos());
            }
        }
    }

    private void cargarVehiculos() {
        cbVehiculo.removeAllItems();
        cbVehiculo.addItem("-- Seleccione --");

        VehiculoController vc = new VehiculoController();
        List<Vehiculo> lista = vc.listar();
        for (Vehiculo v : lista) {
            if (v.isActivo() && v.isDisponible()) {
                cbVehiculo.addItem(v.getId() + " - " + v.getMarca() + " " + v.getModelo() + " (" + v.getPlacas() + ")");
            }
        }
    }

    private void cargarAsignaciones() {
        modeloTabla.setRowCount(0);

        RepartidorVehiculoController rvc = new RepartidorVehiculoController();
        EmpleadoController ec = new EmpleadoController();
        VehiculoController vc = new VehiculoController();

        List<Empleado> repartidores = ec.listar();
        for (Empleado e : repartidores) {
            UsuarioController uc = new UsuarioController();
            Usuario u = uc.obtenerPorId(e.getUsuarioId());
            if (u != null && "REPARTIDOR".equalsIgnoreCase(u.getTipoUsuario())) {
                List<RepartidorVehiculo> asignaciones = rvc.listarPorRepartidor(e.getId());
                for (RepartidorVehiculo rv : asignaciones) {
                    Vehiculo v = vc.obtenerPorId(rv.getVehiculoId());
                    modeloTabla.addRow(new Object[]{
                        e.getNombre() + " " + e.getApellidos(),
                        v != null ? v.getMarca() + " " + v.getModelo() : "Desconocido",
                        v != null ? v.getPlacas() : "N/A",
                        rv.getFechaAsignacion(),
                        rv.getFechaFin() != null ? rv.getFechaFin() : "En uso"
                    });
                }
            }
        }
    }

    private void asignarVehiculo() {
        if (cbRepartidor.getSelectedIndex() <= 0 || cbVehiculo.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un repartidor y un vehículo.");
            return;
        }

        long repartidorId = Long.parseLong(cbRepartidor.getSelectedItem().toString().split(" - ")[0]);
        long vehiculoId = Long.parseLong(cbVehiculo.getSelectedItem().toString().split(" - ")[0]);

        RepartidorVehiculo rv = new RepartidorVehiculo();
        rv.setRepartidorId(repartidorId);
        rv.setVehiculoId(vehiculoId);
        rv.setFechaAsignacion(new Date());

        RepartidorVehiculoController rvc = new RepartidorVehiculoController();
        rvc.asignarVehiculo(rv);

        JOptionPane.showMessageDialog(this, "Vehículo asignado correctamente.");
        cargarVehiculos();
        cargarAsignaciones();
    }

    private void finalizarAsignacion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignación de la tabla.");
            return;
        }

        String repartidorNombre = (String) modeloTabla.getValueAt(fila, 0);
        String placas = (String) modeloTabla.getValueAt(fila, 2);

        EmpleadoController ec = new EmpleadoController();
        VehiculoController vc = new VehiculoController();

        long repartidorId = 0;
        for (Empleado e : ec.listar()) {
            if ((e.getNombre() + " " + e.getApellidos()).equals(repartidorNombre)) {
                repartidorId = e.getId();
                break;
            }
        }

        long vehiculoId = 0;
        for (Vehiculo v : vc.listar()) {
            if (v.getPlacas().equals(placas)) {
                vehiculoId = v.getId();
                break;
            }
        }

        if (repartidorId != 0 && vehiculoId != 0) {
            RepartidorVehiculoController rvc = new RepartidorVehiculoController();
            rvc.finalizarAsignacion(repartidorId, vehiculoId, new Date());
            JOptionPane.showMessageDialog(this, "Asignación finalizada.");
            cargarVehiculos();
            cargarAsignaciones();
        }
    }
}
