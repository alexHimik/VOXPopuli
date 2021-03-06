package kz.voxpopuli.voxapplication.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import kz.voxpopuli.voxapplication.dialog.ProgressFragment;
import kz.voxpopuli.voxapplication.fragments.About;
import kz.voxpopuli.voxapplication.fragments.CategoryFragment;
import kz.voxpopuli.voxapplication.fragments.ChangePasswordFragment;
import kz.voxpopuli.voxapplication.fragments.EditUserProfileFragment;
import kz.voxpopuli.voxapplication.fragments.CommentsListFragment;
import kz.voxpopuli.voxapplication.fragments.ForgotPasswordFragment;
import kz.voxpopuli.voxapplication.fragments.InfoFragment;
import kz.voxpopuli.voxapplication.fragments.LoginFragment;
import kz.voxpopuli.voxapplication.fragments.NewsPageFragment;
import kz.voxpopuli.voxapplication.fragments.RegistrationFragment;
import kz.voxpopuli.voxapplication.fragments.RubricsFragment;
import kz.voxpopuli.voxapplication.fragments.RulesFragment;
import kz.voxpopuli.voxapplication.fragments.SearchFragment;
import kz.voxpopuli.voxapplication.fragments.SettingsFragment;
import kz.voxpopuli.voxapplication.fragments.UserProfileFragment;
import kz.voxpopuli.voxapplication.fragments.VacanciesFragment;
import kz.voxpopuli.voxapplication.fragments.VersionFragment;

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
            case UserProfileFragment.FRAGMENT_ID: {
                return new UserProfileFragment();
            }
            case EditUserProfileFragment.FRAGMENT_ID: {
                return new EditUserProfileFragment();
            }
            case CommentsListFragment.FRAGMENT_ID: {
                return new CommentsListFragment();
            }
            case NewsPageFragment.FRAGMENT_ID: {
                return new NewsPageFragment();
            }
            case ChangePasswordFragment.FRAGMENT_ID: {
                return new ChangePasswordFragment();
            }
            case ProgressFragment.FRAGMENT_ID: {
                return new ProgressFragment();
            }
            case About.FRAGMENT_ID: {
                return new About();
            }
            case RulesFragment.FRAGMENT_ID: {
                return new RulesFragment();
            }
            case VacanciesFragment.FRAGMENT_ID: {
                return new VacanciesFragment();
            }
            case VersionFragment.FRAGMENT_ID: {
                return new VersionFragment();
            }
            case InfoFragment.FRAGMENT_ID: {
                return new InfoFragment();
            }
            default: {
                return null;
            }
        }
    }
}
