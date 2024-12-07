package ecnic.service.user.domain.models;

import ecnic.service.common.models.CommonData;

public class ContactInfo {
    
    public static class CommonDataContact extends CommonData<ContactType, ContactField> {
    
    }
    
    public enum ContactType {
        MOBILE,
        HOME,
        EMERGENCY,
        OTHER
    }
    
    public enum ContactField {
        VALUE,
        STATUS
    }
    
}
