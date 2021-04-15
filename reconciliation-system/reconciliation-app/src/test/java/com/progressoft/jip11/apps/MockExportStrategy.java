package com.progressoft.jip11.apps;

import java.util.ArrayList;

class MockExportStrategy implements ExportStrategy {

    private final ArrayList<String> lists = new ArrayList<>();

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        lists.add(exportRequest.getMatched().toString());
        lists.add(exportRequest.getMismatched().toString());
        lists.add(exportRequest.getMissing().toString());
    }

    public ArrayList<String> getLists() {
        return lists;
    }
}
