
# Money Tracker

**A Java Swing Desktop Application for Personal Financial Management**

Money Tracker is a desktop application developed using Java Swing and SQLite. It allows users to record income and expenses, monitor their financial balance, organize transactions by category, and visualize their financial activity through interactive doughnut charts.

This application was developed as a **CS0070 Final Project**.

## Features

- **Income and Expense Tracking** — Record financial transactions with descriptions, amounts, and categories.
- **Transaction Management** — Add, view, edit, and delete transactions.
- **Financial Dashboard** — Display total income, total expenses, and available balance.
- **Interactive Doughnut Charts** — Visualize income, expenses, and category-based breakdowns.
- **Recent Transactions** — Quickly access recently recorded transactions.
- **Transaction History** — View previously recorded transactions.
- **SQLite Database** — Store transaction records locally and preserve them between application sessions.
- **Automatic Database Initialization** — Create the required database structure when the application first runs.
- **Frosted-Glass Interface** — A custom Java Swing interface featuring translucent panels, rounded components, and soft gradient backgrounds.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Java Swing | Graphical user interface |
| Java 2D | Custom graphics and doughnut charts |
| SQLite | Local database storage |
| SQLite JDBC | Java database connectivity |
| Git and GitHub | Version control and source code hosting |
| jpackage | Windows application packaging |


## Download and Installation

### Windows Application

1. Visit the repository's **Releases** section.
2. Download `MoneyTracker-v1.0.0-Windows.zip`.
3. Extract the downloaded ZIP file.
4. Open the extracted `MoneyTracker` folder.
5. Double-click `MoneyTracker.exe`.

The Windows package includes its own Java runtime. A separate Java installation is not required.

**Important:** Keep the `MoneyTracker.exe`, `app`, and `runtime` components together.

## Database Storage

Money Tracker uses SQLite to store transaction records locally.

On Windows, the database is stored in:

`%LOCALAPPDATA%\MoneyTracker\moneytracker.db`

The application automatically creates the database and initializes the required tables when needed.

Transactions remain stored locally after closing the application.

## Running from Source Code

### Requirements

- Java Development Kit 21 or later
- SQLite JDBC and its required runtime dependencies

From the repository root, compile the application:

```powershell
New-Item -ItemType Directory -Path build -Force

javac --release 21 -encoding UTF-8 -cp "lib/*" -d build src/Money_tracker_organized.java src/Transaction.java src/Transaction_manager.java src/TransactionDAO.java src/DatabaseConnection.java src/RecentTransactions.java src/Transaction_history.java src/RoundedPanel.java

Copy-Item "database/schema.sql" "build/schema.sql"
```

Run the application:

```powershell
java --enable-native-access=ALL-UNNAMED -cp "build;lib/*" Money_tracker_organized
```

## Project Structure

```text
CS0070-Final-Project/
├── src/
│   ├── Money_tracker_organized.java
│   ├── Transaction.java
│   ├── Transaction_manager.java
│   ├── TransactionDAO.java
│   ├── DatabaseConnection.java
│   ├── RecentTransactions.java
│   ├── Transaction_history.java
│   └── RoundedPanel.java
├── database/
│   └── schema.sql
├── lib/
│   └── sqlite-jdbc-3.54.1.0.jar
├── screenshots/
│   ├── dashboard.png
│   ├── add-transaction.png
│   └── transaction-history.png
├── README.md
└── .gitignore
```

## Testing

The application was tested for database connectivity, transaction creation, updating and deletion, financial calculations, chart visualization, and transaction persistence.

The packaged Windows executable was also tested outside the development environment.

## Version

**Version:** 1.0.0  
**Platform:** Windows  
**Project:** CS0070 Final Project  
**Application Type:** Java Swing Desktop Application

