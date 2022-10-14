package ru.agentche.personal_finance_manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Клиент добавлен для себя.
 * Date of creation: 25.09.2022
 */

public class Client {
    private static final int PORT = 8989;
    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(SERVER_IP, PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            out.println("{\"title\": \"булка\", \"date\": \"2022.02.22\", \"sum\": 200}");
            System.out.println(in.readLine());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}