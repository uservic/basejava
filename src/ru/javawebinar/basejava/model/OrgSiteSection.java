package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;

public class OrgSiteSection implements Section {
    private Map<String, String> sites = new HashMap<>();

    public OrgSiteSection(String orgTitle, String webSite) {
        sites.put(orgTitle, webSite);
    }

    public String getContent() {
        return sites.toString();
    }

    public void addContent(Map<String, String> pairOrgSite) {
        sites.putAll(pairOrgSite);
    }

    @Override
    public String toString() {
        return getContent();
    }
}