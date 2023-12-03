package br.com.bd.ingressoja.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.bd.ingressoja.model.Administrador;
import br.com.bd.ingressoja.model.Comprador;
import br.com.bd.ingressoja.model.Usuario;
import br.com.bd.ingressoja.model.dto.UserDto;
import br.com.bd.ingressoja.repository.AdministradorRepository;
import br.com.bd.ingressoja.repository.CompradorRepository;
import br.com.bd.ingressoja.repository.UsuarioRepository;

@Controller
@RequestMapping("/ingressoja")
public class LoginController {
  private static final String REDIRECT_HOME = "redirect:/ingressoja/home";
  private HttpSession httpSession;
  private UsuarioRepository loginRepository;
  private CompradorRepository compradorRepository;
  private AdministradorRepository administradorRepository;

  public LoginController(HttpSession httpSession, UsuarioRepository loginRepository,
      CompradorRepository compradorRepository, AdministradorRepository administradorRepository) {
    this.httpSession = httpSession;
    this.loginRepository = loginRepository;
    this.compradorRepository = compradorRepository;
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
      modelAndView = getTipoUsuario(userDto, modelAndView);
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

  private ModelAndView getTipoUsuario(Usuario usuario, ModelAndView modelAndView) {
    Comprador comprador = compradorRepository.findByUsuario(usuario);
    Administrador administrador = administradorRepository.findByUsuario(usuario);

    modelAndView.addObject("isAdm", administrador != null);
    modelAndView.addObject("isComprador", comprador != null);
    return modelAndView;
  }
}