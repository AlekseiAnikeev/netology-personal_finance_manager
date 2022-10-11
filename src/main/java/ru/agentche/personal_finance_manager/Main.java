package ru.agentche.personal_finance_manager;


import ru.agentche.personal_finance_manager.server.Server;
import ru.agentche.personal_finance_manager.treatment.Miscalculation;
import ru.agentche.personal_finance_manager.treatment.Request;

/**
 * @author Aleksey Anikeev aka AgentChe
 * Date of creation: 10.10.2022
 */
public class Main {
    public static void main(String[] args) {
        Thread server = new Thread(new Server(new Request(), new Miscalculation()));
        server.start();
    }
}
