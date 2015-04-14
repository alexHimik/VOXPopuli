package kz.voxpopuli.voxapplication.fragments;

/**
 * Created by user on 26.03.15.
 */
public class RubricsFragment extends FaddingTitleBaseFragment {

    public static final String TAG = "RubricsFragment";
    public static final int FRAGMENT_ID = 1;

    public static final String PARALAX_IMAGE_HEADER_KEY = "parallax_header";
    public static final String LIST_DATA_KEY = "list_data";

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getFragmentId() {
        return FRAGMENT_ID;
    }
}
