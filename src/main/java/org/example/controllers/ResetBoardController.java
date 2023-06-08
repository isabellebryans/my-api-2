package org.example.controllers;

import jep.Jep;
import jep.JepException;
import jep.MainInterpreter;
import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import java.io.File;
import java.io.IOException;

public class ResetBoardController {

    // call initBoardStatus.py
    // call initPiecesInfo.py
    public static String handleResetBoard(spark.Request req, spark.Response res) throws IOException {
        System.out.println("reseting board...");

        try {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.execfile("C:\\Users\\isabe\\IdeaProjects\\my-api-2\\src\\main\\resources\\initBoardPositions.py");
            System.out.println("Run");
        } catch (PyException e) {
            e.printStackTrace();
        }


        return "Reset board";
    }
}
