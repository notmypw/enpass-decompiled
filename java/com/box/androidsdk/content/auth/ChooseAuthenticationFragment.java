package com.box.androidsdk.content.auth;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.box.androidsdk.content.auth.AuthenticatedAccountsAdapter.DifferentAuthenticationInfo;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.sdk.android.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ChooseAuthenticationFragment extends Fragment {
    private static final String EXTRA_BOX_AUTHENTICATION_INFOS = "boxAuthenticationInfos";
    private ListView mListView;

    public interface OnAuthenticationChosen {
        void onAuthenticationChosen(BoxAuthenticationInfo boxAuthenticationInfo);

        void onDifferentAuthenticationChosen();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<BoxAuthenticationInfo> infos = getAuthenticationInfoList();
        View view = inflater.inflate(R.layout.boxsdk_choose_auth_activity, null);
        this.mListView = (ListView) view.findViewById(R.id.boxsdk_accounts_list);
        if (infos == null) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        } else {
            this.mListView.setAdapter(new AuthenticatedAccountsAdapter(getActivity(), R.layout.boxsdk_list_item_account, infos));
            this.mListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getAdapter() instanceof AuthenticatedAccountsAdapter) {
                        BoxAuthenticationInfo info = ((AuthenticatedAccountsAdapter) parent.getAdapter()).getItem(position);
                        if (info instanceof DifferentAuthenticationInfo) {
                            if (ChooseAuthenticationFragment.this.getActivity() instanceof OnAuthenticationChosen) {
                                ((OnAuthenticationChosen) ChooseAuthenticationFragment.this.getActivity()).onDifferentAuthenticationChosen();
                            }
                        } else if (ChooseAuthenticationFragment.this.getActivity() instanceof OnAuthenticationChosen) {
                            ((OnAuthenticationChosen) ChooseAuthenticationFragment.this.getActivity()).onAuthenticationChosen(info);
                        }
                    }
                }
            });
        }
        return view;
    }

    public ArrayList<BoxAuthenticationInfo> getAuthenticationInfoList() {
        ArrayList<BoxAuthenticationInfo> arrayList;
        Iterator it;
        if (getArguments() == null || getArguments().getCharSequenceArrayList(EXTRA_BOX_AUTHENTICATION_INFOS) == null) {
            Map<String, BoxAuthenticationInfo> map = BoxAuthentication.getInstance().getStoredAuthInfo(getActivity());
            if (map == null) {
                return null;
            }
            arrayList = new ArrayList(map.size());
            for (String key : map.keySet()) {
                arrayList.add(map.get(key));
            }
            return arrayList;
        }
        ArrayList<CharSequence> jsonSerialized = getArguments().getCharSequenceArrayList(EXTRA_BOX_AUTHENTICATION_INFOS);
        arrayList = new ArrayList(jsonSerialized.size());
        it = jsonSerialized.iterator();
        while (it.hasNext()) {
            CharSequence sequence = (CharSequence) it.next();
            BoxAuthenticationInfo info = new BoxAuthenticationInfo();
            info.createFromJson(sequence.toString());
            arrayList.add(info);
        }
        return arrayList;
    }

    public static ChooseAuthenticationFragment createAuthenticationActivity(Context context) {
        return new ChooseAuthenticationFragment();
    }

    public static ChooseAuthenticationFragment createChooseAuthenticationFragment(Context context, ArrayList<BoxAuthenticationInfo> listOfAuthInfo) {
        ChooseAuthenticationFragment fragment = createAuthenticationActivity(context);
        Bundle b = fragment.getArguments();
        if (b == null) {
            b = new Bundle();
        }
        ArrayList<CharSequence> jsonSerialized = new ArrayList(listOfAuthInfo.size());
        Iterator it = listOfAuthInfo.iterator();
        while (it.hasNext()) {
            jsonSerialized.add(((BoxAuthenticationInfo) it.next()).toJson());
        }
        b.putCharSequenceArrayList(EXTRA_BOX_AUTHENTICATION_INFOS, jsonSerialized);
        fragment.setArguments(b);
        return fragment;
    }
}
