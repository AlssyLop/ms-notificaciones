package com.plazoleta.notificaciones.domain.model;

import java.time.LocalDateTime;

public class Notificacion {

    private String id;
    private Long idPedido;
    private String celular;
    private String mensaje;
    private boolean exito;
    private LocalDateTime fechaEnvio;

    public Notificacion() {}

    public Notificacion(String id, Long idPedido, String celular, String mensaje,
                        boolean exito, LocalDateTime fechaEnvio) {
        this.id = id;
        this.idPedido = idPedido;
        this.celular = celular;
        this.mensaje = mensaje;
        this.exito = exito;
        this.fechaEnvio = fechaEnvio;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
}
