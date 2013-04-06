package com.pail.myzkbycy.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.pail.myzkbycy.R;

public class PaymentShowDialog extends AlertDialog {

	public PaymentShowDialog(Context context, int theme) {
		super(context, theme);
	}

	public PaymentShowDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_show_dialog_layout);
	}
}