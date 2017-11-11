import java.util.ArrayList;

public class Tree {
	private Node root;
	private ArrayList<Node> nodes;

	ArrayList<Edge> edges = new ArrayList<Edge>();

	public Tree() {
		root = null;
		nodes = new ArrayList<Node>(); edges = new ArrayList<Edge>();
	}

	public static Tree createTree(Edge[] edge) {
		Tree t = new Tree();
		for(int i = 0; i < edge.length; i++) {
			t.addEdge(edge[i]) ;
		}
		return t;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
		Node parent	= addToNodeList(edge.getParent());
		Node child	= addToNodeList(edge.getChild());

		if(root == null || root == child)
			root = parent;

		parent.addChild(child);
	}

	public void buildTree(ArrayList<Edge> Edges) {

		for(Edge e:Edges) {
			addEdge( e) ;
		}
	}

	public Node addToNodeList(int key) {

		Node n = search(key);
		if(n == null) {
			n = new Node(key);
			nodes.add(n);
		}
		return n;
	}

	public Node search(int key) {
		for(Node n : nodes) {
			if(n.getKey() == key)
				return n;
		}
		return null;
	}

	public int getMaxDepth(Node root) {
		
		if(root.getChildren().isEmpty())
			return 1;
		
		int max = 0;
		for(Node n : root.getChildren()) {
			int c = getMaxDepth(n);
			max = (max > c) ? max : c;
		}
		return max + 1;		
	}

	public int getNumberOfNodes() {

		return nodes.size();		
	}

	public String toString() {
		String display = new String();
		
		for(Node n : nodes)
			display += n + "\n";
		
		return display;
	}
}
