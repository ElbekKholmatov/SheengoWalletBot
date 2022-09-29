package com.company.entity;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MoneyTransfer {
    private int id;
    private String where;
    private Double amount;
    private String time;
}
