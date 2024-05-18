package src.test.java.graphe.algos;

import org.junit.jupiter.api.Test;
import src.main.graphe.algos.DijkstraTools;
import src.main.graphe.core.IGraphe;
import src.main.graphe.ihm.CheminATrouver;
import src.main.graphe.ihm.GraphDirectoryImporter;
import src.main.graphe.ihm.Main;
import src.main.graphe.implems.GrapheHHAdj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Dijkstra algorithm.
 * Place the graphs to be tested in the iut-graph-dijkstra-algorithm/graphs directory
 * And the expected response files in iut-graph-dijkstra-algorithm/answers.
 * @author Corentin L. / Seweryn C.
 */

class DijkstraTest {
    @Test
    void testSameSourceAndDestinationDijkstra() {
        IGraphe g = new GrapheHHAdj();
        g.ajouterSommet("A");
        checkAndTime(g, "A", "A", 0);
    }

    @Test
    void testerTousLesGraphes() {
        GraphDirectoryImporter importer = new GraphDirectoryImporter(Main.GRAPHS_REP,
            Main.ANSWERS_REP, true, new GrapheHHAdj());
        for (CheminATrouver cat : importer) {
            checkAndTime(cat.getGraph(), cat.getSD_arc().getSource(),
            cat.getSD_arc().getDestination(), cat.getDistance_attendue());
        }
    }

    void checkAndTime(IGraphe g, String source, String dest, int distanceAttendue) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        long duree = DijkstraTools.time(g, source, dist, prev);
        System.out.println("dijkstra a dure " + duree + " millisecondes");
        List<String> cheminTrouve = DijkstraTools.getPath(source, dest, prev);
        int distanceTrouvee = dist.getOrDefault(dest, -1);
        if (distanceTrouvee >= 0) {
            System.out.println("Chemin trouve : <" + String.join(", ", cheminTrouve) + ">");
            System.out.println("Distance trouvee : " + distanceTrouvee);
        } else
            System.out.println("Aucun chemin trouve !");
        assertEquals(distanceAttendue, distanceTrouvee);
    }
}