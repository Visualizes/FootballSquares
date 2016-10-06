package com.visual.android.superbowlsquares;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by Rami on 2/9/2016.
 */
public class NameInputAdapter extends ArrayAdapter<String> {

    private List<String> items;
    private UserChoices userChoices;
    private int storedPosition = 0;
    private NavigationViewController navigationViewController;
    public NameInputAdapter(Context context, List<String> items, NavigationViewController navigationViewController, UserChoices userChoices) {
        super(context, 0, items);
        this.items = items;
        this.navigationViewController = navigationViewController;
        this.userChoices = userChoices;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_namechoices, parent, false);
        }

        final TextView mName = (TextView) convertView.findViewById(R.id.nameItem);
        final ImageButton mEditName = (ImageButton) convertView.findViewById(R.id.editName);
        final ImageButton mRemoveName = (ImageButton) convertView.findViewById(R.id.removeName);

        String item = items.get(position);

        if (item != null) {

            mRemoveName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.remove(position);
                    notifyDataSetChanged();
                    navigationViewController.enable();
                }
            });
            mEditName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChangeLangDialog(position);
                    notifyDataSetChanged();
                    navigationViewController.enable();
                }
            });
        }

        mName.setText(item);
        return convertView;
    }

    public void resetStoredPosition(){
        storedPosition = 0;
    }

    public int getStoredPosition() {
        return storedPosition;
    }

    private void showChangeLangDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)(getContext())).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_editname, null);
        dialogBuilder.setView(dialogView);

        final EditText editName = (EditText) dialogView.findViewById(R.id.editName);
        editName.setText(items.get(position));
        dialogBuilder.setTitle("Edit Name");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = editName.getText().toString();
                if (name.equals("")){
                    Toast.makeText(getContext(), "Enter a name.", Toast.LENGTH_SHORT).show();
                }
                else {
                    items.set(position, editName.getText().toString());
                    dialog.cancel();
                    notifyDataSetChanged();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        b.show();
    }

    private void editName(final int position){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_editname);
        dialog.setTitle("Edit Name");
        final EditText editName = (EditText) dialog.findViewById(R.id.editName);
        editName.setText(items.get(position));
        /*Button doneButton = (Button)dialog.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                if (name.equals("")){
                    Toast.makeText(getContext(), "Enter a name.", Toast.LENGTH_SHORT).show();
                }
                else {
                    items.set(position, editName.getText().toString());
                    dialog.cancel();
                    notifyDataSetChanged();
                }
            }
        });
*/
        dialog.show();

    }
}
