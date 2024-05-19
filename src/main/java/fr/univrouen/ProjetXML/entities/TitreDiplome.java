package fr.univrouen.ProjetXML.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@XmlRootElement(name = "titreDiplome", namespace = "http://univ.fr/cv24")
@XmlAccessorType(XmlAccessType.FIELD)
public class TitreDiplome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private Long id;

    //@NotNull
    @XmlElement
    private String titre;

    @ManyToOne
    @JoinColumn(name = "diplome_id")
    @XmlTransient // Pour éviter la sérialisation cyclique ou redondante
    private Diplome diplome;
}
