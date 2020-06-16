package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.SearchResultsBean;

public interface SearchResultsListener {

    void onLoadCompleted(SearchResultsBean searchResultsBean);

    void onLoadFailed(String webErrorMsg);
}
