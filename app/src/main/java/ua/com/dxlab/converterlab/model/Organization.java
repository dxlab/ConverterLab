
package ua.com.dxlab.converterlab.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable
public class Organization {

    @DatabaseField(id = true, columnName = "_id")
    private String id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String upperTitle;

    @DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true)
    private Region region;

    @DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true)
    private OrganizationType organizationType;

    @DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true)
    private City city;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String address;

    @DatabaseField
    private String link;

    private List<OrganizationCurrancy> mCurrencies;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.upperTitle = title.toUpperCase();
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region _region) {
        this.region = _region;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City _city) {
        this.city = _city;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String _phone) {
        this.phone = _phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String _address) {
        this.address = _address;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public OrganizationType getOrganizationType() {
        return this.organizationType;
    }

    public void setOrganizationType(OrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public List<OrganizationCurrancy> getCurrencies() {
        return this.mCurrencies;
    }

    public void setCurrencies(List<OrganizationCurrancy> _currencies) {
        this.mCurrencies = _currencies;
    }

    public String getUpperTitle() {
        return this.upperTitle;
    }

    public void setUpperTitle(String _upperTitle) {
        this.upperTitle = _upperTitle;
    }
}
