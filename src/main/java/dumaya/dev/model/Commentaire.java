package dumaya.dev.model;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 120)
    @NotBlank
    private String message;

    @Column(name = "dateMaj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateMaj;

    @ManyToOne
    private Utilisateur utilisateurCommentaire;

    @ManyToOne
    private Site site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateMaj() {
        return dateMaj;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Utilisateur getUtilisateurCommentaire() {
        return utilisateurCommentaire;
    }

    public void setUtilisateurCommentaire(Utilisateur utilisateurCommentaire) {
        this.utilisateurCommentaire = utilisateurCommentaire;
    }
}