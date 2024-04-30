package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProbabilidadeAdocaoTest {

  @Test
  @DisplayName("Deveria retonar probabilida ALTA quando idade e peso do pet são baixos")
  void probabilidadeAltaCenario01(){

    //ARRENGE
    Abrigo abrigo = new Abrigo(
            new CadastroAbrigoDto("Adopet",
                    "31999998888",
                    "adopet@adope.com")
    );

    Pet pet = new Pet(new CadastroPetDto(
            TipoPet.CACHORRO,
            "Chocolate",
            "Shitzu",
            4,
            "preto",
            4.0f
    ), abrigo);

    //ACT
    CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
    ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

    //ASSERT
    Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
  }

  @Test
  @DisplayName("Deveria retornar probabilidade MEDIA quando idade do pet é alta e peso é baixo")
  void probabilidadeMediaCenario02(){

    //ARRENGE
    Abrigo abrigo = new Abrigo(
            new CadastroAbrigoDto("Adopet",
                    "31999998888",
                    "adopet@adope.com")
    );

    Pet pet = new Pet(new CadastroPetDto(
            TipoPet.CACHORRO,
            "Chocolate",
            "Shitzu",
            15,
            "preto",
            4.0f
    ), abrigo);

    //ACT
    CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
    ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

    //ASSERT
    Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
  }

}