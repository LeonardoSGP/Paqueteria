package modelo;

public class PuestoEmpleado {
    private int id;
    private String nombre;
    private String descripcion;
    private double salarioBase;
    private String permisosJson;
    private boolean activo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public String getPermisosJson() { return permisosJson; }
    public void setPermisosJson(String permisosJson) { this.permisosJson = permisosJson; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
