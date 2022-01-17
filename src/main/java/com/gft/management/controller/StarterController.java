package com.gft.management.controller;

import com.gft.management.handlers.ModelAndViewUtilParaHandlers;
import com.gft.management.models.Starter;
import com.gft.management.services.GrupoStartersService;
import com.gft.management.services.StarterService;
import com.gft.management.utils.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/starters")
public class StarterController {
    @Autowired
    private StarterService starterService;

    @Autowired
    private GrupoStartersService grupoService;

    @RequestMapping(method = RequestMethod.GET, path = "/new")
    public ModelAndView saveStarterView(@RequestParam(name = "id", required = false) Long id) throws Exception {
        ModelAndView view = new ModelAndView("/starterForm.html");
        Starter starter;

        view.addObject("starter", new Starter());
        view.addObject("gruposList", grupoService.pegarGruposComEspacoDisponivel());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new", consumes = "multipart/form-data")
    public ModelAndView saveStarter(@Valid Starter starterForm,
                                    BindingResult bind,
                                    RedirectAttributes redirect,
                                    @RequestParam(value = "image")
                                    @Valid MultipartFile multipartFile) throws Exception {
        ModelAndView view = new ModelAndView("/starterForm");
        ModelAndView red = new ModelAndView("redirect:/starters");


        verSe4LetrasSaoUnicas(starterForm, bind);
        if (bind.hasErrors()) {
            view.addObject("starter", starterForm);
            view.addObject("gruposList", grupoService.pegarGruposComEspacoDisponivel());
            return view;
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        starterForm.setFoto(fileName);
        starterForm.setLetras(starterForm.getLetras().toUpperCase());
        starterService.saveStarter(starterForm);

        String uploadDir = "starters-photos/" + starterForm.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        redirect.addFlashAttribute("message", "Starter salvo com sucesso");
        view.addObject("gruposList", grupoService.pegarGruposComEspacoDisponivel());
        return red;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/photo/{id}")
    public ModelAndView updateFotoView(@PathVariable(name = "id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/starterPutForm");
        Starter starter = new Starter();

        try {
            starter = starterService.getStarter(id);
        } catch (Exception e) {
            return ModelAndViewUtilParaHandlers.retornaPaginaErro();
        }
        view.addObject("starter", starter);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/photo/{id}", consumes = "multipart/form-data")
    public ModelAndView updateFotoStarter(@Valid Starter starterForm, RedirectAttributes redirect,
                                          @RequestParam(value = "image", required = false)
                                                  MultipartFile multipartFile,
                                          BindingResult bind) throws Exception {
        ModelAndView view = new ModelAndView("/starterPutForm");
        ModelAndView red = new ModelAndView("redirect:/starters");

        if (bind.hasErrors()) {
            return red;
        }
        if (multipartFile.isEmpty()) {
            view.addObject("messageFoto", "Foto não pode ser vazia");
            return view;
        }
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        starterService.updateFotoStarter(starterForm, fileName);

        String uploadDir = "starters-photos/" + starterForm.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        redirect.addFlashAttribute("message", "Foto de " + starterForm.getNome() + " salva com sucesso");
        return red;
    }

    private void verSe4LetrasSaoUnicas(Starter starterForm, BindingResult bind) {
        List<Starter> todosStarters = starterService.getAllStarters();
        for (Starter starter : todosStarters) {
            if (starter.getLetras().equals(starterForm.getLetras().toUpperCase())) {
                bind.rejectValue("letras",
                        "Duplicado",
                        "Já existe um starter com essas 4 letras.");
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit")
    public ModelAndView editStarter(@RequestParam(name = "id") Long id) throws Exception {
        ModelAndView view = new ModelAndView("/starterEditarForm");
        Starter starter = new Starter();

        try {
            starter = starterService.getStarter(id);
        } catch (Exception e) {
            return ModelAndViewUtilParaHandlers.retornaPaginaErro();
        }

        view.addObject("starter", starter);
        view.addObject("gruposList", grupoService.pegarGruposComEspacoDisponivel());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public ModelAndView updateStarter(@Valid Starter starter, BindingResult bind, RedirectAttributes redirect) {
        ModelAndView view = new ModelAndView("/starterEditarForm.html");
        ModelAndView correctView = new ModelAndView("redirect:/starters");

        verSe4LetrasSaoUnicas(starter, bind);
        if (bind.hasErrors()) {
            view.addObject("starter", starter);
            view.addObject("gruposList", grupoService.pegarGruposComEspacoDisponivel());
            return view;
        }
        redirect.addFlashAttribute("message", "Starter editado!");
        view.addObject("starter", new Starter());
        starter.setLetras(starter.getLetras().toUpperCase());
        starterService.updateStarter(starter);

        return correctView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultPage() throws IOException {
        verificarPastasDeFotos();
        return listAllStarters(1);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{pageNo}")
    public ModelAndView listAllStarters(@PathVariable(value = "pageNo") int pageNo) {
        ModelAndView view = new ModelAndView("/starterListAll");
        int pageSize = 5;
        List<Starter> list;

        Page<Starter> page = starterService.pegaPaginado(pageNo, pageSize);
        list = page.getContent();

        view.addObject("currentPage", pageNo);
        view.addObject("totalPages", page.getTotalPages());
        view.addObject("pageSize", pageSize);
        view.addObject("totalItems", page.getTotalElements());
        view.addObject("list", list);
        return view;
    }

    @RequestMapping(method = {RequestMethod.DELETE, RequestMethod.GET}, path = "/delete")
    public ModelAndView deleteStarter(@RequestParam(name = "id") Long id, RedirectAttributes redirect) {
        ModelAndView view = new ModelAndView("redirect:/starters");
        Starter starter;

        try {
            starter = starterService.getStarter(id);
            starterService.deleteStarter(id);
            redirect.addFlashAttribute("message", "Starter apagado!");
            FileUtils.deleteDirectory(new File("starters-photos/" + starter.getId()));
        } catch (Exception e) {
            redirect.addFlashAttribute("message", "Erro ao excluir starter. Erro: " + e.getMessage());
        }
        return view;
    }

    public void verificarPastasDeFotos() throws IOException {
        String pastaASerCopiadaNome;
        File pastaASerCopiadaPasta;

        File pasta = new File("starters-photos-new");
        pasta.mkdirs();

        List<Starter> starters = starterService.getAllStarters();
        for (Starter starter : starters) {
            pastaASerCopiadaNome = "starters-photos" + "/" + starter.getId();
            pastaASerCopiadaPasta = new File(pastaASerCopiadaNome);

            String starterDest = "starters-photos-new" + "/" + starter.getId();
            File starterDestFile = new File(starterDest);
            try {
                if(pastaASerCopiadaPasta.exists()){
                    if(pastaASerCopiadaPasta.isDirectory()){
                        FileUtils.copyDirectory(pastaASerCopiadaPasta, starterDestFile);
                    } else {
                        FileUtils.deleteQuietly(pastaASerCopiadaPasta);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtils.deleteDirectory(new File("starters-photos"));
        pasta.renameTo(new File("starters-photos"));
    }

}
