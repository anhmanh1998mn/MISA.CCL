package vn.com.misa.ccl.view.report;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.util.AndroidDeviceHelper;

public class FragmentMainReport extends Fragment implements View.OnClickListener {

    private LinearLayout llReportHeader;

    private Dialog mDialogTimeOption;

    private ConstraintLayout clDialogMainReport;

    private TextView tvTimeNew,tvTimeThisWeek,tvTimeLastWeek,tvTimeThisMonth,tvTimeLastMonth,
            tvTimeThisYear,tvTimeLastYear,tvTimeOther;

    private ImageView ivTick,ivTickTwo,ivTickThree,ivTickFour,ivTickFive,ivTickSix,ivTickSeven,ivTickEight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_report,container,false);

        initView(view);

        onViewClickListener();

        replaceFragment(new FragmentReportTimeRecently());

        return view;
    }

    private void initView(View view) {
        llReportHeader=view.findViewById(R.id.llReportHeader);
    }

    private void onViewClickListener(){
        llReportHeader.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llReportHeader:{
                showDialogTimeOption();
                break;
            }
            case R.id.tvTimeNew:{
                ivTick.setVisibility(View.VISIBLE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeThisWeek:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.VISIBLE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeLastWeek:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.VISIBLE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeThisMonth:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.VISIBLE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeLastMonth:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.VISIBLE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeThisYear:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.VISIBLE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeLastYear:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.VISIBLE);
                ivTickEight.setVisibility(View.GONE);
                break;
            }
            case R.id.tvTimeOther:{
                ivTick.setVisibility(View.GONE);
                ivTickTwo.setVisibility(View.GONE);
                ivTickThree.setVisibility(View.GONE);
                ivTickFour.setVisibility(View.GONE);
                ivTickFive.setVisibility(View.GONE);
                ivTickSix.setVisibility(View.GONE);
                ivTickSeven.setVisibility(View.GONE);
                ivTickEight.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void showDialogTimeOption() {
        mDialogTimeOption=new Dialog(getContext());
        mDialogTimeOption.setContentView(R.layout.dialog_main_report);
        mDialogTimeOption.show();
        initViewDialog();
        onCLickViewDialogListener();
    }

    private void initViewDialog() {
        clDialogMainReport=mDialogTimeOption.findViewById(R.id.clDialogMainReport);
        clDialogMainReport.getLayoutParams().width= AndroidDeviceHelper.getWitdhScreen(getContext())-100;
        clDialogMainReport.requestLayout();
        tvTimeNew=mDialogTimeOption.findViewById(R.id.tvTimeNew);
        ivTick=mDialogTimeOption.findViewById(R.id.ivTick);
        tvTimeThisWeek=mDialogTimeOption.findViewById(R.id.tvTimeThisWeek);
        ivTickTwo=mDialogTimeOption.findViewById(R.id.ivTickTwo);
        tvTimeLastWeek=mDialogTimeOption.findViewById(R.id.tvTimeLastWeek);
        ivTickThree=mDialogTimeOption.findViewById(R.id.ivTickThree);
        tvTimeThisMonth=mDialogTimeOption.findViewById(R.id.tvTimeThisMonth);
        ivTickFour=mDialogTimeOption.findViewById(R.id.ivTickFour);
        tvTimeLastMonth=mDialogTimeOption.findViewById(R.id.tvTimeLastMonth);
        ivTickFive=mDialogTimeOption.findViewById(R.id.ivTickFive);
        tvTimeThisYear=mDialogTimeOption.findViewById(R.id.tvTimeThisYear);
        ivTickSix=mDialogTimeOption.findViewById(R.id.ivTickSix);
        tvTimeLastYear=mDialogTimeOption.findViewById(R.id.tvTimeLastYear);
        ivTickSeven=mDialogTimeOption.findViewById(R.id.ivTickSeven);
        tvTimeOther=mDialogTimeOption.findViewById(R.id.tvTimeOther);
        ivTickEight=mDialogTimeOption.findViewById(R.id.ivTickEight);
    }

    private void onCLickViewDialogListener(){
        tvTimeNew.setOnClickListener(this);
        tvTimeThisWeek.setOnClickListener(this);
        tvTimeLastWeek.setOnClickListener(this);
        tvTimeThisMonth.setOnClickListener(this);
        tvTimeLastMonth.setOnClickListener(this);
        tvTimeThisYear.setOnClickListener(this);
        tvTimeLastYear.setOnClickListener(this);
        tvTimeOther.setOnClickListener(this);
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.frmMainReportContent,fragment);
        transaction.commit();
    }
}
