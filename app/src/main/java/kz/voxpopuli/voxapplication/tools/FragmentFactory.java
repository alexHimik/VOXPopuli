package kz.voxpopuli.voxapplication.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.ForgotPasswordFragment;
import kz.voxpopuli.voxapplication.fragments.LoginFragment;
import kz.voxpopuli.voxapplication.fragments.RegistrationFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.SearchFragment;
import kz.voxpopuli.voxapplication.fragments.SettingsFragment;

/**
 * Created by user on 25.03.15.
 */
public class FragmentFactory {

    public static Fragment getFragment(FragmentManager fm, int fragmentId) {
        Fragment fr = fm.findFragmentById(fragmentId);
        return (fr != null) ? fr : createFragment(fragmentId);
    }

    private static Fragment createFragment(int fragmentId) {
        switch (fragmentId){
            case RubricsFragment.FRAGMENT_ID: {
                return new RubricsFragment();
            }
            case CategoryFragment.FRAGMENT_ID: {
                return new CategoryFragment();
            }
            case SearchFragment.FRAGMENT_ID: {
                return new SearchFragment();
            }
            case LoginFragment.FRAGMENT_ID: {
                return new LoginFragment();
            }
            case ForgotPasswordFragment.FRAGMENT_ID: {
                return new ForgotPasswordFragment();
            }
            case RegistrationFragment.FRAGMENT_ID: {
                return new RegistrationFragment();
            }
            case SettingsFragment.FRAGMENT_ID: {
                return new SettingsFragment();
            }
            default: {
                return null;
            }
        }
    }
}
