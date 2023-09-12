package org.example.controllers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.utils.loadData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

// Resets board status and pieces info for all 3 windows

public class ResetBoardController {

    // call initBoardStatus.py
    // call initPiecesInfo.py
    public static String handleResetBoard(spark.Request req, spark.Response res) throws IOException {
        System.out.println("reseting board...");

        // need to replace board status by initBoardstatus
        Model status_model1 = loadData.initAndLoadModelFromResource("initial/initBoardStatusW1.ttl", Lang.TURTLE);
        Model pieces_model1 = loadData.initAndLoadModelFromResource("initial/initPiecesInfoW1.ttl", Lang.TURTLE);

        Model status_model2 = loadData.initAndLoadModelFromResource("initial/initBoardStatusW2.ttl", Lang.TURTLE);
        Model pieces_model2 = loadData.initAndLoadModelFromResource("initial/initPiecesInfoW2.ttl", Lang.TURTLE);

        Model status_model3 = loadData.initAndLoadModelFromResource("initial/initBoardStatusW3.ttl", Lang.TURTLE);
        Model pieces_model3 = loadData.initAndLoadModelFromResource("initial/initPiecesInfoW3.ttl", Lang.TURTLE);

        // replacing model
        File file = new File("src/main/resources/boardStatusW1.ttl");
        OutputStream out = new FileOutputStream(file);
        RDFDataMgr.write(out, status_model1, Lang.TURTLE);

        File file1 = new File("src/main/resources/piecesInfoW1.ttl");
        OutputStream out1 = new FileOutputStream(file1);
        RDFDataMgr.write(out1, pieces_model1, Lang.TURTLE);

        // replacing model
        File file2 = new File("src/main/resources/boardStatusW2.ttl");
        OutputStream out2 = new FileOutputStream(file2);
        RDFDataMgr.write(out2, status_model2, Lang.TURTLE);

        File file3 = new File("src/main/resources/piecesInfoW2.ttl");
        OutputStream out3 = new FileOutputStream(file3);
        RDFDataMgr.write(out3, pieces_model2, Lang.TURTLE);

        // replacing model
        File file4 = new File("src/main/resources/boardStatusW3.ttl");
        OutputStream out4 = new FileOutputStream(file4);
        RDFDataMgr.write(out4, status_model3, Lang.TURTLE);

        File file5 = new File("src/main/resources/piecesInfoW3.ttl");
        OutputStream out5 = new FileOutputStream(file5);
        RDFDataMgr.write(out5, pieces_model3, Lang.TURTLE);

        System.out.println("Board reset.");

        return "Reset board";
    }
}
