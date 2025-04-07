package covid.executor;

import covid.service.RegionService;

public class ExecutorJobHandler implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("[INFO] Waiting 15 seconds before executing job...");
            Thread.sleep(15000);

            System.out.println("[INFO] Starting job execution...");

            RegionService regionService = new RegionService();
            regionService.fetchRegions();

            System.out.println("[INFO] Job execution completed.");

        } catch (InterruptedException e) {
            System.err.println("[ERROR] Executor interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
