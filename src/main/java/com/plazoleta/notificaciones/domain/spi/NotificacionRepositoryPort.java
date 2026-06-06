package com.plazoleta.notificaciones.domain.spi;

import com.plazoleta.notificaciones.domain.model.Notificacion;

public interface NotificacionRepositoryPort {
    Notificacion save(Notificacion notificacion);
}
