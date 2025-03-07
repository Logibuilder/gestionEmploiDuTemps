package org.univ.projet_tutore.teachPlanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.univ.projet_tutore.teachPlanner.model.Groupe;
import org.univ.projet_tutore.teachPlanner.service.GroupeService;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    private final GroupeService groupeService;

    @Autowired
    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return ResponseEntity.ok(groupeService.getAllGroupes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Groupe> getGroupe(@PathVariable Integer id) {
        return ResponseEntity.ok(groupeService.getGroupeById(id));
    }

    @GetMapping("/annee/{anneeScolaire}")
    public ResponseEntity<List<Groupe>> getGroupesByAnnee(@PathVariable String anneeScolaire) {
        return ResponseEntity.ok(groupeService.getGroupesByAnneeScolaire(anneeScolaire));
    }

    @PostMapping
    public ResponseEntity<Groupe> createGroupe(@RequestBody Groupe groupe) {
        return ResponseEntity.ok(groupeService.createGroupe(groupe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Integer id) {
        groupeService.deleteGroupe(id);
        return ResponseEntity.ok().build();
    }
}