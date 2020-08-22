package ru.rsmu.reque.utils;

/**
 * @author leonid.
 */
public class RuDateHelper {

    public static String genetiveDay( String date ) {
        String result = date.replaceAll( "(сред|пятниц|суббот)а", "$1у" );
        result = result.replaceAll( "в (вторник)", "во $1" );
        return result;
    }
}
