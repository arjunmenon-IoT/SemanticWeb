package com.emse.semweb.Converter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.sparql.function.library.e;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.XSD;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class SensorsToTripleStore {



    public static void main(String[] args) throws IOException  {
        // TODO Auto-generated method stub

        Model m = ModelFactory.createDefaultModel();










        m.setNsPrefix("rdfs",RDFS.uri);
        m.setNsPrefix("rdf",RDFS.uri);
        m.setNsPrefix("sosa","http://www.w3.org/ns/sosa/");
        m.setNsPrefix("time","http://www.w3.org/2006/time#");
        m.setNsPrefix("cdt","http://w3id.org/lindt/custom_datatypes#");
        m.setNsPrefix("xsd","http://www.w3.org/2001/XMLSchema#");
        String datasetURL = "http://localhost:3030/Sensors/";
        String sparqlEndpoint = datasetURL + "sparql";
        String sparqlUpdate = datasetURL + "update";
        String graphStore = datasetURL + "data";
        RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);

        Resource re = m.createResource("https://example.com/");
        String sensor= "";
        String geo="http://www.w3.org/2003/01/geo/wgs84_pos#";
        String sosa="http://www.w3.org/ns/sosa/";
        String cdt="http://w3id.org/lindt/custom_datatypes#";
        String rdfs= RDFS.uri;
        BufferedReader r = null;
        try {
             r = new BufferedReader(new FileReader("C:\\\\Users\\\\eleves\\\\Desktop\\\\Sensors.csv"));
            r.readLine(); // remove the header
            String line;
            int i=1;
            ArrayList<String> isDuplicate = new ArrayList<String>();
            while ((line = r.readLine()) != null) {
                i++;
                if (line.isEmpty())
                    continue;

                String[] raw = line.split(",");
                String stopId = raw[0].replace(" ", "_");

                String str = raw[1];
                SimpleDateFormat sf = new SimpleDateFormat("H");
                Date date = new Date(Long.parseLong(str));
                String datetime=sf.format(date);

                // Create an instance of SimpleDateFormat used for formatting
                // the string representation of date according to the chosen pattern

                String id = raw[8];
                String location = raw[9];
                String humidity = raw[7];

                if(!isDuplicate.contains(location+datetime) && !humidity.equals("")) {

                    m.add(
                            m.createResource(
                                    "sensor/" + id),
                            RDF.type,

                            m.createResource(sosa + "Sensor")

                    );

                    m.add(
                            m.createResource(
                                    "observation/" + i),
                            m.createProperty(sosa + "madeBySensor"),
                            m.createResource("sensor/" + id)
                    );
                    m.add(
                            m.createResource(
                                    "observation/" + i),
                            m.createProperty(sosa + "hasFeatureOfInterest"),
                            m.createResource(location)
                    );
                    isDuplicate.add(location+datetime);
                    m.add(
                            m.createResource(
                                    "observation/" + i),
                            m.createProperty(sosa + "resultTime"),

                            m.createTypedLiteral(datetime, XSDDatatype.XSDtime)


                    );

                    m.add(
                            m.createResource(
                                    "observation/" + i),
                            m.createProperty(sosa + "hasSimpleResult"),

                            m.createTypedLiteral(humidity, cdt)


                    );
                    try (RDFConnection conn = RDFConnectionFactory.connect(datasetURL)) {
                        conn.put(m);

                    }
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // add the triple to the triplestore




    }

}






