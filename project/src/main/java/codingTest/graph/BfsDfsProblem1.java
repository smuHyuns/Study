package codingTest.queue;

import java.util.*;

/**
 * 문제 : 네트워크 (프로그래머스)
 * 링크 : * https://school.programmers.co.kr/learn/courses/30/lessons/43162
 */
public class BfsDfsProblem1 {

    class Solution {

        /**
         * 문제분석
         * 1. 양방향 연결이다.
         * 2. computers는 nxn의 배열이다.
         * 3. 본인을 연결한 것은 1로 표기되어있다. 즉 [current][current] 은 반드시 1이다.
         * 4. 그래프 탐색이므로 bfs/dfs를 사용한다. (당연히 성능은 불필요한 방문을 줄이는 queue를 사용하는 bfs가 빠르다)
         */

        public int solution(int n, int[][] computers) {
            int netWork = 0;
            boolean[] visited = new boolean[n];

            // 탐색
            for (int now = 0; now < n; now++) {
                if (visited[now]) continue;

                // 미방문시
                // dfs(computers, visited, now);
                bfs(computers, visited, now);
                netWork++;
            }

            return netWork;
        }


        // stack
        public void dfs(int[][] computers, boolean[] visited, int start) {
            Stack<Integer> stack = new Stack<>();
            int len = visited.length;

            stack.add(start);

            while (!stack.isEmpty()) {
                int now = stack.pop();

                for (int next = 0; next < len; next++) {
                    if (now == next) continue;
                    if (visited[next]) continue;
                    if (computers[now][next] == 0) continue;

                    visited[next] = true;
                    stack.add(next);
                }
            }
        }

        // bfs
        public void bfs(int[][] computers, boolean[] visited, int start) {
            Queue<Integer> q = new LinkedList<>();
            int len = visited.length;

            q.offer(start);

            while (!q.isEmpty()) {
                int now = q.poll();

                for (int next = 0; next < len; next++) {
                    if (now == next) continue;
                    if (visited[next]) continue;
                    if (computers[now][next] == 0) continue;

                    visited[next] = true;
                    q.offer(next);
                }
            }
        }
    }
}
