package com.progressoft.jip11.parsers;

import java.util.List;

public interface TransactionsParser {

    List<Transaction> parse(FilePath filePath);
}
