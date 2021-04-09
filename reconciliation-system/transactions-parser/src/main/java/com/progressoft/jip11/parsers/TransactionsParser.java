package com.progressoft.jip11.parsers;

public interface TransactionsParser {

    Iterable<Transaction> parse(ValidPath validPath);
}
