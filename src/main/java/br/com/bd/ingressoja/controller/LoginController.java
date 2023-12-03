package br.com.bd.ingressoja.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingressoja.model.Usuario;
import br.com.bd.ingressoja.model.dto.UserDto;
import br.com.bd.ingressoja.repository.AdministradorRepository;
import br.com.bd.ingressoja.repository.UsuarioRepository;

@Controller
@RequestMapping("/ingressoja")
public class LoginController {
  private static final String REDIRECT_HOME = "redirect:/ingressoja/home";
  private HttpSession httpSession;
  private UsuarioRepository loginRepository;
  private AdministradorRepository administradorRepository;

  public LoginController(HttpSession httpSession, UsuarioRepository loginRepository,
      AdministradorRepository administradorRepository) {
    this.httpSession = httpSession;
    this.loginRepository = loginRepository;
    this.administradorRepository = administradorRepository;
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
      httpSession.setAttribute("isAdm", administradorRepository.findByUsuario(userDto) != null);
    } else {
      userDto = loginRepository.findByEmail(user.getEmail());
      modelAndView.setViewName("login");
      if (Objects.nonNull(userDto)) {
        modelAndView.addObject("wrongPassword", true);
      } else {
        modelAndView.addObject("userFind", false);
      }
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