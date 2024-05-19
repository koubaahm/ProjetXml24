package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Entity
@Table(name = "diplomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "diplome", namespace = "http://univ.fr/cv24")
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Temporal(TemporalType.DATE)
    @XmlElement(required = true)
    private Date date;

    @Column(length = 32, nullable = true) // Permettre null puisque minOccurs=0 dans le XSD
    @XmlElement(nillable = true) // Spécifier nillable si vous voulez représenter explicitement l'absence dans le XML
    private String institut;

    @NotNull
    @Column(nullable = false)
    @XmlAttribute(required = true) // Utilisation d'@XmlAttribute pour correspondre au XSD
    private Integer niveau;  // Assurez-vous que ce champ respecte le type NiveauQualification si c'est un type personnalisé

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "competence_id", referencedColumnName = "competence_id")
    @XmlTransient
    private Competence competence;

}
