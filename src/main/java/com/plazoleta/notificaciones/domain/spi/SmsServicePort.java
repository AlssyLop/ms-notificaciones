package com.plazoleta.notificaciones.domain.spi;

public interface SmsServicePort {
    boolean enviarSms(String celular, String mensaje);
}
