package fr.univrouen.ProjetXML.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@XmlRootElement(name = "autre", namespace = "http://univ.fr/cv24")
//@XmlAccessorType(XmlAccessType.FIELD)
//@Embeddable
//public class AutreType {
//
//    @Pattern(regexp = "[A-Z].{0,31}")
//    @Column(nullable = false, length = 32)
//    private String titre;
//
//    @Pattern(regexp = "[a-zA-Z0-9\\s.,:;~@\\(\\)\\-'_!?$*=]{0,128}")
//    private String comment;
//
//}
@Entity
@Table(name = "autre")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor@NoArgsConstructor
public class AutreType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @ManyToOne
    @JoinColumn(name = "divers_id")
    @XmlTransient
    private Divers divers;

    @XmlAttribute(required = true)
    @Pattern(regexp = "^[a-zA-Z \\-'']*$")
    private String titre;

    @XmlAttribute
    @Pattern(regexp = "[a-zA-Z0-9\\s.,:;~@\\(\\)\\-'_!?$*=]{0,128}")
    private String comment; // Optional
}

