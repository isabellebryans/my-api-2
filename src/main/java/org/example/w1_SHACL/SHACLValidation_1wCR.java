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
import java.io.StringWriter;

public class SHACLValidation_1wCR {
    public static String handleValidation() throws IOException {
        System.out.println("Starting shacl validation.");

        // Data graph is:
        // BS + PI + status + move
        Graph dataGraph = loadData.initAndLoadModelFromResource("chessBoardstructure.ttl", Lang.TURTLE)
                .union(loadData.initAndLoadModelFromResource("boardStatusW1.ttl", Lang.TURTLE))
                .union(loadData.initAndLoadModelFromResource("piecesInfoW1.ttl", Lang.TURTLE))
                .union(loadData.initAndLoadModelFromResource("CRchessMove_t1.ttl", Lang.TURTLE))
                .getGraph();
        Graph shapesGraph = loadData.initAndLoadModelFromResource("shapes/w1_CR_shapes.ttl", Lang.TURTLE).getGraph();
       // Graph shapesGraph = loadData.initAndLoadModelFromFolder("src/main/java/org/example/Chess/type2/chessMove1Shapes.ttl", Lang.TURTLE).getGraph();
        Shapes shapes = Shapes.parse(shapesGraph);
        System.out.println(dataGraph);

        ValidationReport report = ShaclValidator.get().validate(shapes, dataGraph);
        boolean conforms = report.conforms();
        //ShLib.printReport(report);
        //System.out.println();
        //RDFDataMgr.write(System.out, report.getModel(), Lang.RDFJSON);
        StringWriter stringWriter = new StringWriter();
        RDFDataMgr.write(stringWriter, report.getModel(), Lang.JSONLD);
        //System.out.println(stringWriter);
        /*if (conforms){
            return 0;
        }*/

        // IF CONFORMS, RETURNS 0
        return stringWriter.toString();
    }
}
