package eus.ehu.tta.upv_ehutour.presentador;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import eus.ehu.tta.upv_ehutour.R;

/**
 * Created by josu on 2/01/18.
 */

public class Dialogo extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage(getResources().getString(R.string.felicitacion))
                .setTitle(getResources().getString(R.string.enhorabuena))
                .setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}

