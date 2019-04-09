package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraGraph {

    private Graph graph;

    public DijkstraGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public List<Node> getShortestPath(Node from, Node to) {
        // A mapping from a Node to a number.
        // This number is the distance from the Node `from` that we provide
        // as a param.
        Map<Node, Integer> nodeDistanceMapping = getNodeDistanceMapping(from);
        // A mapping from a Node to another Node.
        // This can be used to track back to our `from` Node, so we
        // can build up a path later on in this function.
        Map<Node, Node> previousNodeMapping = getPreviousNodeMapping();

        // A set of Nodes which have not yet been visited,
        // that we know the distance value of.
        Set<Node> unsettled = new HashSet<Node>();
        unsettled.add(from);

        // A set of Nodes which have been visited, all neighbors
        // have been checked, so we don't need to check it anymore.
        Set<Node> settled = new HashSet<Node>();

        // While we still have Nodes whose neighbors have not been checked.
        while (unsettled.size() != 0) {
            // Get the Node with the lowest distance from the unsettled Nodes.
            Node currentNode = getLowestUnsettledNode(unsettled, nodeDistanceMapping);
            // Get the distance from the `from` Node to the `currentNode` Node.
            int currentDistance = nodeDistanceMapping.get(currentNode);
            unsettled.remove(currentNode);

            // For each Edge coming out of the `currentNode` Node:
            // - If the node is in the settled Set, skip it
            // - Calculate the total distance value to the Node on the other side of the Edge.
            // - If the distance is lower than the distance in the `nodeDistanceMapping`, update it.
            // - Add the Node to the unsettled Set, so it's neighbors will be checked in another iteration.
            for (final Edge e : currentNode.getEdges()) {
                int currentNeighorDistance = nodeDistanceMapping.get(e.getTo());
                int newNeighborDistance = currentDistance + e.getWeight();
                if (!settled.contains(e.getTo()) && newNeighborDistance < currentNeighorDistance) {
                    nodeDistanceMapping.put(e.getTo(), newNeighborDistance);
                    previousNodeMapping.put(e.getTo(), currentNode);
                    unsettled.add(e.getTo());
                }
            }
            // Add the `currentNode` to the settled set, so we don't check it anymore.
            settled.add(currentNode);
        }

        // Build up the path of Nodes from `from` to `to`.
        // `previousNodeMapping` would look something like this
        //  ----------------------
        // | Node | Previous Node |
        //  ----------------------
        // | A    | null          |
        // | B    | A             |
        // | C    | A             |
        // | D    | B             |
        // | E    | D             |
        //  ----------------------
        // Where A is our `from` Node.
        // So the path from A to E involves getting the Previous Node of E from `previousNodeMapping`, it's D. Add that to the list.
        // The Previous Node of D isn't null, so get D from `previousNodeMapping`, it's B. Add that to the list.
        // The Previous Node of B isn't null, so get B from `previousNodeMapping`, it's A. Add that to the list.
        // The Previous Node of A is null, add that to the list, we're done.
        //
        // We have a list [E, D, B, A], the path from A->E is that list in reverse.
        List<Node> path = new ArrayList<Node>();
        for (Node n = to; n != null; n = previousNodeMapping.get(n)) {
            path.add(n);
        }
        Collections.reverse(path);
        return path;
    }

    private Map<Node, Node> getPreviousNodeMapping() {
        Map<Node, Node> mappings = new HashMap<>();
        for(Node n : getGraph().getNodes()) {
            mappings.put(n, null);
        }
        return mappings;
    }

    private Node getLowestUnsettledNode(Set<Node> unsettled, Map<Node, Integer> distanceMapping) {
        Iterator<Node> iter = unsettled.iterator();
        if (!iter.hasNext()) {
            return null;
        }
        Node selected = iter.next();
        while (iter.hasNext()) {
            Node n = iter.next();
            if (distanceMapping.get(n) < distanceMapping.get(selected)) {
                selected = n;
            }
        }
        return selected;
    }

    private Map<Node, Integer> getNodeDistanceMapping(Node from) {
        Map<Node, Integer> mappings = new HashMap<Node, Integer>();
        for(Node n : getGraph().getNodes()) {
            mappings.put(n, Integer.MAX_VALUE);
        }
        mappings.put(from, 0);
        return mappings;
    }
}
