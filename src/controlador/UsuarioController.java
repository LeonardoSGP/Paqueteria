package controlador;

import conexion.Conexion;
import modelo.Usuario;
import java.sql.*;
import java.util.*;

public class UsuarioController {

    public void insertar(Usuario u) {
        String sql = "INSERT INTO USUARIO (username, password_hash, email, tipo_usuario, fecha_creacion, ultimo_acceso, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTipoUsuario());
            ps.setTimestamp(5, u.getFechaCreacion() == null ? null : new Timestamp(u.getFechaCreacion().getTime()));
            ps.setTimestamp(6, u.getUltimoAcceso() == null ? null : new Timestamp(u.getUltimoAcceso().getTime()));
            ps.setBoolean(7, u.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario obtenerPorId(long id) {
        String sql = "SELECT * FROM USUARIO WHERE id=?";
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

    public List<Usuario> listar() {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void actualizar(Usuario u) {
        String sql = "UPDATE USUARIO SET username=?, password_hash=?, email=?, tipo_usuario=?, fecha_creacion=?, ultimo_acceso=?, activo=? WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPasswordHash());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTipoUsuario());
            ps.setTimestamp(5, u.getFechaCreacion() == null ? null : new Timestamp(u.getFechaCreacion().getTime()));
            ps.setTimestamp(6, u.getUltimoAcceso() == null ? null : new Timestamp(u.getUltimoAcceso().getTime()));
            ps.setBoolean(7, u.isActivo());
            ps.setLong(8, u.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(long id) {
        String sql = "DELETE FROM USUARIO WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Usuario map(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setEmail(rs.getString("email"));
        u.setTipoUsuario(rs.getString("tipo_usuario"));
        Timestamp fc = rs.getTimestamp("fecha_creacion");
        Timestamp ua = rs.getTimestamp("ultimo_acceso");
        u.setFechaCreacion(fc == null ? null : new java.util.Date(fc.getTime()));
        u.setUltimoAcceso(ua == null ? null : new java.util.Date(ua.getTime()));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }

    public Usuario login(String username, String password) {
        String sql = "SELECT * FROM USUARIO WHERE username=? AND password_hash=? AND activo=TRUE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // 👈 aquí luego deberías usar hash real
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setEmail(rs.getString("email"));
                    u.setTipoUsuario(rs.getString("tipo_usuario"));
                    u.setActivo(rs.getBoolean("activo"));
                    return u;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void crearGerentePorDefecto() {
        String username = "gerente";
        String password = "Messi10";
        String email = "gerente@sistema.com";

        String sqlCheck = "SELECT id FROM USUARIO WHERE username=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sqlCheck)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                // no existe, lo creamos
                Usuario u = new Usuario();
                u.setUsername(username);
                u.setPasswordHash(password);
                u.setEmail(email);
                u.setTipoUsuario("GERENTE");
                u.setFechaCreacion(new java.util.Date());
                u.setUltimoAcceso(null);
                u.setActivo(true);

                insertar(u);
                System.out.println("Usuario gerente creado con éxito (user: " + username + ", pass: " + password + ")");
            } else {
                System.out.println("Usuario gerente ya existe, no se creó de nuevo.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario loginPorRol(String tipoUsuario, String password) {
        String sql = "SELECT * FROM USUARIO WHERE tipo_usuario=? AND password_hash=? AND activo=TRUE";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, tipoUsuario);
            ps.setString(2, password);
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

    public void actualizarUltimoAcceso(long userId) {
        String sql = "UPDATE USUARIO SET ultimo_acceso = NOW() WHERE id=?";
        try (Connection cn = Conexion.conectar(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
}
    public boolean existeUsername(String username) {
    String sql = "SELECT 1 FROM USUARIO WHERE username=?";
    try (Connection cn = Conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
