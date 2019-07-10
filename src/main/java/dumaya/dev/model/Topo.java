package dumaya.dev.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Description d'un Topo
 */
@Entity
public class Topo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40)
    @NotBlank
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(length = 60)
    @NotBlank
    private String lieu;

    @Column(nullable = false)
    private boolean dispoPret;

    @Column(name = "dateParution", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateParution;

    @Column(length = 40)
    private String auteur;

    @ManyToOne
    private Utilisateur utilisateurProprietaire;

    @ManyToOne
    private Utilisateur utilisateurEmprunteur;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public boolean isDispoPret() {
        return dispoPret;
    }

    public void setDispoPret(boolean dispoPret) {
        this.dispoPret = dispoPret;
    }

    public Utilisateur getUtilisateurProprietaire() {
        return utilisateurProprietaire;
    }

    public void setUtilisateurProprietaire(Utilisateur utilisateurProprietaire) {
        this.utilisateurProprietaire = utilisateurProprietaire;
    }

    public Utilisateur getUtilisateurEmprunteur() {
        return utilisateurEmprunteur;
    }

    public void setUtilisateurEmprunteur(Utilisateur utilisateurEmprunteur) {
        this.utilisateurEmprunteur = utilisateurEmprunteur;
    }
}
