package controlador;

import conexion.Conexion;
import modelo.Empleado;
import modelo.EmpleadoPuesto;
import java.sql.*;
import java.util.*;
import modelo.Usuario;

public class EmpleadoController {

    public void insertar(Empleado e) {
        String sql = "INSERT INTO EMPLEADO (usuario_id, numero_empleado, nombre, apellidos, telefono, salario_actual, fecha_ingreso, tienda_id, supervisor_id, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, e.getUsuarioId());
            ps.setString(2, e.getNumeroEmpleado());
            ps.setString(3, e.getNombre());
            ps.setString(4, e.getApellidos());
            ps.setString(5, e.getTelefono());
            ps.setDouble(6, e.getSalarioActual());
            ps.setTimestamp(7, e.getFechaIngreso() == null ? null : new Timestamp(e.getFechaIngreso().getTime()));

            // tienda_id puede ser null
            if (e.getTiendaId() == null) {
                ps.setNull(8, java.sql.Types.BIGINT);
            } else {
                ps.setLong(8, e.getTiendaId());
            }

            // supervisor_id puede ser null
            if (e.getSupervisorId() == null) {
                ps.setNull(9, java.sql.Types.BIGINT);
            } else {
                ps.setLong(9, e.getSupervisorId());
            }

            ps.setBoolean(10, e.isActivo());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Empleado obtenerPorId(long id) {
        String sql = "SELECT * FROM EMPLEADO WHERE id=?";
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

    public List<Empleado> listar() {
        List<Empleado> list = new ArrayList<>();
        String sql = "SELECT * FROM EMPLEADO";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ✅ Listar solo empleados activos
    public List<Empleado> listarActivos() {
        List<Empleado> list = new ArrayList<>();
        String sql = "SELECT * FROM EMPLEADO WHERE activo=TRUE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // ✅ Buscar empleado por número
    public Empleado obtenerPorNumero(String numeroEmpleado) {
        String sql = "SELECT * FROM EMPLEADO WHERE numero_empleado=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numeroEmpleado);
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

    public void actualizar(Empleado e) {
        String sql = "UPDATE EMPLEADO SET usuario_id=?, numero_empleado=?, nombre=?, apellidos=?, telefono=?, salario_actual=?, fecha_ingreso=?, tienda_id=?, supervisor_id=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, e.getUsuarioId());
            ps.setString(2, e.getNumeroEmpleado());
            ps.setString(3, e.getNombre());
            ps.setString(4, e.getApellidos());
            ps.setString(5, e.getTelefono());
            ps.setDouble(6, e.getSalarioActual());
            ps.setTimestamp(7, e.getFechaIngreso() == null ? null : new Timestamp(e.getFechaIngreso().getTime()));

            if (e.getTiendaId() == null) {
                ps.setNull(8, java.sql.Types.BIGINT);
            } else {
                ps.setLong(8, e.getTiendaId());
            }

            if (e.getSupervisorId() == null) {
                ps.setNull(9, java.sql.Types.BIGINT);
            } else {
                ps.setLong(9, e.getSupervisorId());
            }

            ps.setBoolean(10, e.isActivo());
            ps.setLong(11, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // ✅ Desactivar empleado
    public void desactivar(long id) {
        String sql = "UPDATE EMPLEADO SET activo=FALSE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM EMPLEADO WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Relación EMPLEADO_PUESTO (M:N)
    public void asignarPuesto(long empleadoId, int puestoId, java.util.Date fechaAsignacion, boolean activo) {
        String sql = "INSERT INTO EMPLEADO_PUESTO (empleado_id, puesto_id, fecha_asignacion, activo) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE fecha_fin=NULL, activo=VALUES(activo)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, empleadoId);
            ps.setInt(2, puestoId);
            ps.setTimestamp(3, fechaAsignacion == null ? null : new Timestamp(fechaAsignacion.getTime()));
            ps.setBoolean(4, activo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void desactivarPuesto(long empleadoId, int puestoId, java.util.Date fechaFin) {
        String sql = "UPDATE EMPLEADO_PUESTO SET activo=0, fecha_fin=? WHERE empleado_id=? AND puesto_id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, fechaFin == null ? null : new Timestamp(fechaFin.getTime()));
            ps.setLong(2, empleadoId);
            ps.setInt(3, puestoId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<EmpleadoPuesto> listarPuestosPorEmpleado(long empleadoId) {
        List<EmpleadoPuesto> list = new ArrayList<>();
        String sql = "SELECT * FROM EMPLEADO_PUESTO WHERE empleado_id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, empleadoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmpleadoPuesto ep = new EmpleadoPuesto();
                    ep.setEmpleadoId(rs.getLong("empleado_id"));
                    ep.setPuestoId(rs.getInt("puesto_id"));
                    ep.setFechaAsignacion(rs.getTimestamp("fecha_asignacion"));
                    ep.setFechaFin(rs.getTimestamp("fecha_fin"));
                    ep.setActivo(rs.getBoolean("activo"));
                    list.add(ep);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private Empleado map(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setId(rs.getLong("id"));
        e.setUsuarioId(rs.getLong("usuario_id"));
        e.setNumeroEmpleado(rs.getString("numero_empleado"));
        e.setNombre(rs.getString("nombre"));
        e.setApellidos(rs.getString("apellidos"));
        e.setTelefono(rs.getString("telefono"));
        e.setSalarioActual(rs.getDouble("salario_actual"));
        Timestamp fi = rs.getTimestamp("fecha_ingreso");
        e.setFechaIngreso(fi == null ? null : new java.util.Date(fi.getTime()));

        long tienda = rs.getLong("tienda_id");
        e.setTiendaId(rs.wasNull() ? null : tienda);

        long supervisor = rs.getLong("supervisor_id");
        e.setSupervisorId(rs.wasNull() ? null : supervisor);

        e.setActivo(rs.getBoolean("activo"));
        return e;
    }

    public boolean existeTelefono(String telefono) {
        String sql = "SELECT id FROM EMPLEADO WHERE telefono=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generarSiguienteNumeroEmpleado() {
        String sql = "SELECT MAX(numero_empleado) FROM EMPLEADO";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String ultimo = rs.getString(1);
                if (ultimo != null) {
                    // Suponiendo que numero_empleado son códigos tipo "EMP001"
                    int numero = Integer.parseInt(ultimo.replaceAll("\\D", ""));
                    int siguiente = numero + 1;
                    return String.format("EMP%03d", siguiente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "EMP001"; // si no hay empleados, empezar en EMP001
    }

    public Empleado obtenerPorUsuarioId(long usuarioId) {
        String sql = "SELECT * FROM EMPLEADO WHERE usuario_id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, usuarioId);
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
    public void actualizar(Usuario u) {
    String sql = "UPDATE USUARIO SET username=?, password_hash=?, email=?, tipo_usuario=?, activo=? WHERE id=?";
    try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPasswordHash());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getTipoUsuario());
        ps.setBoolean(5, u.isActivo());
        ps.setLong(6, u.getId());
        ps.executeUpdate();

        // 🔑 Si ya no es REPARTIDOR, desactivar asignaciones
        if (!"REPARTIDOR".equalsIgnoreCase(u.getTipoUsuario())) {
            EmpleadoController ec = new EmpleadoController();
            Empleado emp = ec.obtenerPorUsuarioId(u.getId());
            if (emp != null) {
                try (PreparedStatement ps2 = cn.prepareStatement(
                        "UPDATE ZONA_REPARTIDOR SET activo=0 WHERE repartidor_id=?")) {
                    ps2.setLong(1, emp.getId()); // 👈 Ojo: aquí usas el ID del EMPLEADO, no del usuario
                    ps2.executeUpdate();
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    


}
