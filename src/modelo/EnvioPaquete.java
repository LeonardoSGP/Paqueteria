package modelo;

import java.util.Date;

public class EnvioPaquete {
    private long envioId;
    private long paqueteId;
    private Date fechaAgregado;

    public long getEnvioId() { return envioId; }
    public void setEnvioId(long envioId) { this.envioId = envioId; }
    public long getPaqueteId() { return paqueteId; }
    public void setPaqueteId(long paqueteId) { this.paqueteId = paqueteId; }
    public Date getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(Date fechaAgregado) { this.fechaAgregado = fechaAgregado; }

}
