package org.example;
import com.google.gson.Gson;
import jep.*;
import org.example.controllers.ResetBoardController;
import org.example.controllers.TestMoveController;
import org.example.controllers.UpdateBoardController;


import static spark.Spark.*;
public class Main {
    Gson gson = new Gson();
    public static void main(String[] args) {

        port(3000);
        TestMoveController testMoveController = new TestMoveController();
        UpdateBoardController updateBoardController = new UpdateBoardController();
        get("/move", (req, res)->"Hello, world");

        // gives it a move, perform shacl validation, then send back shacl validation results for each type, don't update board status
        post("/testMove", testMoveController::handleTestMove);

        // update board by passing in newest move
        // no shacl validation
        // just updates boardStatusW1.ttl
        post("/updateBoard", updateBoardController::handleUpdateBoard);

        // Resets the board status and pieces info (in case of a promotion)
        // work
        post("/resetBoard", ResetBoardController::handleResetBoard);




        // NEED THIS
        // reset board status, doesn't work
        // emtpy boardStatusT2
        post("/totalReset", (req,res)->{
            System.out.println("here3");
            res.type("application/json");
            System.out.println("here");
            // call the python script to reset the initial board status
            int stat = runScript("initial/initBoardPositions.py");
            System.out.println(stat);
            res.status(stat);

            if(stat == 400){
                return "Error in reseting board";
            }
            return "Board reset";

        });



    }
    // doesn't work
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