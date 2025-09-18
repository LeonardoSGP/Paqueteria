package controlador;

import conexion.Conexion;
import modelo.Zona;
import modelo.RepartidorInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZonaController {

    public void insertar(Zona z) {
        String sql = "INSERT INTO ZONA (codigo_zona, nombre_zona, descripcion, tarifa_base, tiempo_entrega_estimado, tienda_responsable_id, activa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, z.getCodigoZona());
            ps.setString(2, z.getNombreZona());
            ps.setString(3, z.getDescripcion());
            ps.setDouble(4, z.getTarifaBase());
            ps.setInt(5, z.getTiempoEntregaEstimado());

            if (z.getTiendaResponsableId() == null || z.getTiendaResponsableId() == 0) {
                ps.setNull(6, Types.BIGINT);
            } else {
                ps.setLong(6, z.getTiendaResponsableId());
            }

            ps.setBoolean(7, z.isActiva());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    z.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Zona> listar() {
        List<Zona> lista = new ArrayList<>();
        String sql = "SELECT * FROM ZONA";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Zona obtenerPorCodigo(String codigo) {
        String sql = "SELECT * FROM ZONA WHERE codigo_zona=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
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

    public void actualizar(Zona z) {
        String sql = "UPDATE ZONA SET nombre_zona=?, descripcion=?, tarifa_base=?, tiempo_entrega_estimado=?, tienda_responsable_id=?, activa=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, z.getNombreZona());
            ps.setString(2, z.getDescripcion());
            ps.setDouble(3, z.getTarifaBase());
            ps.setInt(4, z.getTiempoEntregaEstimado());

            if (z.getTiendaResponsableId() == null || z.getTiendaResponsableId() == 0) {
                ps.setNull(5, Types.BIGINT);
            } else {
                ps.setLong(5, z.getTiendaResponsableId());
            }

            ps.setBoolean(6, z.isActiva());
            ps.setLong(7, z.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleEstado(long zonaId, boolean activa) {
        String sql = "UPDATE ZONA SET activa=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, activa);
            ps.setLong(2, zonaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // GESTIÓN DE REPARTIDORES
    // ========================
    public void asignarRepartidor(long zonaId, long repartidorId) {
        String sql = "INSERT INTO ZONA_REPARTIDOR (zona_id, repartidor_id, activo) VALUES (?, ?, 1) "
                + "ON DUPLICATE KEY UPDATE activo=1, fecha_asignacion=CURRENT_TIMESTAMP";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            ps.setLong(2, repartidorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desactivarRepartidor(long zonaId, long repartidorId) {
        String sql = "UPDATE ZONA_REPARTIDOR SET activo=0 WHERE zona_id=? AND repartidor_id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            ps.setLong(2, repartidorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RepartidorInfo> listarRepartidoresPorZona(long zonaId) {
        List<RepartidorInfo> lista = new ArrayList<>();
        String sql = "SELECT r.* FROM ZONA_REPARTIDOR zr "
                + "JOIN REPARTIDOR_INFO r ON zr.repartidor_id = r.id "
                + "WHERE zr.zona_id=? AND zr.activo=1";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, zonaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RepartidorInfo r = new RepartidorInfo();
                    r.setId(rs.getLong("id"));
                    r.setEmpleadoId(rs.getLong("empleado_id"));
                    r.setLicenciaConducir(rs.getString("licencia_conducir"));
                    r.setDisponible(rs.getBoolean("disponible"));
                    r.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Zona map(ResultSet rs) throws SQLException {
        Zona z = new Zona();
        z.setId(rs.getLong("id"));
        z.setCodigoZona(rs.getString("codigo_zona"));
        z.setNombreZona(rs.getString("nombre_zona"));
        z.setDescripcion(rs.getString("descripcion"));
        z.setTarifaBase(rs.getDouble("tarifa_base"));
        z.setTiempoEntregaEstimado(rs.getInt("tiempo_entrega_estimado"));
        long tiendaId = rs.getLong("tienda_responsable_id");
        z.setTiendaResponsableId(rs.wasNull() ? null : tiendaId);
        z.setActiva(rs.getBoolean("activa"));
        return z;
    }

    public String obtenerUltimoCodigo() {
        String sql = "SELECT codigo_zona FROM ZONA ORDER BY id DESC LIMIT 1";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("codigo_zona");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
