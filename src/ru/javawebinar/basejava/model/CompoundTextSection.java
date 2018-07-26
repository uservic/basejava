package ru.javawebinar.basejava.model;

import java.util.List;

public class CompoundTextSection implements Section {
    private final List<OrganisationData> organisationDataList;

    public CompoundTextSection(List<OrganisationData> organisationDataList) {
        this.organisationDataList = organisationDataList;
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (OrganisationData od : organisationDataList) {
            sb.append(od.getOrganisationData()).append(" ");
        }
        return sb.toString();
    }

    public void addContent(OrganisationData organisationData) {
        organisationDataList.add(organisationData);
    }

    @Override
    public String toString() {
        return getContent();
    }
}