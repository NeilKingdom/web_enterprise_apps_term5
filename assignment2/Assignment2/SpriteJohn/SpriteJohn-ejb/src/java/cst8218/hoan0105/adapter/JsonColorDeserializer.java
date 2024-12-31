/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.hoan0105.adapter;

import java.awt.Color;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser.Event;

/**
 *json deserializer uses Json Parser
 * @author J-PC
 */
public class JsonColorDeserializer implements JsonbDeserializer<Color> {
    @Override
    public Color deserialize(javax.json.stream.JsonParser parser, javax.json.bind.serializer.DeserializationContext ctx,
        java.lang.reflect.Type rtType) {
        String keyname = ""; int value = 0; int red = 0; int green = 0; int blue = 0;
    while (parser.hasNext()) {
        Event event = parser.next();
        switch (event) {
            case KEY_NAME: {
                keyname = parser.getString();
                break;
                }
            case VALUE_NUMBER: {
                value = parser.getInt();
                if (keyname.equals("red")) red = value;
                else if (keyname.equals("green")) green = value;
                else if (keyname.equals("blue")) blue = value;
                break;
                }
            }
    }
        return new Color(red,green,blue);
    }
}
