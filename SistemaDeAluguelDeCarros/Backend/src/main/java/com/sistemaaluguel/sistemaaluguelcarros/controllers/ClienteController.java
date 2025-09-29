package com.sistemaaluguel.sistemaaluguelcarros.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaaluguel.sistemaaluguelcarros.dto.ClienteDTO;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Cliente;
import com.sistemaaluguel.sistemaaluguelcarros.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSenha(clienteDTO.getSenha());
        cliente.setRg(clienteDTO.getRg());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setProfissao(clienteDTO.getProfissao());
        
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOpt = clienteService.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setNome(clienteDTO.getNome());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setSenha(clienteDTO.getSenha());
            cliente.setRg(clienteDTO.getRg());
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setEndereco(clienteDTO.getEndereco());
            cliente.setProfissao(clienteDTO.getProfissao());
            
            Cliente updatedCliente = clienteService.save(cliente);
            return ResponseEntity.ok(updatedCliente);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
