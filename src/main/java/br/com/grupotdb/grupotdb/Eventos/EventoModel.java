package br.com.grupotdb.grupotdb.Eventos;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_Evento")
public class EventoModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String nomedoevento;
    private String descricao;
    private String local;
    private Date data;
    private String hora;
    private String statusEvento;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setStatusEvento(String status) {
        this.statusEvento = status;
    }
}