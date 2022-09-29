package com.company.bot;

import com.company.container.ComponentContainer;
import com.company.files.WorkWithFiles;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {

        try {

            WorkWithFiles.readCustomerList();

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            MyBot myBot = new MyBot();
            ComponentContainer.MY_BOT = myBot;

            botsApi.registerBot(myBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
