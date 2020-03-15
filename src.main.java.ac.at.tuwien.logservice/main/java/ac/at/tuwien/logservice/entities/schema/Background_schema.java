package ac.at.tuwien.logservice.entities.schema;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class Background_schema {
    /**
     * <p>The ontology model that holds the vocabulary terms</p>
     */
    private static final OntModel M_MODEL = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

    /**
     * <p>The namespace of the vocabulary as a string</p>
     */
    public static final String NS = "http://w3id.org/sepses/vocab/event/background#";

    /**
     * <p>The namespace of the vocabulary as a string</p>
     *
     * @return namespace as String
     * @see #NS
     */
    public static String getURI() {
        return NS;
    }

    /**
     * <p>The namespace of the vocabulary as a resource</p>
     */
    public static final Resource NAMESPACE = M_MODEL.createResource(NS);

    public static final OntClass Account = M_MODEL.createClass("http://w3id.org/sepses/vocab/background#Account");

    public static final DatatypeProperty uid = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#uid");

    public static final DatatypeProperty firstName = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#firstName");

    public static final DatatypeProperty lastName = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#lastName");

    public static final DatatypeProperty email = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#email");

    public static final DatatypeProperty userName = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#userName");

    public static final OntClass Channel = M_MODEL.createClass("http://w3id.org/sepses/vocab/background#Channel");

    public static final DatatypeProperty type = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#type");

    public static final DatatypeProperty name = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#name");

    public static final DatatypeProperty path = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#path");

    public static final DatatypeProperty program = M_MODEL.createDatatypeProperty("http://w3id.org/sepses/vocab/background#program");
}
