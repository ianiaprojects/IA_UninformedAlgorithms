import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Stefania
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private ArrayList<Node> nodesOrder = new ArrayList<Node>();
	private String[] currentState;
	private GraphX applet;
	private GraphX model;

	// Frame properties.
	private String frameTitle = "Uninformed Search Algorithms (Blind Search)";
	private final Dimension minFrameDimension = new Dimension(1000, 500);

	private Tree tree;
	// Frame panels.
	private JPanel topPanel;		
	private Label topPanelText;
	private JPanel middlePanel;
	private JPanel bottomPanel;

	// Frame components.
	private JButton   nextButton;
	private JTextField textField;
	private JComboBox<?> algorithmsComboBox;

	// Components properties.
	private final Dimension buttonDimension = new Dimension(100,25);
	private final Dimension comboBoxDimension = new Dimension(200, 25);

	// Iterate through list
	private int MaxDepth = 0;
	private int curentIndex = 0;

	private static final  String[]  algorithms = {
			"Breadth First Search",
			"Depth First Search",
			"Depth Limited Search"/*,
			"Iterative Deepening Search"*/
	};

	//------------------------------------------//
	//			 Breadth First Search	        //
	//------------------------------------------//
	public void bfs(ArrayList<Node> nodeList) {
		if(nodeList.isEmpty())
			return;

		Node n = nodeList.remove(0);
		nodeList.addAll(n.getChildren());
		nodesOrder.add(n);

		bfs(nodeList);
	}

	public void bfsAlgorithm() {
		ArrayList<Node> l = new ArrayList<Node>();
		l.add(tree.getRoot());

		bfs(l);
	}

	//------------------------------------------//
	//			  Depth First Search	      	//
	//------------------------------------------//
	public void dfs(Node root) {
		nodesOrder.add(root);

		for(Node n : root.getChildren()) {
			dfs(n);
		}
	}

	//------------------------------------------//
	//			Depth Limited Search	      	//
	//------------------------------------------//
	public void dls(Node root, int depth) {
		if(depth == 0)
			return;

		nodesOrder.add(root);
		for(Node n : root.getChildren()) {
			dls(n, depth-1);
		}
	}

	//------------------------------------------//
	//		  Iterative Deepening Search      	//
	//------------------------------------------//
	/*
	public void ids() {
		int maxDepth = tree.getMaxDepth(tree.getRoot());
		for(int i = 1; i <= maxDepth; i++)
			dls(tree.getRoot(),i);
	}
	*/
	
	//-------------------------------------------//
	// 		Frame Updates: ComboBox & Nodes		 //
	//-------------------------------------------//
	
	public void updateTree(String[] g) {
		GraphX updatedApplet = new GraphX(g, applet.getEdge(), "tree");
		updatedApplet.init();

		middlePanel.removeAll();
		middlePanel.add(updatedApplet);

		SwingUtilities.updateComponentTreeUI(this);
	}

	public void comboBoxUpdate(String currentAlgorithm) {
		init();
		
		switch(currentAlgorithm) {
					case "Breadth First Search":
						topPanelText.setText("Breadth First Search");
						bfsAlgorithm();
						break;
						
					case "Depth First Search":
						topPanelText.setText("Depth First Search");
						dfs(tree.getRoot());
						break;
						
					case "Depth Limited Search":
						topPanelText.setText("Depth Limited Search");
						MaxDepth = Integer.parseInt(textField.getText());
						dls(tree.getRoot(),MaxDepth);
						break;
					
					/*
					case "Iterative Deepening Search":
						ids();
						break;
					*/
					default:break;
		}
		updateTree(currentState);
	}

	public void init() {
		nodesOrder.clear();

		for(int i = 0; i <currentState.length; i++ )
			currentState[i] = "";
		curentIndex = 0;
	}

	public void init(Tree tree) {

		Edge[] s = new  Edge[tree.edges.size()];
		tree.edges.toArray(s);

		currentState = new String[tree.getNumberOfNodes()];
		for(int i = 0; i < tree.getNumberOfNodes();i++)
			currentState[i] = "";

		GraphX applet = new GraphX(currentState, s, "tree");
		GraphX model = new GraphX(currentState, s, "graph");
		this.tree = tree;
		this.applet = applet;
		applet.init();

		// Set frame properties.
		setSize(minFrameDimension);
		setMinimumSize(minFrameDimension);

		setLocationRelativeTo(null);
		setTitle(frameTitle);

		// Organize panels.
		//====================top panel=======================
		topPanel = new JPanel();
		//topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		topPanelText = new Label();
		Font font = new Font("LucidaSans", Font.PLAIN, 20);
		topPanelText.setFont(font);
		topPanelText.setText("Blind Search Algorithms");

		topPanel.add(topPanelText);

		//=====================middle panel=====================
		middlePanel = new JPanel();
		//middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		middlePanel.add(model);
		middlePanel.add(applet);

		//=====================bottom panel====================
		bottomPanel = new JPanel();
		//bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		nextButton = new JButton();
		nextButton.setPreferredSize(buttonDimension);
		nextButton.setText("Next");
		bottomPanel.add(nextButton);

		algorithmsComboBox = new JComboBox<Object>(algorithms);
		algorithmsComboBox.setPreferredSize(comboBoxDimension);
		bottomPanel.add(algorithmsComboBox);

		JLabel levelLabel = new JLabel();
		levelLabel.setText("Level:");
		bottomPanel.add(levelLabel);

		textField = new JTextField("", 20);
		textField.setText(0 + "");

		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				String algorithm = algorithmsComboBox.getSelectedItem().toString();
				if(algorithm == algorithms[2]) // If current algorithm == dls algorithm
					comboBoxUpdate(algorithm);

			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				String algorithm = algorithmsComboBox.getSelectedItem().toString();
				if(algorithm == algorithms[2])
					comboBoxUpdate(algorithm);

			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {


			}
		});

		bottomPanel.add(textField);


		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		bfsAlgorithm();

		//===================Action listeners======================
		algorithmsComboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String algorithm = algorithmsComboBox.getSelectedItem().toString();
				comboBoxUpdate(algorithm);
			}
		});

		nextButton.addActionListener((ActionEvent event) -> {
			if(curentIndex<currentState.length && curentIndex<nodesOrder.size()) {

				int index = nodesOrder.get(curentIndex).getKey();
				currentState[index] = index + "";
				curentIndex++;
				updateTree(currentState);}
		});

	}

	public int getLevel() {
		return Integer.parseInt(textField.getText());
	}

}
