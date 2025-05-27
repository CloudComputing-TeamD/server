package com.project.cloud.domain.user.enumerate;

public enum Frequency {
    ONE_TO_TWO, THREE_TO_FOUR, FIVE_TO_SIX, DAILY;

    public static Frequency fromCount(int frequency) {
        if (frequency == 1 || frequency == 2) {
            return ONE_TO_TWO;
        }
        if (frequency == 3 || frequency == 4) {
            return THREE_TO_FOUR;
        }
        if (frequency == 5 || frequency == 6) {
            return FIVE_TO_SIX;
        }
        if (frequency == 7) {
            return DAILY;
        }
        throw new IllegalArgumentException("올바르지 않은 빈도수 값입니다.");
    }
}
