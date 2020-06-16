package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.PromoCodeBean;

public interface PromoCodeListener {

    void onLoadCompleted(PromoCodeBean promoCodeBean);

    void onLoadFailed(String error);

}
