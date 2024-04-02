package org.bot.bot;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String bitcoinInformation = "";

        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    start(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help ":
                    sendMessage(chatId, "xui");
                    break;
                default:
                    try {
                        bitcoinInformation = BitcoinService.getStringBitcoinInformation(BitcoinService.getInformationAboutBitcoin());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sendMessage(chatId, bitcoinInformation);
            }
        }
    }

    private void start(long chatId, String name) {
        String answer = "Hello " + name + ", Enter any symbol to get some information about Bitcoin";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
