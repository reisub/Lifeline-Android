package hr.foi.rsc.lifeline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.models.MenuItem;

/**
 * Created by dino on 22/11/14.
 */
public class MenuAdapter extends ArrayAdapter<MenuItem> {

    public MenuAdapter(Context context, int resource,
                       MenuItem[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView =
                LayoutInflater.from(getContext()).inflate(R.layout.list_item_menu, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MenuItem item = getItem(position);

        holder.itemIcon.setImageResource(item.getIconResId());
        holder.itemTitle.setText(item.getTitleResId());

        return convertView;
    }

    static class ViewHolder {

        @InjectView(R.id.item_icon)
        ImageView itemIcon;

        @InjectView(R.id.item_title)
        TextView itemTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
