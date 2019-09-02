# android-repository-using-retrofit2

Unit testing how to make an *asynchronous* Retrofit call *synchronous* in the unit tests ?

## Add Library Dependencies for Retrofit

    dependencies {
        ...
        implementation 'com.squareup.retrofit2:retrofit:2.5.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
        implementation 'com.google.code.gson:gson:2.8.5'
        testImplementation 'org.mockito:mockito-core:1.10.19'
        testImplementation 'android.arch.core:core-testing:1.1.1'
    }

## Adding android.arch.code-core-testing:1.1.1 allows you to add the following to the RepositoryTest class:

    @Rule
    public InstantTaskExecutorRule instantTaskExecutor = new InstantTaskExecutorRule();

For some obscure reason it also allows you to use the following:

    Callback<GroupedInvestmentProductsResponse> callback = invocation.getArgument(0);
    
instead of the ugly casting:

    Callback<GroupedInvestmentProductsResponse> callback = (Callback<GroupedInvestmentProductsResponse>) invocation.getArguments()[0]

## Adding org.mockito:mockito-core:1.10.19 allows you to use Mockito static methods like:

### mock

    WebService webService = mock(WebService.class);

### when

    when(webService.getGroupedInvestmentProducts()).thenReturn(mockedCall);

### doAnswer and any

    doAnswer(new Answer() {
        @Override
        public Object answer(InvocationOnMock invocation) {
            Callback<GroupedInvestmentProductsResponse> callback = invocation.getArgument(0);
            callback.onResponse(mockedCall, Response.success(aFakeResponse()));
            return null;
        }
    }).when(mockedCall).enqueue(any(Callback.class));

