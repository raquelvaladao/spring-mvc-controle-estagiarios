package com.gft.management.controller;

import com.gft.management.models.Tecnologia;
import com.gft.management.services.TecnologiaService;
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
@RequestMapping("/tecnologias")
public class TecnologiaController {
    @Autowired
    private TecnologiaService tecnologiaService;

    @RequestMapping
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index.html");

        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new")
    public ModelAndView newTecnologia(){
        ModelAndView view = new ModelAndView("/tecnologiaForm.html");

        view.addObject("tecnologia", new Tecnologia());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new")
    public ModelAndView saveTecnologia(@Valid Tecnologia tecnologia, BindingResult bind, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("/tecnologiaForm.html");
        ModelAndView correctView = new ModelAndView("redirect:/tecnologias");

        if(bind.hasErrors()){
            view.addObject("tecnologia", tecnologia);
            return view;
        }
        redirect.addFlashAttribute("message", "Tecnologia salvo!");

        view.addObject("tecnologia", new Tecnologia());
        tecnologiaService.saveTecnologia(tecnologia);

        return correctView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listTecnologias(){
        ModelAndView view = new ModelAndView("/tecnologiaListAll");
        List<Tecnologia> tecnologias = tecnologiaService.listAllTecnologias();

        view.addObject("list", tecnologias);
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit")
    public ModelAndView editTecnologia(@RequestParam(name = "id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/tecnologiaForm");
        Tecnologia tecnologia;

        try{
            tecnologia = tecnologiaService.findTecnologia(id);
        } catch (Exception e){
            tecnologia = new Tecnologia();
            view.addObject("message", e.getMessage());
        }
        view.addObject("tecnologia", tecnologia);

        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteTecnologia(@RequestParam(name = "id") Long id, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("redirect:/tecnologias");
        Tecnologia tecnologia;

        try{
            tecnologia = tecnologiaService.findTecnologia(id);
            tecnologiaService.deleteTecnologia(id);
            redirect.addFlashAttribute("message", "Tecnologia apagada!");
        } catch (Exception e){
            redirect.addFlashAttribute("message", "Você não pode apagar uma tecnologia usada por um grupo.");
        }

        return view;
    }
}
