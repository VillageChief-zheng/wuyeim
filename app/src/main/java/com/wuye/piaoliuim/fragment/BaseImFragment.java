package com.wuye.piaoliuim.fragment;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chuange.basemodule.BaseApplication;
import com.chuange.basemodule.view.DialogView;

/**
 * @ClassName BaseImFragment
 * @Description
 * @Author VillageChief
 * @Date 2020/1/2 10:32
 */
public class BaseImFragment  extends Fragment {
    public void forward(Fragment fragment, boolean hide) {
        forward(getId(), fragment, null, hide);
    }

    public void forward(int viewId, Fragment fragment, String name, boolean hide) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        if (hide) {
            trans.hide(this);
            trans.add(viewId, fragment);
        } else {
            trans.replace(viewId, fragment);
        }

        trans.addToBackStack(name);
        trans.commitAllowingStateLoss();
    }

    public void backward() {
        getFragmentManager().popBackStack();
    }
    public DialogView loading(String msg) {
        return ((BaseApplication) getActivity().getApplication()).loading(getActivity(), msg);
    }

    public DialogView loading(int layout) {
        return ((BaseApplication) getActivity().getApplication()).loading(getActivity(), layout);
    }

    public DialogView loading(int layout, DialogView.DialogViewListener dialogViewListener) {
        return ((BaseApplication) getActivity().getApplication()).loading(getActivity(), layout, dialogViewListener);
    }
    public void cancelLoading() {

        ((BaseApplication)getActivity().getApplication()).cancelLoading();
    }
}
