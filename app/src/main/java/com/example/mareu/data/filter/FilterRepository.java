package com.example.mareu.data.filter;

import androidx.lifecycle.MutableLiveData;

import com.example.mareu.ui.meetingfilter.FilterState;

/**
 * This repository contain the current filter for the meeting list
 */
public class FilterRepository {

    private final MutableLiveData<FilterState> mFilterState = new MutableLiveData<>();

    /**
     * Get filter state
     *
     * @return Filter state live data
     */
    public MutableLiveData<FilterState> getFilterState() {
        return this.mFilterState;
    }

    /**
     * Define a new {@link FilterState}
     *
     * @param filterState Filter state
     */
    public void setFilterState(FilterState filterState) {
        this.mFilterState.setValue(filterState);
    }

    /**
     * Clear current filters
     */
    public void clearFilters() {
        this.mFilterState.setValue(null);
    }

}
