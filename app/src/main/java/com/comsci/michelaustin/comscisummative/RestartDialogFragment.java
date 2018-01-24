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
 * Created by Austin on 2018-01-14.
 */

public class RestartDialogFragment extends DialogFragment {

    int currentModule;

    public RestartDialogFragment() { /*empty*/ }

    /** creates a new instance of RestartDialogFragment */
    public static RestartDialogFragment newInstance() {
        return new RestartDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        currentModule = getArguments().getInt("ID");

        //getting proper access to LayoutInflater is the trick. getLayoutInflater is a                   //Function
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.restart_module_prompt, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

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
        /*builder.setTitle(getActivity().getString(R.string.sysinfo)).setNeutralButton(
                getActivity().getString(R.string.okay), null);*/
        return builder.create();
    }
}