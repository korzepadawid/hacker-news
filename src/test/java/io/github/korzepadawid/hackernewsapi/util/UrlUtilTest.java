package io.github.korzepadawid.hackernewsapi.util;

import io.github.korzepadawid.hackernewsapi.common.exception.HackerNewsException;
import io.github.korzepadawid.hackernewsapi.common.util.UrlUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class UrlUtilTest {

    @Test
    void shouldReturnSubdomainWhenSubdomain() {
        final String have = "https://play.google.com/store/apps/details?";
        final String expected = "play.google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnDomainWhenNoSubdomains() {
        final String have = "https://google.com/store/apps/details?";
        final String expected = "google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnDomainWhenHttpInsteadOfHttps() {
        final String have = "http://google.com/store/apps/details?";
        final String expected = "google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnSubDomainWhenHttpInsteadOfHttps() {
        final String have = "http://play.google.com/store/apps/details?";
        final String expected = "play.google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnSubDomainWhenWwwUsedWithHttp() {
        final String have = "http://www.play.google.com/store/apps/details?";
        final String expected = "play.google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnSubDomainWhenWwwUsedWithHttps() {
        final String have = "https://www.play.google.com/store/apps/details?";
        final String expected = "play.google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldReturnSubDomainWhenOnlyWwwUsed() {
        final String have = "www.play.google.com/store/apps/details?";
        final String expected = "play.google.com";

        final String got = UrlUtil.getDomainNameFromUrl(have);

        assertThat(got).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionWhenInvalidUrl() {
        final String have = "store/apps/details?";

        final Throwable throwable = catchThrowable(() -> UrlUtil.getDomainNameFromUrl(have));

        assertThat(throwable).isInstanceOf(HackerNewsException.class);
    }
}