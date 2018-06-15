package be.technifutur.checkcleaning.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.LottieAnimationView;
import be.technifutur.checkcleaning.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingAnimationFragment extends Fragment {


    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    Unbinder unbinder;

    public LoadingAnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_loading_animation, container, false);
        ButterKnife.bind(this, view);

        animationView.setAnimation("glow_loading.json");
        animationView.loop(true);
        animationView.playAnimation();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
