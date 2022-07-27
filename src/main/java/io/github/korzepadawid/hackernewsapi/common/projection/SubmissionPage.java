package io.github.korzepadawid.hackernewsapi.common.projection;

import java.util.List;

public class SubmissionPage {

    private Long totalElements;
    private Integer currentPage;
    private Integer itemsPerPage;
    private List<SubmissionRead> submissionReads;

    public SubmissionPage() {
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(final Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(final Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<SubmissionRead> getSubmissionReads() {
        return submissionReads;
    }

    public void setSubmissionReads(final List<SubmissionRead> submissionReads) {
        this.submissionReads = submissionReads;
    }
}
