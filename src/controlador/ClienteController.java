package controlador;

import conexion.Conexion;
import modelo.Cliente;
import modelo.DireccionCliente;

import java.sql.*;
import java.util.*;

public class ClienteController {

    public void insertar(Cliente c) {
        String sql = "INSERT INTO CLIENTE (usuario_id, codigo_cliente, nombre, apellidos, telefono, credito_disponible, descuento_asignado, fecha_registro, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // usuario_id puede ser null
            if (c.getUsuarioId() != null) {
                ps.setLong(1, c.getUsuarioId());
            } else {
                ps.setNull(1, java.sql.Types.BIGINT);
            }

            ps.setString(2, c.getCodigoCliente());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellidos());
            ps.setString(5, c.getTelefono());
            ps.setDouble(6, c.getCreditoDisponible());
            ps.setDouble(7, c.getDescuentoAsignado());
            ps.setTimestamp(8, c.getFechaRegistro() == null ? null : new Timestamp(c.getFechaRegistro().getTime()));
            ps.setBoolean(9, c.isActivo());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente obtenerPorId(long id) {
        String sql = "SELECT * FROM CLIENTE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
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

    public List<Cliente> listar() {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Cliente> listarActivos() {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE WHERE activo=TRUE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Cliente obtenerPorCodigo(String codigoCliente) {
        String sql = "SELECT * FROM CLIENTE WHERE codigo_cliente=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigoCliente);
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

    public void actualizar(Cliente c) {
        String sql = "UPDATE CLIENTE SET usuario_id=?, codigo_cliente=?, nombre=?, apellidos=?, telefono=?, credito_disponible=?, descuento_asignado=?, fecha_registro=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            if (c.getUsuarioId() != null) {
                ps.setLong(1, c.getUsuarioId());
            } else {
                ps.setNull(1, java.sql.Types.BIGINT);
            }

            ps.setString(2, c.getCodigoCliente());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellidos());
            ps.setString(5, c.getTelefono());
            ps.setDouble(6, c.getCreditoDisponible());
            ps.setDouble(7, c.getDescuentoAsignado());
            ps.setTimestamp(8, c.getFechaRegistro() == null ? null : new Timestamp(c.getFechaRegistro().getTime()));
            ps.setBoolean(9, c.isActivo());
            ps.setLong(10, c.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desactivar(long id) {
        String sql = "UPDATE CLIENTE SET activo=FALSE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM CLIENTE WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeTelefono(String telefono) {
        String sql = "SELECT id FROM CLIENTE WHERE telefono=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT c.id FROM CLIENTE c JOIN USUARIO u ON c.usuario_id = u.id WHERE u.email=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generarSiguienteCodigoCliente() {
        String sql = "SELECT MAX(CAST(SUBSTRING(codigo_cliente, 4) AS UNSIGNED)) FROM CLIENTE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int ultimo = rs.getInt(1);
                return String.format("CLI%03d", ultimo + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CLI001";
    }

    // === Map ===
    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        long usuarioId = rs.getLong("usuario_id");
        c.setUsuarioId(rs.wasNull() ? null : usuarioId);
        c.setCodigoCliente(rs.getString("codigo_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellidos(rs.getString("apellidos"));
        c.setTelefono(rs.getString("telefono"));
        c.setCreditoDisponible(rs.getDouble("credito_disponible"));
        c.setDescuentoAsignado(rs.getDouble("descuento_asignado"));
        Timestamp fr = rs.getTimestamp("fecha_registro");
        c.setFechaRegistro(fr == null ? null : new java.util.Date(fr.getTime()));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }
}
