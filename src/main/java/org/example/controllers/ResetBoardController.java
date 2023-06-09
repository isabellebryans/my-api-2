package org.example.controllers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.example.utils.loadData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResetBoardController {

    // call initBoardStatus.py
    // call initPiecesInfo.py
    public static String handleResetBoard(spark.Request req, spark.Response res) throws IOException {
        System.out.println("reseting board...");

        // need to replace board status by initBoardstatus
        Model status_model = loadData.initAndLoadModelFromResource("initial/initBoardStatusW1.ttl", Lang.TURTLE);
        Model pieces_model = loadData.initAndLoadModelFromResource("initial/initPiecesInfoW1.ttl", Lang.TURTLE);

        // replacing model
        File file = new File("src/main/resources/boardStatusW1.ttl");
        OutputStream out = new FileOutputStream(file);
        RDFDataMgr.write(out, status_model, Lang.TURTLE);

        File file1 = new File("src/main/resources/piecesInfoW1.ttl");
        OutputStream out1 = new FileOutputStream(file1);
        RDFDataMgr.write(out1, pieces_model, Lang.TURTLE);

        System.out.println("Board reset.");

        return "Reset board";
    }
}
