package codingTest.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 : 해킹
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/10282
 */
public class DijkStraProblem1 {

    /**
     * 문제
     * - a가 b를 의존했을때 b가 감염시 a가 감염된다.
     * -> 의존시 감염이 되기 때문에, 연관관계를 역순으로 정렬하기 위해 List를 사용하면 좋겠다고 생각
     * - 시간을 계속해서 갱신해나가며 개수와 가장 많이 걸린 시간을 찾아야 함.
     * -> 거리 갱신 문제와 동일. 따라서 다익스트라 알고리즘을 사용하여 번호별 최단거리 배열을 구하고
     * 거리값이 존재하는 것을 감염, 그중 최대값을 구해서 반환하는 방식으로 구현
     */


    public static int MAX = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            List<List<int[]>> computers = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                computers.add(new ArrayList<>());
            }

            for (int i = 0; i < d; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                computers.get(b).add(new int[]{a, time});
            }

            int[] distance = dijkstra(computers, c);

            int infected = 0;
            int maxDist = 0;
            for (int i = 1; i <= n; i++) {
                if (distance[i] != MAX) {
                    infected++;
                    maxDist = Math.max(maxDist, distance[i]);
                }
            }
            sb.append(infected).append(" ").append(maxDist);
            if (t != T - 1) sb.append("\n");
        }

        System.out.println(sb);
    }

    // 메인 - 다익스트라
    public static int[] dijkstra(List<List<int[]>> computers, int start) {
        // 우선순위 큐
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        q.offer(new int[]{start, 0});

        int[] distance = new int[computers.size()];
        Arrays.fill(distance, MAX);
        distance[start] = 0;

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int number = now[0];
            int time = now[1];

            // skip 조건
            if (time != distance[number]) continue;

            // 알고리즘
            List<int[]> links = computers.get(number);
            for (int[] link : links) {
                int nextNo = link[0];
                int nextTime = time + link[1];

                if (distance[nextNo] > nextTime) {
                    q.offer(new int[]{nextNo, nextTime});
                    distance[nextNo] = nextTime;
                }
            }
        }

        return distance;
    }

}
