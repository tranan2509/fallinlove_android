package com.example.fallinlove.Model;

public class ItemDetail {
    public enum ItemType{
        PERSON, PERSON_DETAIL;
    }

    private Person person;
    private PersonDetail personDetail;
    private ItemType type;

    public ItemDetail() {
    }

    public ItemDetail(Person person, ItemType type) {
        this.person = person;
        this.type = type;
        this.personDetail = null;
    }

    public ItemDetail(PersonDetail personDetail, ItemType type) {
        this.personDetail = personDetail;
        this.type = type;
        this.person = null;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonDetail getPersonDetail() {
        return personDetail;
    }

    public void setPersonDetail(PersonDetail personDetail) {
        this.personDetail = personDetail;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
