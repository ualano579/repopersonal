package estudio.bstree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BSTreeTest {
    private BSTree<Integer> bst;

    @BeforeEach
    void setUp() {
        bst = new BSTree<>();
        int[] numeros = {7, 3, 9, 6, 4, 5, 2, 8, 1};
        for (int i : numeros) {
            bst.add(i);
        }
    }

    @Test
    void testDisplayTree() {
        String result = bst.displayTree();
        String expected = 
        		  "     (9)[1]\n"
        		+ "          (8)[2]\n"
        		+ "(7)[0]\n"
        		+ "          (6)[2]\n"
        		+ "                    (5)[4]\n"
        		+ "               (4)[3]\n"
        		+ "     (3)[1]\n"
        		+ "          (2)[2]\n"
        		+ "               (1)[3]\n"
        		+ "";
        assertNotNull(result);
        assertEquals(expected, result);
        
    }

    @Test
    void testDisplayTreeAsterisk() {
        String result = bst.displayTreeAsterisk(2);
        String expected = 
        		  "     (9)[1]\n"
        		+ "          (*)[2]\n"
        		+ "(7)[0]\n"
        		+ "          (*)[2]\n"
        		+ "                    (5)[4]\n"
        		+ "               (4)[3]\n"
        		+ "     (3)[1]\n"
        		+ "          (*)[2]\n"
        		+ "               (1)[3]\n"
        		+ "";
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testPathHeightIterative() {
        assertEquals(0, bst.pathHeightIterative(7));
        assertEquals(1, bst.pathHeightIterative(3));
        assertEquals(3, bst.pathHeightIterative(1));
        assertEquals(-1, bst.pathHeightIterative(100));
    }

    @Test
    void testPathHeightRecursive() {
        assertEquals(0, bst.pathHeightRecursive(7));
        assertEquals(1, bst.pathHeightRecursive(3));
        assertEquals(3, bst.pathHeightRecursive(1));
        assertEquals(-1, bst.pathHeightRecursive(100));
    }

    @Test
    void testToStringLevel() {
        String level0 = bst.toStringLevel(0);
        assertEquals("7 ", level0);
        String level1 = bst.toStringLevel(1);
        assertEquals("3 9 ", level1);
        String level2 = bst.toStringLevel(2);
        assertEquals("2 6 8 ", level2);
    }

    @Test
    void testCountNodes() {
        assertEquals(9, bst.countNodes());
    }

    @Test
    void testCountNodesOfLevel() {
        assertEquals(1, bst.countNodesOfLevel(0));
        assertEquals(2, bst.countNodesOfLevel(1));
        assertEquals(3, bst.countNodesOfLevel(2));
        assertEquals(2, bst.countNodesOfLevel(3));
        assertEquals(1, bst.countNodesOfLevel(4));
        assertEquals(0, bst.countNodesOfLevel(5));
    }

    @Test
    void testHeight() {
        assertEquals(4, bst.height());
    }

    @Test
    void testNumberOfLeaves() {
        assertEquals(3, bst.numberOfLeaves());
    }

    @Test
    void testFindMin() {
        assertEquals(1, bst.findMin());
    }

    @Test
    void testFindMax() {
        assertEquals(9, bst.findMax());
    }

    @Test
    void testRemoveLeaves() {
        bst.removeLeaves();
        assertEquals(6, bst.countNodes());
        String expected = "[2, 3, 4, 6, 7, 9]";
        assertEquals(expected, bst.toString());
    }

    @Test
    void testFindLevel() {
        assertEquals(0, bst.findLevel(7));
        assertEquals(1, bst.findLevel(3));
        assertEquals(3, bst.findLevel(1));
        assertEquals(-1, bst.findLevel(100));
    }

    @Test
    void testNumberOfIntermediateNodes() {
        assertEquals(6, bst.numberOfIntermediateNodes());
    }

    @Test
    void testNumberOfNodesWithTwoChildren() {
        assertEquals(2, bst.numberOfNodesWithTwoChildren());
    }

    @Test
    void testNumberOfNodesWithOneChild() {
        assertEquals(4, bst.numberOfNodesWithOneChild());
    }

    @Test
    void testRemoveMin() {
        bst.removeMin();
        assertEquals(2, bst.findMin());
        String expected = "[2, 3, 4, 5, 6, 7, 8, 9]";
        assertEquals(expected, bst.toString());
    }

    @Test
    void testRemoveMax() {
        bst.removeMax();
        assertEquals(8, bst.findMax());
        String expected = "[1, 2, 3, 4, 5, 6, 7, 8]";
        assertEquals(expected, bst.toString());
    }

    @Test
    void testNodeGrade() {
        assertEquals(2, bst.nodeGrade(7));
        assertEquals(1, bst.nodeGrade(9));
        assertEquals(0, bst.nodeGrade(1));
        assertEquals(-1, bst.nodeGrade(100));
    }
}

