package br.com.bd.ingressoja.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingressoja.model.Comprador;
import br.com.bd.ingressoja.model.Usuario;
import br.com.bd.ingressoja.model.dto.PerfilDto;
import br.com.bd.ingressoja.repository.CompradorRepository;
import br.com.bd.ingressoja.repository.UsuarioRepository;
import br.com.bd.ingressoja.service.CadastroService;

@Controller
@RequestMapping("/ingressoja")
public class CadastroController {
    private CompradorRepository compradorRepository;
    private UsuarioRepository usuarioRepository;
    private CadastroService cadastroService;

    public CadastroController(CompradorRepository compradorRepository,
            UsuarioRepository usuarioRepository, CadastroService cadastroService) {
        this.compradorRepository = compradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.cadastroService = cadastroService;
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastroPost(PerfilDto perfilDto) {

        perfilDto.setCpf(perfilDto.getCpf().replaceAll("[^0-9]", ""));

        ModelAndView modelAndView = new ModelAndView();
        if (cadastroService.validateCPF(perfilDto.getCpf())) {

            Comprador cpFromBd = compradorRepository.findByCpf(perfilDto.getCpf());

            if (cpFromBd == null) {
                Comprador cp = new Comprador();
                cp.setId(compradorRepository.findNextId());
                cp.setNome(perfilDto.getNome());
                cp.setCpf(perfilDto.getCpf());
                cp.setAtivo(1);

                Usuario usuario = usuarioRepository.findByEmail(perfilDto.getEmail());

                if (usuario == null) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                    usuario = new Usuario(usuarioRepository.findNextId(), perfilDto.getEmail(),
                    passwordEncoder.encode(perfilDto.getSenha()));

                    usuarioRepository.save(usuario);
                }

                cp.setUsuario(usuario);
                usuarioRepository.save(usuario);
                compradorRepository.save(cp);

                modelAndView.setViewName("/login");
                modelAndView.addObject("userCreate", true);
            } else {
                modelAndView.setViewName("/cadastro");
                modelAndView.addObject("userExist", true);
            }
        } else {
            modelAndView.setViewName("/cadastro");
            perfilDto.setCpf(perfilDto.getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));
            modelAndView.addObject("usuario", perfilDto);
            modelAndView.addObject("cpfInvalid", true);
        }
        return modelAndView;
    }

}
