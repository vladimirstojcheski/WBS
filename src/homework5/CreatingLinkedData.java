package homework5;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;

public class CreatingLinkedData {
    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String myUrl = "https://raw.githubusercontent.com/vladimirstojcheski/foaf/main/foaf.ttl";
        model.read(myUrl, "TTL");

        NodeIterator it = model.listObjectsOfProperty(FOAF.knows);

        Model unionModel = ModelFactory.createDefaultModel();
        while (it.hasNext())
        {
            Resource res = (Resource)it.nextNode();
            Statement statement = res.getProperty(RDFS.seeAlso);

            Model tempModel = ModelFactory.createDefaultModel();
            tempModel.read(statement.getObject().toString(), "TTL");

            unionModel = unionModel.union(tempModel);
        }

        unionModel = unionModel.union(model);
        unionModel.write(System.out, "TTL");
    }

}
