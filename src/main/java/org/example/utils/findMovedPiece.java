package org.example.utils;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.example.move.Move;

import java.io.IOException;

public class findMovedPiece {
    public static Resource findMovedPiece_w1(Move move) throws IOException {
        String filename = "CRchessMove_t1.ttl";

        String ns = "http://example.org/chess/";
        Property predicate = ResourceFactory.createProperty(ns +"occupiesPosition_t0");
        RDFNode object = ResourceFactory.createResource(ns +move.getfrom());

        Model model = loadData.initAndLoadModelFromResource("boardStatusW1.ttl", Lang.TURTLE);

        return findSubject(model, predicate, object);
    }

    private static Resource findSubject(Model model, Property predicate, RDFNode object) throws IOException {

        SimpleSelector selector = new SimpleSelector(null, predicate, object);
        ResIterator iterator = model.listSubjectsWithProperty(predicate, object);

        if (iterator.hasNext()) {
            Resource subject = iterator.nextResource();
            return subject;
        }
        System.out.println("Couldn't find chess piece in from position.");
        return null; // Subject not found

    }
}
