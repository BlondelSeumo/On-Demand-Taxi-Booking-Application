package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.RecentSearchBean;

public interface RecentSearchListener {

    void onLoadCompleted(RecentSearchBean recentSearchBean);

    void onLoadFailed(String webErrorMsg);
}
