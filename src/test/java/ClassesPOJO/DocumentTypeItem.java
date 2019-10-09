package ClassesPOJO;

/**
 * Класс-описатель для объекта Тип документа
 * Геттеры только для тех полей, которые нужно запрашивать
 */
public class DocumentTypeItem{
    String nodeRef;//	workspace://SpacesStore/a5bb15fd-2f05-4037-9d0b-a1f95d641fd6
    String type;//	lecm-doc-dic-dt:typeDictionary
    String page;//	document
    String createdOn;//	2017-12-14T12:44:11.556+03:00 - or Datetime?
    CreatedBy createdBy;
    String modifiedOn;// 2019-10-03T23:23:43.328+03:00 - or Datetime?
    ModifiedBy modifiedBy;

    Permissions permissions;
    ItemDada itemData;

    public String getItemDataProp_cm_nameValue() { return itemData.getProp_cm_nameValue(); }
    public String getProp_cm_nameDisplayValue() { return itemData.getProp_cm_nameDisplayValue(); }
}

class CreatedBy {
    String value;//	System
    String displayValue;//	System
}

class ModifiedBy{
    String value;//	System
    String displayValue;//	System
}

class ItemDada{
    Prop_cm_name prop_cm_name;
    Prop_lecm_dic_active prop_lecm_dic_active;

    String getProp_cm_nameValue(){ return prop_cm_name.value; }

    String getProp_cm_nameDisplayValue(){ return prop_cm_name.displayValue; }
}

class Prop_cm_name{
    String value;//	Входящий документ
    String displayValue;//	Входящий документ
}

class Prop_lecm_dic_active{
    Boolean value;
    Boolean displayValue;
}

