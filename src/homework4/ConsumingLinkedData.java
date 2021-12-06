package homework4;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFParser;

public class ConsumingLinkedData {
    public static void main(String[] args) {

        String arcaneUrl = "http://dbpedia.org/resource/Arcane_(TV_series)";

        Model model = ModelFactory.createDefaultModel();
        RDFParser.source(arcaneUrl).httpAccept("application/ld+json").parse(model.getGraph());
        Resource arcane = model.getResource(arcaneUrl);
        Property directorProp = model.getProperty("http://dbpedia.org/ontology/director");
        Resource director = (Resource) arcane.getProperty(directorProp).getObject();

        Model dModel = ModelFactory.createDefaultModel();
        RDFParser.source(director.getURI()).httpAccept("application/ld+json").parse(dModel.getGraph());
        Resource dir = dModel.getResource(director.getURI());
        Property birthPlace = dModel.getProperty("http://dbpedia.org/property/birthPlace");
        Resource country = (Resource) dir.getProperty(birthPlace).getObject();

        Model cModel = ModelFactory.createDefaultModel();
        RDFParser.source(country.getURI()).httpAccept("application/ld+json").parse(cModel.getGraph());
        Resource us = cModel.getResource(country.getURI());
        Property currencyProp = cModel.getProperty("http://dbpedia.org/property/currencyCode");
        Property govermentTypeProp = cModel.getProperty("http://dbpedia.org/property/governmentType");
        Property languageProp = cModel.getProperty("http://dbpedia.org/ontology/language");

        RDFNode currency = us.getProperty(currencyProp).getObject();
        Resource govermentType = (Resource) us.getProperty(govermentTypeProp).getObject();
        Resource language = (Resource) us.getProperty(languageProp).getObject();

        String s = "Currency of the United States is: " + currency.toString() + "\n" +
                "Goverment type of the United States is: " + govermentType.toString() + "\n" +
                "Official language of the United States is: " + language.toString();
        System.out.println(s);

    }
}
