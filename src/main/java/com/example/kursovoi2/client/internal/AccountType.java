package com.example.kursovoi2.client.internal;

import lombok.Getter;

public enum AccountType {
    CLIENT("Client"), WORKER("Worker"), ADMIN("Admin");

    @Getter
    private final String displayName;

    AccountType(String displayName)
    {
        this.displayName = displayName;
    }

    public String toString() { return displayName; }
}
