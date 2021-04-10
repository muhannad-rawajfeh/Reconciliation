package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionsComparatorTest {

    private TransactionsComparator transactionsComparator;

    @BeforeEach
    void setUp() {
        transactionsComparator = new TransactionsComparator();
    }

    @Test
    void givenSourceAndTargetLists_whenFindMatching_thenReturnListOfMatchingTransactionsAndResultsAreRemovedFromGivenLists() {
        List<Transaction> source = FindMatchingCases.prepareSource();
        List<Transaction> target = FindMatchingCases.prepareTarget();

        List<Transaction> result = transactionsComparator.findMatching(source, target);
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

        List<SourcedTransaction> result = transactionsComparator.findMisMatching(source, target);
        List<SourcedTransaction> expected = FindMismatchingCases.prepareExpected();
        assertEquals(expected, result);

        List<Transaction> remainingInSource = FindMismatchingCases.prepareRemainingInSource();
        assertEquals(source, remainingInSource);

        List<Transaction> remainingInTarget = FindMismatchingCases.prepareRemainingInTarget();
        assertEquals(target, remainingInTarget);
    }
}