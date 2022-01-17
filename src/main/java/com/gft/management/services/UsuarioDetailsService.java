package com.gft.management.services;

import com.gft.management.enums.RoleType;
import com.gft.management.models.Usuario;
import com.gft.management.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String USUARIO_NOT_FOUND = "Usuário de email %s não encontrado";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario;
        try{
            usuario = usuarioRepository.findByUsername(email);
            if(usuario == null){
                throw new UsernameNotFoundException(String.format(USUARIO_NOT_FOUND, email));
            }
        } catch (Exception e){
            throw new UsernameNotFoundException(String.format(USUARIO_NOT_FOUND, email) + e.getMessage());
        }
        List<GrantedAuthority> authorities = getUserAuthority(Set.of(usuario.getRole()));

        return new User(usuario.getUsername(), usuario.getPassword(), authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleType> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (RoleType role : userRoles) {
            if(role.getNome().equals("ROLE_USER")){
                roles.add(new SimpleGrantedAuthority(role.getNome()));
            } else {
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    public void salvarUsuario(Usuario usuario){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listAllUsuarios(){
       return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscaUsuario(String email){
        return usuarioRepository.getOptionalUsuarioByUsername(email);
    }

    public List<Usuario> listUsuariosDisponiveisParaSerScrum(Long idDoGrupoEditado){
        List<Usuario> usuariosDisponiveis = new ArrayList<>();
        for(Usuario usuario: usuarioRepository.findAll()){
            if(usuario.getGrupo() == null || usuario.getGrupo().getId().equals(idDoGrupoEditado)){
                usuariosDisponiveis.add(usuario);
            }
        }
        return usuariosDisponiveis;
    }

}