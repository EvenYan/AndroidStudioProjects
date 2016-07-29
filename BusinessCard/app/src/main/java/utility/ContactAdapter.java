package utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evenyan.businesscard.R;

import java.util.List;

/**
 * Created by evenyan on 16/7/29.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

    private int resourceId;

    public ContactAdapter(Context context, int textViewResourceId, List<Contact> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        Contact contact = getItem(postion);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView contactImage = (ImageView) view.findViewById(R.id.contact_image);
        TextView contactName = (TextView) view.findViewById(R.id.contact_name);
        TextView contactPosition = (TextView) view.findViewById(R.id.contact_position);
        TextView contactCompany = (TextView) view.findViewById(R.id.contact_company_name);

        contactImage.setImageResource(contact.getImageId());
        contactName.setText(contact.getName());
        contactPosition.setText(contact.getPosition());
        contactCompany.setText(contact.getCompany_name());

        return view;
    }
}
