package modelo;

public class Tienda {

    private Long id;
    private String codigoTienda;
    private String nombreTienda;
    private String direccionCompleta;
    private String telefono;
    private String email;
    private Long gerenteId;
    private java.sql.Time horarioApertura;
    private java.sql.Time horarioCierre;
    private int capacidadAlmacen;
    private boolean activa;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getGerenteId() {
        return gerenteId;
    }

    public void setGerenteId(long gerenteId) {
        this.gerenteId = gerenteId;
    }

    public java.sql.Time getHorarioApertura() {
        return horarioApertura;
    }

    public void setHorarioApertura(java.sql.Time horarioApertura) {
        this.horarioApertura = horarioApertura;
    }

    public java.sql.Time getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(java.sql.Time horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    public int getCapacidadAlmacen() {
        return capacidadAlmacen;
    }

    public void setCapacidadAlmacen(int capacidadAlmacen) {
        this.capacidadAlmacen = capacidadAlmacen;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
