package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@XmlRootElement(name = "objectif", namespace = "http://univ.fr/cv24")
@XmlAccessorType(XmlAccessType.FIELD)
public class Objectif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long objectif_id;  // Removed XML mapping since not specified as part of XML.

    @NotNull(message = "Le statut est requis")
    @Pattern(regexp = "^(stage|emploi)$", message = "Le statut doit être 'stage' ou 'emploi'")
    @XmlAttribute(name = "statut")  // Corrected to 'statut' to match the XSD.
    @Column(nullable = false)
    private String status;

    @OneToOne(mappedBy = "objectif")
    @XmlTransient // Prevents the mapping of the CV24 object to XML
    private CV24 cv24;
}
