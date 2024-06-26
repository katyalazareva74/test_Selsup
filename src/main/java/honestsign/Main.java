package honestsign;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static honestsign.CrptApi.createDocument;

public class Main {
    public static void main(String[] args) throws IOException {
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 1);
        CrptApi.Description description = new CrptApi.Description("string");
        CrptApi.Product product = new CrptApi.Product("string", "2020-01-23", "string", "string", "string", "2020-01-23", "string", "string", "string");
        CrptApi.Document document = new CrptApi.Document();
        document.setDescription(description);
        document.setDoc_id("string");
        document.setDoc_status("string");
        document.setDoc_type(CrptApi.Document.DOC_TYPE);
        document.setReg_date("2020-01-23");
        document.setImportRequest(true);
        document.setOwner_inn("string");
        document.setParticipant_inn("string");
        document.setProducer_inn("string");
        document.setProduction_date("2020-01-23");
        document.setProducts(Arrays.asList(product));
        document.setReg_number("string");
        document.setProduction_type("strimg");
        createDocument(document, "signature");
    }
}
