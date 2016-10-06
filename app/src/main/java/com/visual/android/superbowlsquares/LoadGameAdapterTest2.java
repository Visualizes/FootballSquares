package com.visual.android.superbowlsquares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by RamiK on 5/17/2016.
 */
public class LoadGameAdapterTest2 extends ArrayAdapter<String>{

    private List<String> keys;
    private String key;
    //private TinyDB tinyDB;
    private UserChoices userChoices;
    private View view;

    public LoadGameAdapterTest2(Context context, List<String> items, UserChoices userChoices) {
        super(context, 0, items);
        Collections.sort(items);
        //tinyDB = new TinyDB(getContext());
        this.keys = items;
        this.userChoices = userChoices;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_loadgame, parent, false);
        }

        final TextView loadName = (TextView) convertView.findViewById(R.id.loadName);
        TextView loadDescription = (TextView) convertView.findViewById(R.id.loadDescription);
        final ImageButton dropDownMenu = (ImageButton) convertView.findViewById(R.id.loadDropdown);

        key = keys.get(position);
        final String arr[] = key.split(",");
/*
        if (key != null) {
            loadName.setText(arr[0]);
            if (arr.length > 1){
                loadDescription.setText(arr[1]);
            }

            loadName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), loadName.getText().toString() + " loaded", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), StartingScreenActivity.class);
                    i.putExtra("UserChoices", (UserChoices)tinyDB.getObject(keys.get(position), UserChoices.class));
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
                                saveName.setText(arr[0]);
                                if (arr.length > 1){
                                    saveDescription.setText(arr[1]);
                                }

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
                                            UserChoices userChoices = (UserChoices)tinyDB.getObject(keys.get(position), UserChoices.class);
                                            tinyDB.remove(keys.get(position));
                                            tinyDB.putObject(name + "," + description, userChoices);
                                            keys.set(position, name + "," + description);
                                            Collections.sort(keys);
                                            notifyDataSetChanged();
                                            dialog.cancel();
                                        }
                                        else{
                                            Toast.makeText(getContext(), "Enter a name.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                            if (menuItem.getTitle().equals("Delete")) {
                                tinyDB.remove(keys.get(position));
                                keys.remove(position);
                                notifyDataSetChanged();
                            }

                            if (menuItem.getTitle().equals("Details")){
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                                LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.content_preview, null);
                                view = dialogView.findViewById(R.id.board);

                                PreviewGame previewGame = new PreviewGame(dialogView, getContext(),
                                        (UserChoices)tinyDB.getObject(keys.get(position), UserChoices.class));

                                LinearLayout linearLayout = (LinearLayout)dialogView.findViewById(R.id.llsavegame);
                                linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

                                ListView listView = (ListView)dialogView.findViewById(R.id.listview);
                                listView.setAdapter(previewGame.getArrayAdapter(R.layout.textview_previewgame));

                                //((TextView)dialogView.findViewById(R.id.game)).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                                //((TextView)dialogView.findViewById(R.id.players)).setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                                //((TextView)dialogView.findViewById(R.id.board)).setTextColor(ContextCompat.getColor(getContext(), R.color.green));

                                initializeBoard();

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

        */
        return convertView;
    }

    private void initializeBoard(){

        TextView[][] board = new TextView[10][10];
        TableRow[] rows = new TableRow[10];
        TableLayout layout = (TableLayout) view.findViewById(R.id.table);

        for (int i = 0; i < 10; i++){
            rows[i] = (TableRow)layout.getChildAt(i);
        }
        //sets the ints of the arrays equal to each respective child
        for (int i = 0; i < 10; i++){
            for (int y = 0; y < 10; y++) {
                int position = (i*10) + y;
                if (userChoices.getNamesOnBoard().size() < 100) {
                    userChoices.getNamesOnBoard().add("");
                }
                board[i][y] = (TextView) rows[i].getChildAt(y);
                board[i][y].setText(userChoices.getNamesOnBoard().get(position));
            }//loop y end
        }//loop i end
    }//method end


}
