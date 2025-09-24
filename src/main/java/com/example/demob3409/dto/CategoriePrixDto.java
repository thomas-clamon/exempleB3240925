package com.example.demob3409.dto;

public class CategoriePrixDto {

    private String catgorie;
    private Double prix_total;

    public String getCatgorie() {
        return catgorie;
    }

    public void setCatgorie(String catgorie) {
        this.catgorie = catgorie;
    }

    public Double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(Double prix_total) {
        this.prix_total = prix_total;
    }
}
