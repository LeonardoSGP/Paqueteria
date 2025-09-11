package controlador;

import conexion.Conexion;
import modelo.RepartidorInfo;
import java.sql.*;
import java.util.*;

public class RepartidorInfoController {

    public void insertar(RepartidorInfo r) {
        String sql = "INSERT INTO REPARTIDOR_INFO (empleado_id, licencia_conducir, disponible, calificacion_promedio, latitud_actual, longitud_actual, fecha_actualizacion_ubicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getEmpleadoId());
            ps.setString(2, r.getLicenciaConducir());
            ps.setBoolean(3, r.isDisponible());
            ps.setDouble(4, r.getCalificacionPromedio());
            ps.setDouble(5, r.getLatitudActual());
            ps.setDouble(6, r.getLongitudActual());
            ps.setTimestamp(7, r.getFechaActualizacionUbicacion() == null ? null : new Timestamp(r.getFechaActualizacionUbicacion().getTime()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RepartidorInfo obtenerPorId(long id) {
        String sql = "SELECT * FROM REPARTIDOR_INFO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RepartidorInfo> listar() {
        List<RepartidorInfo> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARTIDOR_INFO";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private RepartidorInfo map(ResultSet rs) throws SQLException {
        RepartidorInfo r = new RepartidorInfo();
        r.setId(rs.getLong("id"));
        r.setEmpleadoId(rs.getLong("empleado_id"));
        r.setLicenciaConducir(rs.getString("licencia_conducir"));
        r.setDisponible(rs.getBoolean("disponible"));
        r.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
        r.setLatitudActual(rs.getDouble("latitud_actual"));
        r.setLongitudActual(rs.getDouble("longitud_actual"));
        r.setFechaActualizacionUbicacion(rs.getTimestamp("fecha_actualizacion_ubicacion"));
        return r;
    }
}
