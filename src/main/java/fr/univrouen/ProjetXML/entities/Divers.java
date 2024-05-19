package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "divers")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "divers")
public class Divers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long divers_id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "divers_id")
    @XmlElement(name = "lv")
    @Size(min = 1, max = 5) // Respect the minOccurs and maxOccurs constraints
    @Valid
    private List<LvType> lv;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "divers_id")
    @XmlElement(name = "autre")
    @Size(max = 3) // Respect the maxOccurs constraint
    @Valid
    private List<AutreType> autre;

    @OneToOne(mappedBy = "divers")
    @XmlTransient
    private CV24 cv24;

}

