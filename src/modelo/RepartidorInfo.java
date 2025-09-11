package modelo;

import java.util.Date;

public class RepartidorInfo {
    private long id;
    private long empleadoId;
    private String licenciaConducir;
    private boolean disponible;
    private double calificacionPromedio;
    private double latitudActual;
    private double longitudActual;
    private Date fechaActualizacionUbicacion;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(long empleadoId) { this.empleadoId = empleadoId; }
    public String getLicenciaConducir() { return licenciaConducir; }
    public void setLicenciaConducir(String licenciaConducir) { this.licenciaConducir = licenciaConducir; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }
    public double getLatitudActual() { return latitudActual; }
    public void setLatitudActual(double latitudActual) { this.latitudActual = latitudActual; }
    public double getLongitudActual() { return longitudActual; }
    public void setLongitudActual(double longitudActual) { this.longitudActual = longitudActual; }
    public Date getFechaActualizacionUbicacion() { return fechaActualizacionUbicacion; }
    public void setFechaActualizacionUbicacion(Date fechaActualizacionUbicacion) { this.fechaActualizacionUbicacion = fechaActualizacionUbicacion; }
}
