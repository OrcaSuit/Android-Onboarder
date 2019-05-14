package com.cuneytayyildiz.onboarder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.content.res.AppCompatResources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//PagerAdapter는 abstract 키워드로 정의된 추상 클래스이므로 OnboarderPagerAdapter로 상속받아서 씀
//데이터 리소스를 뷰페이저의 페이지뷰를 생성하는데 사용되는 클래스.

//오버라이드 해야하는 메서드
/*      instantiateItem(ViewGroup container, int position)	            position에 해당하는 페이지 생성.
        destroyItem(ViewGroup container, int position, Object object)	position 위치의 페이지 제거.
        getCount()	                                                    사용 가능한 뷰의 갯수를 리턴.
        isViewFromObject(View view, Object object)	                    페이지뷰가 특정 키 객체(key object)와 연관되는지 여부.*/

//참조 : https://recipes4dev.tistory.com/148

public class OnboarderPagerAdapter extends PagerAdapter {
    private Context context;
    private List<OnboarderPage> onboarderPages;

    public OnboarderPagerAdapter(Context context, List<OnboarderPage> onboarderPages) {
        this.context = context;
        this.onboarderPages = onboarderPages;
    }

    //getCount()	사용 가능한 뷰의 갯수를 리턴
    @Override
    public int getCount() {
        return onboarderPages.size();
    }

    //isViewFromObject(View view, Object object) 페이지뷰가 특정 키(key object) 객체와 연관되는지 여부
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    //instantiateItem(ViewGroup container, int position) position에 해당하는 페이지 생성
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_onboarder, container, false);

        OnboarderPage page = onboarderPages.get(position);

        ImageView onboarderImage = itemView.findViewById(R.id.image_top);
        TextView onboarderTitle = itemView.findViewById(R.id.textview_title);
        TextView onboarderDescription = itemView.findViewById(R.id.textview_description);

        if (page.getTitle() != null) {
            onboarderTitle.setText(page.getTitle());
        }

        if (page.getTitleResourceId() != 0) {
            onboarderTitle.setText(container.getResources().getString(page.getTitleResourceId()));
        }

        if (page.getDescription() != null) {
            onboarderDescription.setText(page.getDescription());
        }

        if (page.getDescriptionResourceId() != 0) {
            onboarderDescription.setText(context.getResources().getString(page.getDescriptionResourceId()));
        }

        if (page.getTitleColor() != 0) {
            onboarderTitle.setTextColor(page.getTitleColor());
        }

        if (page.getDescriptionColor() != 0) {
            onboarderDescription.setTextColor(page.getDescriptionColor());
        }

        if (page.getTitleColorId() != 0) {
            onboarderTitle.setTextColor(ContextCompat.getColor(context, page.getTitleColorId()));
        }

        if (page.getDescriptionColorId() != 0) {
            onboarderDescription.setTextColor(ContextCompat.getColor(context, page.getDescriptionColorId()));
        }

        if(page.getImageResource()!=null){
            onboarderImage.setImageDrawable(page.getImageResource());
        }

        if (page.getImageResourceId() != 0) {
            onboarderImage.setImageDrawable(AppCompatResources.getDrawable(context, page.getImageResourceId()));
        }

        if (page.getTitleTextSize() != 0f) {
            onboarderTitle.setTextSize(page.getTitleTextSize());
        }

        if (page.getDescriptionTextSize() != 0f) {
            onboarderDescription.setTextSize(page.getDescriptionTextSize());
        }

        if (page.isMultilineDescriptionCentered()) {
            onboarderDescription.setGravity(Gravity.CENTER);
        } else {
            onboarderDescription.setGravity(onboarderDescription.getLineCount() > 1 ? Gravity.START : Gravity.CENTER);
        }

        onboarderImage.getLayoutParams().height = page.getImageHeightPx();
        onboarderImage.getLayoutParams().width = page.getImageWidthPx();
        ((ConstraintLayout.LayoutParams)onboarderImage.getLayoutParams()).verticalBias = page.getImageBias();
        ((ConstraintLayout.LayoutParams)onboarderDescription.getLayoutParams()).bottomMargin = page.getTextPaddingBottomPx();

        container.addView(itemView);

        return itemView;
    }

    //destroyItem(ViewGroup container, int position, Object object) position 위치의 페이지 제거
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}