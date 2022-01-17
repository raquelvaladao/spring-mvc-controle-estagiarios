package com.gft.management.controller;


import com.gft.management.models.Usuario;
import com.gft.management.services.EmailService;
import com.gft.management.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioDetailsService usuarioService;

    private final String EMAIL_ENVIADO = "Olá Scrum, um email foi enviado para INSIRA_EMAIL_DESTINATARIO_AQUI.";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView enviarEmail(@RequestParam(name = "success", required = false) String sucessoLogin,
                                    HttpServletRequest request,
                                    RedirectAttributes redirect){
        ModelAndView mv = new ModelAndView("/index.html");
        ModelAndView afterLogin = new ModelAndView("redirect:/home");

        if(sucessoLogin != null){
            Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
            String usuarioEmail = autenticacao.getName();
            Usuario usuarioLogado = usuarioService.buscaUsuario(usuarioEmail).get();
            verSeScrum(redirect, usuarioLogado);
            return afterLogin;
        }
        return mv;
    }

    private void verSeScrum(RedirectAttributes redirect, Usuario usuarioLogado) {
        if(usuarioLogado.getRole().getNome().equals("ROLE_USER")){
            triggerEmail(usuarioLogado.getUsername());
            redirect.addFlashAttribute("message", EMAIL_ENVIADO);
        }
    }

    public void triggerEmail(String usuarioNome){
        String email = "INSIRA_EMAIL_DESTINATARIO_AQUI";
        emailService.enviarEmail(
                email,
                String.format("Obrigado por visitar nosso site, %s%s", usuarioNome, email),
                "OBRIGADO POR VISITAR O GERENCIADOR DE STARTERS!");
    }

}
