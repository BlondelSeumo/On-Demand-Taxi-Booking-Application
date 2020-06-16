package in.techware.lataxicustomer.adapter;

public class SearchResultsRecyclerAdapter {

   /* private static final CharacterStyle STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);
    private final Activity mContext;
    private SearchResultsRecyclerAdapterListener searchResultsRecyclerAdapterListener;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<AutocompletePrediction> mResultList;


    public SearchResultsRecyclerAdapter(Activity mContext, GoogleApiClient mGoogleApiClient, ArrayList<AutocompletePrediction> mResultList) {
        this.mContext = mContext;
        this.mGoogleApiClient = mGoogleApiClient;
        this.mResultList = mResultList;
    }

    public ArrayList<AutocompletePrediction> getmResultList() {
        return mResultList;
    }

    public void setmResultList(ArrayList<AutocompletePrediction> mResultList) {
        this.mResultList = mResultList;
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    @Override
    public SearchResultsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_search_results, parent, false);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(SearchResultsRecyclerAdapter.ViewHolder holder, int position) {
        setLayoutSearchResults(holder, position);
    }

    private void setLayoutSearchResults(final SearchResultsRecyclerAdapter.ViewHolder holder, final int position) {
        AutocompletePrediction item = mResultList.get(position);

        holder.txtPlace.setText(item.getPrimaryText(STYLE_NORMAL));
        holder.txtAddress.setText(item.getSecondaryText(STYLE_NORMAL));
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtPlace;
        private final TextView txtAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            txtPlace = (TextView) itemView.findViewById(R.id.txt_place);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);

            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {


                    AutocompletePrediction item = mResultList.get(getLayoutPosition());
                    String placeId = item.getPlaceId();
                    CharSequence primaryText = item.getPrimaryText(null);

                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                            .getPlaceById(mGoogleApiClient, placeId);
                    placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

                }
            });
        }
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }

            final CharSequence thirdPartyAttribution = places.getAttributions();

            places.release();
        }
    };


    public interface SearchResultsRecyclerAdapterListener {

        void onItemSelected(Place place);

        void onSnackBarShow(String message);

    }

    public SearchResultsRecyclerAdapterListener getSearchResultsRecyclerAdapterListener() {
        return searchResultsRecyclerAdapterListener;
    }

    public void setSearchResultsRecyclerAdapterListener(SearchResultsRecyclerAdapterListener searchResultsRecyclerAdapterListener) {
        this.searchResultsRecyclerAdapterListener = searchResultsRecyclerAdapterListener;
    }*/
}
