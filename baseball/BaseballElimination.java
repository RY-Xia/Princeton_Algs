/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class BaseballElimination {
        private final int numbers;
        private HashMap<String, Team> teams;
        private HashMap<Integer, Integer> vToId;
        private final int[][] matrix;
        private final String[] idToTeam;
        private int games;
        private int v;
        private int flow;

        private class Team {
            private final int id;
            private final int wins;
            private final int loses;
            private final int res;
            public Team(int id, int wins, int loses, int res) {
                this.id = id;
                this.wins = wins;
                this.loses = loses;
                this.res = res;
            }
        }

        public BaseballElimination(String filename) {
            In in = new In(filename);
            this.numbers = Integer.parseInt(in.readLine());
            teams = new HashMap<>();
            matrix = new int[numbers][numbers];
            idToTeam = new String[numbers];
            vToId = new HashMap<>();

            int id = 0;
            while (in.hasNextLine()) {
                String line = in.readLine().trim();
                String[] tokens = line.split(" +");
                String name = tokens[0];
                int wins = Integer.parseInt(tokens[1]);
                int loses = Integer.parseInt(tokens[2]);
                int res = Integer.parseInt(tokens[3]);
                Team team = new Team(id, wins, loses, res);
                if (!teams.containsKey(name)) {
                    teams.put(name, team);
                    idToTeam[id] = name;

                    for (int i = 0; i < numbers; i++) {
                        matrix[id][i] = Integer.parseInt(tokens[4+i]);
                    }
                    id++;
                }
            }

        }                    // create a baseball division from given filename in format specified below
        public int numberOfTeams() {
            return this.numbers;
        }                        // number of teams
        public Iterable<String> teams() {
            return teams.keySet();
        }                                // all teams
        public int wins(String team) {
            if (team == null) throw new IllegalArgumentException();
            Team t = teams.get(team);
            return t.wins;
        }                      // number of wins for given team
        public int losses(String team) {
            if (team == null) throw new IllegalArgumentException();
            Team t = teams.get(team);
            return t.loses;
        }                   // number of losses for given team
        public int remaining(String team) {
            if (team == null) throw new IllegalArgumentException();
            Team t = teams.get(team);
            return t.res;

        }                 // number of remaining games for given team
        public int against(String team1, String team2) {
            if (team1 == null || team2 == null) throw new IllegalArgumentException();
            int id1 = teams.get(team1).id;
            int id2 = teams.get(team2).id;
            return matrix[id1][id2];
        }   // number of remaining games between team1 and team2
        public boolean isEliminated(String team) {
            if (team == null) throw new IllegalArgumentException();
            FlowNetwork G = constructFlowNetwork(team);
            if (G == null)  // 情形1
                return true;
            else     // 情形2
            {
                FordFulkerson fordFulkerson = new FordFulkerson(G, 0, v-1);
                return flow > fordFulkerson.value();
            }
        }

             // is given team eliminated?

        private FlowNetwork constructFlowNetwork(String team) {
            Team t = teams.get(team);
            games = (numbers-1)*(numbers-2) / 2;
            // 总节点数
            v = games + numbers - 1 + 2;
            // 最大可能胜场
            int winMost = t.wins + t.res;
            int indexMatches = 1; // 用于表示新增加的比赛节点的下标
            int indexI = games; // 用于表示球队节点i的下标
            int indexJ = indexI;   // 用于表示球队节点j的下标
            int s = 0; // 源节点s的下标
            int target = v-1; // 目标节点t的下标
            flow = 0;

            FlowNetwork flowNetwork = new FlowNetwork(v); // 构建flowNetwork类对象
            // i,j分别为Team的id
            for (int i = 0; i < numbers; i++)
            {
                if (i == t.id) // 关于thisID的球队的flownetwork不会出现关于thisId的节点
                    continue;

                indexI++; // 球队节点i的下标
                indexJ = indexI;  // 球队节点j的下标

                if (winMost < wins(idToTeam[i])) // 情景1 thisID的球队的最大可能获胜数 < 球队i的当前获胜数
                    return null;

                for (int j = i + 1; j < numbers; j++)
                {
                    if (j == t.id) // 关于thisID的球队的flownetwork不会出现关于thisId的节点
                        continue;

                    indexJ++;  // 球队节点j的下标
                    flow = flow + matrix[i][j]; // 流
                    flowNetwork.addEdge(new FlowEdge(s, indexMatches, matrix[i][j])); // 新增关于比赛节点到源节点s的边
                    flowNetwork.addEdge(new FlowEdge(indexMatches, indexI, Double.POSITIVE_INFINITY)); // 新增比赛节点到对应的球队节点i之间的边
                    flowNetwork.addEdge(new FlowEdge(indexMatches, indexJ, Double.POSITIVE_INFINITY)); // 新增比赛节点到对应的球队节点j之间的边

                    indexMatches++;
                }

                vToId.put(indexI, i); // 网络下标与球队ID的对应关系
                flowNetwork.addEdge(new FlowEdge(
                        indexI, target, winMost-wins(idToTeam[i]))); // 新增球队节点i到目标节点s之间的边
            }

            return flowNetwork;
        }



        public Iterable<String> certificateOfElimination(String team) {
            if (team == null) throw new IllegalArgumentException();
            if (!isEliminated(team)) return null;
            Queue<String> certificates = new Queue<>();  // 存放被消除子集
            int thisId = teams.get(team).id;  // 当前球队ID
            FlowNetwork flowNetwork = constructFlowNetwork(team); // 构建关于team的flowNetwork

            if (flowNetwork == null) // 情景1
            {
                int thisTeamMostWins = wins(team) + remaining(team); // 当前球队最大可获胜数
                for (int i = 0; i < numbers; i++)
                {
                    if (i == thisId)
                        continue;

                    if (thisTeamMostWins < wins(idToTeam[i]))
                        certificates.enqueue(idToTeam[i]); // 入栈
                }
            }
            else // 情景2
            {
                FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, v-1);
                for (int i = 1 + games; i < v; i++) // 球队节点
                {
                    if (fordFulkerson.inCut(i))
                    {
                        int id = this.vToId.get(i); // 网络下标为i的球队的id
                        certificates.enqueue(idToTeam[id]);
                    }
                }
            }
            return certificates;

        } // subset R of teams that eliminates given team; null if not eliminated
        public static void main(String[] args) {

        }

    }

