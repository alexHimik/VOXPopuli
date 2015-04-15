package kz.voxpopuli.voxapplication.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.TestFragmet;

/**
 * Created by user on 25.03.15.
 */
public class FragmentFactory {

    public static Fragment getFragment(FragmentManager fm, int fragmentId) {
        Fragment fr = fm.findFragmentById(fragmentId);
        return (fr != null) ? fr : createFragment(fragmentId);
    }

    private static Fragment createFragment(int fragmentId){
        switch (fragmentId){
            case RubricsFragment.FRAGMENT_ID: {
                return new RubricsFragment();
            }
            case CategoryFragment.FRAGMENT_ID: {
                return new CategoryFragment();
            }
            case TestFragmet.FRAGMENT_ID: {
                return new TestFragmet();
            }
            default: {
                return null;
            }
        }
    }
}
