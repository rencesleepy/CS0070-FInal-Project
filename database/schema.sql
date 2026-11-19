-- SQLite database for the offline, single-user Moneytracker app.
-- Money is stored directly in Philippine pesos: PHP 150.00 = 150.00.
-- Run this script on a local file named moneytracker.db.

BEGIN TRANSACTION;


CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT NOT NULL
        CHECK (type IN ('INCOME', 'EXPENSE')),
    description TEXT NOT NULL
        CHECK (length(trim(description)) > 0),
    category TEXT NOT NULL COLLATE NOCASE
        CHECK (length(trim(category)) > 0),
    amount_php NUMERIC NOT NULL
        CHECK (
            typeof(amount_php) IN ('integer', 'real')
            AND amount_php BETWEEN 0.01 AND 999999999999.99
            AND amount_php = round(amount_php, 2)
        ),
    transaction_date TEXT NOT NULL DEFAULT (date('now', 'localtime'))
        CHECK (
            length(transaction_date) = 10
            AND transaction_date GLOB '[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]'
            AND COALESCE(date(transaction_date, '+0 days') = transaction_date, 0)
        ),
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_transactions_date
    ON transactions (transaction_date DESC, id DESC);

CREATE VIEW IF NOT EXISTS money_summary AS
SELECT
    round(COALESCE(SUM(CASE WHEN type = 'INCOME' THEN amount_php ELSE 0 END), 0), 2)
        AS total_income_php,
    round(COALESCE(SUM(CASE WHEN type = 'EXPENSE' THEN amount_php ELSE 0 END), 0), 2)
        AS total_expenses_php,
    round(COALESCE(SUM(CASE WHEN type = 'INCOME'
        THEN amount_php ELSE -amount_php END), 0), 2)
        AS balance_php
FROM transactions;

COMMIT;
