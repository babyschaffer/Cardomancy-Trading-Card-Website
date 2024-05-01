package com.techelevator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.dao.CardDao;
import com.techelevator.dao.JdbcCardDao;
import com.techelevator.model.Card;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Component
public class ScryfallBulkLoader {
    private static final String FILE_PATH = "BulkDownload.json";
    final int MTG_ID = 1;
    CardDao cardDao;
    ObjectMapper objectMapper;
    File jsonPath;



    /**
     * Run this!
     *
     * @param args redundant, leave as empty
     */
    public static void main(String[] args) {
        ScryfallBulkLoader scryfallBulkLoader = new ScryfallBulkLoader();
        scryfallBulkLoader.run();
    }

    /**
     * parses through the json file and fills
     * in the postgresql database
     */
    public void run(){
        if (!jsonPath.exists()){
            System.out.println("Please download the latest bulk download file from " +
                    "https://scryfall.com/docs/api/bulk-data and rename it to \"BulkDownload.json\" in /java");
            System.exit(1);
        }
        try {
            JsonNode cardArray = objectMapper.readTree(jsonPath);
            System.out.println("Enter amount of cards to parse through (-1 for all): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            int max = Integer.parseInt(response);
            max = (max == -1)? cardArray.size() : max;
            for (int i = 0; i < max; i++) {
                JsonNode cardJson = cardArray.get(i);
                Card currentCard = mapJsonCardToCard(cardJson);
                uploadCard(currentCard);
            }
        } catch (IOException e){
            System.out.println("failed to process file!");
            System.out.println(e.getMessage() + " " + e.getCause());
            System.exit(1);
        }
    }

    /**
     * constructor to instantiate the ScryfallBulkLoader object
     */
    private ScryfallBulkLoader() {
        this.objectMapper = new ObjectMapper();
        this.jsonPath = new File(FILE_PATH);

        BasicDataSource datasource = new BasicDataSource();
        datasource.setUsername("final_capstone_appuser");
        datasource.setPassword("finalcapstone");
        datasource.setUrl("jdbc:postgresql://localhost:5432/final_capstone");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

        this.cardDao = new JdbcCardDao(jdbcTemplate);
    }

    /**
     * creates a java Card object from a Scryfall json card object
     *
     * @param cardJson <a href="https://scryfall.com/docs/api/cards">Scryfall card object</a> from a json
     * @return java Card object based on the fields in the json card object
     */
    private Card mapJsonCardToCard(JsonNode cardJson) {
        String id = cardJson.get("id").asText();
        String scryfallUrl = cardJson.get("scryfall_uri").asText();
        String name = cardJson.get("name").asText();
        String imgUrl = null;
        String smallImgUrl = null;
        String reverseImgUrl = null;
        String smallReverseImgUrl = null;
        if (cardJson.get("image_status").asText().equals("missing")){
            System.out.println("card is missing images!");
        } else if (cardJson.has("image_uris")) {
            JsonNode imgUris = cardJson.get("image_uris");
            if (imgUris.has("normal")) {
                imgUrl = imgUris.get("normal").asText();
            }
            if (imgUris.has("small")) {
                smallImgUrl = imgUris.get("small").asText();
            }
        } else {
            JsonNode cardFaces = cardJson.get("card_faces");
            JsonNode frontImageUris = cardFaces.get(0).get("image_uris");
            if (frontImageUris.has("normal")) {
                imgUrl = frontImageUris.get("normal").asText();
            }
            if (frontImageUris.has("small")){
                smallImgUrl = frontImageUris.get("small").asText();
            }
            JsonNode reverseImageUris = cardFaces.get(1).get("image_uris");
            if (reverseImageUris.has("normal")){
                reverseImgUrl = reverseImageUris.get("normal").asText();
            }
            if (reverseImageUris.has("small")){
                smallReverseImgUrl = reverseImageUris.get("small").asText();
            }
        }
        String set = cardJson.get("set").asText();
        String setName = cardJson.get("set_name").asText();
        List<String> colors = new ArrayList<>();
        if (cardJson.has("colors")){
            JsonNode colorsJson = cardJson.get("colors");
            for (int i = 0; i < colorsJson.size(); i++) {
                colors.add(colorsJson.get(i).asText());
            }
        }
        List<String> colorIdentities = new ArrayList<>();
        if (cardJson.has("color_identity")){
            JsonNode colorsJson = cardJson.get("color_identity");
            for (int i = 0; i < colorsJson.size(); i++) {
                colorIdentities.add(colorsJson.get(i).asText());
            }
        }
        String collectorNumber = cardJson.get("collector_number").asText();
        Map<String, String> legalities = new HashMap<>();
        JsonNode legalitiesJson = cardJson.get("legalities");
        legalities.put("standard", legalitiesJson.get("standard").asText());
        legalities.put("pioneer", legalitiesJson.get("pioneer").asText());
        legalities.put("modern", legalitiesJson.get("modern").asText());
        legalities.put("vintage", legalitiesJson.get("vintage").asText());
        legalities.put("commander", legalitiesJson.get("commander").asText());
        legalities.put("oathbreaker", legalitiesJson.get("oathbreaker").asText());
        String layout = cardJson.get("layout").asText();
        double cmc = (cardJson.has("cmc"))? cardJson.get("cmc").asDouble(): 0.0;
        int edhrecRank = -1;
        if(cardJson.has("edhrec_rank"))
            edhrecRank = cardJson.get("edhrec_rank").asInt();

        return new Card(id, MTG_ID, name, imgUrl, smallImgUrl, scryfallUrl, colorIdentities, set, setName,
                collectorNumber, legalities, colors, reverseImgUrl, smallReverseImgUrl, layout, cmc, edhrecRank);
    }

    /**
     * updates the postgresql database with a card
     *
     * @param card the java card object to upload onto the postgresql server
     */
    private void uploadCard(Card card){
        if (cardDao.getCardById(card.getId()) == null) {
            Card uploadedCard = cardDao.addCard(card);
            System.out.println(uploadedCard.getName() + " has been added! Card id: " + uploadedCard.getId());
        } else {
            System.out.println(card.getName() + " already exists! Card id: " + card.getId());
        }
//        if (!card.getLayout().equals("normal"))
//            System.out.println(card);
    }
}
