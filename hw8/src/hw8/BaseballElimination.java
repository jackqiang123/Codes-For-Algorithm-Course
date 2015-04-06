package hw8;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.introcs.*;
import edu.princeton.cs.introcs.StdOut;

public class BaseballElimination {
	private int N; // number of teams;
	private String[] teamname; // to store team name
	private int[] w; // to store the winning game number
	private int[] l; // to store the lost game number
	private int[] r; // to store the remaining number
	private int[][] g; // to store the matching matrix
	private final double maxcapa = Double.POSITIVE_INFINITY;
	private FlowNetwork G;

	public BaseballElimination(String filename) // create a baseball division
												// from given filename in format
												// specified below
	{
		In in = new In(filename);
		this.N = in.readInt();
		teamname = new String[this.N];
		w = new int[this.N];
		l = new int[this.N];
		r = new int[this.N];
		g = new int[this.N][this.N];

		for (int i = 0; i < N; i++) {
			teamname[i] = in.readString();
			w[i] = in.readInt();
			l[i] = in.readInt();
			r[i] = in.readInt();
			for (int j = 0; j < this.N; j++) {
				g[i][j] = in.readInt();
			}
		}

	}

	private int maxwinindex() {
		int index = 0;
		int maxwin = w[0];
		for (int i = 1; i < N; i++)
			if (w[i] > maxwin) {
				maxwin = w[i];
				index = i;
			}
		return index;
	}

	private FlowNetwork constructnetwork() {
		// construct the flow network G (partial), we will finish it later in
		// the APIs.
		int gamen = N * (N - 1) / 2; // number of games, the game is from 1 to
		int vn = 2 + N + gamen; // number of vetics in the flow network, thus
								// the node index is from 0 to vn-1;
		FlowNetwork inital = new FlowNetwork(vn); // let 0 be s, and vn-1 be the
													// destination
		int k = 1; // for connect use
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++) {
				inital.addEdge(new FlowEdge(0, k, g[i][j])); // connect from
																// source to the
																// game nodes
				inital.addEdge(new FlowEdge(k, gamen + i + 1, maxcapa)); // connect
																			// gamendoe
																			// to
																			// node
																			// i
				inital.addEdge(new FlowEdge(k, gamen + j + 1, maxcapa));
				k++;
			}
		return inital;
	}

	public int numberOfTeams() // number of teams
	{
		return this.N;
	}

	public Iterable<String> teams() // all teams
	{
		Queue<String> team = new Queue<String>();
		for (int i = 0; i < N; i++)
			team.enqueue(teamname[i]);
		return team;
	}

	public int wins(String team) // number of wins for given team
	{
		int index = findindex(team);
		return w[index];
	}

	public int losses(String team) // number of losses for given team
	{
		int index = findindex(team);
		return l[index];
	}

	public int remaining(String team) // number of remaining games for given
										// team
	{
		int index = findindex(team);
		return r[index];
	}

	public int against(String team1, String team2) // number of remaining games
													// between team1 and team2
	{
		int index1 = findindex(team1);
		int index2 = findindex(team2);
		return g[index1][index2];
	}

	public boolean isEliminated(String team) // is given team eliminated?
	{
		int index = findindex(team);
		int mostwinteam = maxwinindex();
		if (w[mostwinteam] > w[index] + r[index])
			return true;
		G = constructnetwork();
		int gamen = N * (N - 1) / 2; // number of games, the game is from 1 to
		int vn = 2 + N + gamen; // number of vetics in the flow network, thus
								// the node index is from 0 to vn-1;

		for (int i = 0; i < N; i++) {
			if (w[index] + r[index] - w[i] > 0) // only add edge when it is with
												// positive capacity
				G.addEdge(new FlowEdge(gamen + 1 + i, vn - 1, w[index]
						+ r[index] - w[i])); // connect team vertices to t
		}

		FordFulkerson solver = new FordFulkerson(G, 0, vn - 1);

		for (FlowEdge e : G.adj(0)) {
			if (e.capacity() > e.flow())
				return true;
		}
		return false;
	}

	public Iterable<String> certificateOfElimination(String team) // subset R of
																	// teams
																	// that
																	// eliminates
																	// given
																	// team;
																	// null if
																	// not
																	// eliminated
	{

		Queue<String> result = new Queue<String>();

		int index = findindex(team);
		int mostwinteam = maxwinindex();
		if (w[mostwinteam] > w[index] + r[index]) {
			result.enqueue(teamname[mostwinteam]);
			return result;
		}
		G = constructnetwork();

		int gamen = N * (N - 1) / 2; // number of games, the game is from 1 to
		int vn = 2 + N + gamen; // number of vetics in the flow network, thus
								// the node index is from 0 to vn-1;

		for (int i = 0; i < N; i++) {
			if (w[index] + r[index] - w[i] > 0) // only add edge when it is with
												// positive capacity
				G.addEdge(new FlowEdge(gamen + 1 + i, vn - 1, w[index]
						+ r[index] - w[i])); // connect team vertices to t
		}

		FordFulkerson solver = new FordFulkerson(G, 0, vn - 1);
		boolean flag = false;
		for (int i = 0; i < N; i++) {
			if (solver.inCut(gamen + i + 1)) {
				flag = true;
				result.enqueue(teamname[i]);
			}
		}
		if (flag == true)
			return result;
		return null;

	}

	private int findindex(String team) {
		int index = -1;
		for (int i = 0; i < this.N; i++) {
			if (team.compareTo(teamname[i]) == 0)
				index = i;
		}
		if (index == -1)
			throw new java.lang.IllegalArgumentException("no such team");

		return index;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}
