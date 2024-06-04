package br.com.grupotdb.grupotdb.Eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody EventoModel eventoModel) {
        eventoModel.setStatusEvento("Em Breve");
        var eventoCreated = this.eventoRepository.save(eventoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreated);
    }

    @GetMapping("/list-evento")
    public ResponseEntity<List<EventoModel>> getAllEvento() {
        List<EventoModel> eventos = eventoRepository.findAll();
        if (eventos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody String status) {
        Optional<EventoModel> optionalEvento = eventoRepository.findById(id);
        if (optionalEvento.isPresent()) {
            EventoModel evento = optionalEvento.get();
            evento.setStatusEvento(status);
            eventoRepository.save(evento);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}