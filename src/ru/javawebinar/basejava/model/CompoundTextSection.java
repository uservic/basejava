package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompoundTextSection implements Section {
    private final List<OrganisationData> organisationDataList = new ArrayList<>();

    public void addContent(OrganisationData data) {
        organisationDataList.add(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundTextSection that = (CompoundTextSection) o;
        return Objects.equals(organisationDataList, that.organisationDataList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(organisationDataList);
    }

    @Override
    public String toString() {
        return organisationDataList.toString();
    }
}