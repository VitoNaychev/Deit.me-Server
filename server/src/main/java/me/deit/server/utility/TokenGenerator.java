package me.deit.server.utility;

import java.util.Random;

public class TokenGenerator {
    private static final int  UPPER_BOND = 62; /* 26 lower case letters, 26 upper case and 10 numbers */
    private static final int LOWER_BOUNDARY = 0;
    private static final int UPPER_BOUNDARY = 26;
    private static final int NUMBER_BOUNDARY = 52;

    private static Random rng = new Random();

    public static String generateToken(int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0 ; i < length; ++ i) {
            int rand = rng.nextInt(UPPER_BOND);

            if (rand < 26) {
                stringBuilder.append((char) ('a' + (rand - LOWER_BOUNDARY)));
            } else if (rand < 52) {
                stringBuilder.append((char) ('A' + (rand - UPPER_BOUNDARY)));
            } else {
                stringBuilder.append((char) ('0' + (rand - NUMBER_BOUNDARY)));
            }
        }

        return stringBuilder.toString();
    }

}
