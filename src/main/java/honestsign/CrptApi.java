package honestsign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class CrptApi {
    private TimeUnit timeUnit;
    private int requestLimit;
    private static RateLimiter rateLimiter;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.rateLimiter = RateLimiter.create(this.requestLimit / (TimeUnit.SECONDS.convert(1L, this.timeUnit)));
    }

    @Data
    static class Document {
        public static final String DOC_TYPE = "LP_INTRODUCE_GOODS";
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private List<Product> products;
        private String reg_date;
        private String reg_number;

    }

    @Data
    @AllArgsConstructor
    static class Product {
        private String certificate_document;
        private String certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;
    }

    @Data
    @AllArgsConstructor
    static class Description {
        private String participantInn;
    }

    public static void createDocument(Document document, String signature) {
        rateLimiter.acquire();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(document);
            URL url = new URL("https://ismp.crpt.ru/api/v3/lk/documents/create");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
            outputStream.write(json);
            outputStream.write(signature);
            outputStream.flush();
            outputStream.close();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}