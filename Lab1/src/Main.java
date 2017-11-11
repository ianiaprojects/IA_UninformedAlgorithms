/**
 * 
 * @author Stefania
 *
 */

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Edge[] edge = new Edge[9];
		
		edge[0] = new Edge(0, 1);
		edge[1] = new Edge(0, 2);
		edge[2] = new Edge(0, 3);
		edge[3] = new Edge(1, 4);
		edge[4] = new Edge(1, 5);
		edge[5] = new Edge(1, 6);
		edge[6] = new Edge(3, 7);
		edge[7] = new Edge(3, 8);
		edge[8] = new Edge(4, 9);

		Tree tree = Tree.createTree(edge);
		
        MainFrame frame = new MainFrame();
		frame.init(tree);
	
		System.out.println(tree);
    }

}
