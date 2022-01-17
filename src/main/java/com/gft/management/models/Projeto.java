package com.gft.management.models;

import com.gft.management.enums.TipoEtapa;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;

@Data
@Getter
@Setter
@Entity
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0)
    @Max(value = 100)
    @NotNull(message = INPUT_VAZIO)
    private Integer nota;

    @NotNull(message = "Uma etapa deve ser escolhida.")
    @Enumerated(EnumType.STRING)
    private TipoEtapa etapa;

    @ManyToOne
    @NotNull(message = "Um starter deve ser escolhido.")
    private Starter starter;

    @ManyToOne
    private Modulo modulo;
}
