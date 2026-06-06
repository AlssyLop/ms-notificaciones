package com.plazoleta.notificaciones.application.dto;

public class NotificacionResponse {

    private String mensaje;

    public NotificacionResponse() {}

    public NotificacionResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
