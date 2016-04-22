package com.justbuyit.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

    private String email;
    private String language;
    private String firstName;
    private String lastName;
    private String openId;
    private String uuid;
    private boolean authenticated;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((openId == null) ? 0 : openId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (openId == null) {
            if (other.openId != null)
                return false;
        } else if (!openId.equals(other.openId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [email=").append(email).append(", language=").append(language).append(", firstName=").append(firstName).append(", lastName=")
                .append(lastName).append(", openId=").append(openId).append(", uuid=").append(uuid).append("]");
        return builder.toString();
    }
    
}
