package com.plazoleta.notificaciones.infrastructure.twilio;

import com.plazoleta.notificaciones.domain.spi.SmsServicePort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioSmsService implements SmsServicePort {

    private final String fromPhoneNumber;

    public TwilioSmsService(
            @Value("${twilio.account-sid}") String accountSid,
            @Value("${twilio.auth-token}") String authToken,
            @Value("${twilio.phone-number}") String fromPhoneNumber) {
        if (accountSid != null && !accountSid.isBlank() && authToken != null && !authToken.isBlank()) {
            Twilio.init(accountSid, authToken);
        }
        this.fromPhoneNumber = fromPhoneNumber;
    }

    @Override
    public boolean enviarSms(String celular, String mensaje) {
        try {
            if (fromPhoneNumber == null || fromPhoneNumber.isBlank()) {
                return false;
            }
            Message.creator(
                    new PhoneNumber(celular),
                    new PhoneNumber(fromPhoneNumber),
                    mensaje
            ).create();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
