package com.sistemaaluguel.sistemaaluguelcarros.services;

import com.sistemaaluguel.sistemaaluguelcarros.dto.PedidoDTO;
import com.sistemaaluguel.sistemaaluguelcarros.dto.PedidoResponseDTO;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Pedido;
import com.sistemaaluguel.sistemaaluguelcarros.enums.StatusPedido;
import com.sistemaaluguel.sistemaaluguelcarros.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutomovelService automovelService;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll(); // CONSIDERAR: Adicionar paginação
    }

    public List<PedidoResponseDTO> findAllAsDTO() {
        List<PedidoResponseDTO> dtos = new ArrayList<>();

        // PROBLEMA: Dados mockados em produção - isso deve ser removido
        // CONSIDERAR: Usar dados reais do banco e o método convertToResponseDTO
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(1L);
        dto.setDataInicio(java.time.LocalDate.now());
        dto.setDataFim(java.time.LocalDate.now().plusDays(7));
        dto.setStatus(StatusPedido.PENDENTE);
        dto.setClienteId(1L);
        dto.setClienteNome("João Silva");
        dto.setAutomovelId(1L);
        dto.setAutomovelModelo("Corolla");
        dto.setAutomovelMarca("Toyota");
        dto.setAutomovelPlaca("ABC1234");

        dtos.add(dto);

        return dtos;
    }

    private PedidoResponseDTO convertToResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setDataInicio(pedido.getDataInicio());
        dto.setDataFim(pedido.getDataFim());
        dto.setStatus(pedido.getStatus());

        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNome(pedido.getCliente().getNome());
        }

        if (pedido.getAutomovel() != null) {
            dto.setAutomovelId(pedido.getAutomovel().getId());
            dto.setAutomovelModelo(pedido.getAutomovel().getModelo());
            dto.setAutomovelMarca(pedido.getAutomovel().getMarca());
            dto.setAutomovelPlaca(pedido.getAutomovel().getPlaca());
        }

        return dto;
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findByAgenteId(Long agenteId) {
        return pedidoRepository.findByAgenteId(agenteId);
    }

    public List<Pedido> findByStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public List<Pedido> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim) {
        // CONSIDERAR: Validar se dataInicio é antes de dataFim
        return pedidoRepository.findByDataInicioBetween(dataInicio, dataFim);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido save(PedidoDTO pedidoDTO) {
        // CONSIDERAR: Validar se datas são coerentes (dataInicio antes de dataFim)
        // CONSIDERAR: Validar se automóvel está disponível nas datas solicitadas
        Pedido pedido = new Pedido();
        pedido.setDataInicio(pedidoDTO.getDataInicio());
        pedido.setDataFim(pedidoDTO.getDataFim());
        pedido.setStatus(pedidoDTO.getStatus() != null ? pedidoDTO.getStatus() : StatusPedido.PENDENTE);

        // PROBLEMA: Se clienteId ou automovelId não existirem, o pedido é salvo sem essas relações
        // CONSIDERAR: Validar se cliente e automóvel existem antes de salvar
        if (pedidoDTO.getClienteId() != null) {
            clienteService.findById(pedidoDTO.getClienteId())
                    .ifPresent(pedido::setCliente);
        }

        if (pedidoDTO.getAutomovelId() != null) {
            automovelService.findById(pedidoDTO.getAutomovelId())
                    .ifPresent(pedido::setAutomovel);
        }

        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        // CONSIDERAR: Verificar se pedido existe antes de deletar
        pedidoRepository.deleteById(id);
    }

    public Pedido aprovarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.aprovar();
            return save(pedido);
        }
        return null; // CONSIDERAR: Lançar exceção em vez de retornar null
    }

    public Pedido rejeitarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.rejeitar();
            return save(pedido);
        }
        return null; // CONSIDERAR: Lançar exceção em vez de retornar null
    }

    public Pedido cancelarPedido(Long id) {
        Optional<Pedido> pedidoOpt = findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.cancelar();
            return save(pedido);
        }
        return null; // CONSIDERAR: Lançar exceção em vez de retornar null
    }
}