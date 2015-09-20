package ua.com.dxlab.converterlab.service;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ua.com.dxlab.converterlab.R;
import ua.com.dxlab.converterlab.database.CurrencyDatabase;
import ua.com.dxlab.converterlab.model.City;
import ua.com.dxlab.converterlab.model.Currency;
import ua.com.dxlab.converterlab.model.Organization;
import ua.com.dxlab.converterlab.model.OrganizationCurrancy;
import ua.com.dxlab.converterlab.model.OrganizationType;
import ua.com.dxlab.converterlab.model.PublicCurrency;
import ua.com.dxlab.converterlab.model.PublicOrganization;
import ua.com.dxlab.converterlab.model.Region;

/**
 * Created by Dima on 16.09.2015.
 */
public class CurrencyService {

    private Context mContext;

    private CurrencyDatabase mDb;

    public CurrencyService(Context _context) {


        this.mContext = _context;
        this.mDb = new CurrencyDatabase(_context);
    }

    public void update(PublicCurrency _publicCurrency) {
        PreferencesService service = new PreferencesService(mContext);
        //if (!_publicCurrency.getDate().equals(service.getLatestUpdateDate())) {
            doUpdate(_publicCurrency);
            service.setLatestUpdateDate(_publicCurrency.getDate());
        //}
    }

    private void doUpdate(PublicCurrency _publicCurrency) {
        List<City> cities = convertCities(_publicCurrency);
        List<Region> regions = convertRegions(_publicCurrency);
        List<Currency> currencies = convertCurrency(_publicCurrency);
        List<OrganizationType> organizationTypes = convertOrganizationTypes(_publicCurrency);
        List<Organization> organizations = convertOrganizations(_publicCurrency);
        updateEntities(cities, City.class);
        updateEntities(regions, Region.class);
        updateEntities(organizationTypes, OrganizationType.class);
        updateEntities(currencies, Currency.class);
        updateOrganization(organizations, currencies);
    }

    private void updateOrganization(List<Organization> _organizations, List<Currency> _currencies) {
        RuntimeExceptionDao<Organization, String> dao = mDb.getRuntimeExceptionDao(Organization.class);
        RuntimeExceptionDao<OrganizationCurrancy, String> curDao = mDb.getRuntimeExceptionDao(OrganizationCurrancy.class);

        for (Organization city : _organizations) {
            dao.createOrUpdate(city);
            List<OrganizationCurrancy> newCurrencies = city.getCurrencies();
            for (OrganizationCurrancy newCurrency : newCurrencies) {
                OrganizationCurrancy matchCurrancy = new OrganizationCurrancy();
                matchCurrancy.setOrganization(city);
                matchCurrancy.setCurrency(newCurrency.getCurrency());
                List<OrganizationCurrancy> organizationCurrancies = curDao.queryForMatching(matchCurrancy);
                if (organizationCurrancies.isEmpty()) {
                    curDao.createOrUpdate(newCurrency);
                } else {
                    OrganizationCurrancy currentCurrency = organizationCurrancies.get(0);
                    currentCurrency.setOldAsk(currentCurrency.getAsk());
                    currentCurrency.setOldBid(currentCurrency.getBid());
                    currentCurrency.setAsk(newCurrency.getAsk());
                    currentCurrency.setBid(newCurrency.getBid());
                    curDao.createOrUpdate(currentCurrency);
                }
            }
        }
    }

