package com.example.pivotal.boomerang_pivotal.model;

import com.example.pivotal.boomerang_pivotal.model.OpportunitiesMap;

public class OpportunitiesResponse {
    OpportunitiesMap _embedded;

    public OpportunitiesMap get_embedded() {
        return _embedded;
    }

    public void set_embedded(OpportunitiesMap _embedded) {
        this._embedded = _embedded;
    }
}
