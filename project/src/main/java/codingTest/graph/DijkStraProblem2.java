package codingTest.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 : 최소비용 구하기
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/1916
 */
public class DijkStraProblem2 {

    /**
     * 문제
     * - 문제가 전형적인 다익스트라 문제이다. (단방향으로 연결되어있으며, 거리를 최소화 하는 문제)
     * - 따라서 다익스트라 알고리즘을 사용하였으며, 방문시 최단거리가 보장되어 있지 않기때문에 boolean[] 으로 검사하는 것이 아닌
     * - 거리일치 여부로 확인하였다.
     */


    public static final int MAX = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int cities = Integer.parseInt(br.readLine());
        int buses = Integer.parseInt(br.readLine());

        List<List<int[]>> busMap = new ArrayList<>();
        for (int i = 0; i <= cities; i++) {
            busMap.add(new ArrayList<>());
        }

        for (int i = 0; i < buses; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            busMap.get(from).add(new int[]{to, dist});
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        int[] distMap = dijkstra(busMap, start);

        System.out.println(distMap[target]);
    }

    private static int[] dijkstra(List<List<int[]>> busMap, int start) {
        int[] dist = new int[busMap.size()];
        Arrays.fill(dist, MAX);
        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curNo = cur[0];
            int curDist = cur[1];

            if (dist[curNo] != curDist) continue;

            List<int[]> links = busMap.get(curNo);
            for (int[] link : links) {
                int nextNo = link[0];
                int nextDist = curDist + link[1];

                if (dist[nextNo] > nextDist) {
                    dist[nextNo] = nextDist;
                    pq.offer(new int[]{nextNo, nextDist});
                }
            }
        }

        return dist;
    }

}
