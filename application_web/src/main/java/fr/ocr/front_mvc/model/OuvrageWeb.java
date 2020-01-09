package fr.ocr.front_mvc.model;

public class OuvrageWeb {

    private String titre;
    private String auteur;
    private Integer quantite;

    public OuvrageWeb(String titre, String auteur, Integer quantite) {
        this.titre = titre;
        this.auteur = auteur;
        this.quantite = quantite;
    }

    public OuvrageWeb() {
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}