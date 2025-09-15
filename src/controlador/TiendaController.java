package controlador;

import conexion.Conexion;
import modelo.Tienda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiendaController {

    public void insertar(Tienda t) {
        String sql = "INSERT INTO TIENDA (codigo_tienda, nombre_tienda, direccion_completa, telefono, email, gerente_id, horario_apertura, horario_cierre, capacidad_almacen, activa) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getCodigoTienda());
            ps.setString(2, t.getNombreTienda());
            ps.setString(3, t.getDireccionCompleta());
            ps.setString(4, t.getTelefono());
            ps.setString(5, t.getEmail());

            // gerente_id puede ser opcional
            if (t.getGerenteId() == 0) {
                ps.setNull(6, Types.BIGINT);
            } else {
                ps.setLong(6, t.getGerenteId());
            }

            // horarios pueden ser null
            if (t.getHorarioApertura() == null) {
                ps.setNull(7, Types.TIME);
            } else {
                ps.setTime(7, t.getHorarioApertura());
            }

            if (t.getHorarioCierre() == null) {
                ps.setNull(8, Types.TIME);
            } else {
                ps.setTime(8, t.getHorarioCierre());
            }

            ps.setInt(9, t.getCapacidadAlmacen());
            ps.setBoolean(10, t.isActiva());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tienda> listar() {
        List<Tienda> lista = new ArrayList<>();
        String sql = "SELECT * FROM TIENDA";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Tienda t = new Tienda();
                t.setId(rs.getLong("id"));
                t.setCodigoTienda(rs.getString("codigo_tienda"));
                t.setNombreTienda(rs.getString("nombre_tienda"));
                t.setDireccionCompleta(rs.getString("direccion_completa"));
                t.setTelefono(rs.getString("telefono"));
                t.setEmail(rs.getString("email"));
                t.setGerenteId(rs.getLong("gerente_id"));
                t.setHorarioApertura(rs.getTime("horario_apertura"));
                t.setHorarioCierre(rs.getTime("horario_cierre"));
                t.setCapacidadAlmacen(rs.getInt("capacidad_almacen"));
                t.setActiva(rs.getBoolean("activa"));
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Tienda obtenerPorId(long id) {
        String sql = "SELECT * FROM TIENDA WHERE id = ?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tienda t = new Tienda();
                    t.setId(rs.getLong("id"));
                    t.setCodigoTienda(rs.getString("codigo_tienda"));
                    t.setNombreTienda(rs.getString("nombre_tienda"));
                    t.setDireccionCompleta(rs.getString("direccion_completa"));
                    t.setTelefono(rs.getString("telefono"));
                    t.setEmail(rs.getString("email"));
                    t.setGerenteId(rs.getLong("gerente_id"));
                    t.setHorarioApertura(rs.getTime("horario_apertura"));
                    t.setHorarioCierre(rs.getTime("horario_cierre"));
                    t.setCapacidadAlmacen(rs.getInt("capacidad_almacen"));
                    t.setActiva(rs.getBoolean("activa"));
                    return t;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String obtenerUltimoCodigo() {
        String sql = "SELECT codigo_tienda FROM TIENDA ORDER BY id DESC LIMIT 1";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("codigo_tienda");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 NUEVO: actualizar tienda existente
    public void actualizar(Tienda t) {
        String sql = "UPDATE TIENDA SET nombre_tienda=?, direccion_completa=?, telefono=?, email=?, gerente_id=?, horario_apertura=?, horario_cierre=?, capacidad_almacen=?, activa=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, t.getNombreTienda());
            ps.setString(2, t.getDireccionCompleta());
            ps.setString(3, t.getTelefono());
            ps.setString(4, t.getEmail());

            if (t.getGerenteId() == 0) {
                ps.setNull(5, Types.BIGINT);
            } else {
                ps.setLong(5, t.getGerenteId());
            }

            if (t.getHorarioApertura() == null) {
                ps.setNull(6, Types.TIME);
            } else {
                ps.setTime(6, t.getHorarioApertura());
            }

            if (t.getHorarioCierre() == null) {
                ps.setNull(7, Types.TIME);
            } else {
                ps.setTime(7, t.getHorarioCierre());
            }

            ps.setInt(8, t.getCapacidadAlmacen());
            ps.setBoolean(9, t.isActiva());
            ps.setLong(10, t.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
