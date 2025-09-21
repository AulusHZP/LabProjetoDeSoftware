package org.example;

import java.math.BigDecimal;
import java.util.Optional;

import org.example.model.Automovel;
import org.example.repository.AutomovelRepository;
import org.example.service.AutomovelService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Testes unitários para AutomovelService
 * Demonstra a funcionalidade implementada na parte 02S03
 */
@ExtendWith(MockitoExtension.class)
class AutomovelServiceTest {
    
    @Mock
    private AutomovelRepository automovelRepository;
    
    @InjectMocks
    private AutomovelService automovelService;
    
    private Automovel automovel;
    
    @BeforeEach
    void setUp() {
        automovel = new Automovel();
        automovel.setMarca("Toyota");
        automovel.setModelo("Corolla");
        automovel.setAno("2023");
        automovel.setPlaca("ABC1234");
        automovel.setCor("Branco");
        automovel.setCategoria("Sedan");
        automovel.setValorDiario(new BigDecimal("150.00"));
        automovel.setCapacidadePassageiros(5);
        automovel.setTipoCombustivel("Flex");
        automovel.setTransmissao("Automático");
        automovel.setArCondicionado(true);
        automovel.setDirecaoHidraulica(true);
        automovel.setAirbag(true);
        automovel.setAbs(true);
    }
    
    @Test
    void testCadastrarAutomovel_Sucesso() {
        // Arrange
        when(automovelRepository.existsByPlaca(anyString())).thenReturn(false);
        when(automovelRepository.save(any(Automovel.class))).thenReturn(automovel);
        
        // Act
        Automovel resultado = automovelService.cadastrarAutomovel(automovel);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Toyota", resultado.getMarca());
        assertEquals("Corolla", resultado.getModelo());
        assertEquals("ABC1234", resultado.getPlaca());
        assertTrue(resultado.getDisponivel());
        assertTrue(resultado.getAtivo());
        
        verify(automovelRepository).existsByPlaca("ABC1234");
        verify(automovelRepository).save(automovel);
    }
    
    @Test
    void testCadastrarAutomovel_PlacaJaExiste() {
        // Arrange
        when(automovelRepository.existsByPlaca(anyString())).thenReturn(true);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> automovelService.cadastrarAutomovel(automovel)
        );
        
        assertEquals("Placa já cadastrada no sistema", exception.getMessage());
        verify(automovelRepository).existsByPlaca("ABC1234");
        verify(automovelRepository, never()).save(any(Automovel.class));
    }
    
    @Test
    void testBuscarPorId_Sucesso() {
        // Arrange
        Long id = 1L;
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        
        // Act
        Optional<Automovel> resultado = automovelService.buscarPorId(id);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(automovel, resultado.get());
        verify(automovelRepository).findById(id);
    }
    
    @Test
    void testBuscarPorId_NaoEncontrado() {
        // Arrange
        Long id = 999L;
        when(automovelRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        Optional<Automovel> resultado = automovelService.buscarPorId(id);
        
        // Assert
        assertFalse(resultado.isPresent());
        verify(automovelRepository).findById(id);
    }
    
    @Test
    void testAtivarAutomovel_Sucesso() {
        // Arrange
        Long id = 1L;
        automovel.setAtivo(false);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        when(automovelRepository.save(any(Automovel.class))).thenReturn(automovel);
        
        // Act
        Automovel resultado = automovelService.ativarAutomovel(id);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getAtivo());
        verify(automovelRepository).findById(id);
        verify(automovelRepository).save(automovel);
    }
    
    @Test
    void testAtivarAutomovel_NaoEncontrado() {
        // Arrange
        Long id = 999L;
        when(automovelRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> automovelService.ativarAutomovel(id)
        );
        
        assertEquals("Automóvel não encontrado com ID: 999", exception.getMessage());
        verify(automovelRepository).findById(id);
        verify(automovelRepository, never()).save(any(Automovel.class));
    }
    
    @Test
    void testDesativarAutomovel_Sucesso() {
        // Arrange
        Long id = 1L;
        automovel.setAtivo(true);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        when(automovelRepository.save(any(Automovel.class))).thenReturn(automovel);
        
        // Act
        Automovel resultado = automovelService.desativarAutomovel(id);
        
        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.getAtivo());
        verify(automovelRepository).findById(id);
        verify(automovelRepository).save(automovel);
    }
    
    @Test
    void testDisponibilizarAutomovel_Sucesso() {
        // Arrange
        Long id = 1L;
        automovel.setDisponivel(false);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        when(automovelRepository.save(any(Automovel.class))).thenReturn(automovel);
        
        // Act
        Automovel resultado = automovelService.disponibilizarAutomovel(id);
        
        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getDisponivel());
        verify(automovelRepository).findById(id);
        verify(automovelRepository).save(automovel);
    }
    
    @Test
    void testIndisponibilizarAutomovel_Sucesso() {
        // Arrange
        Long id = 1L;
        automovel.setDisponivel(true);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        when(automovelRepository.save(any(Automovel.class))).thenReturn(automovel);
        
        // Act
        Automovel resultado = automovelService.indisponibilizarAutomovel(id);
        
        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.getDisponivel());
        verify(automovelRepository).findById(id);
        verify(automovelRepository).save(automovel);
    }
    
    @Test
    void testIsDisponivel_Disponivel() {
        // Arrange
        Long id = 1L;
        automovel.setDisponivel(true);
        automovel.setAtivo(true);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        
        // Act
        boolean resultado = automovelService.isDisponivel(id);
        
        // Assert
        assertTrue(resultado);
        verify(automovelRepository).findById(id);
    }
    
    @Test
    void testIsDisponivel_Indisponivel() {
        // Arrange
        Long id = 1L;
        automovel.setDisponivel(false);
        automovel.setAtivo(true);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        
        // Act
        boolean resultado = automovelService.isDisponivel(id);
        
        // Assert
        assertFalse(resultado);
        verify(automovelRepository).findById(id);
    }
    
    @Test
    void testIsDisponivel_Inativo() {
        // Arrange
        Long id = 1L;
        automovel.setDisponivel(true);
        automovel.setAtivo(false);
        when(automovelRepository.findById(id)).thenReturn(Optional.of(automovel));
        
        // Act
        boolean resultado = automovelService.isDisponivel(id);
        
        // Assert
        assertFalse(resultado);
        verify(automovelRepository).findById(id);
    }
    
    @Test
    void testIsDisponivel_NaoEncontrado() {
        // Arrange
        Long id = 999L;
        when(automovelRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        boolean resultado = automovelService.isDisponivel(id);
        
        // Assert
        assertFalse(resultado);
        verify(automovelRepository).findById(id);
    }
}
