# Reconciliation system

#### Table of content

- [Goals](#goals)
- [Introduction to the system](#introduction-to-the-system)
- [File formats](#file-formats)
    - [CSV file format](#csv-file-format)
    - [JSON file format](#json-file-format)
    - [Result file format](#result-file-format)
        - [Matching Transactions file](#matching-transactions-file)
        - [Mismatching Transactions file](#mismatching-transactions-file)
        - [Missing Transactions file](#missing-transactions-file)
- [Requirements](#requirements)
    - [Transactions Comparison](#transactions-comparison)
    - [Result](#result)
- [General considerations and tips](#general-considerations-and-tips)

## Goals

During this lab you will:

1. Practice TDD
1. Work with IO/NIO Java API
1. Practice Reading and Writing system files
1. Practice Parsing CSV and JSON formats
1. Apply OOP SOLID principles
1. Use Dependency Injection and applying Design Patterns (like Strategy and DAO patterns)
1. Practice separation of software components packages by introducing multiple modules

## Introduction to the system

In accounting, *reconciliation* is the process of ensuring that two sets of records (financial transactions) matches
each other. Reconciliation is used to validate that different financial systems recorded the same financial
transactions.

A reconciliation system needs to be flexible enough to be able to process transactions generated by any financial
system (like, Core Banking Systems, Online Payment Systems, Checks Clearing Systems, Payroll Systems...)
Different systems generate their transactions in different **formats** (CSV, Excel, XML, JSON...) and expose these data
using different **communication channels** (File system, Web service, Message queues...).

In this task, you are required to implement a console application which should be able to handle System Files as a
communication channel, and both CSV and JSON file formats. The application should read two files, compare the records of
these files and produce result files showing the matched, mismatched and missing records.

## File formats

#### CSV file format

In this CSV file, the first line is the header names, and the financial transactions starts from the second line with
the following fields:

1. **trans unique id**: this field is the transaction *unique identifier*
1. **trans description**: the transaction description
1. **amount**
1. **currency**: following ISO 4217 alphabetic code
1. **purpose**
1. **value date**: following the format `yyyy-MM-dd`
1. **trans type**: whether the transaction is a credit or debit example:

```csv
trans unique id,trans description ,amount  ,currecny,purpose ,value date,trans type
TR-47884222201 ,online transfer   ,140     ,USD     ,donation,2020-01-20,D
TR-47884222202 ,atm withdrwal     ,20.0000 ,JOD     ,        ,2020-01-22,D
TR-47884222203 ,counter withdrawal,5000    ,JOD     ,        ,2020-01-25,D
TR-47884222204 ,salary            ,1200.000,JOD     ,donation,2020-01-31,C
TR-47884222205 ,atm withdrwal     ,60.0    ,JOD     ,        ,2020-02-02,D
TR-47884222206 ,atm withdrwal     ,500.0   ,USD     ,        ,2020-02-10,D
```

#### JSON file format

The json file consists of an array of transactions with the following properties:

1. **date**: following the format `dd/MM/yyyy`
1. **reference**: this field is the transaction *unique identifier*
1. **amount**
1. **currencyCode**: following ISO 4217 alphabetic code
1. **purpose**

```json
[
  {
    "date": "20/01/2020",
    "reference": "TR-47884222201",
    "amount": "140.00",
    "currencyCode": "USD",
    "purpose": "donation"
  },
  {
    "date": "03/02/2020",
    "reference": "TR-47884222205",
    "amount": "60.000",
    "currencyCode": "JOD",
    "purpose": ""
  },
  {
    "date": "10/02/2020",
    "reference": "TR-47884222206",
    "amount": "500.00",
    "currencyCode": "USD",
    "purpose": "general"
  },
  {
    "date": "22/01/2020",
    "reference": "TR-47884222202",
    "amount": "30.000",
    "currencyCode": "JOD",
    "purpose": "donation"
  },
  {
    "date": "14/02/2020",
    "reference": "TR-47884222217",
    "amount": "12000.000",
    "currencyCode": "JOD",
    "purpose": "salary"
  },
  {
    "date": "25/01/2020",
    "reference": "TR-47884222203",
    "amount": "5000.000",
    "currencyCode": "JOD",
    "purpose": "not specified"
  },
  {
    "date": "12/01/2020",
    "reference": "TR-47884222245",
    "amount": "420.00",
    "currencyCode": "USD",
    "purpose": "loan"
  }
]
```

#### Result file format

The comparison result should be written to 3 CSV files with the following details:

###### Matching Transactions file

This file shall contain all the matching records, with the following details

1. **transaction id**
1. **amount**: formatted with decimal places as per the currency specification (see `E` column
   in https://en.wikipedia.org/wiki/ISO_4217#Active_codes)
1. **currency code**: following ISO 4217 alphabetic code
1. **value date**: having the format `yyyy-MM-dd`

example:

```csv
transaction id,amount  ,currecny code,value date
TR-47884222201,140.00  ,USD          ,2020-01-20
TR-47884222203,5000.000,JOD          ,2020-01-25
TR-47884222206,500.00  ,USD          ,2020-02-10
```

###### Mismatching Transactions file

This file contains the records that has been found on both files, but one or more of the financial details (amount,
currency and value date) did not match.

1. **found in file**: `SOURCE` for the source record, and `TARGET` for the target record
1. **transaction id**
1. **amount**: formatted with decimal places as per the currency specification (see `E` column
   in https://en.wikipedia.org/wiki/ISO_4217#Active_codes)
1. **currency code**: following ISO 4217 alphabetic code
1. **value date**: having the format `yyyy-MM-dd`

Each two mismatched records should be written below each other, see the below example:

```csv
found in file,transaction id,amount,currecny code,value date
SOURCE       ,TR-47884222202,20.000,JOD          ,2020-01-22
TARGET       ,TR-47884222202,30.000,JOD          ,2020-01-22
SOURCE       ,TR-47884222205,60.000,JOD          ,2020-02-02
TARGET       ,TR-47884222205,60.000,JOD          ,2020-02-03
```

###### Missing Transactions file

This file shows the records that were present in one file but not the other.

1. **found in file**: if present in first file, the value should be `SOURCE`, otherwise `TARGET`
1. **transaction id**
1. **amount**: formatted with decimal places as per the currency specification (see `E` column
   in https://en.wikipedia.org/wiki/ISO_4217#Active_codes)
1. **currency code**: following ISO 4217 alphabetic code
1. **value date**: having the format `yyyy-MM-dd`

example:

```csv
found in file,transaction id,amount   ,currency code,value date
SOURCE       ,TR-47884222204,1200.000 ,JOD          ,2020-01-31
TARGET       ,TR-47884222217,12000.000,JOD          ,2020-02-14
TARGET       ,TR-47884222245,420.00   ,USD          ,2020-01-12
```

## Requirements

The application should prompt the user to enter the path of two files (source and target files), and specify each file's
format, the below can serve as an example.

```
>> Enter source file location:
/home/user/file.csv
>> Enter source file format:
CSV
>> Enter target file location:
/home/user/another-file.csv
>> Enter target file format:
CSV
```

Then the reconciliation process should start, where the files are read and parsed, then the application compares the
transactions information.

NOTE: the user can enter two CSV files, two JSON files or one CSV and one JSON.

#### Transactions Comparison

To compare transactions, the application should start by matching transactions in both files using their *unique
identifier*, once two transactions are paired (have same *unique identifier*), the application should compare their
financial information to determine whether these transactions are *Matched* or *Mismatched*. The application should
compare the following fields for matching:

* amount
* currency
* value date

two transactions are considered *Matched* only if all three fields are equal.

When the application finds a transaction in one file, but does not find a transaction with the same *unique identifier*
in the other file, the transaction is considered *Missing*

#### Result

Once the application finished the reconciliation process, the location of the result files should be printed out to the
user, for example as below.

```
Reconciliation finished.
Result files are availble in directory /home/user/reconciliation-results
```

Where the directory `/home/user/reconciliation-results` should contain the [3 result files](#Result-file-format).

## General considerations and tips

* The samples above are available as files in this project, you can use them for testing.
* The application should be designed in a way to allow supporting new formats in the future, like XML, YAML, Excel...
* The application should be designed in a way to allow supporting other communication channels in the future, like
  Database, web services, JMS...
* The application should report clear error messages when there are problems in the input files