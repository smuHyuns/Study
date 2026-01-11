package codingTest.implementation;

import java.io.*;
import java.util.*;

/**
 * 문제 : 로봇 청소기 성공
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/14503
 */
public class ImplementationProblem01 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] room = new int[N][M];

        st = new StringTokenizer(br.readLine());
        int startN = Integer.parseInt(st.nextToken());
        int startM = Integer.parseInt(st.nextToken());
        int direction = Integer.parseInt(st.nextToken());

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                room[n][m] = Integer.parseInt(st.nextToken());
            }
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{startN, startM, direction});
        int count = 0;

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int nowN = now[0];
            int nowM = now[1];
            int nowD = now[2];

            // 현재칸이 청소되지 않은 경우, 현재 칸을 청소한다.
            if (room[nowN][nowM] == 0) {
                count++;
                room[nowN][nowM] = 2; // 룸을 청소시 2로 표기
                q.offer(new int[]{nowN, nowM, nowD});
                continue;
            }

            // 현재 칸이 청소된 경우, 주변을 탐색한다.
            // 1. 상하좌우 청소되지 않은 빈칸이 없는 경우
            if (!isNearByCleaned(now, room)) {
                // 2. 바라보는 방향을 유지한 채로 한칸 후진할 수 있다면 후진
                int backD = (nowD + 2) % 4; // 후진 방향(이동 벡터용)
                int[] backMove = getNextMove(nowN, nowM, backD);
                int backN = backMove[0];
                int backM = backMove[1];

                if (isValidMove(backN, backM, room) && room[backN][backM] != 1) {
                    // 방향은 유지해야 하므로 nowD로 다시 넣는다.
                    q.offer(new int[]{backN, backM, nowD});
                } else {
                    // 움직일 수 없는 경우 동작을 멈춘다.
                    break;
                }

                // 후진 처리했으면 이번 턴 종료
                continue;
            }

            // 2. 상하좌우 청소되지 않은 빈칸이 있는 경우
            // 2-1. 반시계기준 회전
            int nextD = nowD == 0 ? 3 : nowD - 1;

            // 2-2. 앞칸 청소되지 않은 빈칸일 경우 한칸 전진
            int[] nextMove = getNextMove(nowN, nowM, nextD);
            int nextN = nextMove[0];
            int nextM = nextMove[1];

            if (isValidMove(nextN, nextM, room) && room[nextN][nextM] == 0) {
                q.offer(nextMove);
            } else {
                // 전진 못하면 회전만 하고 제자리에서 다시 시도해야 함
                q.offer(new int[]{nowN, nowM, nextD});
            }
        }

        System.out.println(count);
    }

    public static int[] getNextMove(int nowN, int nowM, int nowD) {
        int nextN = nowN;
        int nextM = nowM;

        // 0:북, 1:동, 2:남, 3:서
        if (nowD == 0) {
            nextN--;
        } else if (nowD == 1) {
            nextM++;
        } else if (nowD == 2) {
            nextN++;
        } else {
            nextM--;
        }

        return new int[]{nextN, nextM, nowD};
    }

    public static boolean isValidMove(int nowN, int nowM, int[][] room) {
        int N = room.length;
        int M = room[0].length;

        if (nowN >= N || nowN < 0) return false;
        if (nowM >= M || nowM < 0) return false;

        return true;
    }

    public static boolean isNearByCleaned(int[] now, int[][] room) {
        int nowN = now[0];
        int nowM = now[1];

        int[] xrr = {-1, 1, 0, 0};
        int[] yrr = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nextN = nowN + xrr[i];
            int nextM = nowM + yrr[i];
            if (isValidMove(nextN, nextM, room) && room[nextN][nextM] == 0) return true;
        }

        return false;
    }

}
