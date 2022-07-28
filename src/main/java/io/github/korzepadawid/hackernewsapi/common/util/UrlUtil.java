package io.github.korzepadawid.hackernewsapi.common.util;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsError;
import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import org.apache.commons.validator.routines.DomainValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

    private static final Pattern MATCH_DOMAIN_REGEX = Pattern.compile("^(?:https?://)?(?:[^@/\\n]+@)?(?:www\\.)?([^:/?\\n]+)");
    private static final int DOMAIN_AND_SUBDOMAIN_REGEX_GROUP = 1;

    public static String getDomainNameFromUrl(final String url) {
        final Matcher matcher = MATCH_DOMAIN_REGEX.matcher(url);
        if (matcher.find()) {
            final String domain = matcher.group(DOMAIN_AND_SUBDOMAIN_REGEX_GROUP);
            validateResult(domain);
            return domain;
        }
        throw new HackerNewsException(HackerNewsError.URL_PARSER_ERROR);
    }

    private static void validateResult(final String domain) {
        final DomainValidator domainValidator = DomainValidator.getInstance();
        if (!domainValidator.isValid(domain)) {
            throw new HackerNewsException(HackerNewsError.URL_PARSER_ERROR);
        }
    }
}
