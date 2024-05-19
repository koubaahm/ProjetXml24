package fr.univrouen.ProjetXML.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;


@Entity
@Table(name = "certifs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "certif", namespace = "http://univ.fr/cv24")

public class Certif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Temporal(TemporalType.DATE)
    @XmlElement(required = true) // Correspond à minOccurs="1"
    private Date datedeb;

    @Temporal(TemporalType.DATE)
    @XmlElement(nillable = true) // Correspond à minOccurs="0", peut être omis
    private Date datefin;

    @Column(length = 128)
    @XmlElement(required = true) // Correspond à minOccurs="1"
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "competence_id")
    @XmlTransient
    private Competence competence;

}