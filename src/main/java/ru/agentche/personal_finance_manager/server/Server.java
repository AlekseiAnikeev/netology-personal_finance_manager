package ru.agentche.personal_finance_manager.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.agentche.personal_finance_manager.entity.Purchase;
import ru.agentche.personal_finance_manager.treatment.Miscalculation;
import ru.agentche.personal_finance_manager.treatment.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Server implements Runnable {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final int PORT = 8989;
    Request request;
    Miscalculation miscalculations;

    public Server(Request request, Miscalculation miscalculations) {
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
                    ObjectMapper mapper = new ObjectMapper();
                    Purchase purchase;
                    try {
                        purchase = mapper.readValue(in.readLine(), new TypeReference<>() {
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    request(purchase);
                    answer(out, purchase);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void request(Purchase purchase) {
        request.addPurchase(purchase);
    }

    private void answer(PrintWriter out, Purchase purchase) {
        out.println(miscalculations.getStatistic(request.getCategories(), purchase));
    }
}

