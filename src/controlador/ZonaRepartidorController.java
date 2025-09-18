package controlador;

import conexion.Conexion;
import modelo.ZonaRepartidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZonaRepartidorController {

    // Asignar un repartidor a una zona
    public void asignar(long zonaId, long repartidorId) {
        String sql = "INSERT INTO ZONA_REPARTIDOR (zona_id, repartidor_id, fecha_asignacion, activo) " +
                     "VALUES (?, ?, NOW(), 1) " +
                     "ON DUPLICATE KEY UPDATE activo=1, fecha_asignacion=NOW()";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            ps.setLong(2, repartidorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Desactivar un repartidor en una zona
    public void desactivar(long zonaId, long repartidorId) {
        String sql = "UPDATE ZONA_REPARTIDOR SET activo=0 WHERE zona_id=? AND repartidor_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            ps.setLong(2, repartidorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar repartidores asignados a una zona
    public List<ZonaRepartidor> listarPorZona(long zonaId) {
        List<ZonaRepartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM ZONA_REPARTIDOR WHERE zona_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ZonaRepartidor zr = new ZonaRepartidor();
                    zr.setZonaId(rs.getLong("zona_id"));
                    zr.setRepartidorId(rs.getLong("repartidor_id"));
                    zr.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));
                    zr.setActivo(rs.getBoolean("activo"));
                    lista.add(zr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
