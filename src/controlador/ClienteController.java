package controlador;

import conexion.Conexion;
import modelo.Cliente;
import modelo.DireccionCliente;
import java.sql.*;
import java.util.*;

public class ClienteController {

    public void insertar(Cliente c) {
        String sql = "INSERT INTO CLIENTE (usuario_id, codigo_cliente, nombre, apellidos, telefono, tipo_cliente_id, credito_disponible, descuento_asignado, fecha_registro, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getUsuarioId());
            ps.setString(2, c.getCodigoCliente());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellidos());
            ps.setString(5, c.getTelefono());
            ps.setInt(6, c.getTipoClienteId());
            ps.setDouble(7, c.getCreditoDisponible());
            ps.setDouble(8, c.getDescuentoAsignado());
            ps.setTimestamp(9, c.getFechaRegistro()==null? null : new Timestamp(c.getFechaRegistro().getTime()));
            ps.setBoolean(10, c.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Cliente obtenerPorId(long id, boolean incluirDirecciones) {
        String sql = "SELECT * FROM CLIENTE WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = map(rs);
                    if (incluirDirecciones) {
                        c = c; // no-op
                    }
                    return c;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Cliente> listar() {
        List<Cliente> list = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void actualizar(Cliente c) {
        String sql = "UPDATE CLIENTE SET usuario_id=?, codigo_cliente=?, nombre=?, apellidos=?, telefono=?, tipo_cliente_id=?, credito_disponible=?, descuento_asignado=?, fecha_registro=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, c.getUsuarioId());
            ps.setString(2, c.getCodigoCliente());
            ps.setString(3, c.getNombre());
            ps.setString(4, c.getApellidos());
            ps.setString(5, c.getTelefono());
            ps.setInt(6, c.getTipoClienteId());
            ps.setDouble(7, c.getCreditoDisponible());
            ps.setDouble(8, c.getDescuentoAsignado());
            ps.setTimestamp(9, c.getFechaRegistro()==null? null : new Timestamp(c.getFechaRegistro().getTime()));
            ps.setBoolean(10, c.isActivo());
            ps.setLong(11, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM CLIENTE WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Direcciones -----
    public void insertarDireccion(DireccionCliente d) {
        String sql = "INSERT INTO DIRECCION_CLIENTE (cliente_id, calle, numero, colonia, ciudad, estado, codigo_postal, pais, referencias, latitud, longitud, tipo_direccion, principal, activa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, d.getClienteId());
            ps.setString(2, d.getCalle());
            ps.setString(3, d.getNumero());
            ps.setString(4, d.getColonia());
            ps.setString(5, d.getCiudad());
            ps.setString(6, d.getEstado());
            ps.setString(7, d.getCodigoPostal());
            ps.setString(8, d.getPais());
            ps.setString(9, d.getReferencias());
            ps.setDouble(10, d.getLatitud());
            ps.setDouble(11, d.getLongitud());
            ps.setString(12, d.getTipoDireccion());
            ps.setBoolean(13, d.isPrincipal());
            ps.setBoolean(14, d.isActiva());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) d.setId(rs.getLong(1)); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<DireccionCliente> listarDireccionesPorCliente(long clienteId) {
        List<DireccionCliente> list = new ArrayList<>();
        String sql = "SELECT * FROM DIRECCION_CLIENTE WHERE cliente_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapDireccion(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void eliminarDireccion(long id) {
        String sql = "DELETE FROM DIRECCION_CLIENTE WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setUsuarioId(rs.getLong("usuario_id"));
        c.setCodigoCliente(rs.getString("codigo_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setApellidos(rs.getString("apellidos"));
        c.setTelefono(rs.getString("telefono"));
        c.setTipoClienteId(rs.getInt("tipo_cliente_id"));
        c.setCreditoDisponible(rs.getDouble("credito_disponible"));
        c.setDescuentoAsignado(rs.getDouble("descuento_asignado"));
        Timestamp fr = rs.getTimestamp("fecha_registro");
        c.setFechaRegistro(fr==null? null : new java.util.Date(fr.getTime()));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }
    private DireccionCliente mapDireccion(ResultSet rs) throws SQLException {
        DireccionCliente d = new DireccionCliente();
        d.setId(rs.getLong("id"));
        d.setClienteId(rs.getLong("cliente_id"));
        d.setCalle(rs.getString("calle"));
        d.setNumero(rs.getString("numero"));
        d.setColonia(rs.getString("colonia"));
        d.setCiudad(rs.getString("ciudad"));
        d.setEstado(rs.getString("estado"));
        d.setCodigoPostal(rs.getString("codigo_postal"));
        d.setPais(rs.getString("pais"));
        d.setReferencias(rs.getString("referencias"));
        d.setLatitud(rs.getDouble("latitud"));
        d.setLongitud(rs.getDouble("longitud"));
        d.setTipoDireccion(rs.getString("tipo_direccion"));
        d.setPrincipal(rs.getBoolean("principal"));
        d.setActiva(rs.getBoolean("activa"));
        return d;
    }
}
