package ch.keepcalm.web.component.price.converter;

/**
 * Created by hkesq on 21.07.2016.
 */
public class UiConverter {


    // TODO: 20.07.2016 move it to a helper class or converters
    public static String convertGender(String value) {
        switch (value) {
            case "m": // Mann
                return "1";
            case "w": // Frau
                return "2";
            case "b": // Baby
                return "3";
            default:
                return "-1";
        }
    }
}
