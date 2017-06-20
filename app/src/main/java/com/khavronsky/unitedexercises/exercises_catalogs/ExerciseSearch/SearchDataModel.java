package com.khavronsky.unitedexercises.exercises_catalogs.ExerciseSearch;

public class SearchDataModel implements Comparable<SearchDataModel> {

    public SearchDataModel(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    private String item;

    @Override
    public int compareTo(SearchDataModel o) {
        return item.compareTo(o.getItem());
    }
}
