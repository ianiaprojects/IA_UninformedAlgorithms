import java.util.ArrayList;

/**
 * 
 * @author Stefania
 *
 */

public class Node {

	private int key;
	private ArrayList<Node> children;

	public Node(int key) {
		this.key = key;
		this.children=new ArrayList<Node>();
	}

	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<Node> children) {
		if(children != null)
			this.children = children;
	}

	public void addChild(Node child) {
		if(child != null)
			this.children.add(child);
	}
	
	public String toString() {
		String display = key + ":";
		
		for(Node child : this.children)
			display += child.key + " ";
		
		return display;
	}
}
