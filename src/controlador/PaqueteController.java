/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Diego Quiroga
 */

import conexion.Conexion;
import modelo.Paquete;
import java.sql.*;
import java.util.*;

public class PaqueteController {
 
    public void insertar(Paquete p) {
        String sql = "INSERT INTO PAQUETE (codigo_paquete, descripcion, peso, largo, ancho, alto, tipo_contenido, fragil, valor_declarado, requiere_seguro, observaciones, fecha_creacion, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, p.getCodigoPaquete());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPeso());
            ps.setDouble(4, p.getLargo());
            ps.setDouble(5, p.getAncho());
            ps.setDouble(6, p.getAlto());
            ps.setString(7, p.getTipoContenido());
            ps.setBoolean(8, p.isFragil());
            ps.setDouble(9, p.getValorDeclarado());
            ps.setBoolean(10, p.isRequiereSeguro());
            ps.setString(11, p.getObservaciones());
            ps.setTimestamp(12, p.getFechaCreacion() == null ? null : new Timestamp(p.getFechaCreacion().getTime()));
            ps.setBoolean(13, p.isActivo());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Paquete obtenerPorId(long id) {
        String sql = "SELECT * FROM PAQUETE WHERE id=?";
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

    public List<Paquete> listar() {
        List<Paquete> list = new ArrayList<>();
        String sql = "SELECT * FROM PAQUETE";
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

    // Listar solo paquetes activos
    public List<Paquete> listarActivos() {
        List<Paquete> list = new ArrayList<>();
        String sql = "SELECT * FROM PAQUETE WHERE activo=TRUE";
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

    // Buscar paquete por código
    public Paquete obtenerPorCodigo(String codigoPaquete) {
        String sql = "SELECT * FROM PAQUETE WHERE codigo_paquete=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigoPaquete);
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

    public void actualizar(Paquete p) {
        String sql = "UPDATE PAQUETE SET codigo_paquete=?, descripcion=?, peso=?, largo=?, ancho=?, alto=?, tipo_contenido=?, fragil=?, valor_declarado=?, requiere_seguro=?, observaciones=?, activo=? WHERE id=?";
        
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, p.getCodigoPaquete());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPeso());
            ps.setDouble(4, p.getLargo());
            ps.setDouble(5, p.getAncho());
            ps.setDouble(6, p.getAlto());
            ps.setString(7, p.getTipoContenido());
            ps.setBoolean(8, p.isFragil());
            ps.setDouble(9, p.getValorDeclarado());
            ps.setBoolean(10, p.isRequiereSeguro());
            ps.setString(11, p.getObservaciones());
            ps.setBoolean(12, p.isActivo());
            ps.setLong(13, p.getId());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //  Desactivar paquete
    public void desactivar(long id) {
        String sql = "UPDATE PAQUETE SET activo=FALSE WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM PAQUETE WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //  Verificar si existe código de paquete
    public boolean existeCodigoPaquete(String codigo) {
        String sql = "SELECT id FROM PAQUETE WHERE codigo_paquete=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Generar siguiente código de paquete
    public String generarSiguienteCodigoPaquete() {
        String sql = "SELECT MAX(CAST(SUBSTRING(codigo_paquete, 4) AS UNSIGNED)) FROM PAQUETE";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int ultimo = rs.getInt(1);
                return String.format("PAQ%03d", ultimo + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "PAQ001"; // si no hay paquetes, empezar en PAQ001
    }

    // Buscar paquetes por tipo de contenido
    public List<Paquete> buscarPorTipoContenido(String tipoContenido) {
        List<Paquete> list = new ArrayList<>();
        String sql = "SELECT * FROM PAQUETE WHERE tipo_contenido=? AND activo=TRUE";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, tipoContenido);
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

    private Paquete map(ResultSet rs) throws SQLException {
        Paquete p = new Paquete();
        p.setId(rs.getLong("id"));
        p.setCodigoPaquete(rs.getString("codigo_paquete"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPeso(rs.getDouble("peso"));
        p.setLargo(rs.getDouble("largo"));
        p.setAncho(rs.getDouble("ancho"));
        p.setAlto(rs.getDouble("alto"));
        p.setTipoContenido(rs.getString("tipo_contenido"));
        p.setFragil(rs.getBoolean("fragil"));
        p.setValorDeclarado(rs.getDouble("valor_declarado"));
        p.setRequiereSeguro(rs.getBoolean("requiere_seguro"));
        p.setObservaciones(rs.getString("observaciones"));
        
        Timestamp fc = rs.getTimestamp("fecha_creacion");
        p.setFechaCreacion(fc == null ? null : new java.util.Date(fc.getTime()));
        p.setActivo(rs.getBoolean("activo"));
        
        return p;
    }
    
    
    public List<Map<String, Object>> reportePorTipoContenido() {
    List<Map<String, Object>> resultados = new ArrayList<>();
    String sql = """
        SELECT tipo_contenido, 
               COUNT(*) as cantidad,
               SUM(peso) as peso_total,
               AVG(peso) as peso_promedio,
               SUM(valor_declarado) as valor_total,
               SUM(largo * ancho * alto) as volumen_total
        FROM PAQUETE 
        WHERE activo = TRUE 
        GROUP BY tipo_contenido 
        ORDER BY cantidad DESC
    """;
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Map<String, Object> fila = new HashMap<>();
            fila.put("tipo_contenido", rs.getString("tipo_contenido"));
            fila.put("cantidad", rs.getInt("cantidad"));
            fila.put("peso_total", rs.getDouble("peso_total"));
            fila.put("peso_promedio", rs.getDouble("peso_promedio"));
            fila.put("valor_total", rs.getDouble("valor_total"));
            fila.put("volumen_total", rs.getDouble("volumen_total"));
            resultados.add(fila);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}

// Reporte de estado (activo/inactivo)
public Map<String, Integer> reportePorEstado() {
    Map<String, Integer> resultados = new HashMap<>();
    String sql = "SELECT activo, COUNT(*) as cantidad FROM PAQUETE GROUP BY activo";
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String estado = rs.getBoolean("activo") ? "Activos" : "Inactivos";
            resultados.put(estado, rs.getInt("cantidad"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}

// Reporte de paquetes frágiles
public Map<String, Integer> reportePorFragilidad() {
    Map<String, Integer> resultados = new HashMap<>();
    String sql = "SELECT fragil, COUNT(*) as cantidad FROM PAQUETE WHERE activo = TRUE GROUP BY fragil";
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String tipo = rs.getBoolean("fragil") ? "Frágiles" : "Normales";
            resultados.put(tipo, rs.getInt("cantidad"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}

// Reporte de estadísticas generales
public Map<String, Object> reporteEstadisticasGenerales() {
    Map<String, Object> stats = new HashMap<>();
    String sql = """
        SELECT COUNT(*) as total_paquetes,
               SUM(peso) as peso_total,
               AVG(peso) as peso_promedio,
               MAX(peso) as peso_maximo,
               MIN(peso) as peso_minimo,
               SUM(valor_declarado) as valor_total,
               AVG(valor_declarado) as valor_promedio,
               SUM(largo * ancho * alto) as volumen_total,
               COUNT(CASE WHEN fragil = TRUE THEN 1 END) as total_fragiles,
               COUNT(CASE WHEN requiere_seguro = TRUE THEN 1 END) as total_con_seguro
        FROM PAQUETE 
        WHERE activo = TRUE
    """;
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            stats.put("total_paquetes", rs.getInt("total_paquetes"));
            stats.put("peso_total", rs.getDouble("peso_total"));
            stats.put("peso_promedio", rs.getDouble("peso_promedio"));
            stats.put("peso_maximo", rs.getDouble("peso_maximo"));
            stats.put("peso_minimo", rs.getDouble("peso_minimo"));
            stats.put("valor_total", rs.getDouble("valor_total"));
            stats.put("valor_promedio", rs.getDouble("valor_promedio"));
            stats.put("volumen_total", rs.getDouble("volumen_total"));
            stats.put("total_fragiles", rs.getInt("total_fragiles"));
            stats.put("total_con_seguro", rs.getInt("total_con_seguro"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return stats;
}

// Reporte de paquetes por rango de fechas
public List<Map<String, Object>> reportePorMes() {
    List<Map<String, Object>> resultados = new ArrayList<>();
    String sql = """
        SELECT DATE_FORMAT(fecha_creacion, '%Y-%m') as mes,
               COUNT(*) as cantidad,
               SUM(peso) as peso_total,
               SUM(valor_declarado) as valor_total
        FROM PAQUETE 
        WHERE activo = TRUE 
        GROUP BY DATE_FORMAT(fecha_creacion, '%Y-%m')
        ORDER BY mes DESC
        LIMIT 12
    """;
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Map<String, Object> fila = new HashMap<>();
            fila.put("mes", rs.getString("mes"));
            fila.put("cantidad", rs.getInt("cantidad"));
            fila.put("peso_total", rs.getDouble("peso_total"));
            fila.put("valor_total", rs.getDouble("valor_total"));
            resultados.add(fila);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}

public void insertarPaqueteEnvio(Paquete paquete, long envioId) {
    String sql = "INSERT INTO ENVIO_PAQUETE (envio_id, paquete_id) VALUES (?, ?)";
    
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        
        ps.setLong(1, envioId);
        ps.setLong(2, paquete.getId());
        
        ps.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}

