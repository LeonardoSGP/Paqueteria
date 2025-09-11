package modelo;

public class DireccionCliente {
    private long id;
    private long clienteId;
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String referencias;
    private double latitud;
    private double longitud;
    private String tipoDireccion;
    private boolean principal;
    private boolean activa;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getClienteId() { return clienteId; }
    public void setClienteId(long clienteId) { this.clienteId = clienteId; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getReferencias() { return referencias; }
    public void setReferencias(String referencias) { this.referencias = referencias; }
    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }
    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
    public String getTipoDireccion() { return tipoDireccion; }
    public void setTipoDireccion(String tipoDireccion) { this.tipoDireccion = tipoDireccion; }
    public boolean isPrincipal() { return principal; }
    public void setPrincipal(boolean principal) { this.principal = principal; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}
