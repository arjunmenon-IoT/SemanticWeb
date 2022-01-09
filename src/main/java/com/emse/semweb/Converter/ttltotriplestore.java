package com.emse.semweb.Converter;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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

import com.github.jsonldjava.shaded.com.google.common.base.Charsets;
import com.github.jsonldjava.shaded.com.google.common.io.Files;


import java.io.IOException;




public class ttltotriplestore {
    // TODO Auto-generated method stub





    public static void main(String[] args) throws IOException  {

        // TODO Auto-generated method stub
        // string filepath="C:\\\\Nouveau dossier\\\\territoire.emse.fr"
        String filepath = "C:\\Users\\eleves\\Desktop\\Nouveau dossier\\Semweb\\src\\main\\java\\com\\emse\\semweb\\Converter\\territoire.emse.fr\\\\ali2";


        String serviceURL = "http://localhost:3030/PalteformeTerritoire";

        try(
                RDFConnection conneg = RDFConnectionFactory.connect(serviceURL)){
            System.out.println("test");
            //if(filepath .contains(".ttl")){
            //  conneg.load(filepath);
            // System.out.println("done");
            // }
            // else if(filepath.contains(".txt")){

            String line = "";
            File ttl = new File(filepath);
            try (FileReader fr = new FileReader(ttl, Charsets.UTF_8)) {
                BufferedReader bReader = new BufferedReader(fr); // Lecture dans le fichier
                while((line = bReader.readLine()) !=null){
                    System.out.println(line);
                    conneg.load(line);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            conneg.commit();
            conneg.close() ;
        }


    };

}





