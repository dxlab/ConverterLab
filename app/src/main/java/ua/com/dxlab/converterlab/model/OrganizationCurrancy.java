package ua.com.dxlab.converterlab.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Dima on 15.09.2015.
 */
@DatabaseTable
public class OrganizationCurrancy {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true)
    private Organization organization;

    @DatabaseField(canBeNull = false, foreign = true,foreignAutoRefresh = true)
    private Currency currency;

    @DatabaseField
    private String ask;

    @DatabaseField
    private String bid;

    @DatabaseField
    private String oldAsk;

    @DatabaseField
    private String oldBid;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer _id) {
        this.id = _id;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization _organization) {
        this.organization = _organization;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency _currency) {
        this.currency = _currency;
    }

    public void setAsk(String _ask) {
        this.ask = _ask;
    }

    public String getAsk() {
        return ask;
    }

    public void setBid(String _bid) {
        this.bid = _bid;
    }

    public String getBid() {
        return bid;
    }

    public void setOldAsk(String _oldAsk) {
        this.oldAsk = _oldAsk;
    }

    public String getOldAsk() {
        return oldAsk;
    }

    public void setOldBid(String _oldBid) {
        this.oldBid = _oldBid;
    }

    public String getOldBid() {
        return oldBid;
    }
}
