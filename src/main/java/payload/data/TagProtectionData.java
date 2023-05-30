package payload.data;

import payload.pojo.TagProtectionPayload;

public class TagProtectionData {
    static TagProtectionPayload protectionPayload = new TagProtectionPayload();

    public static TagProtectionPayload createTagProtectionData(){
        protectionPayload.setPattern("v1.*");
      return protectionPayload;
    }
}
