package com.company.bot;

import com.company.container.ComponentContainer;
import com.company.controller.MainController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotToken() {
        return ComponentContainer.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return ComponentContainer.BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = message.getFrom();

            MainController.handleMessage(user, message);

        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            User user = callbackQuery.getFrom();
            String data = callbackQuery.getData();

            MainController.handleCallback(user, message, data);
        }
    }

//    public void sendMsg(SendMessage sendMessage){
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMsg(SendDocument sendDocument) {
//        try {
//            execute(sendDocument);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMsg(DeleteMessage deleteMessage) {
//        try {
//            execute(deleteMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMsg(EditMessageText editMessageText) {
//        try {
//            execute(editMessageText);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendMsg(Object obj) {
        try {
            if (obj instanceof SendMessage) {
                execute((SendMessage)obj);
            }
            else if (obj instanceof DeleteMessage) {
                execute((DeleteMessage)obj);
            }
            else if (obj instanceof EditMessageText) {
                execute((EditMessageText)obj);
            }
            else if (obj instanceof SendDocument) {
                execute((SendDocument) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
