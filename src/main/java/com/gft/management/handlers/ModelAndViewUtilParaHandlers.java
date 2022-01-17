package com.gft.management.handlers;

import org.springframework.web.servlet.ModelAndView;

public abstract class ModelAndViewUtilParaHandlers {

    public static ModelAndView retornaPaginaErro(){
        ModelAndView notFoundView = new ModelAndView("error/erroPagina");
        notFoundView.addObject("message", "404");
        return notFoundView;
    }
}
