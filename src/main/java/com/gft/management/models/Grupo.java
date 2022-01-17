package com.gft.management.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Um grupo deve ter uma tecnologia.")
    private Tecnologia tecnologia;

    @OneToMany(mappedBy = "grupoStarters")
    @Size(max = 7, message = "Não podem haver mais que 7 pessoas num grupo.")
    private List<Starter> starters;

    @ManyToOne
    @NotNull(message = "Um grupo deve ter um módulo")
    private Modulo modulo;

    @OneToOne
    @NotNull(message = "Um grupo deve ter um Scrum que não esteja num grupo.")
    @JoinColumn(name = "scrumMaster_id", referencedColumnName = "id")
    private Usuario scrumMaster;
}
