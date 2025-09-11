package controlador;

import conexion.Conexion;
import modelo.Envio;
import modelo.Paquete;
import modelo.EnvioPaquete;
import modelo.MovimientoRastreo;
import java.sql.*;
import java.util.*;

public class EnvioController {

    public void insertar(Envio e) {
        String sql = "INSERT INTO ENVIO (numero_seguimiento, remitente_id, destinatario_id, direccion_origen, direccion_destino, tipo_envio, estado_actual, fecha_creacion, fecha_entrega_estimada, fecha_entrega_real, costo, descuento_aplicado, costo_final, repartidor_id, tienda_origen_id, tienda_destino_id, intentos_entrega, motivo_devolucion, fecha_devolucion, prioridad, observaciones) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i=1;
            ps.setString(i++, e.getNumeroSeguimiento());
            ps.setLong(i++, e.getRemitenteId());
            ps.setLong(i++, e.getDestinatarioId());
            ps.setString(i++, e.getDireccionOrigen());
            ps.setString(i++, e.getDireccionDestino());
            ps.setString(i++, e.getTipoEnvio());
            ps.setString(i++, e.getEstadoActual());
            ps.setTimestamp(i++, e.getFechaCreacion()==null? null : new Timestamp(e.getFechaCreacion().getTime()));
            ps.setTimestamp(i++, e.getFechaEntregaEstimada()==null? null : new Timestamp(e.getFechaEntregaEstimada().getTime()));
            ps.setTimestamp(i++, e.getFechaEntregaReal()==null? null : new Timestamp(e.getFechaEntregaReal().getTime()));
            ps.setDouble(i++, e.getCosto());
            ps.setDouble(i++, e.getDescuentoAplicado());
            ps.setDouble(i++, e.getCostoFinal());
            ps.setLong(i++, e.getRepartidorId());
            ps.setLong(i++, e.getTiendaOrigenId());
            ps.setLong(i++, e.getTiendaDestinoId());
            ps.setInt(i++, e.getIntentosEntrega());
            ps.setString(i++, e.getMotivoDevolucion());
            ps.setTimestamp(i++, e.getFechaDevolucion()==null? null : new Timestamp(e.getFechaDevolucion().getTime()));
            ps.setInt(i++, e.getPrioridad());
            ps.setString(i++, e.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) e.setId(rs.getLong(1)); }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public Envio obtenerPorId(long id) {
        String sql = "SELECT * FROM ENVIO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    public List<Envio> listar() {
        List<Envio> list = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public void actualizar(Envio e) {
        String sql = "UPDATE ENVIO SET numero_seguimiento=?, remitente_id=?, destinatario_id=?, direccion_origen=?, direccion_destino=?, tipo_envio=?, estado_actual=?, fecha_creacion=?, fecha_entrega_estimada=?, fecha_entrega_real=?, costo=?, descuento_aplicado=?, costo_final=?, repartidor_id=?, tienda_origen_id=?, tienda_destino_id=?, intentos_entrega=?, motivo_devolucion=?, fecha_devolucion=?, prioridad=?, observaciones=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            int i=1;
            ps.setString(i++, e.getNumeroSeguimiento());
            ps.setLong(i++, e.getRemitenteId());
            ps.setLong(i++, e.getDestinatarioId());
            ps.setString(i++, e.getDireccionOrigen());
            ps.setString(i++, e.getDireccionDestino());
            ps.setString(i++, e.getTipoEnvio());
            ps.setString(i++, e.getEstadoActual());
            ps.setTimestamp(i++, e.getFechaCreacion()==null? null : new Timestamp(e.getFechaCreacion().getTime()));
            ps.setTimestamp(i++, e.getFechaEntregaEstimada()==null? null : new Timestamp(e.getFechaEntregaEstimada().getTime()));
            ps.setTimestamp(i++, e.getFechaEntregaReal()==null? null : new Timestamp(e.getFechaEntregaReal().getTime()));
            ps.setDouble(i++, e.getCosto());
            ps.setDouble(i++, e.getDescuentoAplicado());
            ps.setDouble(i++, e.getCostoFinal());
            ps.setLong(i++, e.getRepartidorId());
            ps.setLong(i++, e.getTiendaOrigenId());
            ps.setLong(i++, e.getTiendaDestinoId());
            ps.setInt(i++, e.getIntentosEntrega());
            ps.setString(i++, e.getMotivoDevolucion());
            ps.setTimestamp(i++, e.getFechaDevolucion()==null? null : new Timestamp(e.getFechaDevolucion().getTime()));
            ps.setInt(i++, e.getPrioridad());
            ps.setString(i++, e.getObservaciones());
            ps.setLong(i++, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM ENVIO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    // ENVIO_PAQUETE (M:N)
    public void agregarPaquete(long envioId, long paqueteId, java.util.Date fechaAgregado) {
        String sql = "INSERT INTO ENVIO_PAQUETE (envio_id, paquete_id, fecha_agregado) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE fecha_agregado=VALUES(fecha_agregado)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, envioId);
            ps.setLong(2, paqueteId);
            ps.setTimestamp(3, fechaAgregado==null? null : new Timestamp(fechaAgregado.getTime()));
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void quitarPaquete(long envioId, long paqueteId) {
        String sql = "DELETE FROM ENVIO_PAQUETE WHERE envio_id=? AND paquete_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, envioId);
            ps.setLong(2, paqueteId);
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public List<EnvioPaquete> listarPaquetes(long envioId) {
        List<EnvioPaquete> list = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO_PAQUETE WHERE envio_id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, envioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EnvioPaquete ep = new EnvioPaquete();
                    ep.setEnvioId(rs.getLong("envio_id"));
                    ep.setPaqueteId(rs.getLong("paquete_id"));
                    ep.setFechaAgregado(rs.getTimestamp("fecha_agregado"));
                    list.add(ep);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    // Movimientos de rastreo
    public void agregarMovimiento(MovimientoRastreo m) {
        String sql = "INSERT INTO MOVIMIENTO_RASTREO (envio_id, fecha_movimiento, ubicacion, descripcion, estado_anterior, estado_nuevo, empleado_responsable_id, tienda_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, m.getEnvioId());
            ps.setTimestamp(2, m.getFechaMovimiento()==null? null : new Timestamp(m.getFechaMovimiento().getTime()));
            ps.setString(3, m.getUbicacion());
            ps.setString(4, m.getDescripcion());
            ps.setString(5, m.getEstadoAnterior());
            ps.setString(6, m.getEstadoNuevo());
            ps.setLong(7, m.getEmpleadoResponsableId());
            ps.setLong(8, m.getTiendaId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) m.setId(rs.getLong(1)); }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private Envio map(ResultSet rs) throws SQLException {
        Envio e = new Envio();
        e.setId(rs.getLong("id"));
        e.setNumeroSeguimiento(rs.getString("numero_seguimiento"));
        e.setRemitenteId(rs.getLong("remitente_id"));
        e.setDestinatarioId(rs.getLong("destinatario_id"));
        e.setDireccionOrigen(rs.getString("direccion_origen"));
        e.setDireccionDestino(rs.getString("direccion_destino"));
        e.setTipoEnvio(rs.getString("tipo_envio"));
        e.setEstadoActual(rs.getString("estado_actual"));
        e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        e.setFechaEntregaEstimada(rs.getTimestamp("fecha_entrega_estimada"));
        e.setFechaEntregaReal(rs.getTimestamp("fecha_entrega_real"));
        e.setCosto(rs.getDouble("costo"));
        e.setDescuentoAplicado(rs.getDouble("descuento_aplicado"));
        e.setCostoFinal(rs.getDouble("costo_final"));
        e.setRepartidorId(rs.getLong("repartidor_id"));
        e.setTiendaOrigenId(rs.getLong("tienda_origen_id"));
        e.setTiendaDestinoId(rs.getLong("tienda_destino_id"));
        e.setIntentosEntrega(rs.getInt("intentos_entrega"));
        e.setMotivoDevolucion(rs.getString("motivo_devolucion"));
        e.setFechaDevolucion(rs.getTimestamp("fecha_devolucion"));
        e.setPrioridad(rs.getInt("prioridad"));
        e.setObservaciones(rs.getString("observaciones"));
        return e;
    }
}
