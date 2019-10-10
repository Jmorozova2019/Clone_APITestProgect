package ClassesPOJO;

import org.joda.time.DateTime;

/**
 * POJO-класс для объекта Тип документа
 * Геттеры только для тех полей, которые нужно запрашивать
 */

public class DocumentTypeItem{
    public String nodeRef;
    String type;
    String page;
    DateTime createdOn;
    CreatedBy createdBy;
    DateTime modifiedOn;
    ModifiedBy modifiedBy;
    Permissions permissions;
    public ItemDada itemData;

    public String getItemDataProp_cm_nameValue() { return itemData.getProp_cm_nameValue(); }
    public String getProp_cm_nameDisplayValue()  { return itemData.getProp_cm_nameDisplayValue(); }
}

class CreatedBy {
    String value;
    String displayValue;
}

class ModifiedBy{
    String value;
    String displayValue;
}

class ItemDada{
    Prop_cm_name prop_cm_name;
    Prop_lecm_dic_active prop_lecm_dic_active;

    String getProp_cm_nameValue(){ return prop_cm_name.value; }
    String getProp_cm_nameDisplayValue(){ return prop_cm_name.displayValue; }
}

class Prop_cm_name{
    String value;
    String displayValue;
}

class Prop_lecm_dic_active{
    Boolean value;
    Boolean displayValue;
}

