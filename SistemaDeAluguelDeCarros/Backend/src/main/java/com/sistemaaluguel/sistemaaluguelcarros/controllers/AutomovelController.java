package com.sistemaaluguel.sistemaaluguelcarros.controllers;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Automovel;
import com.sistemaaluguel.sistemaaluguelcarros.dto.AutomovelDTO;
import com.sistemaaluguel.sistemaaluguelcarros.services.AutomovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/automoveis")
@CrossOrigin(origins = "*")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    @GetMapping
    public ResponseEntity<List<Automovel>> findAll() {
        List<Automovel> automoveis = automovelService.findAll();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Automovel> findById(@PathVariable Long id) {
        Optional<Automovel> automovel = automovelService.findById(id);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Automovel>> findByMarca(@PathVariable String marca) {
        List<Automovel> automoveis = automovelService.findByMarca(marca);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Automovel>> findByModelo(@PathVariable String modelo) {
        List<Automovel> automoveis = automovelService.findByModelo(modelo);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<Automovel>> findByAno(@PathVariable Integer ano) {
        List<Automovel> automoveis = automovelService.findByAno(ano);
        return ResponseEntity.ok(automoveis);
    }

    @PostMapping
    public ResponseEntity<Automovel> create(@RequestBody AutomovelDTO automovelDTO) {
        Automovel automovel = new Automovel();
        automovel.setMatricula(automovelDTO.getMatricula());
        automovel.setAno(automovelDTO.getAno());
        automovel.setMarca(automovelDTO.getMarca());
        automovel.setModelo(automovelDTO.getModelo());
        automovel.setPlaca(automovelDTO.getPlaca());
        automovel.setProprietario(automovelDTO.getProprietario());
        
        Automovel savedAutomovel = automovelService.save(automovel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAutomovel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Automovel> update(@PathVariable Long id, @RequestBody AutomovelDTO automovelDTO) {
        Optional<Automovel> automovelOpt = automovelService.findById(id);
        if (automovelOpt.isPresent()) {
            Automovel automovel = automovelOpt.get();
            automovel.setMatricula(automovelDTO.getMatricula());
            automovel.setAno(automovelDTO.getAno());
            automovel.setMarca(automovelDTO.getMarca());
            automovel.setModelo(automovelDTO.getModelo());
            automovel.setPlaca(automovelDTO.getPlaca());
            automovel.setProprietario(automovelDTO.getProprietario());
            
            Automovel updatedAutomovel = automovelService.save(automovel);
            return ResponseEntity.ok(updatedAutomovel);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        automovelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
