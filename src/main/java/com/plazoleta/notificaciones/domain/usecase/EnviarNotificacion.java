package com.plazoleta.notificaciones.domain.usecase;

import com.plazoleta.notificaciones.domain.api.EnviarNotificacionPort;
import com.plazoleta.notificaciones.domain.model.Notificacion;
import com.plazoleta.notificaciones.domain.spi.NotificacionRepositoryPort;
import com.plazoleta.notificaciones.domain.spi.SmsServicePort;

import java.time.LocalDateTime;

public class EnviarNotificacion implements EnviarNotificacionPort {

    private final SmsServicePort smsService;
    private final NotificacionRepositoryPort notificacionRepository;

    public EnviarNotificacion(SmsServicePort smsService,
                              NotificacionRepositoryPort notificacionRepository) {
        this.smsService = smsService;
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void enviar(Long idPedido, String celular, String mensaje) {
        boolean exito = smsService.enviarSms(celular, mensaje);

        Notificacion notificacion = new Notificacion(
                null,
                idPedido,
                celular,
                mensaje,
                exito,
                LocalDateTime.now()
        );
        notificacionRepository.save(notificacion);
    }
}
