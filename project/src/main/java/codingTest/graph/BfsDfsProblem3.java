package codingTest.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 문제 : 숨바꼭질 3
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/13549
 */
public class BfsDfsProblem3 {

    /**
     * 문제
     * - 처음엔 prority queue 사용해서 다익스트라로 풀이하였으나
     * 해당 문제는 pq사용이 불필요하게 검사를 증기시킨다. (다익스트라 시간복잡도 O((V+E) + logV))
     * 0-1 BFS 를 사용하면 O(V+E)로 더 간단하게 처리가 가능하여, BFS방식으로 전환하여 다시풀이하였음.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        // 걷기 - 1초뒤 -1 / +1
        // 순간이동 - 0초뒤 2*현재위치

        int[] times = new int[100001];

        Arrays.fill(times, Integer.MAX_VALUE);
        times[start] = 0;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{start, 0});

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int no = now[0];
            int time = now[1];

            if (time > times[no]) continue;

            if (isValid(no * 2)) {
                if (times[no * 2] > time) {
                    times[no * 2] = time;
                    q.offer(new int[]{no * 2, time});
                }
            }
            if (isValid(no + 1)) {
                if (times[no + 1] > time + 1) {
                    times[no + 1] = time + 1;
                    q.offer(new int[]{no + 1, time + 1});
                }
            }
            if (isValid(no - 1)) {
                if (times[no - 1] > time + 1) {
                    times[no - 1] = time + 1;
                    q.offer(new int[]{no - 1, time + 1});
                }
            }
        }

        System.out.println(times[target]);
    }

    public static boolean isValid(int num) {
        return num >= 0 && num <= 100000;
    }
}
