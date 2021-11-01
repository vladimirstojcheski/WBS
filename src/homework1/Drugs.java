package homework1;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Drugs {

    public static void main(String[] args) {
        String filePath = ".\\resources\\hifm-dataset.ttl";

        String drugUri = "http://wifo5-04.informatik.uni-mannheim.de/drugbank/resource/drugbank/";

        List<String> drugNames = new ArrayList<>();

        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open(filePath);
        if(in == null)
        {
            throw new IllegalArgumentException("File not found");
        }

        model.read(in, "", "TURTLE");

        model.write(System.out, "TURTLE");
        System.out.println();
        Property drugName = model.getProperty(drugUri + "genericName");
        ResIterator it = model.listSubjectsWithProperty(drugName);
        while(it.hasNext())
        {
            Resource subject = it.nextResource();
            drugNames.add(subject.getProperty(drugName).getString());
        }
        drugNames.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println();


        Resource drug = model.getResource("http://purl.org/net/hifm/data#39195");
        StmtIterator iterator = drug.listProperties();
        System.out.println("All relations for the drug http://purl.org/net/hifm/data#39195 are:");
        while (iterator.hasNext())
        {
            Statement statement = iterator.nextStatement();
            Resource subject = statement.getSubject();
            Property predicate = statement.getPredicate();
            RDFNode object = statement.getObject();

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if(object instanceof Resource)
                System.out.println(object.toString());
            else
                System.out.println("\"" + object.toString() + "\"");
        }

        System.out.println();

        Resource drug2 = model.getResource("http://purl.org/net/hifm/data#983969");
        Property property = model.getProperty("http://purl.org/net/hifm/ontology#similarTo");
        iterator = drug2.listProperties(property);
        System.out.println("Drug "+ drug2.getURI() + " is similar to the next drugs: ");
        while (iterator.hasNext())
        {
            Statement statement = iterator.nextStatement();
            RDFNode object = statement.getObject();

            if(object instanceof Resource)
                System.out.println(object.toString());
            else
                System.out.println("\"" + object.toString() + "\"");
        }

        System.out.println();

        Resource drug3 = model.getResource("http://purl.org/net/hifm/data#966266");
        Property property2 = model.getProperty("http://purl.org/net/hifm/ontology#refPriceWithVAT");
        Property dName = model.getProperty("http://wifo5-04.informatik.uni-mannheim.de/drugbank/resource/drugbank/brandName");
        System.out.println("The selected drug is "+ drug3.getURI());
        System.out.println("His price is: " + drug3.getProperty(property2).getString());

        System.out.println("Similar drugs to the one above and their prices are: ");
        iterator = drug3.listProperties(property);
        while (iterator.hasNext())
        {
            Statement statement = iterator.nextStatement();
            Resource resource = (Resource) statement.getObject();
            String name = resource.getProperty(dName).getString();
            System.out.println(name + " with price: " + resource.getProperty(property2).getString());
        }

    }



}
