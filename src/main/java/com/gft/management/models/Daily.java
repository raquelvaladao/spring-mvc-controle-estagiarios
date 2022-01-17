package com.gft.management.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;


@Getter
@Setter
@Entity
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = INPUT_VAZIO)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @Size(min = 4, message = "Deve ter pelo menos 4 letras")
    @NotEmpty(message = INPUT_VAZIO)
    private String feito;

    @Size(min = 4, message = "Deve ter pelo menos 4 letras")
    @NotEmpty(message = INPUT_VAZIO)
    private String fazendo;

    @Size(min = 3, message = "Deve ter pelo menos 3 letras")
    @NotEmpty(message = INPUT_VAZIO)
    private String impedimentos;

    @NotNull(message = "Deve haver a presença.")
    private Boolean presenca;

    @ManyToOne(fetch = FetchType.LAZY)
    private Starter starter;
}
