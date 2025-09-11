package vista;

import controlador.UsuarioController;
import controlador.ClienteController;
import controlador.EmpleadoController;
import controlador.RepartidorInfoController;
import controlador.VehiculoController;
import controlador.RepartidorVehiculoController;
import controlador.EnvioController;
import controlador.TiendaController;   // 👈 asegúrate de tener este
import modelo.Usuario;
import modelo.Cliente;
import modelo.Empleado;
import modelo.RepartidorInfo;
import modelo.Vehiculo;
import modelo.RepartidorVehiculo;
import modelo.Envio;
import modelo.Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import conexion.Conexion;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Verificar o crear un tipo de cliente base
            int tipoClienteId = 1;
            try (Connection cn = Conexion.conectar()) {
                PreparedStatement ps = cn.prepareStatement("SELECT id FROM TIPO_CLIENTE WHERE id=?");
                ps.setInt(1, tipoClienteId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    ps = cn.prepareStatement(
                        "INSERT INTO TIPO_CLIENTE (nombre, descripcion, descuento_default, credito_limite, activo) " +
                        "VALUES ('Normal', 'Cliente básico', 0, 0, TRUE)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                    );
                    ps.executeUpdate();
                    ResultSet rs2 = ps.getGeneratedKeys();
                    if (rs2.next()) {
                        tipoClienteId = rs2.getInt(1);
                    }
                }
            }

            // 2. Insertar usuario
            UsuarioController uc = new UsuarioController();
            Usuario u = new Usuario();
            u.setUsername("demo" + System.currentTimeMillis());
            u.setPasswordHash("hash");
            u.setEmail("demo" + System.currentTimeMillis() + "@example.com");
            u.setTipoUsuario("ADMIN");
            u.setActivo(true);
            uc.insertar(u);
            System.out.println("Usuario insertado id=" + u.getId());

            // 3. Insertar cliente
            ClienteController cc = new ClienteController();
            Cliente c = new Cliente();
            c.setUsuarioId(u.getId());
            c.setCodigoCliente("C-" + u.getId());
            c.setNombre("Juan");
            c.setApellidos("Pérez");
            c.setTelefono("555-1234");
            c.setTipoClienteId(tipoClienteId);
            c.setActivo(true);
            cc.insertar(c);
            System.out.println("Cliente insertado id=" + c.getId());

            // 4. Insertar empleado
            EmpleadoController empCtrl = new EmpleadoController();
            Empleado emp = new Empleado();
            emp.setUsuarioId(u.getId());
            emp.setNumeroEmpleado("E-" + u.getId());
            emp.setNombre("Pedro");
            emp.setApellidos("López");
            emp.setTelefono("555-6789");
            emp.setSalarioActual(8000);
            emp.setFechaIngreso(new Date());
            emp.setSupervisorId(null); // sin supervisor
            emp.setActivo(true);
            empCtrl.insertar(emp);
            System.out.println("Empleado insertado id=" + emp.getId());

            // 5. Insertar repartidor
            RepartidorInfoController repCtrl = new RepartidorInfoController();
            RepartidorInfo r = new RepartidorInfo();
            r.setEmpleadoId(emp.getId());
            r.setLicenciaConducir("LIC" + System.currentTimeMillis());
            r.setDisponible(true);
            r.setCalificacionPromedio(5.0);
            r.setLatitudActual(19.4326);
            r.setLongitudActual(-99.1332);
            r.setFechaActualizacionUbicacion(new Date());
            repCtrl.insertar(r);
            System.out.println("Repartidor insertado id=" + r.getId());

            // 6. Insertar vehículo
            VehiculoController vCtrl = new VehiculoController();
            Vehiculo v = new Vehiculo();
            v.setPlacas("ABC" + (int)(Math.random()*999));
            v.setMarca("Nissan");
            v.setModelo("NP300");
            v.setCapacidadCarga(1000.0);
            v.setDisponible(true);
            v.setActivo(true);
            vCtrl.insertar(v);
            System.out.println("Vehiculo insertado id=" + v.getId());

            // 6bis. Insertar tienda de prueba
            TiendaController tCtrl = new TiendaController();
            Tienda t = new Tienda();
            t.setCodigoTienda("T-" + System.currentTimeMillis());
            t.setNombreTienda("Sucursal Centro");
            t.setDireccionCompleta("Av. Principal #100");
            t.setTelefono("555-9999");
            t.setEmail("sucursal@example.com");
            t.setCapacidadAlmacen(200);
            t.setActiva(true);
            tCtrl.insertar(t);
            System.out.println("Tienda insertada id=" + t.getId());

            // 7. Asignar vehículo al repartidor
            RepartidorVehiculoController rvCtrl = new RepartidorVehiculoController();
            RepartidorVehiculo rv = new RepartidorVehiculo();
            rv.setRepartidorId(r.getId());
            rv.setVehiculoId(v.getId());
            rv.setFechaAsignacion(new Date());
            rvCtrl.asignarVehiculo(rv);
            System.out.println("Vehiculo asignado al repartidor");

            // 8. Insertar envío con repartidor y tienda
            EnvioController ec = new EnvioController();
            Envio e = new Envio();
            e.setNumeroSeguimiento("ENV-" + System.currentTimeMillis());
            e.setRemitenteId(c.getId());
            e.setDestinatarioId(c.getId());
            e.setDireccionOrigen("Av. Siempre Viva 742");
            e.setDireccionDestino("Calle Falsa 123");
            e.setTipoEnvio("EXPRESS");
            e.setEstadoActual("REGISTRADO");
            e.setCosto(100.00);
            e.setCostoFinal(100.00);
            e.setRepartidorId(r.getId());
            e.setTiendaOrigenId(t.getId());
            e.setTiendaDestinoId(t.getId()); // para pruebas uso la misma
            ec.insertar(e);
            System.out.println("Envio insertado id=" + e.getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
