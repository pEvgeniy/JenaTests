import lombok.extern.slf4j.Slf4j;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM;

@Slf4j
public class WineOntology {
    private static final String nameSpace = "http://www.nti.ru/wine-ontology/1.5.0#";

    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        OntModel ontModel = ModelFactory.createOntologyModel(OWL_MEM, model);

        OntClass wine = ontModel.createClass(nameSpace + "Wine");
        OntClass cheese = ontModel.createClass(nameSpace + "Wine");

        Individual abrauDurso = wine.createIndividual(nameSpace + "AbrauDurso");
        Individual usWine1974 = wine.createIndividual(nameSpace + "UsWine1974");
        Individual BeaulieuVineyard = wine.createIndividual(nameSpace + "BeaulieuVineyard");
        Individual dorBlue = cheese.createIndividual(nameSpace + "DorBlue");

        ObjectProperty redWine = ontModel.createObjectProperty(nameSpace + "RedWine");
        ObjectProperty whiteWine = ontModel.createObjectProperty(nameSpace + "WhiteWine");
        ObjectProperty oldCheese = ontModel.createObjectProperty(nameSpace + "OldCheese");
        ObjectProperty youngCheese = ontModel.createObjectProperty(nameSpace + "YoungCheese");

        abrauDurso.addProperty(redWine,"Red");
        BeaulieuVineyard.addProperty(whiteWine,"White");
        usWine1974.addProperty(whiteWine, "White");

        redWine.addInverseOf(oldCheese);
        youngCheese.addInverseOf(whiteWine);

        dorBlue.addProperty(youngCheese," YoungCheese");

        DatatypeProperty yellowCheese = ontModel.createDatatypeProperty(nameSpace + "yellowCheese");
        dorBlue.addProperty(yellowCheese, " YellowCheese");

        wine.addProperty(redWine, "redWine");
        wine.addProperty(whiteWine, "whiteWine");

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner.bindSchema(model);
        reasoner.supportsProperty(oldCheese);

        try {
            OutputStream outputStream = new FileOutputStream("src/main/resources/wine_and_cheese_v1.owl");
            ontModel.write(outputStream, "RDF/XML-ABBREV");
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


