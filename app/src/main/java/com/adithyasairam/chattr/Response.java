package com.adithyasairam.chattr;

import android.content.Context;

import com.adithyasairam.android.android_commons.RealStoragePathLibrary;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import ai.wit.sdk.model.WitOutcome;

/**
 * Created by AdiSai on 3/12/17.
 */

public class Response {
    final String[] GREETINGS = {"Hey", "Hello", "Hi", "Welcome", "Sup"};
    Context context;
    WitOutcome outcome;
    String data;

    public Response(Context c, WitOutcome o) {
        context = c;
        outcome  = o;
        data = o.get_text();
        save(data + "\n");
    }

    public String process() {
        String output = "";
        HashMap<String, JsonElement> entities = outcome.get_entities();
        if (entities.containsKey("intent")) {
            JsonArray intentArray = entities.get("intent").getAsJsonArray();
            JsonObject intentElements = intentArray.get(0).getAsJsonObject();
            JsonElement conf = intentElements.get("confidence");
            double confidence = conf.getAsDouble();
            JsonElement value = intentElements.get("value");
            String statementType = value.getAsString(); //greeting, conditionQuestion, occurrenceQuestion
            if (statementType.equals("greeting")) { output = selectRandom(GREETINGS); }
            if (statementType.equals("conditionQuestion")) { output = "I'm good, what about you?"; }
            if (statementType.equals("occurrenceQuestion")) { output = "Not much, what about you?"; }
            if (statementType.equals("occurrenceResponse")) { output = "That's cool!"; }
        }
        if (entities.containsKey("sentiment")) {
            JsonArray sentimentArray = entities.get("sentiment").getAsJsonArray();
            JsonObject sentimentElements = sentimentArray.get(0).getAsJsonObject();
            JsonElement conf = sentimentElements.get("confidence");
            double confidence = conf.getAsDouble();
            JsonElement value = sentimentElements.get("value");
            String sentiment = value.getAsString(); //negative or positive
            if (sentiment.equals("negative")) { output = "What's wrong?"; }
            else { output = "That's good"; }
        }
        save(output + "\n");
        return output;
    }

    private void save(String data) {
        RealStoragePathLibrary realStoragePathLibrary = new RealStoragePathLibrary(context);
        File path = new File(realStoragePathLibrary.getInbuiltStorageAppSpecificDirectoryPath());
        File file = new File(path, "DATA.SAVE");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(data);
            bw.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String selectRandom(String[] array) { return array[(int) (Math.random() * array.length - 1)]; }
}
