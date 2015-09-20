package ua.com.dxlab.converterlab.adapter;

import android.database.Cursor;

/**
 * Created by Dima on 16.09.2015.
 */
public class OrganizationListItem {
    private String title;
    private String region;
    private String city;
    private String phone;
    private String address;
    private String id;

    public static OrganizationListItem fromCursor(Cursor cursor) {
        OrganizationListItem organizationListItem = new OrganizationListItem();
        organizationListItem.setId(cursor.getString(cursor.getColumnIndex("_id")));
        organizationListItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        organizationListItem.setRegion(cursor.getString(cursor.getColumnIndex("region")));
        organizationListItem.setCity(cursor.getString(cursor.getColumnIndex("city")));
        organizationListItem.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        organizationListItem.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        return organizationListItem;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
