package com.gft.management.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;

@Data
@Getter
@Setter
@Entity
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = INPUT_VAZIO)
    @Size(min = 3, max = 250, message = "No mínimo 3 e no máximo 250 caracteres")
    private String nome;

    @OneToMany(mappedBy = "modulo")
    private List<Grupo> grupos;

    @ManyToOne
    @NotNull(message = "O módulo deve pertencer a um programa.")
    private ProgramaStarter programaStarter;
}
