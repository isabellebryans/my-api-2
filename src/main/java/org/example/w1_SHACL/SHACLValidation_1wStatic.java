package org.example.w1_SHACL;

import org.apache.jena.graph.Graph;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.shacl.ValidationReport;
import org.example.utils.loadData;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SHACLValidation_1wStatic {
    public static int handleValidation() throws FileNotFoundException, IOException {
        System.out.println("Starting shacl validation.");

        // Data graph is:
        // BS + PI + status + move
        Graph dataGraph = loadData.initAndLoadModelFromResource("chessBoardstructure.ttl", Lang.TURTLE)
                .union(loadData.initAndLoadModelFromResource("boardStatusW1.ttl", Lang.TURTLE))
                .union(loadData.initAndLoadModelFromResource("piecesInfoW1.ttl", Lang.TURTLE))
                .getGraph();
        Graph shapesGraph = loadData.initAndLoadModelFromResource("shapes/w1_CR_shapes.ttl", Lang.TURTLE).getGraph();
        // Graph shapesGraph = loadData.initAndLoadModelFromFolder("src/main/java/org/example/Chess/type2/chessMove1Shapes.ttl", Lang.TURTLE).getGraph();
        Shapes shapes = Shapes.parse(shapesGraph);

        ValidationReport report = ShaclValidator.get().validate(shapes, dataGraph);
        //ShLib.printReport(report);
        System.out.println();
        RDFDataMgr.write(System.out, report.getModel(), Lang.RDFJSON);
        // RDFDataMgr.write(new FileOutputStream("report.ttl"), report.getModel(), Lang.TURTLE);
        //RDFDataMgr.write(System.out, report.getModel(), Lang.TTL);

        // IF CONFORMS, RETURNS 0
        return 0;

    }
}
