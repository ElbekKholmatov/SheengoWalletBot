package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.db.Database;
import com.company.entity.Customer;
import com.company.entity.MoneyTransfer;
import com.company.entity.Wallet;
import com.company.files.WorkWithFiles;
import com.company.service.CustomerService;
import com.company.util.InlineKeyboardButtonUtil;
import com.company.util.KeyboardButtonConstants;
import com.company.util.KeyboardButtonUtil;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static void handleMessage(User user, Message message) {
        String chatId = String.valueOf(message.getChatId());

//        if(chatId.equals(ComponentContainer.ADMIN_CHAT_ID)){
//            AdminController.handleMessage(user, message);
//            return;
//        }

        if(message.hasText()){
            String text = message.getText();
            handleText(user, message, text);
        }else if(message.hasContact()){
            Contact contact = message.getContact();
            handleContact(user, message, contact);
        }else if(message.hasLocation()){
            Location location = message.getLocation();
            handleLocation(user, message, location);
        }else if(message.hasPhoto()){
            List<PhotoSize> photoSizeList = message.getPhoto();
            handlePhoto(user, message, photoSizeList);
        }
    }

    private static void handlePhoto(User user, Message message, List<PhotoSize> photoSizeList) {

    }

    private static void handleLocation(User user, Message message, Location location) {

    }

    private static void handleContact(User user, Message message, Contact contact) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Customer customer = CustomerService.getCustomerByChatId(chatId);
        if(customer != null){
            sendMessage.setText("Your contact already saved 😒");
        }else{
            Customer addCustomer = CustomerService.addCustomer(chatId, contact);
            sendMessage.setText("Your contact successfully saved 😀");

            // FOR ADMIN
            SendMessage msg = new SendMessage(ComponentContainer.ADMIN_CHAT_ID,
                    "new customer : "+addCustomer);
            ComponentContainer.MY_BOT.sendMsg(msg);
        }

        sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
        ComponentContainer.MY_BOT.sendMsg(sendMessage);
    }

    private static void handleText(User user, Message message, String text) {
        String chatId = String.valueOf(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if(text.equals("/start")){

            Customer customer = CustomerService.getCustomerByChatId(chatId);
            if(customer == null){
                sendMessage.setText("Hello!\nSend your number");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getContactMenu());
            }else{
                sendMessage.setText("Hello!");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
            }
        }else if(text.equalsIgnoreCase("/help")){
            sendMessage.setText("I can't help you");
        }
        else if(text.equalsIgnoreCase("/menu")){
            Customer customer = CustomerService.getCustomerByChatId(chatId);
            if(customer != null){
                sendMessage.setText("<b><u> Choose: </u></b>");
                sendMessage.setParseMode(ParseMode.HTML);
                sendMessage.setReplyMarkup(KeyboardButtonUtil.getBaseMenu());
            }else {
                sendMessage.setText("This function is available for customers who registered");
            }


        }
        else if(text.equalsIgnoreCase(KeyboardButtonConstants.INCOME)){
            sendMessage.setText("Where do you get this money?\n" +
                    "Please write as an example given below\n" +
                    "Where:somewhere,Amount:in sum\n" +
                    "exampe---> Where:At work,Amount:1000000");
        }else if(text.equalsIgnoreCase(KeyboardButtonConstants.SPEND)){
            sendMessage.setText("Where do you spend this money?\n" +
                    "Please write as an example given below\n" +
                    "To:somewhere,Amount:in sum\n" +
                    "exampe---> To:Atractions,Amount:100000");
        }else if(text.equalsIgnoreCase(KeyboardButtonConstants.BALANCE)){
            Customer customer = CustomerService.getCustomerByChatId(chatId);
            sendMessage.setText("Your Balance: "+customer.getWallet().getBalance()+"sum");
        }else if(text.equalsIgnoreCase(KeyboardButtonConstants.HISTORY)){
            sendMessage.setText("Choose search type");
            sendMessage.setReplyMarkup(InlineKeyboardButtonUtil.getInlineMenu());
        }else if(text.contains("Where:")&&text.contains(",Amount:")){
            MoneyTransfer income = new MoneyTransfer();
            Customer customer = CustomerService.getCustomerByChatId(chatId);
            String take = text.replace("Where:","");
            take = take.replace("Amount:","");
            String[] all = take.split(",");
            String where = all[0];

                Double amount = Double.valueOf(all[1]);
                System.out.println(amount);
                for (Customer customer1 : Database.customerList) {
                    if (customer1.getChatId().equals(chatId)) {
                        income.setWhere(where);
                        income.setAmount(amount);
                        income.setTime(LocalTime.now().toString());
                        Database.incomeList.add(income);
                        customer1.getWallet().setBalance(customer1.getWallet().getBalance()+amount);
                        WorkWithFiles.writeToIncomeJson();
                    }
                }
                WorkWithFiles.writeCustomerList();
                sendMessage.setText(customer.getFirstName()+" I noticed your income 👍");
        }
        else if(text.contains("To:")&&text.contains(",Amount:")){
            MoneyTransfer spend = new MoneyTransfer();
            Customer customer = CustomerService.getCustomerByChatId(chatId);
            String take = text.replace("To:","");
            take = take.replace("Amount:","");
            String[] all = take.split(",");
            String where = all[0];
            if(!all[1].matches("\\d+.\\d+")){
                sendMessage.setText("This isn't correct type");
            }
            if(Double.parseDouble(all[1]) > customer.getWallet().getBalance()){
                sendMessage.setText("You can't spend this mount of money\n" +
                        "Because you don't have enough money");
            }
            else{
                Double amount = Double.valueOf(all[1]);
                System.out.println(amount);
                for (Customer customer1 : Database.customerList) {
                    if (customer1.getChatId().equals(chatId)) {
                        spend.setWhere(where);
                        spend.setAmount(amount);
                        spend.setTime(LocalTime.now().toString());
                        Database.spendList.add(spend);
                        customer1.getWallet().setBalance(customer1.getWallet().getBalance()-amount);
                        WorkWithFiles.writeToSpendJson();
                    }
                }
                WorkWithFiles.writeCustomerList();
                sendMessage.setText(customer.getFirstName()+" I noticed your spend 😒\n" +
                        "Why you like to spend this money");
            }


        }
        else{
            sendMessage.setText("You Write something wrong!");
        }

        ComponentContainer.MY_BOT.sendMsg(sendMessage);
    }

    public static void handleCallback(User user, Message message, String data) {
        String chatId = String.valueOf(message.getChatId());

        DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
        ComponentContainer.MY_BOT.sendMsg(deleteMessage);

        if(data.equals("byIncome")){
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(new InputFile(WorkWithFiles.generateCustomerExcelFileByIncome()));
            ComponentContainer.MY_BOT.sendMsg(sendDocument);
        }else if(data.equals("bySpend")){
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(new InputFile(WorkWithFiles.generateCustomerExcelFileBySpend()));
            ComponentContainer.MY_BOT.sendMsg(sendDocument);
        }else if(data.equals("all")){
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(chatId);
            sendDocument.setDocument(new InputFile(WorkWithFiles.generateCustomerExcelFileByAll()));
            ComponentContainer.MY_BOT.sendMsg(sendDocument);
        }
    }
}
