package org.bot.bot;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Service
public class BitcoinService {
    public static BitcoinModel getInformationAboutBitcoin() throws IOException {
        URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        String neededInformation = "";
        while (scanner.hasNext()) {
            neededInformation += scanner.nextLine();
        }

        JSONObject informationAboutBitcoin = new JSONObject(neededInformation);
        BitcoinModel bitcoinInformation = new BitcoinModel();
        bitcoinInformation.setName(informationAboutBitcoin.getString("chartName"));
        bitcoinInformation.setRate(informationAboutBitcoin.getJSONObject("bpi").getJSONObject("USD").getFloat("rate_float"));
        bitcoinInformation.setDescription(informationAboutBitcoin.getJSONObject("bpi").getJSONObject("USD").getString("description"));

        return bitcoinInformation;
    }

    public static String getStringBitcoinInformation(BitcoinModel bitcoinModel) {
        return "Name of coin: " + bitcoinModel.getName() + "\nPrice is " + bitcoinModel.getRate() +
                "\nDescription: " + bitcoinModel.getDescription() + "\nЯ люблю тебя, Лерка моя";
    }
}
