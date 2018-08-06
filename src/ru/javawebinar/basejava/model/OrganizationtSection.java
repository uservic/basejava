package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationtSection extends Section {

    private static final long serialVersionUID = 1L;
    private List<Organization> organizations;

    public OrganizationtSection() {
    }

    public OrganizationtSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationtSection(List<Organization> organizations) {
        this.organizations = Objects.requireNonNull(organizations, "organizations must not be null");
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationtSection that = (OrganizationtSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}