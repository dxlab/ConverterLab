
package ua.com.dxlab.converterlab.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OrganizationType {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String name;

    public void setId(String _id) {
        this.id = _id;
    }

    public String getId() {
        return id;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return name;
    }
}
