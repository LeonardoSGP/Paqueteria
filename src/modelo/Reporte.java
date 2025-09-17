package modelo;

import java.util.Date;
import java.math.BigDecimal;

public class Reporte {

    private long id;
    private String tipoReporte;
    private String titulo;
    private String descripcion;
    private Date fechaGeneracion;
    private Date fechaInicio;
    private Date fechaFin;
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
        this.fechaGeneracion = new Date();
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

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
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