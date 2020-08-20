package com.upit.coex.user.screen.map.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.map.SettingFragmentContract;
import com.upit.coex.user.repository.model.custom.SquareView;
import com.upit.coex.user.screen.base.fragment.BaseFragment;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.screen.pandr.ActivityPAndR;
import com.upit.coex.user.screen.profile.ProfileActivity;
import com.upit.coex.user.screen.wallet.activity.WalletActivity;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.map.fragment.SettingFragmentViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends BaseFragment<SettingFragmentViewModel> implements SettingFragmentContract.SettingInterfaceFragmentView {
    private static final int RESULT_P_AND_R = 1477;
    private SquareView itemProfile, itemPAndR, itemGeneral, itemAboutUs, itemPolicy, itemWallet;
    private TextView txtLogout;
    private View v;
    private DialogLoading mDialogLoading;

    public SettingFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);

        mViewModal = ViewModelProviders.of(this).get(SettingFragmentViewModel.class);
        mViewModal.setView(this);
        return v;
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        mDialogLoading = new DialogLoading(getActivity());
        itemPAndR = v.findViewById(R.id.fragment_setting_p_and_r);
        itemAboutUs = v.findViewById(R.id.fragment_setting_about_us);
        itemGeneral = v.findViewById(R.id.fragment_setting_general);
        itemPolicy = v.findViewById(R.id.fragment_setting_policy);
        itemProfile = v.findViewById(R.id.fragment_setting_profile);
        itemWallet = v.findViewById(R.id.fragment_setting_my_wallet);
        txtLogout = v.findViewById(R.id.fragment_setting_txt_logout);
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        itemProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToProfile = new Intent(getActivity(), ProfileActivity.class);
                startActivity(moveToProfile);
            }
        });
        itemPAndR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToWallet = new Intent(getActivity(), ActivityPAndR.class);
                startActivityForResult(moveToWallet, RESULT_P_AND_R);
            }
        });
        itemWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToWallet = new Intent(getActivity(), WalletActivity.class);
                startActivity(moveToWallet);
            }
        });
        itemPolicy.setVisibility(View.INVISIBLE);
        itemGeneral.setVisibility(View.INVISIBLE);
        itemAboutUs.setVisibility(View.INVISIBLE);
    }

    private void showDialog() {
        Dialog myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.dialog_confirm);
        TextView txtTitle = myDialog.findViewById(R.id.confirm_dialog_txt_title);
        txtTitle.setText("Confirm logout");
        TextView txtDes = myDialog.findViewById(R.id.confirm_dialog_txt_description);
        txtDes.setText("Are you sure to logout?");
        myDialog.findViewById(R.id.confirm_dialog_btn_cancel).setOnClickListener(
                v -> myDialog.dismiss());
        myDialog.findViewById(R.id.confirm_dialog_btn_accept).setOnClickListener(v -> {
            mDialogLoading.startLoadingDialog();
            String fcmTolen = CoexSharedPreference.getInstance().get("fcmToken", String.class);
            mViewModal.logout(fcmTolen);
            myDialog.cancel();
        });
        myDialog.show();
        myDialog.setCanceledOnTouchOutside(false);//bam ra ngoai
        myDialog.setCancelable(false);//bam nut back
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_P_AND_R && resultCode == RESULT_OK) {
            Intent moveToWallet = new Intent(getActivity(), WalletActivity.class);
            startActivity(moveToWallet);
        }
    }

    @Override
    public void getResult(boolean result,String message) {
        mDialogLoading.dissLoadingDialog();
        if (result) {
            CoexSharedPreference.getInstance().put("token", "");
            Toast.makeText(getContext(), "Logout successfully", Toast.LENGTH_SHORT).show();
            Intent moveToLogin = new Intent(getContext(), LoginActivity.class);
            getActivity().startActivity(moveToLogin);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
