package POJO.referenceBooks.documentKind;

import java.util.ArrayList;


/**
 * POJO-класс для описания списка видов документов
 */
public class DocumentKindListData {
    public Boolean versionable;
    public int totalRecords;
    public int startIndex;
    public Metadata metadata;
    public ArrayList<DocumentKindItem> items;

    public ArrayList<DocumentKindItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DocumentKindItem> items) {
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



