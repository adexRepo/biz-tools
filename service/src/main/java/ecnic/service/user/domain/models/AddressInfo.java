package ecnic.service.user.domain.models;

import ecnic.service.common.models.CommonData;

public class AddressInfo {
    
    public static class CommonDataAddress extends CommonData<AddressType, AddressField> {
    }
    
    public enum AddressType {
        HOME,
        DOMICILE,
        OFFICE,
        FAMILY
    }
    
    public enum AddressField {
        ADDRESS_LINE,
        BUILDING_NO,
        UNIT_NO,
        FLOOR,
        STREET,
        CITY,
        DISTRICT,
        TOWN,
        STATE_CODE,
        POSTAL_CODE,
        COUNTRY_CODE,
        PO_BOX
    }
    
}
