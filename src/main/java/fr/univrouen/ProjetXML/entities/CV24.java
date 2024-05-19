package fr.univrouen.ProjetXML.entities;


import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@XmlRootElement(name = "cv24", namespace = "http://univ.fr/cv24")
@XmlAccessorType(XmlAccessType.FIELD)
public class CV24 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identite_id", referencedColumnName = "identite_id")
    @XmlElement(name = "identite", namespace = "http://univ.fr/cv24")
    private Identite identite;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "objectif_id", referencedColumnName = "objectif_id")
    @XmlElement(name = "objectif", namespace = "http://univ.fr/cv24")
    private Objectif objectif;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "prof_id", referencedColumnName = "prof_id") // Assurez-vous que cette correspondance est correcte
    @XmlElement(name = "prof", namespace = "http://univ.fr/cv24")
    private Prof prof;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "competence_id", referencedColumnName = "competence_id")
    @XmlElement(name = "competences", namespace = "http://univ.fr/cv24")

    private Competence competence;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "divers_id", referencedColumnName = "divers_id") // Assurez-vous que cette correspondance est correcte
    @XmlElement(name = "divers", namespace = "http://univ.fr/cv24")
    private Divers divers;
}
