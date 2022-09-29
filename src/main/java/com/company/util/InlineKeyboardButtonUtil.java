package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class InlineKeyboardButtonUtil {
    public static InlineKeyboardMarkup getInlineMenu() {

        InlineKeyboardButton button1 = new InlineKeyboardButton("By Income 😎");
        button1.setCallbackData("byIncome");

        InlineKeyboardButton button2 = new InlineKeyboardButton("By Spend 😒");
        button2.setCallbackData("bySpend");

        InlineKeyboardButton button3 = new InlineKeyboardButton("😎😎All😒😒");
        button3.setCallbackData("all");

        List<InlineKeyboardButton> row = List.of(button1, button2);
        List<List<InlineKeyboardButton>> rowList = List.of(row, List.of(button3));

        return new InlineKeyboardMarkup(rowList);
    }
}
