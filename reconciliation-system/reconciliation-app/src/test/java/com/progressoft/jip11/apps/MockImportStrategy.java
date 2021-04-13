package com.progressoft.jip11.apps;

import java.util.ArrayList;

class MockImportStrategy implements ImportStrategy {

    private final ArrayList<String> lists = new ArrayList<>();

    @Override
    public void importTransactions(ImportRequest importRequest) {
        lists.add(importRequest.getMatched().toString());
        lists.add(importRequest.getMismatched().toString());
        lists.add(importRequest.getMissing().toString());
    }

    public ArrayList<String> getLists() {
        return lists;
    }
}
