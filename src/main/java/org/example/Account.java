package org.example;

public abstract class Account {
    protected long balance;

    public abstract boolean add(long amount);

    public abstract boolean pay(long amount);

    public Account(long balance) {
        this.balance = balance;
    }

    public boolean transfer(Account account, long amount) {
        if (this.pay(amount)) {
            if (account.add(amount)) {
                return true;
            } else {
                this.pay(-amount);
            }
        }
        return false;
    }

    public long getBalance() {
        return this.balance;
    }
}