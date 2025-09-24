package com.example.demob3409.controller;

import com.example.demob3409.dto.AgeDto;
import com.example.demob3409.dto.CategoriePrixDto;
import com.example.demob3409.dto.ProduitDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("exemple")
public class Exemple {

    @GetMapping("hello")
    public ResponseEntity hello(){
        return new ResponseEntity<>("Bonjour tout le mode", HttpStatusCode.valueOf(200));
    }

    @GetMapping("intro")
    public ResponseEntity<String> hello2(@RequestParam("firstname") String prenom){
        String message = "Bonjour je m'appelle " + prenom;
        return new ResponseEntity(message, HttpStatusCode.valueOf(200));
    }

    @GetMapping("prof")
    public ResponseEntity cours (@RequestParam("firstname") String prenom,
                                 @RequestParam("cours") List<String> cours){

        String message = "Bonjour je m'appelle " + prenom + "j'ai " + cours.size() + " modules";
        return new ResponseEntity(message, HttpStatusCode.valueOf(200));
    }

    @GetMapping("intro2/{age}")
    public ResponseEntity chemin (@PathVariable String age ){
        String message = "Bonjour je m'appelle Thomas j'ai " + age;
        try {
            Integer AGE = Integer.valueOf(age);
        }catch (NumberFormatException e){
            return new ResponseEntity("age incorrect", HttpStatusCode.valueOf(403));
        }
        return new ResponseEntity(message, HttpStatusCode.valueOf(200));

    }

    @PostMapping("age")
    public ResponseEntity getAge(@RequestBody AgeDto dto) {
        LocalDate birthdate;
        try {
             birthdate = LocalDate.of(dto.getAnnee(), dto.getMois(), dto.getJour());
        }catch (DateTimeException dte){
            return new ResponseEntity("date incorrect", HttpStatusCode.valueOf(400));
        }
        // on calcule la difference entre la date de naissance et la date d'ajourd'hui
        Integer Age = Period.between(birthdate, LocalDate.now()).getYears();
        return new ResponseEntity(Age, HttpStatusCode.valueOf(200));
    }

    @PostMapping("basket")
   public ResponseEntity liste_course(@RequestBody List<ProduitDto> liste_produit){

        List<String> categories = liste_produit.stream()
                .map(ProduitDto::getCategorie).distinct().toList();
        List<CategoriePrixDto> result = new ArrayList();
        for( String categorie :  categories){
            // on cree un nouvel element
            CategoriePrixDto dto = new CategoriePrixDto();
            dto.setCatgorie(categorie);
            dto.setPrix_total(getPrixByCategorie(liste_produit, categorie));
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
   }

   private Double getPrixByCategorie(List<ProduitDto> liste_produit, String categorie){
        // j'ai ma liste d'element pour une sue
        return liste_produit.stream().filter(produit -> produit.getCategorie().equals(categorie)) // Gardez seulement les produits du type désiré
                .mapToDouble(element -> element.getPrix()) // Convertissez le stream d'objets en stream de doubles (prix)
                .sum();
   }



}
