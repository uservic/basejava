package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;

public class OrgSiteSection implements Section {
//    private String orgTitle;
//    private String webSite;
    private Map<String, String> sites = new HashMap<>();

    public OrgSiteSection(String orgTitle, String webSite) {
//        this.orgTitle = orgTitle;
//        this.webSite = webSite;
        sites.put(orgTitle, webSite);
    }

    @Override
    public String getContent() {
        return sites.toString();
    }

    public void addContent(Object pairOrgSite) {
        sites.putAll((Map<String, String>) pairOrgSite);
    }
}