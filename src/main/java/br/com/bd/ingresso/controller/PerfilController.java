package br.com.bd.ingresso.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingresso.model.Comprador;
import br.com.bd.ingresso.model.Usuario;
import br.com.bd.ingresso.model.dto.CompradorDto;
import br.com.bd.ingresso.repository.CompradorRepository;
import br.com.bd.ingresso.repository.UsuarioRepository;

@Controller
@RequestMapping("/ingressoja")
public class PerfilController {
    private static final String PERFIL_VIEW = "perfil";
    private static final String UPDATED = "updated";
    private HttpSession httpSession;
    private CompradorRepository compradorRepository;
    private UsuarioRepository usuarioRepository;

    public PerfilController(HttpSession httpSession, CompradorRepository compradorRepository,
            UsuarioRepository usuarioRepository) {
        this.httpSession = httpSession;
        this.compradorRepository = compradorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/editarPerfil")
    public ModelAndView editarPerfil() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PERFIL_VIEW);
        Usuario user = (Usuario) httpSession.getAttribute("user");
        CompradorDto perfil = compradorRepository.findByEmailAndSenha(user.getEmail(), user.getSenha());

        modelAndView.addObject(PERFIL_VIEW, perfil);
        return modelAndView;
    }

    @PostMapping("/editarPerfil")
    public ModelAndView editarPerfil(CompradorDto perfil) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PERFIL_VIEW);

        Usuario user = (Usuario) httpSession.getAttribute("user");
        CompradorDto perfilFromDb = compradorRepository.findByEmailAndSenha(user.getEmail(), user.getSenha());

        if (!perfilFromDb.getNome().equals(perfil.getNome())
                || !perfilFromDb.getCpf().equals(perfil.getCpf())) {
            Comprador comprador = new Comprador();

            comprador.setId(compradorRepository.findByCpf(perfilFromDb.getCpf()).getId());
            comprador.setNome(perfil.getNome());
            comprador.setCpf(perfil.getCpf());

            compradorRepository.save(comprador);

            modelAndView.addObject(UPDATED, true);
        } else if (!perfilFromDb.getEmail().equals(perfil.getEmail())
                || !perfilFromDb.getSenha().equals(perfil.getSenha())) {

            Usuario usuario = new Usuario();

            usuario.setId(
                    usuarioRepository.findByEmailAndSenha(perfilFromDb.getEmail(), perfilFromDb.getSenha()).getId());
            usuario.setEmail(perfil.getEmail());
            usuario.setSenha(perfil.getSenha());

            usuarioRepository.save(usuario);

            httpSession.invalidate();
            modelAndView.setViewName("login");
            modelAndView.addObject(UPDATED, true);
            user = usuario;
        } else {
            modelAndView.addObject(UPDATED, false);
        }

        perfil = compradorRepository.findByEmailAndSenha(user.getEmail(), user.getSenha());
        modelAndView.addObject(PERFIL_VIEW, perfil);
        return modelAndView;
    }

}
