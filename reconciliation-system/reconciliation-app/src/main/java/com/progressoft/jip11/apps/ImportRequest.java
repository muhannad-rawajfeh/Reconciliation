package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.reconciliators.SourcedTransaction;

import java.util.List;

public class ImportRequest {

    private final List<Transaction> matched;
    private final List<SourcedTransaction> mismatched;
    private final List<SourcedTransaction> missing;

    public ImportRequest(List<Transaction> matched, List<SourcedTransaction> mismatched, List<SourcedTransaction> missing) {
        this.matched = matched;
        this.mismatched = mismatched;
        this.missing = missing;
    }

    public List<Transaction> getMatched() {
        return matched;
    }

    public List<SourcedTransaction> getMismatched() {
        return mismatched;
    }

    public List<SourcedTransaction> getMissing() {
        return missing;
    }
}
