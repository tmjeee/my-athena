package com.tmjee.myathena.domain

import java.util.*

data class User(
    val userId : Int,
    val username: String,
){}

data class LoginResult(
    val userId: Int
){}

data class Account(
    val userId: Int,
    val accountId: Int,
    val accountName: String,
    val amountOutstanding: Float,
    val amountAvailable: Float,
    val bsb: String,
    val accountNumber: String,
    val accountAddress: String,
    val monthlyRepayment: Float,
    val nextDueDate: Date
){}

enum class TypeOfRepayment {
    INTEREST, PRINCIPAL_AND_INTEREST
}

enum class LoanType {
    OWNER_OCCUPIED, INVESTOR
}

data class LoanDetails(
    val messages: List<String>,
    val loanId: Int,
    val extraPayment: Float,
    val totalMonthlyPayment: Float,
    val minMonthlyRepayment: Float,
    val nextInterestPosting: Date,
    val typeOfRepayment: TypeOfRepayment,
    val interestRate: Float,
    val loanType: LoanType,
    val loanTerm: String,
    val termRemaining: String,
    val loanStartDate: Date,
    val originalLoanAmount: Float,
    val amountLeftToPayOff: Float,
    val accountHolders: List<String>,
    val interestsChargedByFinancialYear: Map<String, Float>,
    val features: List<String>,
){}


enum class StatementType {
    LOAN_STATEMENT, OTHERS
}

data class Statements (
    val messages: List<String>,
    val statements: List<Statement>,
){}

data class Statement (
    val statementId: Int,
    val periodFrom: Date,
    val periodTo: Date,
    val type: StatementType,
    val downloadLink: String,
){}

enum class TransactionType {
    DEBIT, CREDIT
}

data class Transactions (
    val accountId: Int,
    val total: Int,
    val offset: Int,
    val limit: Int,
    val entries: List<Transaction>
){}

data class Transaction (
    val transactionId: Int,
    val date: Date,
    val entries: List<TransactionEntry>
){}

data class TransactionEntry (
    val time: String,
    val description: String,
    val amount: Float,
    val type: TransactionType,
    val balance: Float,
){}

data class Payments (
    val accountId: Int,
    val total: Int,
    val limit: Int,
    val offset: Int,
    val payments: List<Payment>,
){}

data class Payment (
    val paymentId: Int,
    val date: Date,
    val amount: Float,
    val description: String,
    val from: String,
    val to: String,
){}

data class PhotoSet (
    val accountId: Int,
    val total: Int,
    val offset: Int,
    val limit: Int,
    val photos: List<Photos>
){}

data class Photos (
    val photo: Photo,
    val description: String,
){}

data class Photo (
    val id: String,
    val imageUrls: List<String>,
    val description: String
){}


