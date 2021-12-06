package homework4;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;

import java.io.InputStream;

public class Drug {
    public static void main(String[] args) {

        String filePath = ".\\resources\\hifm-dataset-bio2rdf.ttl";
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filePath);
        if(in == null)
        {
            throw new IllegalArgumentException("File not found");
        }
        model.read(in, "", "TURTLE");

        Resource drug = model.getResource("http://purl.org/net/hifm/data#993751");
        StmtIterator iterator = drug.listProperties(RDFS.seeAlso);
        System.out.println("Drugs who have same functionality as " + drug.getURI() + " are:");
        while (iterator.hasNext())
        {
            Statement statement = iterator.nextStatement();
            RDFNode object = statement.getObject();
            if(object instanceof Resource)
                System.out.println(object.toString());
            else
                System.out.println("\"" + object.toString() + "\"");
        }


    }
}
