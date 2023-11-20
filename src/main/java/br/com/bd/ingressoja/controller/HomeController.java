package br.com.bd.ingressoja.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ingressoja")
public class HomeController {
    private HttpSession httpSession;

    public HomeController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping(value = { "", "/", "/home" })
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/home");

        Object expirado = httpSession.getAttribute("expired");
        Object user = httpSession.getAttribute("user");
        Object exit = httpSession.getAttribute("exit");

        if (expirado != null) {
            modelAndView.addObject("expired", true);
            httpSession.removeAttribute("expired");
        } else {
            modelAndView.addObject("expired", false);
        }

        modelAndView.addObject("logado", Objects.nonNull(user));

        if (exit != null && exit.equals(true)) {
            modelAndView.addObject("exit", true);
            httpSession.removeAttribute("exit");
        }

        return modelAndView;
    }

}
