//package BellmanFord;

/*
 * @author : Vaishakhi Kulkarni
 * 
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import Graph;

public class BellmanFord {

	/*
	 * Method to perform Bellman Ford operation
	 * Parameter passed is a Graph g
	 * Queue is used to store in the queue 
	 */
	public static boolean BellmanFordM(Graph g){
		
		//Store the Vertex in the Queue
		Queue<Graph.Vertex> queue = new LinkedList<Graph.Vertex>();
		
		//t Vertex g.V[1] as source
		Graph.Vertex s = g.V[1];
		queue.add(s);
		
		//Perform Initialization
		Initialize(g, s);
		
		//Set s.inQ as true
		s.inQ = true;
		
		//Perform until the queue is empty
		while (!queue.isEmpty()) {
			
			Graph.Vertex u = queue.remove();
			
			u.inQ = false;
			u.count++;
			
			if (u.count >= g.V.length) {	//If the count is greater than number of vertices then it has negative cycle
				
				System.out.println("Negative cycle exist");
				return false;// return false
			}

			//Calculate the shortest distance
			for (Graph.Edge e : u.Adj) {
				Graph.Vertex v = e.otherEnd(u);
				if (Relax(u, v) && !v.inQ) {
					queue.add(v);
					v.inQ = true;
				}
			}
		}
		return true;	//returns true as it is a non negative graph

	}

	/*
	 * Method to perform Relax operation 
	 * Set minimum weight for vertex v
	 */
	public static boolean Relax(Graph.Vertex u, Graph.Vertex v) {
		
		Graph.Edge edge = null;

		for (Graph.Edge e : u.Adj) {
			
			if (v == e.otherEnd(u)) {
				
				edge = e;
			}
		}

		//Find mimimum distance
		if (v.distance > u.distance + edge.Weight) {
			
			v.distance = u.distance + edge.Weight;
			v.parent = u;
			
			return true;
			
		} else
			return false;
	}

	/*
	 *Method to perform Initialization
	 *Parameters passed are Graph g and Source vertex s 
	 */
	public static void Initialize(Graph g, Graph.Vertex s) {
		
		for (Graph.Vertex u : g) {
			// Set u.disanc to infinity,u.count = 0 and u.InQ is false
			//u.inQ used to check whether the vertex is in queue
			if (u != s) {
				u.distance = Integer.MAX_VALUE;
				u.count = 0;
				u.inQ = false;
			}
		}
		//Set s.distance to zero
		s.distance = 0;
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		// Take input from Scanner
		Scanner in = new Scanner(new File(args[0]));
		
		// Read the graph
		Graph g = Graph.readGraph(in);
		
		// Print the graph
		g.printGraph();
		
		// To calculate running time complexity
		long startTime = System.currentTimeMillis();
		System.out.println("-----> Bellman Ford <-----");
		
		// BellmanFord having Graph g as input
		if(BellmanFordM(g))
			System.out.println("Specified Graph is a DAG and no negative cycles are present");
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		
		// Total Time complexity
		System.out.println("Time is:" + elapsedTime);
	}
}
