package br.com.bd.ingressoja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.bd.ingressoja.model.Evento;
import br.com.bd.ingressoja.repository.EventoRepository;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;
import java.time.Duration;

@Controller
@RequestMapping("/ingressoja")
public class EventoController {

    private static final String EVENTO_VIEW = "evento";
    private EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/evento")
    public ModelAndView getDescricao(String title) {
        ModelAndView modelAndView = new ModelAndView();
        String descricao = null;

        Evento evento = eventoRepository.findByTituloContaining(title).get(0);
        if (Objects.nonNull(evento)) {
            descricao = evento.getDescricao();
            modelAndView.addObject("descricao", descricao);
            modelAndView.addObject("title", evento.getTitulo());
            modelAndView.addObject("img", evento.getImagemURL());
            modelAndView.addObject("duracao", getHorario(evento));
        }

        modelAndView.setViewName(EVENTO_VIEW);
        modelAndView.addObject("hasDescricao", descricao != null);
        return modelAndView;
    }

    private Long getHorario(Evento evento) {
        return Duration.between(evento.getInicio(), evento.getTermino()).toMinutes();
    }

}
