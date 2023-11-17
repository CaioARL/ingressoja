package br.com.bd.ingresso.controller;

import java.util.InputMismatchException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.bd.ingresso.model.Comprador;
import br.com.bd.ingresso.model.Usuario;
import br.com.bd.ingresso.model.dto.CompradorDto;
import br.com.bd.ingresso.repository.CompradotRepository;
import br.com.bd.ingresso.repository.UsuarioRepository;

@Controller
@RequestMapping("/ingressoja")
public class CadastroController {
    private HttpSession httpSession;
    private CompradotRepository compradorRepository;
    private UsuarioRepository usuarioRepository;

    public CadastroController(HttpSession httpSession, CompradotRepository compradorRepository,
            UsuarioRepository usuarioRepository) {
        this.httpSession = httpSession;
        this.compradorRepository = compradorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/validateCadastro")
    public String validateCadastro(@RequestParam String cpf) {
        if (!validateCPF(cpf)) {
            return "CPF inválido";
        }

        return null;
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public ModelAndView cadastroPost(CompradorDto compradorDto) {
        ModelAndView modelAndView = new ModelAndView();
        Comprador cpFromBd = compradorRepository.findByCpf(compradorDto.getCpf());

        if (cpFromBd == null) {
            Comprador cp = new Comprador();
            cp.setId(compradorRepository.findNextId());
            cp.setNome(compradorDto.getNome());
            cp.setCpf(compradorDto.getCpf());
            cp.setAtivo(1);

            Usuario usuario = usuarioRepository.findByEmailAndSenha(compradorDto.getEmail(), compradorDto.getSenha());

            if (usuario == null) {
                usuario = new Usuario(usuarioRepository.findNextId(), compradorDto.getEmail(),
                        compradorDto.getSenha());

                usuarioRepository.save(usuario);
            }

            cp.setUsuario(usuario);
            compradorRepository.save(cp);
            usuarioRepository.save(usuario);

            modelAndView.setViewName("/home");
            modelAndView.addObject("userExist", false);
            httpSession.setAttribute("user", cp.getUsuario());
        } else {
            modelAndView.setViewName("/cadastro");
            modelAndView.addObject("userExist", true);
        }

        return modelAndView;
    }

    // Valida CPF do comprador
    private boolean validateCPF(String cpf) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return (false);

        char dig10;
        char dig11;
        int sm;
        int i;
        int r;
        int num;
        int peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

}
