package com.plazoleta.notificaciones.infrastructure.endpoint;

import com.plazoleta.notificaciones.application.dto.NotificacionRequest;
import com.plazoleta.notificaciones.application.dto.NotificacionResponse;
import com.plazoleta.notificaciones.application.exception.ErrorResponse;
import com.plazoleta.notificaciones.application.handle.NotificacionHandle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificaciones")
@Tag(name = "Notificaciones", description = "Envio de notificaciones SMS")
public class NotificacionController {

    private final NotificacionHandle notificacionHandle;

    public NotificacionController(NotificacionHandle notificacionHandle) {
        this.notificacionHandle = notificacionHandle;
    }

    @PostMapping("/enviar")
    @PreAuthorize("hasRole('EMPLEADO')")
    @Operation(summary = "Enviar notificacion SMS",
            description = "Envia un SMS al cliente y registra el resultado en MongoDB. Requiere rol EMPLEADO.")
    @ApiResponse(responseCode = "200", description = "Notificacion procesada",
            content = @Content(schema = @Schema(implementation = NotificacionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Error de validacion",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<NotificacionResponse> enviar(@RequestBody NotificacionRequest request) {
        NotificacionResponse response = notificacionHandle.enviar(request);
        return ResponseEntity.ok(response);
    }
}
