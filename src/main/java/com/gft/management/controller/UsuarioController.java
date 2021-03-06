package com.gft.management.controller;

import com.gft.management.models.Usuario;
import com.gft.management.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    public ModelAndView showLogin(@RequestParam(name = "error", required = false) String erro, RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView("/login");
        ModelAndView afterErroLogin = new ModelAndView("redirect:/login");

        if(erro != null){
            redirect.addFlashAttribute("message", "Senha ou usuário incorreto.");
            return afterErroLogin;
        }
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/registrar")
    public ModelAndView criarUsuario() {
        ModelAndView mv = new ModelAndView("/formRegister.html");
        Usuario usuario = new Usuario();

        mv.addObject("usuario", usuario);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, path = "registrar")
    public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult errors, RedirectAttributes redirect) throws Exception {
        ModelAndView redirectView = new ModelAndView("redirect:/login");
        ModelAndView mv = new ModelAndView("/formRegister.html");

        if(isUsuarioExistente(usuario, mv) || isInputsComErros(usuario, errors, mv))
            return mv;

        mv.addObject("usuario", new Usuario());
        redirect.addFlashAttribute("message", "Usuário registrado. Faça login.");
        usuarioDetailsService.salvarUsuario(usuario);
        return redirectView;
    }

    private Boolean isInputsComErros(Usuario usuario, BindingResult errors, ModelAndView mv) {
        if(errors.hasErrors()){
            mv.addObject("usuario", usuario);
            return true;
        }
        return false;
    }

    private Boolean isUsuarioExistente(Usuario usuario, ModelAndView mv) {
        Optional<Usuario> existe = usuarioDetailsService.buscaUsuario(usuario.getUsername());
        if(existe.isPresent()){
            mv.addObject("message", "Esse usuário já existe. Tente outro.");
            mv.addObject("usuario", usuario);
            return true;
        }
        return  false;
    }
}
