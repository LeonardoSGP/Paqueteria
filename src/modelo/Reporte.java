package modelo;

import java.math.BigDecimal;

public class Reporte {

    private long id;
    private String tipoReporte;
    private String titulo;
    private String descripcion;
    private java.util.Date fechaGeneracion;
    private java.util.Date fechaInicio;
    private java.util.Date fechaFin;
    private String parametros;
    private String resultados;
    private long generadoPorUsuarioId;
    private String formatoSalida;
    private boolean activo;

    // Constructor vacío
    public Reporte() {
    }

    // Constructor con parámetros principales
    public Reporte(String tipoReporte, String titulo, String descripcion) {
        this.tipoReporte = tipoReporte;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaGeneracion = new java.util.Date();
        this.activo = true;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public java.util.Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(java.util.Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public java.util.Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public long getGeneradoPorUsuarioId() {
        return generadoPorUsuarioId;
    }

    public void setGeneradoPorUsuarioId(long generadoPorUsuarioId) {
        this.generadoPorUsuarioId = generadoPorUsuarioId;
    }

    public String getFormatoSalida() {
        return formatoSalida;
    }

    public void setFormatoSalida(String formatoSalida) {
        this.formatoSalida = formatoSalida;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "id=" + id +
                ", tipoReporte='" + tipoReporte + '\'' +
                ", titulo='" + titulo + '\'' +
                ", fechaGeneracion=" + fechaGeneracion +
                ", activo=" + activo +
                '}';
    }
}