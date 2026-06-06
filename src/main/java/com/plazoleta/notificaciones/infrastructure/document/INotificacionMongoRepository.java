package com.plazoleta.notificaciones.infrastructure.document;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface INotificacionMongoRepository extends MongoRepository<EntidadNotificacion, String> {
}
