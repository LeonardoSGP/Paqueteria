package modelo;

public class Vehiculo {
    private long id;
    private String placas;
    private String marca;
    private String modelo;
    private double capacidadCarga;
    private boolean disponible;
    private boolean activo;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getPlacas() { return placas; }
    public void setPlacas(String placas) { this.placas = placas; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double capacidadCarga) { this.capacidadCarga = capacidadCarga; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
