package com.example.fallinlove.Activity.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fallinlove.Model.Person;

import java.util.List;

public class PersonDetailPageAdapter  extends FragmentPagerAdapter {
    int tabCount = 0;
    Person person;
    List<Person> persons;
    public PersonDetailPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public PersonDetailPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    public PersonDetailPageAdapter(@NonNull FragmentManager fm, int behavior, Person person) {
        super(fm, behavior);
        tabCount = behavior;
        this.person = person;
    }

    public PersonDetailPageAdapter(@NonNull FragmentManager fm, int behavior, List<Person> persons) {
        super(fm, behavior);
        tabCount = behavior;
        this.persons = persons;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new PersonDetailMaleFragment(persons.get(0));
            case 1: return new PersonDetailFragment(persons.get(1));
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
