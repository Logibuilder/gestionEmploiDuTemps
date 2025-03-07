package org.univ.projet_tutore.teachPlanner.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.univ.projet_tutore.teachPlanner.model.Enseignant;
import org.univ.projet_tutore.teachPlanner.model.Personnel;
import org.univ.projet_tutore.teachPlanner.service.AdministrateurService;
import org.univ.projet_tutore.teachPlanner.service.PasswordHasher;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    @GetMapping("/enseignants")
    public String gestionEnseignants(Model model, HttpSession session) {
        if (!checkAdminAccess(session)) {
            return "redirect:/login";
        }
        return "admin/gestion-enseignants";
    }

    @PostMapping("/enseignants/create")
    @ResponseBody
    public Map<String, Object> createEnseignant(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String nom,
            @RequestParam String prenom,
            HttpSession session) {

        if (!checkAdminAccess(session)) {
            return Map.of("error", "Accès non autorisé");
        }

        try {
            Enseignant enseignant = administrateurService.createEnseignant(email, password, nom, prenom);
            return Map.of(
                    "success", true,
                    "enseignant", Map.of(
                            "id", enseignant.getNumeroEns(),
                            "nom", enseignant.getNomEns(),
                            "prenom", enseignant.getPrenomEns(),
                            "email", enseignant.getPersonnel().getMail()
                    )
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @DeleteMapping("/enseignants/{id}")
    @ResponseBody
    public Map<String, Object> deleteEnseignant(@PathVariable Integer id, HttpSession session) {
        if (!checkAdminAccess(session)) {
            return Map.of("error", "Accès non autorisé");
        }

        try {
            administrateurService.deleteEnseignant(id);
            return Map.of("success", true);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    private boolean checkAdminAccess(HttpSession session) {
        return session.getAttribute("userLoggedIn") != null &&
                Boolean.TRUE.equals(session.getAttribute("isAdmin"));
    }
}