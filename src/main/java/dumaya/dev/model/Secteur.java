package dumaya.dev.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Secteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 40)
    @NotBlank
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(name = "dateMaj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateMaj;

    @ManyToOne
    private Site site;

    @OneToMany(mappedBy="secteur")
    private List<Voie> voies;

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

    public Date getDateMaj() {
        return dateMaj;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }
}