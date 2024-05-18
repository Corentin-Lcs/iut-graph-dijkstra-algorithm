package src.test.java.graphe.core;

import org.junit.jupiter.api.Test;
import src.main.graphe.core.Arc;
import src.main.graphe.core.IGraphe;
import src.main.graphe.ihm.GraphImporter;
import src.main.graphe.implems.GrapheHHAdj;
import src.main.graphe.implems.GrapheLAdj;
import src.main.graphe.implems.GrapheLArcs;
import src.main.graphe.implems.GrapheMAdj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link IGraphe} implementations.
 * @author Corentin L. / Seweryn C.
 */

class IGrapheTest {
    private final IGraphe[] graphes = {
        new GrapheLArcs(), new GrapheLAdj(),
        new GrapheMAdj(), new GrapheHHAdj()
    };

    private final String g31 =
        "A-C(2), A-D(1), "
        + "B-G(3), "
        + "C-H(2), "
        + "D-B(3), D-C(5), D-E(3), "
        + "E-C(1), E-G(3), E-H(7), "
        + "F:, "
        + "G-B(2), G-F(1), "
        + "H-F(4), H-G(2), "
        + "I-H(10), "
        + "J:";

    // Unsorted arcs
    private final String g31a =
        "D-C(5), D-E(3), D-B(3), "
        + "E-G(3), E-C(1), E-H(7), "
        + "I-H(10), "
        + "J:,"
        + "G-B(2), G-F(1), "
        + "F:, "
        + "H-G(2), H-F(4), "
        + "A-C(2), A-D(1), "
        + "B-G(3), "
        + "C-H(2) ";

    void clear(IGraphe g) {
        for (String s : g.getSommets()) {
            g.oterSommet(s);
        }
    }

    void verifErreurAjoutSommet_PasAjoutSommetSiArcInvalide(IGraphe g) {
        clear(g);
        try {
            g.ajouterArc("A", "B", -1);
        } catch (Exception ignored) { }
        assertFalse(g.contientSommet("A") && g.contientSommet("B"));
    }

    @Test
    void testErreurAjoutSommet_PasAjoutSommetSiArcInvalide() {
        for (IGraphe g : graphes)
            verifErreurAjoutSommet_PasAjoutSommetSiArcInvalide(g);
    }

    void verifGrapheVide(IGraphe g) {
        clear(g);
        assertEquals(g.getSommets(), Collections.emptyList());
    }

    @Test
    void testGrapheVide() {
        for (IGraphe g : graphes)
            verifGrapheVide(g);
    }

    void verifGrapheArcDeuxSens(IGraphe g) {
        clear(g);
        g.ajouterArc("A", "B", 2);
        g.ajouterArc("B", "A", 4);
        assertEquals(g.getValuation("A", "B"), 2);
        assertEquals(g.getValuation("B", "A"), 4);
    }

    @Test
    void testGrapheArcDeuxSens() {
        for (IGraphe g : graphes)
            verifGrapheArcDeuxSens(g);
    }

    @Test
    void exo3_1Maths() {
        for (IGraphe g : graphes) {
            g.peupler(g31a);
            tester3_1(g);
        }
    }

    void tester3_1(IGraphe g) {
        List<String> sommets_exp = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        List<String> sommets = new ArrayList<String>(g.getSommets()); // Not necessarily sorted
        Collections.sort(sommets);
        assertEquals(sommets_exp, sommets);
        assertTrue(g.contientSommet("C"));
        assertFalse(g.contientSommet("c"));
        assertTrue(g.contientArc("C", "H"));
        assertFalse(g.contientArc("H", "C"));
        assertEquals(7, g.getValuation("E", "H"));
        List<String> successeurs = new ArrayList<>(g.getSucc("D")); // Not necessarily sorted
        Collections.sort(successeurs);
        assertEquals(List.of("B", "C", "E"), successeurs);
        assertEquals(g31, g.toString());
        g.ajouterSommet("A"); // Does nothing because A is already present
        assertEquals(g31, g.toString());
        assertThrows(IllegalArgumentException.class, () -> g.ajouterArc("G", "B", 1)); // Already exists
        g.oterSommet("X"); // Does nothing because X does not exist
        assertEquals(g31, g.toString());
        assertThrows(IllegalArgumentException.class, () -> g.oterArc("X", "Y")); // Does not exist
        assertThrows(IllegalArgumentException.class, () -> g.ajouterArc("A", "B", -1)); // Negative valuation
    }

    void petiteImporation(IGraphe g, String filename) {
        Arc a = GraphImporter.importer(filename, g);
        System.out.println(filename + " : " + g.toString());
        assertEquals("1-3(5), "
            + "10-3(3), 2-1(5), 2-3(5), 2-5(4), "
            + "3-4(4), 3-5(4), 4-10(1), 4-2(1), 4-7(3), "
            + "5-9(4), 6-2(3), 6-3(4), 7-3(2),"
            + " 8-2(4), 8-6(1), 9-2(4)",
            g.toString());
        assertEquals("5", a.getSource());
        assertEquals("7", a.getDestination());
    }

    @Test
    void petitTestImportation() {
        for (IGraphe g : graphes)
            petiteImporation(g, "graphes/orig/g-10-1.txt");
    }
}