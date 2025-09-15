package controlador;


import conexion.Conexion;
import modelo.Envio;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class EnvioController {

    public void insertar(Envio e) {
        String sql = """
            INSERT INTO ENVIO (numero_seguimiento, remitente_id, destinatario_id, direccion_origen, 
            direccion_destino, tipo_envio, estado_actual, fecha_creacion, fecha_entrega_estimada, 
            costo, descuento_aplicado, costo_final, tienda_origen_id, prioridad, observaciones) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, e.getNumeroSeguimiento());
            ps.setLong(2, e.getRemitenteId());
            ps.setLong(3, e.getDestinatarioId());
            ps.setString(4, e.getDireccionOrigen());
            ps.setString(5, e.getDireccionDestino());
            ps.setString(6, e.getTipoEnvio());
            ps.setString(7, e.getEstadoActual());
            ps.setTimestamp(8, e.getFechaCreacion() == null ? null : new Timestamp(e.getFechaCreacion().getTime()));
            ps.setTimestamp(9, e.getFechaEntregaEstimada() == null ? null : new Timestamp(e.getFechaEntregaEstimada().getTime()));
            ps.setDouble(10, e.getCosto());
            ps.setDouble(11, e.getDescuentoAplicado());
            ps.setDouble(12, e.getCostoFinal());
            
            if (e.getTiendaOrigenId() != null) {
                ps.setLong(13, e.getTiendaOrigenId());
            } else {
                ps.setNull(13, java.sql.Types.BIGINT);
            }
            
            ps.setInt(14, e.getPrioridad());
            ps.setString(15, e.getObservaciones());
            
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

    public Envio obtenerPorId(long id) {
        String sql = "SELECT * FROM ENVIO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
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

    public Envio obtenerPorNumeroSeguimiento(String numeroSeguimiento) {
        String sql = "SELECT * FROM ENVIO WHERE numero_seguimiento=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numeroSeguimiento);
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

    public List<Envio> listar() {
        List<Envio> list = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO ORDER BY fecha_creacion DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Envio> listarPorEstado(String estado) {
        List<Envio> list = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO WHERE estado_actual=? ORDER BY fecha_creacion DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estado);
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

    public List<Envio> listarPorCliente(long clienteId, String tipoCliente) {
        List<Envio> list = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO WHERE " + 
                    ("remitente".equals(tipoCliente) ? "remitente_id" : "destinatario_id") + 
                    "=? ORDER BY fecha_creacion DESC";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, clienteId);
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

    public void actualizarEstado(long envioId, String nuevoEstado) {
        String sql = "UPDATE ENVIO SET estado_actual=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setLong(2, envioId);
            ps.executeUpdate();
            
            // Registrar movimiento en tabla de rastreo
            registrarMovimientoRastreo(envioId, "Cambio de estado", "", nuevoEstado, null, null);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void marcarComoEntregado(long envioId, Date fechaEntrega) {
        String sql = "UPDATE ENVIO SET estado_actual='ENTREGADO', fecha_entrega_real=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(fechaEntrega.getTime()));
            ps.setLong(2, envioId);
            ps.executeUpdate();
            
            registrarMovimientoRastreo(envioId, "Paquete entregado", "", "ENTREGADO", null, null);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void asignarRepartidor(long envioId, long repartidorId) {
        String sql = "UPDATE ENVIO SET repartidor_id=?, estado_actual='ASIGNADO' WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, repartidorId);
            ps.setLong(2, envioId);
            ps.executeUpdate();
            
            registrarMovimientoRastreo(envioId, "Asignado a repartidor", "", "ASIGNADO", null, null);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void registrarMovimientoRastreo(long envioId, String descripcion, String estadoAnterior, 
                                          String estadoNuevo, Long empleadoId, Long tiendaId) {
        String sql = """
            INSERT INTO MOVIMIENTO_RASTREO (envio_id, fecha_movimiento, ubicacion, descripcion, 
            estado_anterior, estado_nuevo, empleado_responsable_id, tienda_id) 
            VALUES (?, NOW(), 'Sistema', ?, ?, ?, ?, ?)
        """;
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, envioId);
            ps.setString(2, descripcion);
            ps.setString(3, estadoAnterior);
            ps.setString(4, estadoNuevo);
            
            if (empleadoId != null) {
                ps.setLong(5, empleadoId);
            } else {
                ps.setNull(5, java.sql.Types.BIGINT);
            }
            
            if (tiendaId != null) {
                ps.setLong(6, tiendaId);
            } else {
                ps.setNull(6, java.sql.Types.BIGINT);
            }
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

  public String generarSiguienteNumeroSeguimiento() {
    String sql = "SELECT MAX(CAST(SUBSTRING(numero_seguimiento, 4) AS UNSIGNED)) FROM ENVIO";
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {          
            long ultimo = rs.getLong(1);
            if (rs.wasNull()) {
                return "ENV000001";
            }
            long siguiente = ultimo + 1;
            return String.format("ENV%06d", siguiente);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
  
    return "ENV000001"; 
}


    public double calcularCosto(String tipoEnvio, double peso, int prioridad) {
        // Lógica básica de cálculo de costo
        double costoBase = 50.0; // Costo base
        
        // Multiplicador por tipo de envío
        double multiplicadorTipo = switch (tipoEnvio) {
            case "EXPRESS" -> 2.0;
            case "NORMAL" -> 1.0;
            case "ECONOMICO" -> 0.7;
            default -> 1.0;
        };
        
        // Multiplicador por peso
        double costoLPeso = peso * 15.0; // $15 por kg
        
        // Multiplicador por prioridad
        double multiplicadorPrioridad = switch (prioridad) {
            case 1 -> 2.5; // Alta prioridad
            case 2 -> 1.5; // Media prioridad  
            case 3 -> 1.0; // Baja prioridad
            default -> 1.0;
        };
        
        return (costoBase + costoLPeso) * multiplicadorTipo * multiplicadorPrioridad;
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
        
        Timestamp fc = rs.getTimestamp("fecha_creacion");
        e.setFechaCreacion(fc == null ? null : new Date(fc.getTime()));
        
        Timestamp fee = rs.getTimestamp("fecha_entrega_estimada");
        e.setFechaEntregaEstimada(fee == null ? null : new Date(fee.getTime()));
        
        Timestamp fer = rs.getTimestamp("fecha_entrega_real");
        e.setFechaEntregaReal(fer == null ? null : new Date(fer.getTime()));
        
        e.setCosto(rs.getDouble("costo"));
        e.setDescuentoAplicado(rs.getDouble("descuento_aplicado"));
        e.setCostoFinal(rs.getDouble("costo_final"));
        
        long repartidor = rs.getLong("repartidor_id");
        e.setRepartidorId(rs.wasNull() ? null : repartidor);
        
        long tiendaOrigen = rs.getLong("tienda_origen_id");
        e.setTiendaOrigenId(rs.wasNull() ? null : tiendaOrigen);
        
        long tiendaDestino = rs.getLong("tienda_destino_id");
        e.setTiendaDestinoId(rs.wasNull() ? null : tiendaDestino);
        
        e.setIntentosEntrega(rs.getInt("intentos_entrega"));
        e.setMotivoDevolucion(rs.getString("motivo_devolucion"));
        
        Timestamp fd = rs.getTimestamp("fecha_devolucion");
        e.setFechaDevolucion(fd == null ? null : new Date(fd.getTime()));
        
        e.setPrioridad(rs.getInt("prioridad"));
        e.setObservaciones(rs.getString("observaciones"));
        
        return e;
    }
}
