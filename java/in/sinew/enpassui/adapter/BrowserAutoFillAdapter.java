package in.sinew.enpassui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import in.sinew.enpass.DisplayItemComparator;
import in.sinew.enpass.EnpassApplication;
import in.sinew.enpassengine.IDisplayItem;
import in.sinew.enpassengine.IDisplayItem.DisplayItemType;
import io.enpass.app.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class BrowserAutoFillAdapter extends BaseAdapter {
    Context mContext;
    boolean mCustomDivider = false;
    DisplayItemComparator mDisplayItemComparator = new DisplayItemComparator();
    private LayoutInflater mInflater;
    ArrayList<IDisplayItem> mList = null;
    boolean mShowImage = false;

    public BrowserAutoFillAdapter(Context aContext, ArrayList<IDisplayItem> aList, boolean aSort, boolean showImage, boolean customDivider) {
        this.mInflater = LayoutInflater.from(aContext);
        this.mContext = aContext;
        this.mList = aList;
        this.mShowImage = showImage;
        this.mCustomDivider = customDivider;
        if (aSort) {
            Collections.sort(this.mList, this.mDisplayItemComparator);
        }
        int loginPosition = -1;
        for (int position = 0; position < this.mList.size(); position++) {
            if (((IDisplayItem) this.mList.get(position)).getDisplayIdentifier().equals("login.default")) {
                loginPosition = position;
                break;
            }
        }
        if (loginPosition != -1) {
            IDisplayItem item = (IDisplayItem) this.mList.get(loginPosition);
            this.mList.remove(loginPosition);
            this.mList.add(0, item);
        }
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.common_list_item, null);
            holder = new ViewHolder(this);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.subText = (TextView) convertView.findViewById(R.id.subText);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.divider = convertView.findViewById(R.id.divider);
            if (!this.mShowImage) {
                holder.icon.setVisibility(8);
            }
            if (!this.mCustomDivider) {
                holder.divider.setVisibility(8);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(((IDisplayItem) this.mList.get(position)).getDisplayName());
        String aSubtitle = ((IDisplayItem) this.mList.get(position)).getSubTitle();
        if (!EnpassApplication.getInstance().getAppSettings().getShowSubtitle() || aSubtitle.isEmpty()) {
            holder.subText.setVisibility(8);
        } else {
            holder.subText.setVisibility(0);
            holder.subText.setText(aSubtitle);
        }
        if (((IDisplayItem) this.mList.get(position)).getDisplayType() == DisplayItemType.DisplayItemFolder) {
            holder.icon.setImageResource(this.mContext.getResources().getIdentifier(String.format(Locale.US, "f%d", new Object[]{Integer.valueOf(((IDisplayItem) this.mList.get(position)).getDisplayIconId())}), "drawable", this.mContext.getPackageName()));
        } else {
            holder.icon.setImageResource(this.mContext.getResources().getIdentifier(String.format(Locale.US, "i%d", new Object[]{Integer.valueOf(((IDisplayItem) this.mList.get(position)).getDisplayIconId())}), "drawable", this.mContext.getPackageName()));
        }
        return convertView;
    }
}
