package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionsComparator {

    public List<Transaction> findMatching(List<Transaction> source, List<Transaction> target) {
        List<Transaction> result = new ArrayList<>();
        Iterator<Transaction> sourceIterator = source.listIterator();
        while (sourceIterator.hasNext()) {
            Transaction sourceTransaction = sourceIterator.next();
            Iterator<Transaction> targetIterator = target.listIterator();
            while (targetIterator.hasNext()) {
                Transaction targetTransaction = targetIterator.next();
                if (sourceTransaction.equals(targetTransaction)) {
                    result.add(sourceTransaction);
                    sourceIterator.remove();
                    targetIterator.remove();
                }
            }
        }
        return result;
    }

    public List<SourcedTransaction> findMisMatching(List<Transaction> source, List<Transaction> target) {
        List<SourcedTransaction> result = new ArrayList<>();
        Iterator<Transaction> sourceIterator = source.listIterator();
        while (sourceIterator.hasNext()) {
            Transaction sourceTransaction = sourceIterator.next();
            Iterator<Transaction> targetIterator = target.listIterator();
            while (targetIterator.hasNext()) {
                Transaction targetTransaction = targetIterator.next();
                if (isMismatched(sourceTransaction, targetTransaction)) {
                    SourcedTransaction st1 = new SourcedTransaction("SOURCE", sourceTransaction);
                    SourcedTransaction st2 = new SourcedTransaction("TARGET", targetTransaction);
                    result.add(st1);
                    result.add(st2);
                    sourceIterator.remove();
                    targetIterator.remove();
                }
            }
        }
        return result;
    }

    private boolean isMismatched(Transaction sourceTransaction, Transaction targetTransaction) {
        return sourceTransaction.getId().equals(targetTransaction.getId()) && !isEqual(sourceTransaction, targetTransaction);
    }

    private boolean isEqual(Transaction sourceTransaction, Transaction targetTransaction) {
        return sourceTransaction.getAmount().equals(targetTransaction.getAmount())
                && sourceTransaction.getCurrency().equals(targetTransaction.getCurrency())
                && sourceTransaction.getDate().equals(targetTransaction.getDate());
    }
}
