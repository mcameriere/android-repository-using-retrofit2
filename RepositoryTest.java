package com.example.easyinvest;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.easyinvest.api.CustodyAccount;
import com.example.easyinvest.api.CustodyGroup;
import com.example.easyinvest.api.GroupedInvestmentProducts;
import com.example.easyinvest.api.GroupedInvestmentProductsResponse;
import com.example.easyinvest.api.OwnGroup;
import com.example.easyinvest.api.WebService;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutor = new InstantTaskExecutorRule();

    @Test
    public void testRepository() {
        WebService webService = mock(WebService.class);
        final Call<GroupedInvestmentProductsResponse> mockedCall = mock(Call.class);
        when(webService.getGroupedInvestmentProducts()).thenReturn(mockedCall);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Callback<GroupedInvestmentProductsResponse> callback = invocation.getArgument(0);
                callback.onResponse(mockedCall, Response.success(aFakeResponse()));
                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));
        Repository repository = new Repository(webService);
        GroupedInvestmentProductsResponse response = repository.getGroupedInvestmentProducts().getValue();
        String productName = response.value.own.custody.investmentProducts[0].productName;

        assertEquals("Custody Account", productName);
    }

    private GroupedInvestmentProductsResponse aFakeResponse() {
        GroupedInvestmentProductsResponse fakeResponse = new GroupedInvestmentProductsResponse();
        fakeResponse.value = new GroupedInvestmentProducts();
        fakeResponse.value.own = new OwnGroup();
        fakeResponse.value.own.custody = new CustodyGroup();
        fakeResponse.value.own.custody.investmentProducts = new CustodyAccount[1];
        fakeResponse.value.own.custody.investmentProducts[0] = new CustodyAccount();
        fakeResponse.value.own.custody.investmentProducts[0].productName = "Custody Account";
        return fakeResponse;
    }
}
