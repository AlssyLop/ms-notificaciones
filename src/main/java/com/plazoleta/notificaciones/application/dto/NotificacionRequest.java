package com.plazoleta.notificaciones.application.dto;

public class NotificacionRequest {

    private Long idPedido;
    private String celular;
    private String mensaje;

    public NotificacionRequest() {}

    public NotificacionRequest(Long idPedido, String celular, String mensaje) {
        this.idPedido = idPedido;
        this.celular = celular;
        this.mensaje = mensaje;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
