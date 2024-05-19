package fr.univrouen.ProjetXML.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.univrouen.ProjetXML.enums.CertType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.xml.bind.annotation.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Embeddable
//@XmlRootElement(name = "lv", namespace = "http://univ.fr/cv24")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class LvType {
// /*   @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @XmlAttribute
//    private Long id;*/
//
//    //@NotNull
//    @Pattern(regexp = "[a-z]{2}")
//    @XmlElement(required = true)
//    private String lang;
//
//    //@NotNull
//    @Enumerated(EnumType.STRING) // Assuming CertType is an enum
//    @XmlElement(required = true)
//    private CertType cert;
//
//    @Pattern(regexp = "(A1|A2|B1|B2|C1|C2)?")
//    @XmlElement
//    private String nivs;
//
//    @XmlElement
//    private Integer nivi;
//}

@Entity
@Table(name = "lv")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor@NoArgsConstructor
public class LvType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    @ManyToOne
    @JoinColumn(name = "divers_id")
    @XmlTransient
    private Divers divers;

    @XmlAttribute(required = true)
    @Pattern(regexp = "[a-z]{2}")
    private String lang;

    @XmlAttribute(required = true)
    @Enumerated(EnumType.STRING)
    private CertType cert;

    @XmlAttribute
    @Pattern(regexp = "(A1|A2|B1|B2|C1|C2)?")
    private String nivs;  // Optional

    @XmlAttribute
    @Min(10)
    @Max(990)
    private Integer niv;  // Optional

    // Enum for certification types
    public enum CertType {
        MAT, CLES, TOEIC
    }
}

