package controlador;

import conexion.Conexion;
import modelo.Reporte;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ReporteController {

    // Insertar un nuevo reporte
    public void insertar(Reporte r) {
        String sql = "INSERT INTO REPORTE (tipo_reporte, titulo, descripcion, fecha_generacion, fecha_inicio, fecha_fin, parametros, resultados, generado_por_usuario_id, formato_salida, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getTipoReporte());
            ps.setString(2, r.getTitulo());
            ps.setString(3, r.getDescripcion());
            ps.setTimestamp(4, r.getFechaGeneracion() == null ? null : new Timestamp(r.getFechaGeneracion().getTime()));
            ps.setTimestamp(5, r.getFechaInicio() == null ? null : new Timestamp(r.getFechaInicio().getTime()));
            ps.setTimestamp(6, r.getFechaFin() == null ? null : new Timestamp(r.getFechaFin().getTime()));
            ps.setString(7, r.getParametros());
            ps.setString(8, r.getResultados());
            ps.setLong(9, r.getGeneradoPorUsuarioId());
            ps.setString(10, r.getFormatoSalida());
            ps.setBoolean(11, r.isActivo());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Obtener reporte por ID
    public Reporte obtenerPorId(long id) {
        String sql = "SELECT * FROM REPORTE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Listar todos los reportes
    public List<Reporte> listar() {
        List<Reporte> list = new ArrayList<>();
        String sql = "SELECT * FROM REPORTE ORDER BY fecha_generacion DESC";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Listar reportes activos
    public List<Reporte> listarActivos() {
        List<Reporte> list = new ArrayList<>();
        String sql = "SELECT * FROM REPORTE WHERE activo=TRUE ORDER BY fecha_generacion DESC";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Listar reportes por tipo
    public List<Reporte> listarPorTipo(String tipoReporte) {
        List<Reporte> list = new ArrayList<>();
        String sql = "SELECT * FROM REPORTE WHERE tipo_reporte=? AND activo=TRUE ORDER BY fecha_generacion DESC";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, tipoReporte);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // Actualizar reporte
    public void actualizar(Reporte r) {
        String sql = "UPDATE REPORTE SET tipo_reporte=?, titulo=?, descripcion=?, fecha_inicio=?, fecha_fin=?, parametros=?, resultados=?, formato_salida=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, r.getTipoReporte());
            ps.setString(2, r.getTitulo());
            ps.setString(3, r.getDescripcion());
            ps.setTimestamp(4, r.getFechaInicio() == null ? null : new Timestamp(r.getFechaInicio().getTime()));
            ps.setTimestamp(5, r.getFechaFin() == null ? null : new Timestamp(r.getFechaFin().getTime()));
            ps.setString(6, r.getParametros());
            ps.setString(7, r.getResultados());
            ps.setString(8, r.getFormatoSalida());
            ps.setBoolean(9, r.isActivo());
            ps.setLong(10, r.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Desactivar reporte
    public void desactivar(long id) {
        String sql = "UPDATE REPORTE SET activo=FALSE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Eliminar reporte
    public void eliminar(long id) {
        String sql = "DELETE FROM REPORTE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // === REPORTES ESPECÍFICOS DEL NEGOCIO ===
    // Reporte de ingresos por periodo
    public Map<String, Object> generarReporteIngresos(java.util.Date fechaInicio, java.util.Date fechaFin) {
        Map<String, Object> resultado = new HashMap<>();
        String sql = """
            SELECT
                COUNT(*) as total_envios,
                SUM(costo_final) as total_ingresos,
                AVG(costo_final) as promedio_costo,
                MIN(costo_final) as costo_minimo,
                MAX(costo_final) as costo_maximo
            FROM ENVIO
            WHERE fecha_creacion BETWEEN ? AND ?
            AND estado_actual != 'CANCELADO'
        """;

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(2, new Timestamp(fechaFin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado.put("totalEnvios", rs.getInt("total_envios"));
                    resultado.put("totalIngresos", rs.getBigDecimal("total_ingresos") != null ? rs.getBigDecimal("total_ingresos") : BigDecimal.ZERO);
                    resultado.put("promedioCosto", rs.getBigDecimal("promedio_costo") != null ? rs.getBigDecimal("promedio_costo") : BigDecimal.ZERO);
                    resultado.put("costoMinimo", rs.getBigDecimal("costo_minimo") != null ? rs.getBigDecimal("costo_minimo") : BigDecimal.ZERO);
                    resultado.put("costoMaximo", rs.getBigDecimal("costo_maximo") != null ? rs.getBigDecimal("costo_maximo") : BigDecimal.ZERO);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    // Reporte de clientes más frecuentes
    public List<Map<String, Object>> generarReporteClientesFrecuentes(int limite) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        String sql = """
            SELECT
                c.nombre,
                c.apellidos,
                c.codigo_cliente,
                COUNT(e.id) as total_envios,
                SUM(e.costo_final) as total_gastado
            FROM CLIENTE c
            INNER JOIN ENVIO e ON c.id = e.remitente_id
            WHERE c.activo = TRUE
            GROUP BY c.id, c.nombre, c.apellidos, c.codigo_cliente
            ORDER BY total_envios DESC
            LIMIT ?
        """;

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("nombre", rs.getString("nombre"));
                    fila.put("apellidos", rs.getString("apellidos"));
                    fila.put("codigoCliente", rs.getString("codigo_cliente"));
                    fila.put("totalEnvios", rs.getInt("total_envios"));
                    fila.put("totalGastado", rs.getBigDecimal("total_gastado"));
                    resultado.add(fila);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    // Reporte de rendimiento de repartidores
    public List<Map<String, Object>> generarReporteRendimientoRepartidores(java.util.Date fechaInicio, java.util.Date fechaFin) {
        List<Map<String, Object>> resultado = new ArrayList<>();
        String sql = """
            SELECT
                emp.nombre,
                emp.apellidos,
                emp.numero_empleado,
                COUNT(e.id) as envios_realizados,
                AVG(ri.calificacion_promedio) as calificacion_promedio,
                SUM(CASE WHEN e.estado_actual = 'ENTREGADO' THEN 1 ELSE 0 END) as envios_exitosos
            FROM EMPLEADO emp
            INNER JOIN REPARTIDOR_INFO ri ON emp.id = ri.empleado_id
            LEFT JOIN ENVIO e ON ri.id = e.repartidor_id
                AND e.fecha_creacion BETWEEN ? AND ?
            WHERE emp.activo = TRUE
            GROUP BY emp.id, emp.nombre, emp.apellidos, emp.numero_empleado, ri.calificacion_promedio
            ORDER BY envios_realizados DESC
        """;

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(2, new Timestamp(fechaFin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("nombre", rs.getString("nombre"));
                    fila.put("apellidos", rs.getString("apellidos"));
                    fila.put("numeroEmpleado", rs.getString("numero_empleado"));
                    fila.put("enviosRealizados", rs.getInt("envios_realizados"));
                    fila.put("calificacionPromedio", rs.getBigDecimal("calificacion_promedio"));
                    fila.put("enviosExitosos", rs.getInt("envios_exitosos"));
                    resultado.add(fila);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    // Reporte de estados de envíos
    public Map<String, Integer> generarReporteEstadosEnvios(java.util.Date fechaInicio, java.util.Date fechaFin) {
        Map<String, Integer> resultado = new HashMap<>();
        String sql = """
            SELECT estado_actual, COUNT(*) as cantidad
            FROM ENVIO
            WHERE fecha_creacion BETWEEN ? AND ?
            GROUP BY estado_actual
            ORDER BY cantidad DESC
        """;

        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(fechaInicio.getTime()));
            ps.setTimestamp(2, new Timestamp(fechaFin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.put(rs.getString("estado_actual"), rs.getInt("cantidad"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    // Mapear ResultSet a objeto Reporte
    private Reporte map(ResultSet rs) throws SQLException {
        Reporte r = new Reporte();
        r.setId(rs.getLong("id"));
        r.setTipoReporte(rs.getString("tipo_reporte"));
        r.setTitulo(rs.getString("titulo"));
        r.setDescripcion(rs.getString("descripcion"));

        Timestamp fg = rs.getTimestamp("fecha_generacion");
        r.setFechaGeneracion(fg == null ? null : new java.util.Date(fg.getTime()));

        Timestamp fi = rs.getTimestamp("fecha_inicio");
        r.setFechaInicio(fi == null ? null : new java.util.Date(fi.getTime()));

        Timestamp ff = rs.getTimestamp("fecha_fin");
        r.setFechaFin(ff == null ? null : new java.util.Date(ff.getTime()));

        r.setParametros(rs.getString("parametros"));
        r.setResultados(rs.getString("resultados"));
        r.setGeneradoPorUsuarioId(rs.getLong("generado_por_usuario_id"));
        r.setFormatoSalida(rs.getString("formato_salida"));
        r.setActivo(rs.getBoolean("activo"));

        return r;
    }
}
