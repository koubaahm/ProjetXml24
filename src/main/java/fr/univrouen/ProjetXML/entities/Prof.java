package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@Entity
@Table(name = "prof")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "prof", namespace = "http://univ.fr/cv24")
@XmlAccessorType(XmlAccessType.FIELD)
public class Prof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long prof_id;

    @OneToMany(mappedBy = "prof", cascade = CascadeType.ALL)
    @XmlElement(name = "detail")
    private List<Detail> details;

    @OneToOne(mappedBy = "prof")
    @XmlTransient // This annotation is used to prevent the mapping of this field in XML serialization
    private CV24 cv24;
}