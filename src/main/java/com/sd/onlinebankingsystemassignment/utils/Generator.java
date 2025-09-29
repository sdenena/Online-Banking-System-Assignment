package com.sd.onlinebankingsystemassignment.utils;

import com.sd.onlinebankingsystemassignment.models.enums.TransactionType;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Generator {
    // Format: ACC-0000000001
    private static final DecimalFormat formatter = new DecimalFormat("0000000000");
    public String REQUEST_UUID = UUID.randomUUID().toString();
    private final Random random = new Random();

    public static String generateAccountNumber(Long number) {
        return "ACC-" + formatter.format(number);
    }

    public static String generateTranId(TransactionType tranType, int sizeOfRandomNumber) {
        String date = DateUtil.convertDateToStr(new Date(), "yyyyMMddHHmmss");
        String rm = date + new Generator().getRandomNumber(sizeOfRandomNumber);

        String prefix = switch (tranType) {
            case TRANSFER -> "ACCT";
            case DEPOSIT -> "ACCD";
            case WITHDRAWAL -> "ACCW";
        };

        return prefix + "-" + rm;
    }

    public String getRandomNumber(int sizeOfRandomNumber) {
        if (sizeOfRandomNumber <= 0) {
            throw new IllegalArgumentException("Size of random number must be greater than 0");
        }

        int minValue = (int) Math.pow(10.0, sizeOfRandomNumber - 1);
        int maxValue = (int) Math.pow(10.0, sizeOfRandomNumber);

        int randomNumber = random.nextInt(maxValue - minValue) + minValue;
        return String.valueOf(randomNumber);
    }
}
