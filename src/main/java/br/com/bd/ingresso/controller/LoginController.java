package br.com.bd.ingresso.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingresso.model.UserDto;
import br.com.bd.ingresso.model.Usuario;
import br.com.bd.ingresso.repository.UsuarioRepository;

@Controller
public class LoginController {
  private UsuarioRepository loginRepository;
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  public LoginController(UsuarioRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @GetMapping(value = "/ingressoja")
  public ModelAndView index() {
    return home(null);
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public ModelAndView login(UserDto user) {
    try {
      ModelAndView modelAndView = new ModelAndView();

      Usuario userDto = loginRepository.findByEmailAndSenha(user.getEmail(), user.getSenha());
      if (Objects.nonNull(userDto)) {
        return home(true);

      }
      modelAndView.addObject("userFind", false);
      return modelAndView;

    } catch (Exception e) {
      logger.error("Erro ao logar", e);
    }

    return null;
  }

  @GetMapping("/home")
  public ModelAndView home(Boolean logado) {
    ModelAndView modelAndView = new ModelAndView("/home");
    modelAndView.addObject("logado", logado);
    return modelAndView;
  }
}