
package ua.com.dxlab.converterlab.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Region {


    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String upperName;

    public void setId(String _id) {
        this.id = _id;
    }

    public String getId() {
        return id;
    }

    public void setName(String _name) {
        this.name = _name;
        this.upperName = _name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getUpperName() {
        return this.upperName;
    }

    public void setUpperName(String _upperName) {
        this.upperName = _upperName;
    }
}
