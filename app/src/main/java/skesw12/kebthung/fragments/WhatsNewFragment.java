package skesw12.kebthung.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.developers.DeveloperNote;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhatsNewFragment extends Fragment {

    @BindView(R.id.news_list) ListView newList;
    @BindView(R.id.img_new) ImageView newIcon;
    public WhatsNewFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview = inflater.inflate(R.layout.fragment_whats_new, container, false);
        ButterKnife.bind(this,rootview);
        final ArrayAdapter<DeveloperNote> adapter = new ArrayAdapter<DeveloperNote>(getActivity(),R.layout.dev_note_cell,new ArrayList<DeveloperNote>());
        adapter.add(new DeveloperNote("Update Version 1.0.7","Fixed bug on chart"));
        adapter.add(new DeveloperNote("Update Version 1.0.6","Change wish to goal for more understanding."));
        adapter.add(new DeveloperNote("Update Version 1.0.5","Fixed bug on chart"));
        adapter.add(new DeveloperNote("Update Version 1.0.4","Fixed bug on data lost when pause Kebthung too long time."));
        adapter.add(new DeveloperNote("Update Version 1.0.3","What's new function to show what's change on Kebthung"));
        adapter.add(new DeveloperNote("Update Version 1.0.2","Fixed Bug on Screen support size and Chart"));
        adapter.add(new DeveloperNote("Apologize on Data Lost","I apologize that your data had lost when update from version 1.0.0 to 1.0.1"));
        adapter.add(new DeveloperNote("Update Version 1.0.1","Fixed Bug on Chart"));
        newList.setAdapter(adapter);
        newList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessegeDialog(adapter.getItem(position).getNote());
            }
        });
        newIcon.setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
        return rootview;
    }
    private void showMessegeDialog(String messege){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(messege)
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

}
