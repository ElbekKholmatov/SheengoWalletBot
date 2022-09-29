package com.company.service;

import com.company.db.Database;
import com.company.entity.Customer;
import com.company.entity.Wallet;
import com.company.files.WorkWithFiles;
import org.telegram.telegrambots.meta.api.objects.Contact;

public class CustomerService {
    public static Customer getCustomerByChatId(String chatId){
        return Database.customerList.stream()
                .filter(customer -> customer.getChatId().equals(chatId))
                .findFirst().orElse(null);
    }

    public static Customer getCustomerByPhoneNumber(String phoneNumber){
        return Database.customerList.stream()
                .filter(customer -> customer.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElse(null);
    }

    public static Customer addCustomer(String chatId, Contact contact){
        if(getCustomerByChatId(chatId) != null) return null;
        if(getCustomerByPhoneNumber(contact.getPhoneNumber()) != null) return null;

        Customer customer = new Customer(chatId, contact.getFirstName(),
                contact.getLastName(), contact.getPhoneNumber(),new Wallet());
        Database.customerList.add(customer);
        WorkWithFiles.writeCustomerList();
        return customer;
    }
}
