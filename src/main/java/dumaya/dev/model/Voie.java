package dumaya.dev.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Voie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 40)
    @NotBlank
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, length = 2)
    private String cotation;

    @Column(name = "dateMaj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateMaj;

    @ManyToOne
    private Secteur secteur;

    @OneToMany(mappedBy="voie")
    private List<Longueur> longueurs;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public List<Longueur> getLongueurs() {
        return longueurs;
    }

    public void setLongueurs(List<Longueur> longueurs) {
        this.longueurs = longueurs;
    }
}