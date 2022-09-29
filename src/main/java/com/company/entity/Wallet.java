package com.company.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Wallet {
    public List<MoneyTransfer> incomes;
    private List<MoneyTransfer> spends;
    private double balance;

}
