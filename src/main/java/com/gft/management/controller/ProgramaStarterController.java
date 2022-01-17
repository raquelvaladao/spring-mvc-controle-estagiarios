package com.gft.management.controller;

import com.gft.management.handlers.ModelAndViewUtilParaHandlers;
import com.gft.management.models.ProgramaStarter;
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
@RequestMapping("/programas")
public class ProgramaStarterController {

    @Autowired
    private ProgramaStarterService programaStarterService;

    @RequestMapping
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index.html");

        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/new")
    public ModelAndView newProgramaStarter(){
        ModelAndView view = new ModelAndView("/programaStarterForm.html");

        view.addObject("programaStarter", new ProgramaStarter());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new")
    public ModelAndView saveProgramaStarter(@Valid ProgramaStarter programaStarter, BindingResult bind, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("/programaStarterForm.html");
        ModelAndView correctView = new ModelAndView("redirect:/programas");

        if(bind.hasErrors()){
            view.addObject("programaStarter", programaStarter);
            return view;
        }
        redirect.addFlashAttribute("message", "Programa salvo!");

        view.addObject("programaStarter", new ProgramaStarter());
        programaStarterService.saveProgramaStarter(programaStarter);

        return correctView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listProgramaStarters(){
        ModelAndView view = new ModelAndView("/programaStarterListAll");
        List<ProgramaStarter> programaStarters = programaStarterService.listAllProgramaStarters();

        view.addObject("list", programaStarters);
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit")
    public ModelAndView editProgramaStarter(@RequestParam(name = "id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/programaStarterForm");
        ProgramaStarter programaStarter;

        try{
            programaStarter = programaStarterService.findProgramaStarter(id);
        } catch (Exception e){
            return ModelAndViewUtilParaHandlers.retornaPaginaErro();
        }
        view.addObject("programaStarter", programaStarter);

        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteProgramaStarter(@RequestParam(name = "id") Long id, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("redirect:/programas");
        ProgramaStarter programaStarter;

        try{
            programaStarter = programaStarterService.findProgramaStarter(id);
            programaStarterService.deleteProgramaStarter(id);
            redirect.addFlashAttribute("message", "Programa apagado!");
        } catch (Exception e){
            redirect.addFlashAttribute("message", "Você não pode excluir um programa populado");
        }

        return view;
    }
}
