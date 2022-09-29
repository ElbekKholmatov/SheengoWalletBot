package com.company.db;

import com.company.entity.Customer;
import com.company.entity.MoneyTransfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface Database {
    List<Customer> customerList = new ArrayList<>();
    List<MoneyTransfer> incomeList = new ArrayList<>();
    List<MoneyTransfer> spendList = new ArrayList<>();
}
