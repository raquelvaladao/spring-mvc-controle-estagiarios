package com.gft.management.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;

@Entity
@Getter
@Setter
public class ProgramaStarter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = INPUT_VAZIO)
    private String nome;

    @NotNull(message = INPUT_VAZIO)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInicio;

    @NotNull(message = INPUT_VAZIO)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataFim;

    @OneToMany(mappedBy = "programaStarter")
    private List<Modulo> modulos;

}
