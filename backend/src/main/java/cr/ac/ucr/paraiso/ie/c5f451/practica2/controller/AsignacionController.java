package cr.ac.ucr.paraiso.ie.c5f451.practica2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cr.ac.ucr.paraiso.ie.c5f451.practica2.domain.Asignacion;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*")
// Habilitar peticiones desde cualquier origen para evitar CORS en desarrollo
public class AsignacionController {
    private final List<Asignacion> asignaciones = new ArrayList<>();
    private int autoincrementId = 1;

    public AsignacionController() {
        // Datos semilla iniciales
        asignaciones.add(new Asignacion(
                autoincrementId++,
                "Práctica Guiada 1",
                "Completar el servidor Java nativo",
                "2026-08-25",
                true));
        asignaciones.add(new Asignacion(
                autoincrementId++,
                "Laboratorio 1",
                "Implementar la persistencia con Maven",
                "2026-08-30",
                false));
    }

    @GetMapping
    public List<Asignacion> obtenerTodas() {
        System.out.println("[API GET] Listado solicitado...");
        return asignaciones;
    }

    @PostMapping
    public ResponseEntity<?> agregarAsignacion(@RequestBody Asignacion nueva) {
        System.out.println("[API POST] Recibiendo: " + nueva.getTitulo());
        // ERROR INTENCIONAL PARA EL TALLER DE DEPURACIÓN:
        // Si el título es nulo/vacío, simulamos un NullPointerException
        if (nueva.getTitulo() == null || nueva.getTitulo().trim().isEmpty()) {
            throw new NullPointerException(
                    "El título no puede procesarse porque es nulo o vacío.");
        }
        // Si la descripción está vacía, retornamos BAD_REQUEST
        if (nueva.getDescripcion() == null ||
                nueva.getDescripcion().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("La descripción es obligatoria.");
        }
        nueva.setId(autoincrementId++);
        asignaciones.add(nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}