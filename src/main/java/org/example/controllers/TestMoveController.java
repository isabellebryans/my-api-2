package org.example.controllers;

import com.google.gson.Gson;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.move.Move;
import org.example.results.Results;
import org.example.w1_SHACL.w1_run_SHACL_validation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestMoveController {


    public String handleTestMove(spark.Request req, spark.Response res) throws IOException {
        res.type("application/json");

        // automatically fills in class properties
        Move move = new Gson().fromJson(req.body(), Move.class);
        if(move == null){
            // something went wrong
            res.status(400);
            return "Move not created";
        }

        // this is what will be returned to client
        Results SHACL_results = new Results();

        // 1. Cash registry
        cashRegistrytoRDF(move);
        int CR_val = w1_run_SHACL_validation.handleValidation(move);

        // Create Gson instance
        Gson gson = new Gson();

        // Convert the object to a JSON string
        String json = gson.toJson(SHACL_results);

        // Print the JSON string
        System.out.println(json);

        res.status(200);

        return json;
    }
    private static void cashRegistrytoRDF(Move move)
    {
        try{
            String filename = "CRchessMove.ttl";
            String ns = "http://example.org/chess/";
            // Get rdf graph from file
            // Model model = loadData.initAndLoadModelFromResource(filename, Lang.TURTLE);

            // Create model
            Model model = ModelFactory.createDefaultModel();
            // create new triple from move
            Resource subject = model.createResource(ns+move.getPiece());
            Property predicate = model.createProperty(ns+"to");
            RDFNode object = model.createResource(ns+move.getTo());
            Statement triple = model.createStatement(subject, predicate, object);
            // Add new triple from graph
            model.add(triple);

            // Put updated graph back into the file
            File file = new File("src/main/resources/"+filename);
            OutputStream out = new FileOutputStream(file);
            RDFDataMgr.write(out, model, Lang.TURTLE);
            RDFDataMgr.write(System.out, model, Lang.TURTLE);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