    private List<Organization> convertOrganizations(PublicCurrency _publicCurrency) {
        List<Organization> organizations = new ArrayList<>();
        List<PublicOrganization> publicOrganizations = _publicCurrency.getOrganizations();
        for (PublicOrganization publicOrganization : publicOrganizations) {

            Organization organization = new Organization();
            organization.setAddress(publicOrganization.getAddress());
            organization.setPhone(publicOrganization.getPhone());
            organization.setId(publicOrganization.getId());
            organization.setTitle(publicOrganization.getTitle());
            organization.setLink(publicOrganization.getLink());

            City city = new City();
            city.setId(publicOrganization.getCityId());
            organization.setCity(city);

            Region region = new Region();
            String regionId = publicOrganization.getRegionId();
            region.setId(regionId);
            organization.setRegion(region);

            OrganizationType type = new OrganizationType();
            type.setId(publicOrganization.getOrgType().toString());
            organization.setOrganizationType(type);

            List<OrganizationCurrancy> organizationCurrancies = new ArrayList<>();
            HashMap<String, HashMap<String, String>> map = publicOrganization.getCurrencies();
            for (String s : map.keySet()) {
                HashMap<String, String> hashMap = map.get(s);
                OrganizationCurrancy organizationCurrancy = new OrganizationCurrancy();
                organizationCurrancy.setAsk(hashMap.get("ask"));
                organizationCurrancy.setBid(hashMap.get("bid"));
                Currency currency = new Currency();
                currency.setId(s);
                organizationCurrancy.setCurrency(currency);
                organizationCurrancy.setOrganization(organization);
                organizationCurrancies.add(organizationCurrancy);
            }

            organization.setCurrencies(organizationCurrancies);
            organizations.add(organization);
        }
        return organizations;
    }


    private List<City> convertCities(PublicCurrency _currency) {
        HashMap<String, String> citiesMap = _currency.getCities();
        List<City> cities = new ArrayList<>();
        for (String s : citiesMap.keySet()) {
            City city = new City();
            city.setId(s);
            city.setName(citiesMap.get(s).toString());
            cities.add(city);
        }
        return cities;
    }

    private List<Currency> convertCurrency(PublicCurrency _currency) {
        HashMap<String, String> citiesMap = _currency.getCurrencies();
        List<Currency> cities = new ArrayList<>();
        for (String s : citiesMap.keySet()) {
            Currency city = new Currency();
            city.setId(s);
            city.setName(citiesMap.get(s).toString());
            cities.add(city);
        }
        return cities;
    }

    private List<Region> convertRegions(PublicCurrency _currency) {
        HashMap<String, String> citiesMap = _currency.getRegions();
        List<Region> cities = new ArrayList<>();
        for (String s : citiesMap.keySet()) {
            Region city = new Region();
            city.setId(s);
            city.setName(citiesMap.get(s).toString());
            cities.add(city);
        }
        return cities;
    }

    private List<OrganizationType> convertOrganizationTypes(PublicCurrency _currency) {
        HashMap<String, String> citiesMap = _currency.getOrgTypes();
        List<OrganizationType> cities = new ArrayList<>();
        for (String s : citiesMap.keySet()) {
            OrganizationType city = new OrganizationType();
            city.setId(s);
            city.setName(citiesMap.get(s).toString());
            cities.add(city);
        }
        return cities;
    }

    private <T> void updateEntities(List<T> _cities, Class<T> _t) {
        RuntimeExceptionDao<T, String> dao = mDb.getRuntimeExceptionDao(_t);
        for (T city : _cities) {
            dao.createOrUpdate(city);
        }
    }

    public SQLiteCursor createOrganizationCursor() {
        SQLiteCursor cursor = null;
        try {
            String query = mContext.getString(R.string.organization_cursor);
            cursor = (SQLiteCursor) mDb.getReadableDatabase().rawQuery(query, new String[]{"%%"});
        } catch (Exception e) {
            Log.e(CurrencyService.class.getSimpleName(), e.toString(), e);
        }

        return cursor;
    }

    public Organization getOrganization(String _id) {
        RuntimeExceptionDao<Organization, String> dao = mDb.getRuntimeExceptionDao(Organization.class);
        RuntimeExceptionDao<OrganizationCurrancy, String> curDao = mDb.getRuntimeExceptionDao(OrganizationCurrancy.class);
        Organization organization = dao.queryForId(_id);
        OrganizationCurrancy matchCurrancy = new OrganizationCurrancy();
        matchCurrancy.setOrganization(organization);
        List<OrganizationCurrancy>  currancies = curDao.queryForMatching(matchCurrancy);
        organization.setCurrencies(currancies);
        return organization;
    }
}

