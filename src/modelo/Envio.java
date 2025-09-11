package modelo;

import java.util.Date;

public class Envio {
    private long id;
    private String numeroSeguimiento;
    private long remitenteId;
    private long destinatarioId;
    private String direccionOrigen;
    private String direccionDestino;
    private String tipoEnvio;
    private String estadoActual;
    private Date fechaCreacion;
    private Date fechaEntregaEstimada;
    private Date fechaEntregaReal;
    private double costo;
    private double descuentoAplicado;
    private double costoFinal;
    private long repartidorId;
    private long tiendaOrigenId;
    private long tiendaDestinoId;
    private int intentosEntrega;
    private String motivoDevolucion;
    private Date fechaDevolucion;
    private int prioridad;
    private String observaciones;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNumeroSeguimiento() { return numeroSeguimiento; }
    public void setNumeroSeguimiento(String numeroSeguimiento) { this.numeroSeguimiento = numeroSeguimiento; }
    public long getRemitenteId() { return remitenteId; }
    public void setRemitenteId(long remitenteId) { this.remitenteId = remitenteId; }
    public long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(long destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getDireccionOrigen() { return direccionOrigen; }
    public void setDireccionOrigen(String direccionOrigen) { this.direccionOrigen = direccionOrigen; }
    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }
    public String getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(String tipoEnvio) { this.tipoEnvio = tipoEnvio; }
    public String getEstadoActual() { return estadoActual; }
    public void setEstadoActual(String estadoActual) { this.estadoActual = estadoActual; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Date getFechaEntregaEstimada() { return fechaEntregaEstimada; }
    public void setFechaEntregaEstimada(Date fechaEntregaEstimada) { this.fechaEntregaEstimada = fechaEntregaEstimada; }
    public Date getFechaEntregaReal() { return fechaEntregaReal; }
    public void setFechaEntregaReal(Date fechaEntregaReal) { this.fechaEntregaReal = fechaEntregaReal; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public double getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(double descuentoAplicado) { this.descuentoAplicado = descuentoAplicado; }
    public double getCostoFinal() { return costoFinal; }
    public void setCostoFinal(double costoFinal) { this.costoFinal = costoFinal; }
    public long getRepartidorId() { return repartidorId; }
    public void setRepartidorId(long repartidorId) { this.repartidorId = repartidorId; }
    public long getTiendaOrigenId() { return tiendaOrigenId; }
    public void setTiendaOrigenId(long tiendaOrigenId) { this.tiendaOrigenId = tiendaOrigenId; }
    public long getTiendaDestinoId() { return tiendaDestinoId; }
    public void setTiendaDestinoId(long tiendaDestinoId) { this.tiendaDestinoId = tiendaDestinoId; }
    public int getIntentosEntrega() { return intentosEntrega; }
    public void setIntentosEntrega(int intentosEntrega) { this.intentosEntrega = intentosEntrega; }
    public String getMotivoDevolucion() { return motivoDevolucion; }
    public void setMotivoDevolucion(String motivoDevolucion) { this.motivoDevolucion = motivoDevolucion; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
