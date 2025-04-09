package covid.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProvinceService {

    private static final Logger logger = Logger.getLogger(ProvinceService.class.getName());

    private static final String API_KEY = "2505eda46amshc60713983b5e807p1da25ajsn36febcbf4a71";
    private static final String API_HOST = "covid-19-statistics.p.rapidapi.com";

    public List<String> fetchProvinces(String iso) {
        List<String> provinces = new ArrayList<>();

        try {
            logger.info("Fetching provinces for ISO: " + iso);

            String provincesUrl = "https://covid-19-statistics.p.rapidapi.com/provinces?iso=" + iso;
            URL url = new URL(provincesUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
            conn.setRequestProperty("X-RapidAPI-Host", API_HOST);

            int responseCode = conn.getResponseCode();
            logger.info("Provinces API Response Code: " + responseCode);

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                JSONArray data = json.getJSONArray("data");

                if (data.length() == 0) {
                    logger.warning("Empty province list. Raw response: " + json.toString(2));
                }

                for (int i = 0; i < data.length(); i++) {
                    JSONObject province = data.getJSONObject(i);
                    String name = province.optString("province", "").trim();

                    if (name.isEmpty()) {
                        logger.warning("[WARNING] Skipping empty province entry.");
                        continue;
                    }
                    logger.info("Province name: " + name);
                    provinces.add(name);
                }

                logger.info("Found " + provinces.size() + " provinces.");
                logger.info("Provinces URL: " + provincesUrl);

            }
        } catch (Exception e) {
            logger.severe("Error fetching provinces: " + e.getMessage());
        }

        return provinces;
    }
}
