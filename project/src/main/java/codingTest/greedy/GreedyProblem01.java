package codingTest.greedy;

import java.io.*;
import java.util.*;


/**
 * 문제 : 회의실 배정
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/1931
 */
public class GreedyProblem01 {

    /**
     * 문제분석
     * 1. 조건확인
     * 시작시간과 종료시간이 함께주어진다.
     * 또한, 시작 시간 = 종료 시간 인 경우 바로 진행이 가능하다.
     * 2. 해당 문제는 일정 구간마다 최적의 선택이 주어져야 한다. (같은 시간 내 최다 선택)
     * 따라서, 그리디 알고리즘이 적합하다 판단하였다.
     * 3. 그리디 알고리즘을 사용하기 이전에, 데이터를 문제풀이에 용이하게 가공해야한다.
     * 조건은
     * - 종료시간은 가장 가까운 것부터 검사
     * - 종료시간 우선적으로 정렬, 이후에는 시작시간 순으로 정렬 ( 가장 짧은 문제부터 선택하기 위함 )
     * 4. 진행시간을 갱신할때마다 값을 증가시킨다.
     */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        List<int[]> meetings = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            meetings.add(new int[]{start, end});
        }

        meetings.sort((a, b) -> {
            int c = Integer.compare(a[1], b[1]);
            return (c != 0) ? c : Integer.compare(a[0], b[0]);
        });

        int count = 0;
        int endTime = 0;

        for (int i = 0; i < N; i++) {
            int[] meeting = meetings.get(i);
            int start = meeting[0];
            int end = meeting[1];

            if (start >= endTime) {
                count++;
                endTime = end;
            }
        }

        System.out.println(count);
    }
}
