package covid.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class RegionService {

    private static final Logger logger = Logger.getLogger(RegionService.class.getName());

    private static final String API_KEY = "2505eda46amshc60713983b5e807p1da25ajsn36febcbf4a71";
    private static final String API_HOST = "covid-19-statistics.p.rapidapi.com";
    private static final String REGIONS_URL = "https://covid-19-statistics.p.rapidapi.com/regions";

    public void fetchRegions() {
        try {
            logger.info("Fetching regions from API...");

            URL url = new URL(REGIONS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", API_HOST);

            int responseCode = conn.getResponseCode();
            logger.info("Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                logger.info("API Response (Regions):\n" + response.toString());

            } else {
                logger.warning("Failed to fetch regions. Response Code: " + responseCode);
            }

        } catch (Exception e) {
            logger.severe("Error fetching regions: " + e.getMessage());
        }
    }
}