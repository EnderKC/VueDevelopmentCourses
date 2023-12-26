package top.qwwq.service.util;

import java.util.UUID;

public class GetUUID {
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return uuidStr.replace("-", "");
    }
}
