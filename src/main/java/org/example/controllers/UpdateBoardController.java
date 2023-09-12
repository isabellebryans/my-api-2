package org.example.controllers;

import com.google.gson.Gson;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.move.Move;
import org.example.utils.findMovedPiece;
import org.example.utils.loadData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UpdateBoardController {
    public String handleUpdateBoard(spark.Request req, spark.Response res) throws IOException {
        String filename = "boardStatusW1.ttl";
        res.type("application/json");
        // automatically fills in class properties
        Move move = new Gson().fromJson(req.body(), Move.class);
        if(move == null){
            // something went wrong
            res.status(400);
            return "Move not created";
        }
        // Load rdf graph

        Model model = loadData.initAndLoadModelFromResource(filename, Lang.TURTLE);
        //RDFDataMgr.write(System.out, model, Lang.TTL);
        // delete piece in TO tile (this piece has been captured) IF captured piece
        delete(model, move.getTo());

        // delete the triple of the piece in the old position
        delete(model, move.getfrom());

        // add triple of piece in new position
        addTriple(model, move);


        // add updated position graph to "boardStatusW1.ttl"
        File file = new File("src/main/resources/"+ filename);
        OutputStream out = new FileOutputStream(file);
        RDFDataMgr.write(out, model, Lang.TURTLE);

        RDFDataMgr.write(System.out, model, Lang.TTL);
        res.status(200);
        return "Board updated";
    }


    // This function deletes the triple with the object of the tile parameter
    // Deletes any triples of the form:
    // <subject> <predicate> <http://example.org/chess/"tile">
    private static void delete(Model model, String tile) {
        String ns = "http://example.org/chess/";
        // Create query
        String queryString = "PREFIX ns1: <http://example.org/chess/> \n SELECT ?subject ?predicate ?object WHERE { ?subject ?predicate ?object. FILTER(?object = ns1:"+ tile + ") }";

        // Create a Query object from the query string
        Query query = QueryFactory.create(queryString);

        // Execute the query
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            //ResultSetFormatter.out(System.out, results, query);


            // Process the query results
            if (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                // Access the individual query results
                String subject = solution.get("subject").toString();
                String predicate = solution.get("predicate").toString();
                String object = solution.get("object").toString();

                /*System.out.println("Subject: " + subject);
                System.out.println("Predicate: " + predicate);
                System.out.println("Object: " + object);*/

                Resource sub = solution.getResource("subject");
                Property prop = ResourceFactory.createProperty(predicate);
                RDFNode obj = solution.get("object");
                Statement tripleToDelete = ResourceFactory.createStatement(sub, prop, obj);
                System.out.println("Deleting triple... " +tripleToDelete);
                model.remove(tripleToDelete);

            }
            else {
                System.out.println("No results to query :(");
            }
        } catch (Exception e){
            System.out.println("Error in deleting triple.");


        }

    }

    private static void addTriple(Model model, Move move) throws IOException {
        String object = move.getTo();
        String ns = "http://example.org/chess/";

        // create triple to add
        // triple is the piece that is moved, is now in its new position
        // So <"piece"> <isIn> <"toPosition">
        Resource sub = findMovedPiece.findMovedPiece_w1(move);
        Property prop = ResourceFactory.createProperty(ns+ "occupiesPosition_t0");
        RDFNode obj = ResourceFactory.createResource(ns + object);

        Statement tripleToAdd = ResourceFactory.createStatement(sub, prop, obj);
        System.out.println("Adding triple... " +tripleToAdd);

        // Add triple to rdf graph
        model.add(tripleToAdd);

    }

}
