package com.progressoft.jip11.parsers;

import java.nio.file.Path;

public interface TransactionsParser {

    Iterable<Transaction> parse(Path path);
}
