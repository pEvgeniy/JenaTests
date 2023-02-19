import lombok.extern.slf4j.Slf4j;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.ReasonerFactory;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasonerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM;

@Slf4j
public class WineOntology {

    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        OntModel ontModel = ModelFactory.createOntologyModel(OWL_MEM, model);
        Resource wine = model.createResource("Wine");
        Resource usWine1978 = model.createResource("US_Wine_1978");
        Resource vintage = model.createResource("Vintage");
        Resource wineMaker = model.createResource("WineMaker");
        Resource winery = model.createResource("Winery");

        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(wine);
        resourceList.add(usWine1978);
        resourceList.add(vintage);
        resourceList.add(winery);
        resourceList.add(wineMaker);

        for (Resource resource : resourceList) {
            ontModel.createIndividual(resource);
        }

        ReasonerFactory reasonerFactory = new RDFSRuleReasonerFactory();

        ReasonerRegistry reasonerRegistry = ReasonerRegistry.theRegistry();
        reasonerRegistry.register(reasonerFactory);

        try {
            OutputStream outputStream = new FileOutputStream("src/main/resources/wine_example_v1.rdf");
            ontModel.write(outputStream, "RDF/XML-ABBREV");
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


