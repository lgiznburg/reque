package ru.rsmu.reque.utils;

/**
 * @author leonid.
 */
public class RuDateHelper {

    public static String genetiveDay( String date ) {
        return date.replaceAll( "(сред|пятниц|суббот)а", "$1у" );
    }
}
