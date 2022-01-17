package com.gft.management.controller;

import com.gft.management.models.Modulo;
import com.gft.management.models.Projeto;
import com.gft.management.services.ModuloStartersService;
import com.gft.management.services.ProjetoService;
import com.gft.management.services.StarterService;
import com.gft.management.utils.NotasPDFExporter;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private StarterService starterService;

    @Autowired
    private ModuloStartersService moduloService;

    @RequestMapping(path = "/edit")
    public ModelAndView editProjeto(@RequestParam(name = "id", required = false) Long id) throws Exception {
        ModelAndView view = new ModelAndView("/projetoForm");
        Projeto projeto;

        if (id == null) {
            projeto = new Projeto();
        } else {
            try {
                projeto = projetoService.getProjeto(id);
            } catch (Exception e) {
                projeto = new Projeto();
                view.addObject("message", "Não existe esse projeto. Crie um novo");
            }
        }

        view.addObject("projeto", projeto);
        view.addObject("startersList", starterService.getAllStarters());
        view.addObject("modulosList", moduloService.listAllModulos());

        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public ModelAndView saveProjeto(@Valid Projeto projetoForm, BindingResult bind, RedirectAttributes redirect) {
        ModelAndView view = new ModelAndView("/projetoForm");
        ModelAndView redirectView = new ModelAndView("redirect:/projetos");
        boolean isNew = projetoForm.getId() == null;

        verSeEtapaDoProjetoEstaRepetida(projetoForm, bind);
        if (bind.hasErrors()) {
            view.addObject("projeto", projetoForm);
            view.addObject("startersList", starterService.getAllStarters());
            return view;
        }

        Projeto projetoSalvo = projetoService.saveProjeto(projetoForm);
        if (isNew) {
            view.addObject("projeto", new Projeto());
        } else {
            view.addObject("projeto", projetoSalvo);
        }
        redirect.addFlashAttribute("message", "Projeto salvo com sucesso");
        return redirectView;
    }

    private void verSeEtapaDoProjetoEstaRepetida(Projeto projetoForm, BindingResult bind) {
        List<Projeto> projetosDoStarterQuePossuiProjeto = projetoForm.getStarter().getProjetos();
        for (Projeto projeto : projetosDoStarterQuePossuiProjeto) {
            if (projeto.getEtapa() == projetoForm.getEtapa() && projeto.getModulo() == projetoForm.getModulo()) {
                bind.rejectValue("etapa",
                        "Duplicado",
                        "Um starter só pode ter um projeto por etapa num mesmo módulo.");
            }
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listProjetosDefault() throws IOException {
        return listProjetos(1);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{pageNo}")
    public ModelAndView listProjetos(@PathVariable(value = "pageNo") int pageNo) {
        ModelAndView view = new ModelAndView("/projetoListAll");
        int pageSize = 7;
        List<Projeto> list;

        Page<Projeto> page = projetoService.pegaPaginado(pageNo, pageSize);
        list = page.getContent();

        view.addObject("currentPage", pageNo);
        view.addObject("totalPages", page.getTotalPages());
        view.addObject("pageSize", pageSize);
        view.addObject("totalItems", page.getTotalElements());
        view.addObject("list", list);
        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteProjeto(@RequestParam(name = "id") Long id, RedirectAttributes redirect) {
        ModelAndView view = new ModelAndView("redirect:/projetos");
        Projeto projeto;

        try {
            projeto = projetoService.getProjeto(id);
            projetoService.deleteProjeto(id);
            redirect.addFlashAttribute("message", "Projeto apagado!");
        } catch (Exception e) {
            redirect.addFlashAttribute("message", "Erro ao excluir projeto. Erro: " + e.getMessage());
        }
        return view;
    }

    @GetMapping("/relatorio/export/pdf")
    public void exportarParaPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=notas_RLDJ_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Projeto> listaDeProjetos = projetoService.getNotasOrdenadas();

        NotasPDFExporter exporter = new NotasPDFExporter(listaDeProjetos);
        exporter.export(response);

    }
}
