package dumaya.dev.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Longueur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(length = 2)
    private String cotation;

    @Column(name = "dateMaj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateMaj;

    @ManyToOne
    private Voie voie;

}