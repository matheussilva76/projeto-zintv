package br.com.dopoke.zintv.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

@Data
public class Eps {
    private Integer temporada;
    private String titulo;
    private Integer ep;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Eps(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.ep = Integer.parseInt(dadosEpisodio.numero());
        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }
    }

    @Override
    public String toString() {
        return "temporada=" + temporada + ", titulo='" + titulo + '\'' + ", ep=" + ep + ", avaliacao=" + avaliacao + ", dataLancamento=" + dataLancamento;
    }
}
