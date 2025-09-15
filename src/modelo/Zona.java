package modelo;

public class Zona {

    private long id;
    private String codigoZona;
    private String nombreZona;
    private String descripcion;
    private double tarifaBase;
    private int tiempoEntregaEstimado;
    private Long tiendaResponsableId;
    private boolean activa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(String codigoZona) {
        this.codigoZona = codigoZona;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public int getTiempoEntregaEstimado() {
        return tiempoEntregaEstimado;
    }

    public void setTiempoEntregaEstimado(int tiempoEntregaEstimado) {
        this.tiempoEntregaEstimado = tiempoEntregaEstimado;
    }

    public Long getTiendaResponsableId() {
        return tiendaResponsableId;
    }

    public void setTiendaResponsableId(Long tiendaResponsableId) {
        this.tiendaResponsableId = tiendaResponsableId;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
