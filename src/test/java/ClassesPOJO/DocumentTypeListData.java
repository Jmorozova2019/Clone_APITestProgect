package ClassesPOJO;

import java.util.ArrayList;


/**
 * POJO-класс для описания списка видов документов
 */
public class DocumentTypeListData {
    public Boolean versionable;
    public int totalRecords;
    public int startIndex;
    public Metadata metadata;
    public ArrayList<DocumentTypeItem> items;

    public ArrayList<DocumentTypeItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DocumentTypeItem> items) {
        this.items = items;
    }
}

class Metadata	{
    Permissions permissions;
}

class Permissions{
    UserAccess userAccess;
}

class UserAccess{
    Boolean create;
    Boolean edit;
    Boolean delete;
}



