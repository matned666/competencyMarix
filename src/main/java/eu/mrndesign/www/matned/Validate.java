package eu.mrndesign.www.matned;

public class Validate {

    public static boolean isLong (String longNo){
        try{
            Long l = Long.parseLong(longNo);
            return true;
        }catch (NumberFormatException ignored){
            return false;
        }
    }
}
