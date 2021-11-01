package homework1;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.VCARD;

public class ModelBuilder {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String personUri = "https://www.linkedin.com/in/vladimir-stojcheski-761793209/";
        String fullName = "Vladimir Stojcheski";
        String birthday = "22-04-1998";
        String streetUri = "https://www.google.com/maps/place/Vasile+Pavloski-Dzhgure+31,+Prilep,+North+Macedonia/@41.3573053,21.5422618,17z/data=!3m1!4b1!4m5!3m4!1s0x1356ebc1ae5cdb45:0xeb864b8c64a8d52d!8m2!3d41.3573013!4d21.5444505?hl=en-US";
        String countryUri = "https://dbpedia.org/resource/North_Macedonia";
        String email = "stojcheskivladimir@gmail.com";
        String maleUri = "https://dbpedia.org/resource/Male";

        Resource vladimirStojcheski = model.createResource(personUri);

        Resource street = model.createResource(streetUri);
        street.addProperty(VCARD.ADR, "Vasile Pavloski Dzhgure 31");

        Resource country = model.createResource(countryUri);

        Resource male = model.createResource(maleUri);
        male.addProperty(VCARD.NAME, "Male");

        vladimirStojcheski.addProperty(VCARD.FN, fullName);
        vladimirStojcheski.addProperty(VCARD.BDAY, birthday);
        vladimirStojcheski.addProperty(VCARD.Street, street);
        vladimirStojcheski.addProperty(VCARD.Country, country.addProperty(VCARD.NAME, "Macedonia"));
        vladimirStojcheski.addProperty(VCARD.EMAIL, email);
        vladimirStojcheski.addProperty(VCARD.EMAIL, "vladimir.stojcheski@finki.ukim.mk");
        vladimirStojcheski.addProperty(FOAF.age, "23");
        vladimirStojcheski.addProperty(FOAF.gender, male);

        System.out.println("Printing with model.listStatements()");
        StmtIterator iter = model.listStatements();
        while (iter.hasNext())
        {
            Statement statement = iter.nextStatement();
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

        System.out.println("Printing as RDF/XML:");
        model.write(System.out);

        System.out.println("Printing as pretty RDF/XML:");
        model.write(System.out, "RDF/XML-ABBREV");

        System.out.println("Printing as N-Triples:");
        model.write(System.out, "N-TRIPLES");

        System.out.println("Printing as Turtle:");
        model.write(System.out, "TURTLE");

        System.out.println("Printing as Json-ld:");
        model.write(System.out, "JSON-LD");
    }
}
