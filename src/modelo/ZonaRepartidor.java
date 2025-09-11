package modelo;

import java.util.Date;

public class ZonaRepartidor {
    private long zonaId;
    private long repartidorId;
    private Date fechaAsignacion;
    private boolean activo;

    public long getZonaId() { return zonaId; }
    public void setZonaId(long zonaId) { this.zonaId = zonaId; }
    public long getRepartidorId() { return repartidorId; }
    public void setRepartidorId(long repartidorId) { this.repartidorId = repartidorId; }
    public Date getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Date fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
