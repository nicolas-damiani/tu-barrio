package com.engineers.tubarrio.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.widget.Toast;

public class PhotoDialog extends DialogFragment {

    public int id;
    static String[] options = {"Sacar foto", "Elegir de galeria"};
    private NoticeDialogListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        mListener.onDialogCameraClick(PhotoDialog.this);
                        dialog.dismiss();
                        break;
                    case 1:
                        mListener.onDialogGalleryClick(PhotoDialog.this);
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        assignListener();

        return dialog;
    }

    public interface NoticeDialogListener {
        void onDialogCameraClick(DialogFragment dialog);
        void onDialogGalleryClick(DialogFragment dialog);
    }

    private void assignListener(){
        NoticeDialogListener listener = (NoticeDialogListener) getTargetFragment();
        if(listener !=null){
            //La llamada al dialog se hace desde un fragment
            try {
                mListener = (NoticeDialogListener) getTargetFragment();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"Exception: Calling fragment must implement NoticeDialogListener interface",Toast.LENGTH_LONG);
            }
        } else {
            try {
                mListener = (NoticeDialogListener)getActivity();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"Exception: Calling activity must implement NoticeDialogListener interface",Toast.LENGTH_LONG);
            }
        }

    }
}