package codingTest.queue;

import java.util.*;


/**
 * 문제 : 서버 증설 횟수
 * 플랫폼 : 프로그래머스
 * 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/389479
 */
public class QProblem2 {

    /**
     * 문제 분석
     * 해당 문제풀이에 있어서 가장 중요한 부분은 2가지다.
     * 1. 서버의 생명 주기 관리
     * 2. 현재 인원을 수용하기 위한 서버가 충분한지 판단하는것
     *
     * 서버는 증설시 쌓이는 형태이며, 선입 선출이어야 하므로 큐를 사용하였다.
     * 우선순위 큐를 사용할까 했지만 종료시간을 기준으로 넣게 되면 정렬을 하지 않더라도
     * 오름차순 순서로 정렬되게 되므로 단순 큐를 사용하였다.
     *
     * 검사전 큐의 최상단을 검사하고, 이후 검사하는 방식으로 문제를 풀이하였다.
     */
    class Solution {
        public int solution(int[] players, int m, int k) {
            Queue<Integer> q = new LinkedList<>();
            int server = 0;
            int count = 0;

            for (int time = 0; time < 24; time++) {
                int player = players[time];

                // 만료가 된 서버 증발
                while (!q.isEmpty() && q.peek() <= time) {
                    q.poll();
                    server--;
                }

                // 필요시 서버 추가
                while (isNeedServer(player, server, m)) {
                    server++;
                    count++;
                    q.offer(time + k);
                }
            }

            return count;
        }

        private static boolean isNeedServer(int player, int server, int m) {
            if (player < m) return false; // 증설 필요 없음
            return server * m <= player - m;
        }
    }
}
