package com.plazoleta.notificaciones.domain.api;

public interface EnviarNotificacionPort {
    void enviar(Long idPedido, String celular, String mensaje);
}
