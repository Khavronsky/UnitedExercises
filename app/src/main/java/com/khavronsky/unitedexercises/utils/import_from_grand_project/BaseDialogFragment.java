package com.khavronsky.unitedexercises.utils.import_from_grand_project;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.view.View.OnClickListener;

public abstract class BaseDialogFragment extends DialogFragment implements OnClickListener {
	public IDialogFragment callback = null;

    public void setCallback(final IDialogFragment callback){
    	this.callback = callback;
    }

    @Override
    public void onDismiss(DialogInterface dialog){
    	super.onDismiss(dialog);
    	try {
    		this.callback.doByDismissed();
    	} catch(Exception e){}
    }

	public IDialogFragment getCallback()
	{
		return  callback;
	}
}
