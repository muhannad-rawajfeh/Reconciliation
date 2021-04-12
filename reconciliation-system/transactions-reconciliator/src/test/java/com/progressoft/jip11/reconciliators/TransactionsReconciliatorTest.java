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
    void givenSourceAndTargetLists_whenFindMatching_thenReturnListOfMatchingTransactionsAndResultsAreRemovedFromGivenLists() {
        List<Transaction> source = FindMatchingCases.prepareSource();
        List<Transaction> target = FindMatchingCases.prepareTarget();

        List<Transaction> result = transactionsReconciliator.findMatching(source, target);
        List<Transaction> expected = FindMatchingCases.prepareExpected();
        assertEquals(expected, result);

        List<Transaction> remainingInSource = FindMatchingCases.prepareRemainingInSource();
        assertEquals(source, remainingInSource);

        List<Transaction> remainingInTarget = FindMatchingCases.prepareRemainingInTarget();
        assertEquals(target, remainingInTarget);
    }

    @Test
    void givenSourceAndTargetLists_whenFindMismatching_thenReturnListOfSourcedMismatchingTransactionsAndResultsAreRemovedFromGivenLists() {
        List<Transaction> source = FindMismatchingCases.prepareSource();
        List<Transaction> target = FindMismatchingCases.prepareTarget();

        List<SourcedTransaction> result = transactionsReconciliator.findMismatching(source, target);
        List<SourcedTransaction> expected = FindMismatchingCases.prepareExpected();
        assertEquals(expected, result);

        List<Transaction> remainingInSource = FindMismatchingCases.prepareRemainingInSource();
        assertEquals(source, remainingInSource);

        List<Transaction> remainingInTarget = FindMismatchingCases.prepareRemainingInTarget();
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