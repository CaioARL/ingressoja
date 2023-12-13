package br.com.bd.ingressoja.controller;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingressoja.model.Administrador;
import br.com.bd.ingressoja.model.Comprador;
import br.com.bd.ingressoja.model.Usuario;
import br.com.bd.ingressoja.model.dto.PerfilDto;
import br.com.bd.ingressoja.repository.AdministradorRepository;
import br.com.bd.ingressoja.repository.CompradorRepository;
import br.com.bd.ingressoja.repository.UsuarioRepository;

@Controller
@RequestMapping("/ingressoja")
public class PerfilController {
    private static final String PERFIL_VIEW = "perfil";
    private static final String UPDATED = "updated";
    private HttpSession httpSession;
    private CompradorRepository compradorRepository;
    private UsuarioRepository usuarioRepository;
    private AdministradorRepository administradorRepository;

    public PerfilController(HttpSession httpSession, CompradorRepository compradorRepository,
            UsuarioRepository usuarioRepository, AdministradorRepository administradorRepository) {
        this.httpSession = httpSession;
        this.compradorRepository = compradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.administradorRepository = administradorRepository;
    }

    @GetMapping("/editarPerfil")
    public ModelAndView editarPerfil() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PERFIL_VIEW);
        Usuario user = (Usuario) httpSession.getAttribute("user");

        PerfilDto perfil = compradorRepository.findByUsuario(user) != null
                ? compradorRepository.findByUsuario(user).toPerfildto()
                : administradorRepository.findByUsuario(user).toPerfildto();

        modelAndView.addObject(PERFIL_VIEW, perfil);
        return modelAndView;
    }

    @PostMapping("/editarPerfil")
    public ModelAndView editarPerfil(PerfilDto perfil) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PERFIL_VIEW);

        Usuario user = (Usuario) httpSession.getAttribute("user");
        PerfilDto perfilFromDb = compradorRepository.findByUsuario(user).toPerfildto();

        if (!perfilFromDb.getNome().equals(perfil.getNome())) {
            Comprador comprador = new Comprador();

            comprador.setId(compradorRepository.findByCpf(perfilFromDb.getCpf()).getId());
            comprador.setNome(perfil.getNome());
            comprador.setCpf(perfilFromDb.getCpf());
            comprador.setAtivo(1);
            comprador.setUsuario(usuarioRepository.findByEmail(user.getEmail()));

            compradorRepository.save(comprador);

            modelAndView.addObject(UPDATED, true);
        } else if (!perfilFromDb.getEmail().equals(perfil.getEmail())
                || !passwordEncoder.matches(perfil.getSenha(), perfilFromDb.getSenha())) {

            Usuario usuario = new Usuario();

            usuario.setId(
                    usuarioRepository.findByEmail(perfilFromDb.getEmail()).getId());
            usuario.setEmail(perfil.getEmail());
            usuario.setSenha(passwordEncoder.encode(perfil.getSenha()));

            usuarioRepository.save(usuario);

            httpSession.invalidate();
            modelAndView.setViewName("login");
            modelAndView.addObject(UPDATED, true);
            user = usuario;
        } else {
            modelAndView.addObject(UPDATED, false);
        }

        perfil = compradorRepository.findByUsuario(user).toPerfildto();
        modelAndView.addObject(PERFIL_VIEW, perfil);
        return modelAndView;
    }

    @GetMapping("/excluirConta")
    public ModelAndView excluirConta(String nome, String email) {
        ModelAndView modelAndView = new ModelAndView();
        
        Usuario user = (Usuario) httpSession.getAttribute("user");

        Comprador comprador = compradorRepository.findByUsuario(user);
        Administrador administrador = administradorRepository.findByUsuario(user);

        if(comprador!=null){
            compradorRepository.delete(comprador);
        }else if(administrador!=null){
            administradorRepository.delete(administrador);
        }
        usuarioRepository.delete(user);

        httpSession.invalidate();
        modelAndView.setViewName("login");
        modelAndView.addObject("accountExcluded", true);
        return modelAndView;
    }

}
