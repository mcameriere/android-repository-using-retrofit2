package com.example.easyinvest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easyinvest.api.GroupedInvestmentProductsResponse;
import com.example.easyinvest.api.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Repository {
    private WebService service;
    private MutableLiveData<GroupedInvestmentProductsResponse> data = new MutableLiveData<>();

    public Repository(WebService service) {
        this.service = service;
    }

    public LiveData<GroupedInvestmentProductsResponse> getGroupedInvestmentProducts() {

        service.getGroupedInvestmentProducts().enqueue(new Callback<GroupedInvestmentProductsResponse>() {
            @Override
            public void onResponse(Call<GroupedInvestmentProductsResponse> call, Response<GroupedInvestmentProductsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GroupedInvestmentProductsResponse> call, Throwable t) {

            }
        });

        return data;
    }
}
