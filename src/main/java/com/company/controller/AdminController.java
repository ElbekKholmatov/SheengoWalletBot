package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.Customer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class AdminController {
    public static void handleMessage(User user, Message message) {
        String chatId = String.valueOf(message.getChatId());
        // send message to customers

        if(message.hasText()){
            String text = message.getText();

            if(Database.customerList.isEmpty()){
                SendMessage sendMessage = new SendMessage(chatId, "no customers");
                ComponentContainer.MY_BOT.sendMsg(sendMessage);
            }else{

                for (Customer customer : Database.customerList) {
                    SendMessage sendMessage = new SendMessage(customer.getChatId(), text);
                    ComponentContainer.MY_BOT.sendMsg(sendMessage);
                }

                SendMessage sendMessage = new SendMessage(chatId, text+" =>  SENT TO CUSTOMERS");
                ComponentContainer.MY_BOT.sendMsg(sendMessage);
            }
        }
    }
}
