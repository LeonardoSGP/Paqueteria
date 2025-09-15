package modelo;
import java.util.Date;

public class Envio {
    private Long id;
    private String numeroSeguimiento;
    private Long remitenteId;
    private Long destinatarioId;
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
    private Long repartidorId;
    private Long tiendaOrigenId;
    private Long tiendaDestinoId;
    private int intentosEntrega;
    private String motivoDevolucion;
    private Date fechaDevolucion;
    private int prioridad;
    private String observaciones;
    
    // Constructor
    public Envio() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroSeguimiento() { return numeroSeguimiento; }
    public void setNumeroSeguimiento(String numeroSeguimiento) { this.numeroSeguimiento = numeroSeguimiento; }
    
    public Long getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Long remitenteId) { this.remitenteId = remitenteId; }
    
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    
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
    
    public Long getRepartidorId() { return repartidorId; }
    public void setRepartidorId(Long repartidorId) { this.repartidorId = repartidorId; }
    
    public Long getTiendaOrigenId() { return tiendaOrigenId; }
    public void setTiendaOrigenId(Long tiendaOrigenId) { this.tiendaOrigenId = tiendaOrigenId; }
    
    public Long getTiendaDestinoId() { return tiendaDestinoId; }
    public void setTiendaDestinoId(Long tiendaDestinoId) { this.tiendaDestinoId = tiendaDestinoId; }
    
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
    
    // Método utilitario para calcular costo final
    public void calcularCostoFinal() {
        this.costoFinal = this.costo - (this.costo * this.descuentoAplicado / 100);
    }
}
