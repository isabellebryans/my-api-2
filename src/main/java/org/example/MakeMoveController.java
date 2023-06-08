package org.example;

import com.google.gson.Gson;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.query.ParameterizedSparqlString;
import org.example.move.Move;
import org.example.utils.loadData;

import java.io.IOException;

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
        String queryString = "SELECT ?subject ?predicate ?object WHERE { ?subject ?predicate ?object. FILTER (?object = $tile) }";

        // Create a ParameterizedSparqlString and set the object variable value
        ParameterizedSparqlString parameterizedQuery = new ParameterizedSparqlString(queryString);
        parameterizedQuery.setLiteral("tile", ns+tile);
        System.out.println("in delete function");
        // Execute the parameterized query
        try (QueryExecution qexec = QueryExecutionFactory.create(parameterizedQuery.asQuery(), model)) {
            System.out.println("In try tatement");
            ResultSet results = qexec.execSelect();
            if(results.getRowNumber()== 0) {
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
            }

        }
        // find object that is the same as the "tile"

        // check if the subject is "piece"
        // if so, remove it from the model
        // if not, error

        return model;
    }
}