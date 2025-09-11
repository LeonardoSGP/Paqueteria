package modelo;

import java.util.Date;

public class RepartidorVehiculo {
    private long repartidorId;
    private long vehiculoId;
    private Date fechaAsignacion;
    private Date fechaFin;

    public long getRepartidorId() { return repartidorId; }
    public void setRepartidorId(long repartidorId) { this.repartidorId = repartidorId; }
    public long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(long vehiculoId) { this.vehiculoId = vehiculoId; }
    public Date getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(Date fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}
