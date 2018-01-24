package com.comsci.michelaustin.comscisummative;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * This class converts an alertdialog into a dialog fragment for use in the menupopup
 * Created by Austin on 2018-01-14.
 */

public class RestartDialogFragment extends DialogFragment {

    int currentModule;//module id of module clicked in menu opening

    public RestartDialogFragment() { /*empty*/ }

    /** creates a new instance of RestartDialogFragment */
    public static RestartDialogFragment newInstance() {
        return new RestartDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        currentModule = getArguments().getInt("ID");

        //getting proper access to LayoutInflater is the trick
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.restart_module_prompt, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        //sets the functionalities of the alert dialog
        builder
                .setCancelable(false)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface menupopup,int id) {
                                FileIO.writeFile("0","resumeModule"+(currentModule+1)+".txt",getActivity().getApplicationContext());
                                Intent startQuiz = new Intent(getActivity().getApplicationContext(), QuizMenuActivity.class);
                                startQuiz.putExtra("MODULE_ID",currentModule+1);
                                startActivity(startQuiz);
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface menupopup,int id) {
                                menupopup.cancel();
                            }
                        });
        return builder.create();
    }
}