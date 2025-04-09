package covid.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ReportService {

    private static final Logger logger = Logger.getLogger(ReportService.class.getName());

    private static final String API_KEY = "2505eda46amshc60713983b5e807p1da25ajsn36febcbf4a71";
    private static final String API_HOST = "covid-19-statistics.p.rapidapi.com";

    public String fetchReportByProvince(String iso, String province, String date) {
        String apiUrl = String.format(
                "https://covid-19-statistics.p.rapidapi.com/reports?iso=%s&region_province=%s&date=%s",
                iso, province.replace(" ", "%20"), date
        );

        try {
            logger.info("Fetching report for province: " + province);

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", API_HOST);

            int responseCode = conn.getResponseCode();
            logger.info("Report API Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                logger.info("Report data for " + province + ": " + response.toString().substring(0, Math.min(500, response.length())) + "...");
                return response.toString();
            } else {
                logger.warning("Failed to fetch report for " + province);
            }

        } catch (Exception e) {
            logger.severe("Error fetching report for " + province + ": " + e.getMessage());
        }

        return null;
    }
}