package com.gft.management.controller;


import com.gft.management.handlers.ModelAndViewUtilParaHandlers;
import com.gft.management.models.Daily;
import com.gft.management.models.Starter;
import com.gft.management.models.Usuario;
import com.gft.management.services.DailyScrumService;
import com.gft.management.services.DailyService;
import com.gft.management.services.StarterService;
import com.gft.management.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/dailys")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @Autowired
    private DailyScrumService dailyScrumService;

    @Autowired
    private UsuarioDetailsService usuarioService;

    @Autowired
    private StarterService starterService;

    @RequestMapping(path = "/new")
    public ModelAndView newDaily(@RequestParam Long id) throws Exception {
        ModelAndView view = new ModelAndView("dailyForm");
        Daily daily = new Daily();
        Starter starter = starterService.getStarter(id);
        daily.setStarter(starter);
        starter.getDailies().add(daily);

        view.addObject("daily", daily);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new")
    public ModelAndView saveADaily(@Valid Daily daily, BindingResult bind, RedirectAttributes redirect) throws Exception {
        ModelAndView view = new ModelAndView("/dailyForm");
        ModelAndView correctView = new ModelAndView("redirect:/dailys");

        if(bind.hasErrors()){
            view.addObject("daily", daily);
            return view;
        }

        redirect.addFlashAttribute("message", "Daily salvo!");

        view.addObject("daily", new Daily());
        dailyService.saveDaily(daily);

        return correctView;
    }

    @RequestMapping(path = "/edit")
    public ModelAndView editDaily(@RequestParam(name = "id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/dailyForm");
        Daily daily;

            try{
                daily = dailyService.getDaily(id);
            } catch (Exception e){
                return ModelAndViewUtilParaHandlers.retornaPaginaErro();
            }
        view.addObject("daily", daily);
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAllDailys(){
        ModelAndView view = new ModelAndView("/dailyListAll");
        List<Daily> list = dailyService.listAllDailys();

        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        String usuarioEmail = autenticacao.getName();
        Usuario usuarioLogado = usuarioService.buscaUsuario(usuarioEmail).get();
        view.addObject("starters", dailyScrumService.listarStartersPorRoleParaAcessoAsDailys(usuarioLogado));
        view.addObject("dailys", list);
        view.addObject("usuario", usuarioLogado);

        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteDaily(@RequestParam(name = "id") Long id, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("redirect:/dailys");
        Daily daily;

        try{
            daily = dailyService.getDaily(id);
            dailyService.deleteDaily(id);
            redirect.addFlashAttribute("message", "Daily apagado!");
        } catch (Exception e){
            redirect.addFlashAttribute("message", "Erro ao excluir daily: " + e.getMessage());
        }
        return view;
    }
}
