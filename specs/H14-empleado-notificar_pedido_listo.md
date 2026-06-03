# HU-14: Notificar que el pedido está listo

**Microservicio:** ms-notificaciones
**BD:** MongoDB (logs de notificaciones)

## Historia de usuario

> **Rol:** Empleado de un restaurante
> **Funcionalidad:** Notificar al cliente de que su pedido está listo
> **Motivo:** Que tenga la certeza de que en pocos minutos lo podrá recoger

---

## Criterios de aceptación

### Reglas de negocio

- El empleado marca el pedido como **LISTO** enviando el **id del pedido en la URL**.
- El pedido debe estar en estado **EN_PREPARACION** para poder marcarse como LISTO.
- Al marcar como LISTO, el sistema genera automáticamente un **pin de seguridad numérico**.
- El sistema envía automáticamente un **SMS** al número de celular del cliente con el pin de seguridad, a través de la **API de Twilio**.
- El pin se almacena en el sistema para validarlo posteriormente cuando el cliente reclame el pedido.
- El empleado solo puede marcar pedidos del restaurante al que pertenece.
- El empleado debe estar autenticado y tener el rol **EMPLEADO**.
- El id del empleado se obtiene del **token de autorización**.
- Si el envío del SMS falla, el sistema igual marca el pedido como LISTO pero informa del error.
- Cada notificación enviada (exitosa o fallida) se registra en **MongoDB** para trazabilidad de los SMS.

### Respuestas del sistema

- **Notificación exitosa:** El sistema responde con un mensaje de confirmación.
- **Error:** Si el pedido no está en EN_PREPARACION, o no pertenece al restaurante del empleado.

---

## Endpoints (propuesta inicial)

| Método | Ruta | Descripción |
|---|---|---|
| `PATCH` | `pedidos/{id}/notificar-listo` | Marcar pedido como LISTO y notificar al cliente |

### Request

Sin cuerpo — el id del empleado se obtiene del token de autorización.

### Response 200 — Notificación exitosa

```json
{
  "mensaje": "Pedido marcado como LISTO y cliente notificado"
}
```

### Response 200 — Notificación con error de SMS

```json
{
  "mensaje": "Pedido marcado como LISTO, pero no se pudo notificar al cliente"
}
```

### Response 400 — Error de validación

```json
{
  "mensaje": "El pedido no se encuentra en estado EN_PREPARACION"
}
```

### Response 401 — No autenticado

*(Sin cuerpo en la respuesta)*

### Response 403 — No autorizado

```json
{
  "mensaje": "No tienes permiso para modificar este pedido"
}
```

### Response 404 — Pedido no encontrado

```json
{
  "mensaje": "El pedido no existe"
}
```

---

## Suposiciones validadas

1. ✅ El empleado marca el pedido como LISTO enviando el id del pedido en la URL.
2. ✅ El pedido debe estar en estado EN_PREPARACION.
3. ✅ Al marcar como LISTO, el sistema genera automáticamente un pin de seguridad numérico.
4. ✅ El sistema envía automáticamente el SMS al celular del cliente con el pin vía Twilio.
5. ✅ El pin se almacena en el sistema para validarlo después.
6. ✅ El empleado solo puede marcar pedidos de su propio restaurante.
7. ✅ El empleado debe estar autenticado y tener el rol EMPLEADO.
8. ✅ El sistema responde con un mensaje de confirmación.
9. ✅ Si el SMS falla, igual marca LISTO pero informa del error.
10. ✅ Cada notificación se registra en MongoDB para trazabilidad.
