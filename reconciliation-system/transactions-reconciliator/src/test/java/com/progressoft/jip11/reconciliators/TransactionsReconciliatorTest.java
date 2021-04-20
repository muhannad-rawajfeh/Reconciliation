package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionsReconciliatorTest {

    private TransactionsReconciliator transactionsReconciliator;

    @BeforeEach
    void setUp() {
        transactionsReconciliator = new TransactionsReconciliator();
    }

    @Test
    void givenSourceAndTargetLists_whenFindMatched_thenReturnListOfMatchedTransactionsAndResultsAreRemovedFromGivenLists() {
        List<Transaction> source = FindMatchedCases.prepareSource();
        List<Transaction> target = FindMatchedCases.prepareTarget();

        List<Transaction> result = transactionsReconciliator.findMatched(source, target);
        List<Transaction> expected = FindMatchedCases.prepareExpected();
        assertEquals(expected, result);

        List<Transaction> remainingInSource = FindMatchedCases.prepareRemainingInSource();
        assertEquals(source, remainingInSource);

        List<Transaction> remainingInTarget = FindMatchedCases.prepareRemainingInTarget();
        assertEquals(target, remainingInTarget);
    }

    @Test
    void givenSourceAndTargetLists_whenFindMismatched_thenReturnListOfSourcedMismatchedTransactionsAndResultsAreRemovedFromGivenLists() {
        List<Transaction> source = FindMismatchedCases.prepareSource();
        List<Transaction> target = FindMismatchedCases.prepareTarget();

        List<SourcedTransaction> result = transactionsReconciliator.findMismatched(source, target);
        List<SourcedTransaction> expected = FindMismatchedCases.prepareExpected();
        assertEquals(expected, result);

        List<Transaction> remainingInSource = FindMismatchedCases.prepareRemainingInSource();
        assertEquals(source, remainingInSource);

        List<Transaction> remainingInTarget = FindMismatchedCases.prepareRemainingInTarget();
        assertEquals(target, remainingInTarget);
    }

    @Test
    void givenSourceAndTargetLists_whenWrapMissing_thenReturnListOfSourcedTransactions() {
        List<Transaction> source = WrapMissingCases.prepareSource();
        List<Transaction> target = WrapMissingCases.prepareTarget();

        List<SourcedTransaction> result = transactionsReconciliator.wrapMissing(source, target);
        List<SourcedTransaction> expected = WrapMissingCases.prepareExpected();
        assertEquals(expected, result);
    }
}