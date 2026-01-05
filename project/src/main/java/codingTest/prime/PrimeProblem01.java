package codingTest.prime;

import java.util.*;
import java.io.*;

/**
 * 문제 : 소수 구하기
 * 플랫폼 : 백준
 * 링크 : https://www.acmicpc.net/problem/1978
 */
public class PrimeProblem01 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int primeCount = 0;

        boolean[] sieve = getPrimeSieve(1000);

        StringTokenizer st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            int input = Integer.parseInt(st.nextToken());
            if (input < 2) continue;
            if (!sieve[input]) primeCount++;
        }

        System.out.println(primeCount);
    }

    /**
     * 에라토스테네스의 체
     * - 해당 체에 걸리지 않은 값(sieve[i] = false)이 소수이다.
     */
    private static boolean[] getPrimeSieve(int N) {
        boolean[] sieve = new boolean[N + 1];

        for (int i = 2; i <= N; i++) {
            if (sieve[i]) continue;

            for (int j = 2 * i; j <= N; j += i) {
                sieve[j] = true;
            }
        }

        return sieve;
    }
}
