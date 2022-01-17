package com.gft.management.models;

import com.gft.management.enums.RoleType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.gft.management.models.ValidacaoInput.INPUT_VAZIO;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = INPUT_VAZIO)
    @Email(message = "o email deve ser no formato ****@****.com")
    private String username;

    @NotEmpty(message = INPUT_VAZIO)
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Size(max = 100, message = "Senha deve ter no máximo 30 caracteres")
    private String password;

    @OneToOne(mappedBy = "scrumMaster")
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Selecionar se é admin ou scrum.")
    private RoleType role;

    public Usuario(String username, String password, RoleType role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(role.getNome()));
        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}