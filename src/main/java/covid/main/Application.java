package covid.main;

import covid.executor.ExecutorJobHandler;

public class Application {
    public static void main(String[] args) {
        System.out.println("[INFO] Application started...");

        // Espera 15 segundos y luego lanza el hilo
        Thread executorThread = new Thread(new ExecutorJobHandler());
        executorThread.start();
    }
}
