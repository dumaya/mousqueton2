package dumaya.dev.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Site implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 40)
    @NotNull
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(length = 2)
    private String cotationMin;

    @Column(length = 2)
    private String cotationMax;

    @Column(length = 40)
    private String typeRoche;

    @Column(length = 40)
    private String ancrage;

    @Column(length = 40)
    private String relais;

    @Column(length = 4)
    private Integer altitude;

    @Column(length = 20)
    private String orientation;

    @Column(length = 50)
    private String lieu;

    @Column(name = "dateMaj", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateMaj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCotationMin() {
        return cotationMin;
    }

    public void setCotationMin(String cotationMin) {
        this.cotationMin = cotationMin;
    }

    public String getCotationMax() {
        return cotationMax;
    }

    public void setCotationMax(String cotationMax) {
        this.cotationMax = cotationMax;
    }

    public String getTypeRoche() {
        return typeRoche;
    }

    public void setTypeRoche(String typeRoche) {
        this.typeRoche = typeRoche;
    }

    public String getAncrage() {
        return ancrage;
    }

    public void setAncrage(String ancrage) {
        this.ancrage = ancrage;
    }

    public String getRelais() {
        return relais;
    }

    public void setRelais(String relais) {
        this.relais = relais;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }


    public Date getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(Date dateMaj) {
        this.dateMaj = dateMaj;
    }
}
