package modelo;

import java.util.Date;

public class Cliente {
    private long id;
    private Long usuarioId;
    private String codigoCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private Integer  tipoClienteId;
    private double creditoDisponible;
    private double descuentoAsignado;
    private Date fechaRegistro;
    private boolean activo;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }
    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Integer getTipoClienteId() { return tipoClienteId; }
    public void setTipoClienteId(int tipoClienteId) { this.tipoClienteId = tipoClienteId; }
    public double getCreditoDisponible() { return creditoDisponible; }
    public void setCreditoDisponible(double creditoDisponible) { this.creditoDisponible = creditoDisponible; }
    public double getDescuentoAsignado() { return descuentoAsignado; }
    public void setDescuentoAsignado(double descuentoAsignado) { this.descuentoAsignado = descuentoAsignado; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
