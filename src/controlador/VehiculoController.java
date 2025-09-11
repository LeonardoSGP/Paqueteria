package controlador;

import conexion.Conexion;
import modelo.Vehiculo;
import java.sql.*;
import java.util.*;

public class VehiculoController {

    public void insertar(Vehiculo v) {
        String sql = "INSERT INTO VEHICULO (placas, marca, modelo, capacidad_carga, disponible, activo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getPlacas());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setDouble(4, v.getCapacidadCarga());
            ps.setBoolean(5, v.isDisponible());
            ps.setBoolean(6, v.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) v.setId(rs.getLong(1)); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Vehiculo obtenerPorId(long id) {
        String sql = "SELECT * FROM VEHICULO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Vehiculo> listar() {
        List<Vehiculo> list = new ArrayList<>();
        String sql = "SELECT * FROM VEHICULO";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void actualizar(Vehiculo v) {
        String sql = "UPDATE VEHICULO SET placas=?, marca=?, modelo=?, capacidad_carga=?, disponible=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, v.getPlacas());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setDouble(4, v.getCapacidadCarga());
            ps.setBoolean(5, v.isDisponible());
            ps.setBoolean(6, v.isActivo());
            ps.setLong(7, v.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM VEHICULO WHERE id=?";
        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Vehiculo map(ResultSet rs) throws SQLException {
        Vehiculo v = new Vehiculo();
        v.setId(rs.getLong("id"));
        v.setPlacas(rs.getString("placas"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setCapacidadCarga(rs.getDouble("capacidad_carga"));
        v.setDisponible(rs.getBoolean("disponible"));
        v.setActivo(rs.getBoolean("activo"));
        return v;
    }
}
