package com.gft.management.models;

import com.gft.management.enums.TipoTecnologia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;

@Entity
@Getter
@Setter
@Table(name = "tecnologia")
public class Tecnologia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = INPUT_VAZIO)
    @Enumerated(value = EnumType.STRING)
    private TipoTecnologia nome;

    @NotEmpty(message = INPUT_VAZIO)
    private String descricao;

    @OneToMany(mappedBy = "tecnologia")
    private List<Grupo> grupos;

}
