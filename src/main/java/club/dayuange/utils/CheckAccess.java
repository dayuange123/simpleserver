package club.dayuange.utils;

import club.dayuange.exection.CheckExection;

public class CheckAccess {

    public static void  checkNull(Object o) throws CheckExection {
        if (o == null)
            throw new CheckExection("CheckAccess\n" +
                    "simpleserver.properties failuer,please check your properties!");

    }

    @Override
    public String toString() {
        return "";
    }
}