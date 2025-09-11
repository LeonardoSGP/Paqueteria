package modelo;

import java.util.Date;

public class Paquete {
    private long id;
    private String codigoPaquete;
    private String descripcion;
    private double peso;
    private double largo;
    private double ancho;
    private double alto;
    private String tipoContenido;
    private boolean fragil;
    private double valorDeclarado;
    private boolean requiereSeguro;
    private String observaciones;
    private Date fechaCreacion;
    private boolean activo;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCodigoPaquete() { return codigoPaquete; }
    public void setCodigoPaquete(String codigoPaquete) { this.codigoPaquete = codigoPaquete; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public double getLargo() { return largo; }
    public void setLargo(double largo) { this.largo = largo; }
    public double getAncho() { return ancho; }
    public void setAncho(double ancho) { this.ancho = ancho; }
    public double getAlto() { return alto; }
    public void setAlto(double alto) { this.alto = alto; }
    public String getTipoContenido() { return tipoContenido; }
    public void setTipoContenido(String tipoContenido) { this.tipoContenido = tipoContenido; }
    public boolean isFragil() { return fragil; }
    public void setFragil(boolean fragil) { this.fragil = fragil; }
    public double getValorDeclarado() { return valorDeclarado; }
    public void setValorDeclarado(double valorDeclarado) { this.valorDeclarado = valorDeclarado; }
    public boolean isRequiereSeguro() { return requiereSeguro; }
    public void setRequiereSeguro(boolean requiereSeguro) { this.requiereSeguro = requiereSeguro; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
