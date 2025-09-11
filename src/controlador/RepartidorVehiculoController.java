package controlador;

import conexion.Conexion;
import modelo.RepartidorVehiculo;
import java.sql.*;
import java.util.*;

public class RepartidorVehiculoController {

    public void asignarVehiculo(RepartidorVehiculo rv) {
        String sql = "INSERT INTO REPARTIDOR_VEHICULO (repartidor_id, vehiculo_id, fecha_asignacion) VALUES (?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, rv.getRepartidorId());
            ps.setLong(2, rv.getVehiculoId());
            ps.setTimestamp(3, rv.getFechaAsignacion()==null? null : new Timestamp(rv.getFechaAsignacion().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void finalizarAsignacion(long repartidorId, long vehiculoId, java.util.Date fechaFin) {
        String sql = "UPDATE REPARTIDOR_VEHICULO SET fecha_fin=? WHERE repartidor_id=? AND vehiculo_id=? AND fecha_fin IS NULL";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, fechaFin==null? null : new Timestamp(fechaFin.getTime()));
            ps.setLong(2, repartidorId);
            ps.setLong(3, vehiculoId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<RepartidorVehiculo> listarPorRepartidor(long repartidorId) {
        List<RepartidorVehiculo> list = new ArrayList<>();
        String sql = "SELECT * FROM REPARTIDOR_VEHICULO WHERE repartidor_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, repartidorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RepartidorVehiculo rv = new RepartidorVehiculo();
                    rv.setRepartidorId(rs.getLong("repartidor_id"));
                    rv.setVehiculoId(rs.getLong("vehiculo_id"));
                    rv.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));
                    rv.setFechaFin(rs.getTimestamp("fecha_fin"));
                    list.add(rv);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
