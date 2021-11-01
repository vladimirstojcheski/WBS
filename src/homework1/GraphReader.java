package homework1;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;

import java.io.InputStream;

public class GraphReader {
    public static void main(String[] args) {

        String filePath = ".\\resources\\vladimir.ttl";
        String personUri = "https://www.linkedin.com/in/vladimir-stojcheski-761793209/";

        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filePath);
        if(in == null)
        {
            throw new IllegalArgumentException("File not found");
        }

        model.read(in, "", "TURTLE");

        Resource vladimirStojcheski = model.getResource(personUri);
        String fullName = vladimirStojcheski.getProperty(VCARD.FN).getString();
        System.out.println(fullName);

        Resource country = (Resource) vladimirStojcheski.getProperty(VCARD.Country).getObject();
        System.out.println(country);
        System.out.println(country.getProperty(VCARD.NAME).getString());

        Resource gender = (Resource) vladimirStojcheski.getProperty(FOAF.gender).getObject();
        System.out.println(gender);
        System.out.println(gender.getProperty(VCARD.NAME).getString());

        String birhday = vladimirStojcheski.getProperty(VCARD.BDAY).getString();
        System.out.println(birhday);

        model.write(System.out, "TURTLE");

    }
}
