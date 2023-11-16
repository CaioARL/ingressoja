package br.com.bd.ingresso.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingresso.model.UserDto;
import br.com.bd.ingresso.model.Usuario;
import br.com.bd.ingresso.repository.UsuarioRepository;

@Controller
public class LoginController {
  private static final String REDIRECT_HOME = "redirect:/home";
  private HttpSession httpSession;
  private UsuarioRepository loginRepository;

  public LoginController(UsuarioRepository loginRepository, HttpSession httpSession) {
    this.loginRepository = loginRepository;
    this.httpSession = httpSession;
  }

  @GetMapping("/home")
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

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public ModelAndView login(UserDto user) {
    ModelAndView modelAndView = new ModelAndView();
    Usuario userDto = loginRepository.findByEmailAndSenha(user.getEmail(), user.getSenha());

    if (Objects.nonNull(userDto)) {
      httpSession.setAttribute("user", userDto);
      modelAndView.setViewName(REDIRECT_HOME);
    } else {
      modelAndView.addObject("userFind", false);
      modelAndView.setViewName("login");
    }

    return modelAndView;
  }

  @GetMapping("/logout")
  public String logout() {
    httpSession.invalidate();
    httpSession.setAttribute("exit", true);
    return REDIRECT_HOME;
  }

  @GetMapping("/expire")
  public String expire() {
    if (httpSession.getAttribute("user") != null) {
      httpSession.removeAttribute("user");
      httpSession.setAttribute("expired", true);
    }
    return REDIRECT_HOME;
  }
}