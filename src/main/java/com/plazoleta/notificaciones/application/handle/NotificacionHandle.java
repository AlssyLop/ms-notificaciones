package com.plazoleta.notificaciones.application.handle;

import com.plazoleta.notificaciones.application.dto.NotificacionRequest;
import com.plazoleta.notificaciones.application.dto.NotificacionResponse;
import com.plazoleta.notificaciones.domain.api.EnviarNotificacionPort;
import org.springframework.stereotype.Component;

@Component
public class NotificacionHandle {

    private final EnviarNotificacionPort enviarNotificacionPort;

    public NotificacionHandle(EnviarNotificacionPort enviarNotificacionPort) {
        this.enviarNotificacionPort = enviarNotificacionPort;
    }

    public NotificacionResponse enviar(NotificacionRequest request) {
        enviarNotificacionPort.enviar(request.getIdPedido(), request.getCelular(), request.getMensaje());
        return new NotificacionResponse("Notificacion enviada");
    }
}
