package com.box.androidsdk.content.auth;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.box.androidsdk.content.auth.BoxAuthentication.BoxAuthenticationInfo;
import com.box.androidsdk.content.utils.BoxLogUtils;
import com.box.androidsdk.content.utils.SdkUtils;
import com.box.sdk.android.R;
import java.util.List;

public class AuthenticatedAccountsAdapter extends ArrayAdapter<BoxAuthenticationInfo> {
    private static final int CREATE_NEW_TYPE_ID = 2;
    private static final int[] THUMB_COLORS = new int[]{-6381922, -10234140, -41121, -8465078, -5299724, -25001, -1752253, -10631001, -888412, -13733450, -1937604, -9007174, -11091626, -1061074, -11680004, -11528315, -1152974, -20195, -2195471};

    public static class DifferentAuthenticationInfo extends BoxAuthenticationInfo {
    }

    public static class ViewHolder {
        public TextView descriptionView;
        public TextView initialsView;
        public TextView titleView;
    }

    public AuthenticatedAccountsAdapter(Context context, int resource, List<BoxAuthenticationInfo> objects) {
        super(context, resource, objects);
    }

    public int getViewTypeCount() {
        return CREATE_NEW_TYPE_ID;
    }

    public BoxAuthenticationInfo getItem(int position) {
        if (position == getCount() - 1) {
            return new DifferentAuthenticationInfo();
        }
        return (BoxAuthenticationInfo) super.getItem(position);
    }

    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return CREATE_NEW_TYPE_ID;
        }
        return super.getItemViewType(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean hasName = false;
        if (getItemViewType(position) == CREATE_NEW_TYPE_ID) {
            return LayoutInflater.from(getContext()).inflate(R.layout.boxsdk_list_item_new_account, parent, false);
        }
        View rowView = LayoutInflater.from(getContext()).inflate(R.layout.boxsdk_list_item_account, parent, false);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.titleView = (TextView) rowView.findViewById(R.id.box_account_title);
            holder.descriptionView = (TextView) rowView.findViewById(R.id.box_account_description);
            holder.initialsView = (TextView) rowView.findViewById(R.id.box_account_initials);
            rowView.setTag(holder);
        }
        BoxAuthenticationInfo info = getItem(position);
        if (info != null && info.getUser() != null) {
            if (!SdkUtils.isEmptyString(info.getUser().getName())) {
                hasName = true;
            }
            holder.titleView.setText(hasName ? info.getUser().getName() : info.getUser().getLogin());
            if (hasName) {
                holder.descriptionView.setText(info.getUser().getLogin());
            }
            setColorsThumb(holder.initialsView, position);
            return rowView;
        } else if (info == null) {
            return rowView;
        } else {
            BoxLogUtils.e("invalid account info", info.toJson());
            return rowView;
        }
    }

    public int getCount() {
        return super.getCount() + 1;
    }

    @TargetApi(16)
    public void setColorsThumb(TextView initialsView, int position) {
        Drawable drawable = initialsView.getResources().getDrawable(R.drawable.boxsdk_thumb_background);
        drawable.setColorFilter(THUMB_COLORS[position % THUMB_COLORS.length], Mode.MULTIPLY);
        if (VERSION.SDK_INT > 15) {
            initialsView.setBackground(drawable);
        } else {
            initialsView.setBackgroundDrawable(drawable);
        }
    }
}
