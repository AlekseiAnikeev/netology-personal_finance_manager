package ru.agentche.personal_finance_manager.server;

import ru.agentche.personal_finance_manager.treatment.Miscalculation;
import ru.agentche.personal_finance_manager.treatment.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Server implements Runnable {
    private static final int PORT = 8989;
    Request request;
    Miscalculation miscalculations;
    public Server(Request request, Miscalculation miscalculations){
        this.request = request;
        this.miscalculations = miscalculations;
    }
    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен и ждет подключения");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    request(in);
                    answer(out);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void request(BufferedReader in) {
        request.addPurchase(in);
    }


    private void answer(PrintWriter out) {
        out.println(miscalculations.getStatistic(request.getCategories()));
    }
}

