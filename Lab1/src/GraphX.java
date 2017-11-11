import java.awt.Insets;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;


@SuppressWarnings("serial")
public class GraphX extends JApplet
{
	private static int MARGIN_WIDTH   = 0;
	private static int MARGIN_HEIGHT  = 0;
	private static int NODE_DIAMETER  = 50;
	private static String NODE_SHAPE  = "shape=ellipse";
	private static String GRAPH_STYLE = "graph";
	
	private String[] vertexes;
	private Edge[]	edge;
	private String style;
	
	public GraphX(String[] vertexes, Edge[] edge, String style) {
		this.vertexes = vertexes;
		this.edge = edge;
		this.style = style;
		init();
	}
	
	public void init() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		Map<String, Object> edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
		edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);

		graph.getModel().beginUpdate();
		try
		{
			/**
			 * OBS:	When creating nodes you only have to change the string ID. 
			 * 		Here you also set the Node ID which is displayed.
			 * 
			 * 		All nodes must be linked by default to a vitual parent.
			 * 		The actual link is done when you connect the nodes (set up the edges).
			 */

			// Create nodes
			Object[] vertex = new Object[vertexes.length];
			for(int i = 0; i < vertexes.length; i++)
				vertex[i] = graph.insertVertex(parent, null, vertexes[i], MARGIN_WIDTH, MARGIN_HEIGHT, NODE_DIAMETER, NODE_DIAMETER, NODE_SHAPE);

			// Connect nodes
			mxICell[] edges = new mxICell[edge.length];
			for(int i = 0; i < edge.length; i++)
				edges[i] = ((mxICell)(graph.insertEdge(parent, "", "", vertex[edge[i].getParent()], vertex[edge[i].getChild()])));

			// Layout
			mxIGraphLayout layout;
			if(this.style.equals(GRAPH_STYLE))
				layout = new mxFastOrganicLayout(graph);
			else
				layout = new ExtendedCompactTreeLayout(graph);
			layout.execute(parent);

		} finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}// End initialisation

	@Override
	public Insets getInsets()
	{
		return new Insets(10,10,10,10);
	}

	@SuppressWarnings("static-access")
	public class ExtendedCompactTreeLayout extends mxCompactTreeLayout {

		public ExtendedCompactTreeLayout(mxGraph graph) {
			super(graph, false);
			super.prefVertEdgeOff = 0;
		}

		@Override 
		public void execute(Object parent)
		{

			// CompactTreeLayout.
			super.execute(parent);

			// Check vertex exit.
			if(!horizontal) {

				// Collect all the vertexes from the previous created model, virtual parent included.
				Object[] vertexes = ((mxGraphModel)graph.getModel()).getChildVertices(graph.getModel(), graph.getDefaultParent());

				for(int i = 0; i < vertexes.length; i++) {

					mxICell parentCell = ((mxICell)(vertexes[i]));
					
					for(int j = 0; j < parentCell.getEdgeCount(); j++) {

						mxICell edge = parentCell.getEdgeAt(j);
						if(edge.getTerminal(true) != parentCell) {
							continue;
						}

						mxRectangle parentBounds = getVertexBounds(parentCell);
						List<mxPoint> edgePoints = edge.getGeometry().getPoints();

						mxPoint outPort = edgePoints.get(0);
						mxPoint elbowPoint = edgePoints.get(1);
						if(outPort.getX() != parentBounds.getCenterX()) {

							outPort.setX(parentBounds.getCenterX());
							elbowPoint.setX(parentBounds.getCenterX());
						}
					}
				}
			}            
		}
	} // End custom layout

	public String[] getVertexes() {
		return vertexes;
	}

	public void setVertexes(String[] vertexes) {
		this.vertexes = vertexes;
	}

	public Edge[] getEdge() {
		return edge;
	}

	public void setEdge(Edge[] edge) {
		this.edge = edge;
	}
}