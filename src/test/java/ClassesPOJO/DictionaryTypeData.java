package ClassesPOJO;
import java.util.Objects;

public class DictionaryTypeData {
    public String title;
    public String type;
    public String nodeRef;

    public String description;
    public String itemType;
    public String attributeForShow;
    public Boolean plane;
    public String path;

    public DictionaryTypeData(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryTypeData dicName = (DictionaryTypeData) o;
        return Objects.equals(title, dicName.title) &&
                Objects.equals(type, dicName.type) &&
                Objects.equals(nodeRef, dicName.nodeRef) &&
                Objects.equals(description, dicName.description) &&
                Objects.equals(itemType, dicName.itemType) &&
                Objects.equals(attributeForShow, dicName.attributeForShow) &&
                Objects.equals(plane, dicName.plane) &&
                Objects.equals(path, dicName.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, nodeRef, description, itemType, attributeForShow, plane, path);
    }
}

