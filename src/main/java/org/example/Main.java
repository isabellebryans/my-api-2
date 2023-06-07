package org.example;
import com.google.gson.Gson;
import jep.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.move.Move;
import org.example.utils.loadData;
import org.apache.jena.rdf.model.*;



import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;

import static spark.Spark.*;
public class Main {
    Gson gson = new Gson();
    public static void main(String[] args) {

        port(3000);
        get("/move", (req, res)->"Hello, world");

        post("/move", (req,res)->{
            System.out.println("here1");
            res.type("application/json");
            // automatically fills in class properties
            Move move = new Gson().fromJson(req.body(), Move.class);
            if(move == null){
                // something went wrong
                res.status(400);
                return "Move not created";
            }
            System.out.println("here1.5");
            convert_to_rdf(move);
            res.status(200);
            System.out.println("here2");
            return "Move created";

        });
        post("/reset", (req,res)->{
            System.out.println("here3");
            res.type("application/json");
            System.out.println("here");
            // call the python script to reset the initial board status
            int stat = runScript("initBoardPositions.py");
            System.out.println(stat);
            res.status(stat);

            if(stat == 400){
                return "Error in reseting board";
            }
            return "Board reset";

        });

    }
    private static void convert_to_rdf(Move move)
    {
    try{

        String filename = "chessMove.ttl";
        String ns = "http://example.org/chess/";
        // Get rdf graph from file
        Model model = loadData.initAndLoadModelFromResource(filename, Lang.TURTLE);
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

    public static int runScript(String pythonScriptRelativePath) {
        System.out.println("about to run python");
        try (Interpreter interp = new SharedInterpreter()) {
            System.out.println("trying to run python");

            // Get the absolute file path of the Python script
            ClassLoader classLoader = Main.class.getClassLoader();
            String pythonScriptAbsolutePath = classLoader.getResource(pythonScriptRelativePath).getFile();
            if (pythonScriptAbsolutePath== null){
                return 400;
            }
            interp.runScript(pythonScriptAbsolutePath);
            System.out.println("ran python");
        } catch (JepException e) {
            // Handle the exception
            e.printStackTrace();
            System.out.println("exception to run python");
            return 400;
        }
        return 200;
    }


}