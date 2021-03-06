package POJO.referenceBooks.documentKind;

import java.util.Objects;

public class DocumentKindTreeItem {
    public String title;
    public String type;
    public String childType;
    public String nodeRef;
    public String isLeaf;

    public DocumentKindTreeItem(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentKindTreeItem that = (DocumentKindTreeItem) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(type, that.type) &&
                Objects.equals(childType, that.childType) &&
                Objects.equals(nodeRef, that.nodeRef) &&
                Objects.equals(isLeaf, that.isLeaf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, childType, nodeRef, isLeaf);
    }
}

