package br.com.bd.ingresso.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import br.com.bd.ingresso.model.UserDto;
import br.com.bd.ingresso.model.Login;
import br.com.bd.ingresso.repository.LoginRepository;

@Controller
public class LoginController {
  private LoginRepository loginRepository;
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

  public LoginController(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @GetMapping(value = { "/ingressoja", "/" })
  public String index() {
    return "login";
  }

  @PostMapping("/login")
  public String login(UserDto login) {

    try {
      Login user = loginRepository.findByEmailAndSenha(login.getEmail(), login.getSenha());
      if (Objects.nonNull(user) && Objects.equals(user.getSenha(), login.getSenha())) {
        return "redirect:/home";
      }
    } catch (Exception e) {
      logger.error("Erro ao logar", e);
    }

    return null;
  }

  @GetMapping("/home")
  public String home() {
    return "home";
  }
}