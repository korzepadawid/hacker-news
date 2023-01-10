package io.github.korzepadawid.hackernewsapi.testutil;


import java.util.UUID;

public abstract class Constants {
    public static final String FILENAME = "coolfile";
    public static final String VALID_ORIGINAL_FILE_NAME = "coolfile.png";

    public static final int TOTAL_SUBMISSIONS = 15;
    public static final int NEGATIVE_PAGE_NUMBER = -100;
    public static final int FIRST_PAGE = 1;
    public static final int SECOND_PAGE = 2;
    public static final int ZERO = 0;
    public static final String DIFFERENT_EMAIL = "differentemail@gmail.com";

    public static final String RANDOM_SUBMISSION_ID = UUID.randomUUID().toString();

    public static final String COMMENT_TEXT = "commenting a submission";
    public static final String RANDOM_MAIL = "cool@mailer.com";
    public static final String RANDOM_ID = "jhadfjgsajdfasdf";

    public static final String FAKE_VERIFICATION_TOKEN = "ahsdfgashf";
    public static final String FAKE_STORAGE_KEY = "ahsdfgashf.jpg";
    public static final byte[] MOCK_BYTE_ARRAY = new byte[1];
}
