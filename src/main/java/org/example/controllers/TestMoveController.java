package org.example.controllers;

import com.google.gson.Gson;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.move.Move;
import org.example.results.Results;
import org.example.utils.findMovedPiece;
import org.example.w1_SHACL.SHACLValidation_1wCR;

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

        Resource piece = findMovedPiece.findMovedPiece_w1(move);

        if(piece == null){
            res.status(400);
            return "Couldn't find piece in from position";
        }

        // Cash registry
        cashRegistrytoRDF(piece, move);

        String jsonReport = SHACLValidation_1wCR.handleValidation();
        //SHACL_results.setCR(CR_val);
        // Create Gson instance
        Gson gson = new Gson();

        // Convert the object to a JSON string
        String json = gson.toJson(SHACL_results);

        // Print the JSON string
        System.out.println(json);
        System.out.println(jsonReport);

        res.status(200);

        return jsonReport;
    }


    // Thi
    private static void cashRegistrytoRDF(Resource subject, Move move){
        String filename = "CRchessMove_t1.ttl";
        String ns = "http://example.org/chess/";

        try{
            // Get rdf graph from file
            // Model model = loadData.initAndLoadModelFromResource(filename, Lang.TURTLE);

            // Create model
            Model model = ModelFactory.createDefaultModel();
            // create new triple from move
            Property predicate = model.createProperty(ns+"movesTo_t1");
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
