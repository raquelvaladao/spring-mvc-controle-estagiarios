package com.gft.management.controller;

import com.gft.management.models.Grupo;
import com.gft.management.services.GrupoStartersService;
import com.gft.management.services.ModuloStartersService;
import com.gft.management.services.TecnologiaService;
import com.gft.management.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/grupos")
public class GrupoStartersController {

    @Autowired
    private GrupoStartersService grupoService;

    @Autowired
    private ModuloStartersService moduloService;

    @Autowired
    private TecnologiaService tecnologiaService;

    @Autowired
    private UsuarioDetailsService usuarioService;

    @RequestMapping(path = "/edit")
    public ModelAndView editGrupo(@RequestParam(name = "id", required = false) Long id) throws Exception {
        ModelAndView view = new ModelAndView("/grupoForm.html");
        Grupo grupo;

        if(id == null){
            grupo = new Grupo();
        } else {
            try{
                grupo = grupoService.getGrupo(id);
            } catch (Exception e){
                grupo = new Grupo();
                view.addObject("message", "Não existe esse grupo. Crie um novo");
            }
        }
        view.addObject("grupo", grupo);
        adicionarObjetosNaView(view, id);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public ModelAndView saveGrupo(@Valid Grupo grupoForm, BindingResult bind, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("/grupoForm");
        ModelAndView red = new ModelAndView("redirect:/grupos");

        boolean isNew = true;
        if(grupoForm.getId() != null){
            isNew = false;
        }

        if(bind.hasErrors()){
            view.addObject("grupo", grupoForm);
            return view;
        }

        Grupo savedGrupo = grupoService.saveGrupo(grupoForm);
        if(isNew){
            view.addObject("grupo", new Grupo());
        } else {
            view.addObject("grupo", savedGrupo);
        }
        redirect.addFlashAttribute("message", "Grupo salvo com sucesso");
        adicionarObjetosNaView(view,null);

        return red;
    }

    private void adicionarObjetosNaView(ModelAndView view, Long id) {
        view.addObject("modulosList", moduloService.listAllModulos());
        view.addObject("tecnologiasList", tecnologiaService.listAllTecnologias());
        view.addObject("usuariosList", usuarioService.listUsuariosDisponiveisParaSerScrum(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage(){
        return listAllGrupos(1);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{pageNo}")
    public ModelAndView listAllGrupos(@PathVariable(value = "pageNo") int pageNo){
        ModelAndView view = new ModelAndView("/grupoListAll");
        int pageSize = 5;
        List<Grupo> list;

        Page<Grupo> page = grupoService.pegaPaginado(pageNo, pageSize);
        list = page.getContent();

        view.addObject("currentPage", pageNo);
        view.addObject("totalPages", page.getTotalPages());
        view.addObject("pageSize", pageSize);
        view.addObject("totalItems", page.getTotalElements());
        view.addObject("list", list);
        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteGrupo(@RequestParam(name = "id") Long id, RedirectAttributes redirect){
        ModelAndView view = new ModelAndView("redirect:/grupos");
        Grupo grupo;

        try{
            grupoService.deleteGrupo(id);
            redirect.addFlashAttribute("message", "Grupo apagado!");
        } catch (Exception e){
            redirect.addFlashAttribute("message", "Você não pode excluir um grupo populado." + e.getMessage());
        }
        return view;
    }
}
