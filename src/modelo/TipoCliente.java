package modelo;

public class TipoCliente {
    private int id;
    private String nombre;
    private String descripcion;
    private double descuentoDefault;
    private double creditoLimite;
    private boolean activo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getDescuentoDefault() { return descuentoDefault; }
    public void setDescuentoDefault(double descuentoDefault) { this.descuentoDefault = descuentoDefault; }
    public double getCreditoLimite() { return creditoLimite; }
    public void setCreditoLimite(double creditoLimite) { this.creditoLimite = creditoLimite; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
