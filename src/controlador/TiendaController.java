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
}
