package codingTest.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 : 숨바꼭질
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/1697
 */
public class BfsDfsProblem2 {

    /**
     * 범위는 0~100000
     * 수빈 : n / 동생 : k
     * 움직일수 있는것은 수빈이만임.
     * 걷는것은 x+1 혹은 x-1 / 순간이동을 하는경우는 2*x
     * q에 한번 넣을때마다 위치/이동횟수+1 한 값을 넣어서 탐색시킨후
     * 발견시 응답하게 되면 최단 거리 이동 발견이 가능하다.
     */

    private static final int MAX = 100000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // 수빈
        int k = Integer.parseInt(st.nextToken()); // 동생

        solution(n, k);
    }

    public static void solution(int start, int target) {
        // 뒤로가는건 -1 뿐
        if (start >= target) {
            System.out.println(start - target);
            return;
        }

        // 탐색
        Queue<int[]> q = new LinkedList<>();
        boolean[] visited = new boolean[MAX + 1];

        q.offer(new int[]{start, 0}); // 현재위치 / 이동횟수
        while (!q.isEmpty()) {
            int[] current = q.poll();

            int nowIdx = current[0];
            int moveCount = current[1];

            visited[nowIdx] = true;

            // 발견시 종료
            if (nowIdx == target) {
                System.out.println(moveCount);
                return;
            }

            // 앞, 뒤, 순간이동(*2)
            int plus = nowIdx + 1;
            int minus = nowIdx - 1;
            int multi = nowIdx * 2;
            int nextMoveCount = moveCount + 1;

            if (isValidMove(plus) && !visited[plus]) {
                q.offer(new int[]{plus, nextMoveCount});
            }
            if (isValidMove(minus) && !visited[minus]) {
                q.offer(new int[]{minus, nextMoveCount});
            }
            if (isValidMove(multi) && !visited[multi]) {
                q.offer(new int[]{multi, nextMoveCount});
            }
        }
    }

    public static boolean isValidMove(int next) {
        return 0 <= next && next <= MAX;
    }
}
