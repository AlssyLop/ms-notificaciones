package com.plazoleta.notificaciones.infrastructure.config;

import com.plazoleta.notificaciones.domain.api.EnviarNotificacionPort;
import com.plazoleta.notificaciones.domain.spi.NotificacionRepositoryPort;
import com.plazoleta.notificaciones.domain.spi.SmsServicePort;
import com.plazoleta.notificaciones.domain.usecase.EnviarNotificacion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public EnviarNotificacionPort enviarNotificacionPort(SmsServicePort smsService,
                                                          NotificacionRepositoryPort notificacionRepository) {
        return new EnviarNotificacion(smsService, notificacionRepository);
    }
}
