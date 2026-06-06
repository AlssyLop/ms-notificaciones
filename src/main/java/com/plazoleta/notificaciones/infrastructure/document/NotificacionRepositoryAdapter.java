package com.plazoleta.notificaciones.infrastructure.document;

import com.plazoleta.notificaciones.domain.model.Notificacion;
import com.plazoleta.notificaciones.domain.spi.NotificacionRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class NotificacionRepositoryAdapter implements NotificacionRepositoryPort {

    private final INotificacionMongoRepository mongoRepository;

    public NotificacionRepositoryAdapter(INotificacionMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Notificacion save(Notificacion notificacion) {
        EntidadNotificacion entity = new EntidadNotificacion();
        entity.setIdPedido(notificacion.getIdPedido());
        entity.setCelular(notificacion.getCelular());
        entity.setMensaje(notificacion.getMensaje());
        entity.setExito(notificacion.isExito());
        entity.setFechaEnvio(notificacion.getFechaEnvio());

        EntidadNotificacion saved = mongoRepository.save(entity);

        notificacion.setId(saved.getId());
        return notificacion;
    }
}
