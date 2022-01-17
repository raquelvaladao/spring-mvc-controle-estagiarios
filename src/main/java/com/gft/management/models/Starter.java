package com.gft.management.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;


@Getter
@Setter
@Entity
public class Starter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = INPUT_VAZIO)
    @Size(min = 5, max = 250)
    private String nome;

    @NotEmpty(message = INPUT_VAZIO)
    @Size(min = 4, max = 4, message = "Devem ser exatamente 4 letras")
    private String letras;

    private String foto;

    @ManyToOne
    @NotNull(message = "Um starter deve ter um grupo.")
    private Grupo grupoStarters;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "starter_id")
    private List<Daily> dailies = new ArrayList<>();


    @Size(max = 2)
    @OneToMany(mappedBy = "starter", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Projeto> projetos;

    @Transient
    public String getFotoImagePath() {
        if (foto == null || id == null) return null;

        return "/starters-photos/" + id + "/" + foto;
    }

}
