package org.bitcoin.client;

import java.io.IOException;

/**
 * Created by handong on 17/1/17.
 */
public class TradeThread extends Thread {
    private TradeClient client;

    public TradeThread (TradeClient client) {
        this.client = client;
    }

    @Override
    public void run() {
            while (true) {
                try {
                    client.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
