package com.movieapp.android.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.movieapp.android.model.Cinema;
import com.movieapp.android.model.LocationPosition;

import java.lang.reflect.Type;
import java.util.UUID;

public class CinemaDeserializer implements JsonDeserializer<Cinema> {

    @Override
    public Cinema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        UUID id = UUID.fromString(json.getAsJsonObject().get("id").getAsString());
        String name = json.getAsJsonObject().get("name").getAsString();
        String address = json.getAsJsonObject().get("address").getAsString();
        double latitude = json.getAsJsonObject().getAsJsonObject().get("latitude").getAsDouble();
        double longitude = json.getAsJsonObject().getAsJsonObject().get("longitude").getAsDouble();
        LocationPosition location = new LocationPosition(latitude, longitude);
        return new Cinema(id, name, address, location);
    }

}
