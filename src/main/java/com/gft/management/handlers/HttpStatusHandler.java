package com.gft.management.handlers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HttpStatusHandler implements ErrorController {

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView paginaErro = new ModelAndView("/error/erroPagina");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                paginaErro.addObject("message", "404");

            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                paginaErro.addObject("message", "403");

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                paginaErro.addObject("message", "500");
            }
        }
        return paginaErro;
    }
}