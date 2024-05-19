package fr.univrouen.ProjetXML.services;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import fr.univrouen.ProjetXML.entities.*;
import fr.univrouen.ProjetXML.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
@AllArgsConstructor
public class BindingXmlService {

    private final CompetenceRepository competenceRepository;
    private final ProfRepository profRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * convertit des données XML en une entité CV24 et traite ses composants.
     *
     * @param xmlData les données XML à convertir
     * @return l'entité CV24 convertie
     * @throws JAXBException en cas d'erreur lors du démasquage
     */
    public CV24 convertXmlToEntity(String xmlData) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CV24.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xmlData);
        CV24 cv24 = (CV24) jaxbUnmarshaller.unmarshal(reader);
        processDiplomes(cv24);
        processCertifs(cv24);
        processDetails(cv24);
        return cv24;
    }

    /**
     * convertit une entité CV24 en sa représentation XML.
     *
     * @param cv l'entité CV24 à convertir
     * @return la représentation XML de l'entité CV24
     */
    public String convertEntityToXml(CV24 cv) {
        if (cv == null) {
            log.error("tentative de marshalling d'une entité CV24 null.");
            throw new IllegalArgumentException("L'entité CV24 fournie est null.");
        }

        try (StringWriter writer = new StringWriter()) {
            JAXBContext context = JAXBContext.newInstance(CV24.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                @Override
                public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                    return "http://univ.fr/cv24".equals(namespaceUri) ? "cv24" : suggestion;
                }
            });

            JAXBElement<CV24> jaxbElement = new JAXBElement<>(new QName("http://univ.fr/cv24", "cv24"), CV24.class, cv);
            marshaller.marshal(jaxbElement, writer);
            return writer.toString();
        } catch (JAXBException | IOException e) {
            log.error("erreur lors du marshalling de l'entité CV24 : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la conversion de l'entité en XML", e);
        }
    }

    /**
     * applique une transformation XSLT aux données XML fournies.
     *
     * @param xmlData les données XML à transformer
     * @param xsltFile le fichier XSLT à utiliser pour la transformation
     * @return les données XML transformées
     * @throws TransformerException en cas d'erreur lors de la transformation
     * @throws IOException en cas d'erreur d'entrée/sortie
     */
    public String applyXsltTransformation(String xmlData, File xsltFile) throws TransformerException, IOException {
        Source xmlSource = new StreamSource(new StringReader(xmlData));
        Source xsltSource = new StreamSource(xsltFile);

        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);

        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xsltSource);
        transformer.transform(xmlSource, result);

        return sw.toString();
    }

    private void processDiplomes(CV24 cv24) {
        for (Diplome diplome : cv24.getCompetence().getDiplomes()) {
            if (diplome.getCompetence() == null) {
                Competence competence = new Competence();
                competence = competenceRepository.save(competence);
                diplome.setCompetence(competence);
            }
        }
    }

    private void processDetails(CV24 cv24) {
        for (Detail detail : cv24.getProf().getDetails()) {
            if (detail.getProf() == null) {
                Prof prof = new Prof();
                prof = profRepository.save(prof);
                detail.setProf(prof);
            }
        }
    }

    private void processCertifs(CV24 cv24) {
        for (Certif certif : cv24.getCompetence().getCertifs()) {
            if (certif.getCompetence() == null) {
                Competence competence = new Competence();
                competence = competenceRepository.save(competence);
                certif.setCompetence(competence);
            }
        }
    }
}
