package com.example.demob3409.controller;

import com.example.demob3409.dto.AgeDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
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



}
