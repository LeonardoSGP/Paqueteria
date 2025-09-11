package modelo;

import java.util.Date;

public class EmpleadoPuesto {
    private long empleadoId;
    private int puestoId;
    private Date fechaAsignacion;
    private Date fechaFin;
    private boolean activo;

    public long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(long empleadoId) { this.empleadoId = empleadoId; }
    public int getPuestoId() { return puestoId; }
    public void setPuestoId(int puestoId) { this.puestoId = puestoId; }
    public Date getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Date fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
