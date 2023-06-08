package org.example;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.gson.Gson;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.move.Move;
import org.example.utils.loadData;

import java.io.IOException;
import java.util.List;

public class MakeMoveController {
    private final String filename = "boardStatus.ttl";
    public String handleMakeMove(spark.Request req, spark.Response res) throws IOException {
        res.type("application/json");
        // automatically fills in class properties
        Move move = new Gson().fromJson(req.body(), Move.class);
        if(move == null){
            // something went wrong
            res.status(400);
            return "Move not created";
        }
        // Load rdf graph
        //String filename = "boardStatus.ttl";
        Model model = loadData.initAndLoadModelFromResource(filename, Lang.TURTLE);
        //RDFDataMgr.write(System.out, model, Lang.TTL);
        // delete triple in to tile if there is one (do after)
        model = delete(model, move.getTo());
        // calculate which triple to delete and delete it

        // delete triple in

        // add new triple to "boardStatus"

        res.status(200);
        return "Move created";
    }

    private static Model delete(Model model, String tile) {
        String ns = "http://example.org/chess/";
        // Create query
        String queryString = "PREFIX ns1: <http://example.org/chess/> \n SELECT ?subject ?predicate ?object WHERE { ?subject ?predicate ?object. FILTER(?object = ns1:"+ tile + ") }";

        // Create a Query object from the query string
        Query query = QueryFactory.create(queryString);

        // Execute the query
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            //ResultSetFormatter.out(System.out, results, query);
            if (results.getRowNumber() == 0) {
                System.out.println("No results found.");
            }

            // Process the query results
            while (results.hasNext()) {

                QuerySolution solution = results.nextSolution();
                // Access the individual query results
                String subject = solution.get("subject").toString();
                String predicate = solution.get("predicate").toString();
                String object = solution.get("object").toString();

                System.out.println("Subject: " + subject);
                System.out.println("Predicate: " + predicate);
                System.out.println("Object: " + object);

                Resource sub = solution.getResource("subject");
                Property prop = ResourceFactory.createProperty(predicate);
                RDFNode obj = solution.get("object");
                Statement tripleToDelete = ResourceFactory.createStatement(sub, prop, obj);
                System.out.println(tripleToDelete);
                model.remove(tripleToDelete);
                RDFDataMgr.write(System.out, model, Lang.TTL);
            }
        }
        // find object that is the same as the "tile"

        // check if the subject is "piece"
        // if so, remove it from the model
        // if not, error

        return model;
    }
}
