package modelo;

import java.util.Date;

public class Ruta {
    private long id;
    private String codigoRuta;
    private Date fechaCreacion;
    private long repartidorId;
    private String origen;
    private String destino;
    private double distanciaTotal;
    private int tiempoEstimadoTotal;
    private String estado;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private String observaciones;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCodigoRuta() { return codigoRuta; }
    public void setCodigoRuta(String codigoRuta) { this.codigoRuta = codigoRuta; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public long getRepartidorId() { return repartidorId; }
    public void setRepartidorId(long repartidorId) { this.repartidorId = repartidorId; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    public double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(double distanciaTotal) { this.distanciaTotal = distanciaTotal; }
    public int getTiempoEstimadoTotal() { return tiempoEstimadoTotal; }
    public void setTiempoEstimadoTotal(int tiempoEstimadoTotal) { this.tiempoEstimadoTotal = tiempoEstimadoTotal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFinalizacion() { return fechaFinalizacion; }
    public void setFechaFinalizacion(Date fechaFinalizacion) { this.fechaFinalizacion = fechaFinalizacion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
