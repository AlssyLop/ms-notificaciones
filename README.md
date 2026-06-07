# ms-notificaciones

Microservicio de notificaciones para la plataforma Plaza de Comidas. Implementa envío de SMS vía Twilio y almacena logs en MongoDB.

## Stack

- Java 25 + Spring Boot 4.0.6 + Maven (mvnw wrapper)
- MongoDB (`plazoleta.notificaciones`)
- Spring Security + JWT (jjwt 0.12.6, **RSA-4096 RS256** — verificación con llave pública)
- SpringDoc OpenAPI 3.0.2
- Twilio SDK (SMS)
- Pruebas: JUnit 5 + Mockito

## Arquitectura Hexagonal

```
com.plazoleta.notificaciones/
├── domain/
│   ├── model/         Notificacion
│   ├── api/           EnviarNotificacionPort
│   ├── spi/           NotificacionRepositoryPort
│   └── usecase/       EnviarNotificacion
├── application/
│   ├── dto/           request/ response/
│   ├── exception/     ErrorResponse
│   ├── factory/
│   └── handle/        NotificacionHandle
└── infrastructure/
    ├── config/        BeanConfiguration, OpenApiConfig
    ├── endpoint/      NotificacionController, GlobalExceptionHandler
    ├── entity/        EntidadNotificacion (MongoDB)
    ├── persistence/   NotificacionRepositoryAdapter, INotificacionMongoRepository
    ├── security/      SecurityConfig, JwtTokenProvider, JwtAuthenticationFilter
    └── twilio/        TwilioSmsService
```

Reglas de dependencia: `infrastructure → application → domain`.

## Base de Datos

MongoDB colección `notificaciones` — documentos con `idPedido`, `celular`, `mensaje`, `exito`, `fechaEnvio`.

## Configuración

Copiar `application.properties.example` a `application.properties` y completar las credenciales de Twilio:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/plazoleta.notificaciones
server.port=8084
jwt.public-key=-----BEGIN PUBLIC KEY-----\n...\n-----END PUBLIC KEY-----

twilio.account.sid=${TWILIO_ACCOUNT_SID}
twilio.auth.token=${TWILIO_AUTH_TOKEN}
twilio.phone.number=${TWILIO_PHONE_NUMBER}
```

> Nota: `application.properties` está en `.gitignore` para evitar subir credenciales reales.

## Ejecución

```bash
./mvnw spring-boot:run    # Puerto 8084
./mvnw clean test         # Pruebas unitarias (1 test de contexto)
```

## Endpoints Implementados

| Método | Ruta | Descripción | Autenticación |
|--------|------|-------------|---------------|
| POST | `/notificaciones/enviar` | Enviar notificación SMS | JWT (llamado por ms-pedidos) |

Documentación OpenAPI disponible en `/swagger-ui.html` y `/v3/api-docs`.

## Seguridad JWT

Requiere un token JWT válido emitido por `ms-usuarios`. El token se envía vía header:

```
Authorization: Bearer <token>
```

- **401** — token ausente, inválido o expirado (sin body)
- **403** — token válido pero rol insuficiente (sin body)

La validación usa la **llave pública RSA-4096** (`jwt.public-key`) proporcionada por `ms-usuarios`.

---

## H14: Enviar Notificación

Recibe un pedido listo y envía un SMS al cliente con un PIN de 6 dígitos para la entrega.

### Request body

```json
{
  "idPedido": 1,
  "celular": "+573005698325",
  "mensaje": "Su pedido está listo. PIN de entrega: 123456"
}
```

### Respuestas

- **200**: SMS enviado y log guardado en MongoDB
- **400**: datos inválidos
- **401/403**: errores de autenticación/autorización

### Flujo

1. `ms-pedidos` genera el PIN y llama a `POST /notificaciones/enviar` forwardeando el JWT del empleado.
2. `ms-notificaciones` envía el SMS vía Twilio.
3. Se persiste el resultado (éxito o fallo) en MongoDB.
