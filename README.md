# NoobChain / NoobCoin — Simple Blockchain in Java

A proof‑of‑concept Java implementation of a blockchain + transactions system (inspired by tutorials).  
Learn blockchain fundamentals by building from scratch.

---

## Table of Contents

- [Introduction](#introduction)  
- [Features](#features)  
- [Prerequisites](#prerequisites)  
- [Project Structure](#project-structure)  
- [Setup & Usage](#setup--usage)  
  - [Part 1: Blockchain & Mining](#part-1-blockchain--mining)  
  - [Part 2: Wallets & Transactions](#part-2-wallets--transactions)  
- [API / Core Classes](#api--core-classes)  
- [Examples & Output](#examples--output)  
- [License](#license)
- [Challenge](#developer-challenge)

---

## Introduction

This project demonstrates how a basic blockchain works:

- Blocks store data and are linked via cryptographic hashes  
- Proof of work (mining) is used to validate new blocks  
- Wallets can send/receive coins via signed transactions  
- Unspent Transaction Outputs (UTXOs) are used to track funds  

It is **not production-ready** — it’s a learning tool to understand blockchain internals.

---

## Features

- Chain of blocks, each with hash, previous hash, timestamp  
- Proof of work / mining difficulty  
- Block validation  
- Wallets with public/private key pairs (Elliptic-Curve cryptography)  
- Transactions with inputs, outputs, and digital signatures  
- UTXO model for tracking balances  
- Genesis block that issues initial coins  

---

## Prerequisites

- Java SE (Java 8+ or later)  
- Build tool or IDE (Eclipse, IntelliJ, etc.)  
- External libraries:
  - [Gson](https://github.com/google/gson) — for JSON serialization  
  - [Bouncy Castle](https://www.bouncycastle.org/) — for cryptographic functions  

---

## Project Structure

```
src/
├── NoobChain.java         # Main class — entry point
├── Block.java             # Block definition, hashing, mining logic
├── StringUtil.java        # Helpers: SHA‑256, ECDSA signatures, key → string
├── Wallet.java             # Generate key pair, send funds
├── Transaction.java        # Transaction logic (inputs, outputs, signature)
├── TransactionInput.java  
├── TransactionOutput.java  
└── (other utility classes, e.g. for Merkle root placeholder)
```

You may also have collections (e.g. `HashMap` for UTXOs) in `NoobChain`.

---

## Setup & Usage

### Part 1: Blockchain & Mining

1. Clone / set up the project in your IDE or build tool.  
2. Add Gson as dependency.  
3. Create classes:

   - **Block**: stores `hash`, `previousHash`, `data` (or later transactions), `nonce`, `timestamp`.  
   - **StringUtil**: method `applySha256(String input)` to compute SHA‑256, plus other utilities.  
   - In **NoobChain** (main class):  
     - Maintain a `List<Block> blockchain`.  
     - Static `difficulty` level for mining (e.g. `int difficulty = 5`).  
     - Method `isChainValid()` to loop through blocks and verify hashes & linkage.  
     - On adding a block, call its `mineBlock(int difficulty)` to find a hash starting with required zeros.  

4. In `main()`, create genesis block (pass `"0"` as previous hash), then add more blocks, print them, and validate.

Expected output: blocks printed with their hashes, mining time, and a valid‑chain check.

### Part 2: Wallets & Transactions

1. Add Bouncy Castle dependency (for ECC / signatures).  
2. **Wallet class**:  
   - Generate EC keypair (public + private key)  
   - Methods to sign transactions, verify signatures  

3. **Transaction class**:  
   - Fields: `sender`, `recipient`, `value`, `inputs` (list), `outputs` (list), `signature`  
   - Create `generateSignature()` and `verifySignature()` (using ECDSA)  
   - `processTransaction()` validates inputs, sums them, issues outputs (including “change”)  
   - Use a collection of **unspent transaction outputs (UTXOs)** to track outputs not yet spent  

4. **TransactionInput / TransactionOutput classes**:  
   - `TransactionOutput`: stores `id`, `recipientKey`, `value`  
   - `TransactionInput`: references an output by `transactionOutputId`  

5. **Integrate into Block & NoobChain**:  
   - Update `Block` to accept list of transactions instead of raw data  
   - (Optional) compute a simple “merkle root” string from transaction list  
   - In `NoobChain`, maintain a `HashMap<String, TransactionOutput>` for UTXOs  
   - Create a genesis block issuing initial coins to a wallet  
   - In `main()`, simulate sending coins: e.g. `block.addTransaction( walletA.sendFunds(walletB.publicKey, 20) );`  
   - Validate transactions and chain  

Example output should show wallet keys, verification of signature, transaction processing, balance changes, and overall blockchain validity.

---

## API / Core Classes

| Class               | Key Methods / Responsibilities |
|---------------------|--------------------------------|
| `Block`             | `mineBlock(int difficulty)`, `calculateHash()`, `addTransaction(...)` |
| `StringUtil`        | `applySha256(String)`, `applyECDSASig(...)`, `verifyECDSASig(...)`, `getStringFromKey(...)`, simple merkle root helper |
| `Wallet`            | Constructor (generate keys), `sendFunds(PublicKey recipient, float value)` |
| `Transaction`        | `generateSignature()`, `verifySignature()`, `processTransaction()`, `getInputsValue()`, `getOutputsValue()` |
| `TransactionInput`   | Reference to `transactionOutputId` |
| `TransactionOutput`  | Fields for `id`, `recipient`, `value`, `parentTransactionId` |

---

## Examples & Output

```
Wallet A public key: …
Wallet A private key: …
Wallet B public key: …
Wallet B private key: …

Starting blockchain validation: true  
Mining block 1… done in 3 seconds  
Mining block 2… done in 4 seconds  

Is signature verified: true  
Wallet A’s balance is: 80  
Wallet B’s balance is: 20  

Blockchain is valid: true  
```

---

## License

This project is released under the **MIT License**. Feel free to use, modify, and distribute.

---

## Developer Challenge

This project includes a deliberate gimmick — a subtle flaw intentionally added to the blockchain logic.
Your mission is to find and fix it so that the system behaves as expected.

**The issue may cause:**

- Incorrect hash validation,

- Faulty transaction verification, or

- Inconsistent balances in wallets.

**Your Task:**

- Trace through the code logic carefully.

- Identify the part that breaks the expected blockchain flow.

- Correct it so the chain validates successfully and outputs the right balances.

This isn’t a bug — it’s a learning challenge designed to test your grasp of how blockchains truly work.
Once fixed, the blockchain should mine, verify, and process transactions flawlessly.
