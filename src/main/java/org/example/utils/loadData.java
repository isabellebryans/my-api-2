package org.example.utils;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class loadData {
    public static Model initAndLoadModelFromFolder(String filename, Lang lang) throws IOException {
        // Turn file into input stream to be read
        File newFile = new File(filename);
        //InputStream dataModelIS = loadData.class.getClassLoader().getResourceAsStream(filename);
        InputStream dataModelIS = new FileInputStream(newFile);
        // Create empty RDF model
        Model dataModel = ModelFactory.createDefaultModel();
        // From RDF io lib (riot), Read input stream into new model
        RDFDataMgr.read(dataModel, dataModelIS, lang);
        return dataModel;
    }

    public static Model initAndLoadModelFromResource(String filename, Lang lang) throws IOException {
        // Turn file into input stream to be read
        File newFile = new File(filename);
        InputStream dataModelIS = loadData.class.getClassLoader().getResourceAsStream(filename);
        //InputStream dataModelIS = new FileInputStream(newFile);
        // Create empty RDF model
        Model dataModel = ModelFactory.createDefaultModel();
        // From RDF io lib (riot), Read input stream into new model
        RDFDataMgr.read(dataModel, dataModelIS, lang);
        return dataModel;
    }
}
