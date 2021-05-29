package model.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import model.Niveau;

public class NiveauConverter {

    @TypeConverter
    public String fromNiveauToJson(Niveau niveau) {
        if (niveau == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Niveau>() {}.getType();
        String json = gson.toJson(niveau, type);
        return json;
    }

    @TypeConverter
    public Niveau toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Niveau>() {}.getType();
        Niveau niveau = gson.fromJson(countryLangString, type);
        return niveau;
    }
}
