package com.gft.management.controller;

import com.gft.management.models.Grupo;
import com.gft.management.models.Modulo;
import com.gft.management.services.GrupoStartersService;
import com.gft.management.services.ModuloStartersService;
import com.gft.management.services.ProgramaStarterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/modulos")
public class ModuloController {

    @Autowired
    private ModuloStartersService moduloService;

    @Autowired
    private GrupoStartersService grupoService;

    @Autowired
    private ProgramaStarterService programaStarterService;


    @RequestMapping(path = "/edit")
    public ModelAndView editModulo(@RequestParam(name = "id", required = false) Long id) throws Exception {
        ModelAndView view = new ModelAndView("/moduloForm");
        Modulo modulo;

        if(id == null){
            modulo = new Modulo();
        } else {
            try{
                modulo = moduloService.getModulo(id);
            } catch (Exception e){
                modulo = new Modulo();
                view.addObject("message", "Não existe esse modulo. Crie um novo");
            }
        }
        view.addObject("modulo", modulo);
        view.addObject("programasList", programaStarterService.listAllProgramaStarters());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public ModelAndView saveModulo(@Valid Modulo moduloForm, BindingResult bind, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("/moduloForm");
        ModelAndView red = new ModelAndView("redirect:/modulos");

        boolean isNew = true;
        if(moduloForm.getId() != null){
            isNew = false;
        }

        if(bind.hasErrors()){
            view.addObject("modulo", moduloForm);
            view.addObject("programasList", programaStarterService.listAllProgramaStarters());
            return view;
        }

        Modulo savedModulo = moduloService.saveModulo(moduloForm);
        if(isNew){
            view.addObject("modulo", new Modulo());
        } else {
            view.addObject("modulo", savedModulo);
        }
        redirect.addFlashAttribute("message", "Modulo salvo com sucesso");
        return red;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listAllModulos(){
        ModelAndView view = new ModelAndView("/moduloListAll");
        List<Modulo> list = moduloService.listAllModulos();
        List<Grupo> gruposList = grupoService.getAllGrupos();
        view.addObject("list", list);

        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteModulo(@RequestParam(name = "id") Long id, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("redirect:/modulos");
        Modulo modulo;

        try{
            modulo = moduloService.getModulo(id);
            moduloService.deleteModulo(id);
            redirect.addFlashAttribute("message", "Modulo apagado!");
        } catch (Exception e){
            redirect.addFlashAttribute("message", "Erro ao excluir modulo, você não pode excluir um módulo populado.");
        }
        return view;
    }

}
