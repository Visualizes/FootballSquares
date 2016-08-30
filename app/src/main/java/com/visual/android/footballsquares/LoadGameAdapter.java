package com.visual.android.footballsquares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by RamiK on 8/28/2016.
 */
public class LoadGameAdapter extends ArrayAdapter<UserChoices> {

    private View view;
    private DatabaseHandler db;

    public LoadGameAdapter(Context context, DatabaseHandler db) {
        super(context, 0, db.getAllContacts());
        this.db = db;
        System.out.println(db.getLocalGamesCount());
    }

    @Override
    public int getCount() {
        return db.getAllContacts() != null? db.getLocalGamesCount() : 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_loadgame, parent, false);
        }

        final TextView loadName = (TextView) convertView.findViewById(R.id.loadName);
        TextView loadDescription = (TextView) convertView.findViewById(R.id.loadDescription);
        final ImageButton dropDownMenu = (ImageButton) convertView.findViewById(R.id.loadDropdown);


        System.out.println("DB: " + db.getLocalGamesCount());
        System.out.println("position: " + position);


        if (getCount() > position) {

            UserChoices item = db.getAllContacts().get(position);
            System.out.println(item.getLoadGame().getName());

            loadName.setText(item.getLoadGame().getName());
            loadDescription.setText(item.getLoadGame().getDescription());

            System.out.println("NOT NULL");
            System.out.println(item.getLoadGame().getName());

            loadName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), loadName.getText().toString() + " loaded", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), StartingScreenActivity.class);
                    i.putExtra("UserChoices", db.getLocalGame(loadName.getText().toString()));
                    getContext().startActivity(i);
                }
            });

            dropDownMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(getContext(), dropDownMenu);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.loadgame_popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().equals("Edit")) {
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                final LayoutInflater inflater = ((Activity)(getContext())).getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.dialog_savegame, null);
                                dialogBuilder.setView(dialogView);
                                dialogBuilder.setTitle("Edit Game");

                                final EditText saveName = (EditText) dialogView.findViewById(R.id.saveName);
                                final EditText saveDescription = (EditText) dialogView.findViewById(R.id.saveDescription);
                                saveName.setText(getUserChoices(position).getLoadGame().getName());
                                saveDescription.setText(getUserChoices(position).getLoadGame().getDescription());

                                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //overridden

                                    }
                                });
                                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //pass
                                    }
                                });
                                final AlertDialog dialog = dialogBuilder.create();
                                dialog.show();
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String name = saveName.getText().toString();
                                        if (!name.equals("")) {
                                            String description = saveDescription.getText().toString();
                                            final UserChoices userChoices = getUserChoices(position);
                                            final String oldName = userChoices.getLoadGame().getName();
                                            userChoices.setLoadGame(new LoadGame(name, description));
                                            if (db.addLocalGame(userChoices)){
                                                deleteGame(oldName);
                                                notifyDataSetChanged();
                                                dialog.cancel();
                                            }
                                            else{
                                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                                dialogBuilder.setTitle("Are you sure?");
                                                dialogBuilder.setMessage("Game already exists. Would you like to overwrite it?");

                                                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog2, int whichButton) {
                                                        db.deleteLocalGame(oldName);
                                                        db.deleteLocalGame(userChoices.getLoadGame().getName());
                                                        db.addLocalGame(userChoices);
                                                        notifyDataChange();
                                                        dialog.cancel();
                                                        dialog2.cancel();
                                                    }
                                                });
                                                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                        //pass
                                                    }
                                                });
                                                final AlertDialog dialog = dialogBuilder.create();
                                                dialog.show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(getContext(), "Enter a name.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            if (menuItem.getTitle().equals("Delete")) {
                                deleteGame(position);
                                notifyDataSetChanged();
                            }

                            if (menuItem.getTitle().equals("Details")){
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.content_preview, null);
                                view = dialogView.findViewById(R.id.board);

                                PreviewGame previewGame = new PreviewGame(dialogView, getContext(), getUserChoices(position));

                                LinearLayout linearLayout = (LinearLayout)dialogView.findViewById(R.id.llsavegame);
                                linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

                                ListView listView = (ListView)dialogView.findViewById(R.id.listview);
                                listView.setAdapter(previewGame.getArrayAdapter(R.layout.textview_previewgame));


                                dialogView.findViewById(R.id.divider1).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                dialogView.findViewById(R.id.divider2).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                dialogView.findViewById(R.id.divider3).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                dialogView.findViewById(R.id.divider4).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

                                initializeBoard(position);

                                TextView gameText = (TextView)dialogView.findViewById(R.id.gametext);
                                gameText.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                                gameText.setText(previewGame.getGameText());

                                dialogBuilder.setView(dialogView);
                                dialogBuilder.setTitle("Preview");
                                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        //closes dialog
                                    }
                                });

                                AlertDialog dialog = dialogBuilder.create();
                                dialog.show();
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });
        }


        return convertView;
    }

    public static void notifyDataChange(){

    }

    private void deleteGame(int position){
        db.deleteLocalGame(db.getAllContacts().get(position).getLoadGame().getName());
    }

    private void deleteGame(String uniqueName){
        db.deleteLocalGame(uniqueName);
    }

    private UserChoices getUserChoices(int position){
        return db.getAllContacts().get(position);
    }

    private void initializeBoard(int position){

        TextView[][] board = new TextView[10][10];
        TableRow[] rows = new TableRow[10];
        TableLayout layout = (TableLayout) view.findViewById(R.id.table);

        for (int i = 0; i < 10; i++){
            rows[i] = (TableRow)layout.getChildAt(i);
        }
        //sets the ints of the arrays equal to each respective child
        for (int i = 0; i < 10; i++){
            for (int y = 0; y < 10; y++) {
                int location = (i*10) + y;
                board[i][y] = (TextView) rows[i].getChildAt(y);
                board[i][y].setText(getUserChoices(position).getNamesOnBoard().get(location));
            }//loop y end
        }//loop i end
    }//method end

}
