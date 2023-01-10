package io.github.korzepadawid.hackernewsapi.submission;

import io.github.korzepadawid.hackernewsapi.common.util.UrlUtil;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Url {

    @URL
    @NotBlank
    private String url;

    @NotBlank
    private String urlDomain;

    public Url() {
    }

    public Url(final String url) {
        this.url = url;
        this.urlDomain = UrlUtil.getDomainNameFromUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUrlDomain() {
        return urlDomain;
    }

    public void setUrlDomain(final String urlDomain) {
        this.urlDomain = urlDomain;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Url)) return false;
        final Url url1 = (Url) o;
        return Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
