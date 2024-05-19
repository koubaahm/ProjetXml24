package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Entity
@Table(name = "details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long detail_id;

    @Temporal(TemporalType.DATE)
    @XmlElement(required = true, name = "datedeb")
    private Date datedeb;

    @Temporal(TemporalType.DATE)
    @XmlElement(name = "datefin")
    private Date datefin;

    @Column(length = 128)
    @XmlElement(required = true, name = "titre")
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "prof_id")
    @XmlTransient // This prevents serialization and infinite recursion in XML and JSON
    private Prof prof;
}