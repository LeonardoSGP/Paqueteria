package modelo;

import java.util.Date;

public class MovimientoRastreo {
    private long id;
    private long envioId;
    private Date fechaMovimiento;
    private String ubicacion;
    private String descripcion;
    private String estadoAnterior;
    private String estadoNuevo;
    private long empleadoResponsableId;
    private long tiendaId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getEnvioId() { return envioId; }
    public void setEnvioId(long envioId) { this.envioId = envioId; }
    public Date getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(Date fechaMovimiento) { this.fechaMovimiento = fechaMovimiento; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public void setEstadoAnterior(String estadoAnterior) { this.estadoAnterior = estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public void setEstadoNuevo(String estadoNuevo) { this.estadoNuevo = estadoNuevo; }
    public long getEmpleadoResponsableId() { return empleadoResponsableId; }
    public void setEmpleadoResponsableId(long empleadoResponsableId) { this.empleadoResponsableId = empleadoResponsableId; }
    public long getTiendaId() { return tiendaId; }
    public void setTiendaId(long tiendaId) { this.tiendaId = tiendaId; }
}
