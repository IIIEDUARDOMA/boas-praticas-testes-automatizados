package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class AdocaoServiceTest {

  @InjectMocks
  private AdocaoService adocaoService;

  @Mock
  private AdocaoRepository adocaoRepository;

  @Mock
  private PetRepository petRepository;

  @Mock
  private TutorRepository tutorRepository;

  @Mock
  private EmailService emailService;

  @Spy
  private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

  @Mock ValidacaoSolicitacaoAdocao validador1;
  @Mock ValidacaoSolicitacaoAdocao validador2;

  @Mock
  private Pet pet;

  @Mock
  private Tutor tutor;

  @Mock
  private Abrigo abrigo;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  private SolicitacaoAdocaoDto dto;

  @Captor
  private ArgumentCaptor<Adocao> adocaoCaptor;

  @Test
  void deveriaSalvarAdocaoSolicitar() {
    //ARRANGE
    this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
    given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
    given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
    given(pet.getAbrigo()).willReturn(abrigo);


    adocaoService.solicitar(dto);

    then(adocaoRepository).should().save(adocaoCaptor.capture());
    Adocao adocaoSalva = adocaoCaptor.getValue();
    Assertions.assertEquals(pet, adocaoSalva.getPet());
    Assertions.assertEquals(tutor, adocaoSalva.getTutor());
    Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
  }
  @Test
  void deveriaChamarValidadoresDeAdocaoSolicitar() {
    //ARRANGE
    this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
    given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
    given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
    given(pet.getAbrigo()).willReturn(abrigo);
    validacoes.add(validador1);
    validacoes.add(validador2);


    adocaoService.solicitar(dto);

    BDDMockito.then(validador1).should().validar(dto);
    BDDMockito.then(validador2).should().validar(dto);


  }
}