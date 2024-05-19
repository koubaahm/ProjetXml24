package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import lombok.*;
import javax.xml.bind.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "competences", namespace = "http://univ.fr/cv24")
//@XmlType(propOrder = {"id", "diplomes", "certifs"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "competences")
public class Competence {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute // `@XmlAttribute` pourrait être inapproprié si ID n'est pas supposé être un attribut XML selon le XSD
    private Long competence_id;

    //@OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)


    @XmlElement(name = "diplome")
    // Correctement mappé selon le XSD
    @Valid
    private List<Diplome> diplomes = new ArrayList<>();

    //@OneToMany(mappedBy = "competence", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @XmlElement(name = "certif") // Correctement mappé selon le XSD
    @Valid
    private List<Certif> certifs = new ArrayList<>();

    @OneToOne(mappedBy = "competence")
    @XmlTransient
    //@XmlTransient // Correct pour éviter la sérialisation de la référence inverse
    private CV24 cv24;

}