package covid.executor;

import covid.service.ProvinceService;
import covid.service.ReportService;

import java.util.List;

public class ExecutorJobHandler implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("[INFO] Waiting 15 seconds before executing job...");
            Thread.sleep(15000);

            System.out.println("[INFO] Starting job execution...");

            String iso = "GTM";
            String date = "2022-04-16";

            ProvinceService provinceService = new ProvinceService();
            List<String> provinces = provinceService.fetchProvinces(iso);

            ReportService reportService = new ReportService();

            for (String province : provinces) {
                if (province != null && !province.isBlank()) {
                    String reportJson = reportService.fetchReportByProvince(iso, province, date);
                    if (reportJson != null) {
                        System.out.println("[INFO] Report fetched for " + province);
                    }
                } else {
                    System.out.println("[WARNING] Skipping empty province entry.");
                }
            }

            System.out.println("[INFO] Job execution completed.");

        } catch (InterruptedException e) {
            System.err.println("[ERROR] Executor interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
