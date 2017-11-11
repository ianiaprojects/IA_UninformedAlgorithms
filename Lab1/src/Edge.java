
@SuppressWarnings("unused")
public class Edge {
	private int parent;
	private int child;
	
	public Edge(int parent, int child) {
		super();
		this.parent = parent;
		this.child  = child;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "Edge [parent=" + parent + ", child=" + child + "]";
	}
}
