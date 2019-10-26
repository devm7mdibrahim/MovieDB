package com.devmohamedibrahim1997.populartest.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devmohamedibrahim1997.populartest.R;
import com.google.android.material.snackbar.Snackbar;


import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;

import static com.devmohamedibrahim1997.populartest.Utils.Constant.IMAGE_BACKDROP_PATH;
import static com.devmohamedibrahim1997.populartest.Utils.Constant.IMAGE_POSTER_PATH;

public class HelperClass {

    public static String getPosterUrl(String path){
        return IMAGE_POSTER_PATH + path;
    }

    public static String getBackDropUrl(String path){
        return IMAGE_BACKDROP_PATH + path;
    }

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    public static String convertVoteCountToString(Integer voteCount){
        return voteCount != null ? ("(" + voteCount + ")") : "";
    }

    public static String convertVoteAverageToString(Double voteAverage){
        return voteAverage != null ? ("(" + voteAverage + ")") : "";
    }

    public static float convertVoteAverageToFloat(Double rating){
        return rating != null ? (rating.floatValue() / 2) : 0;
    }

    public static String getReleasedYear(String releaseDate){
        String[] parts = releaseDate.split("-");
        //or String[] parts = releaseDate.substring(0, 4);
        return parts[0];
    }

    public static void showSnackBar(Activity activity){
        Snackbar.make(activity.findViewById(android.R.id.content),
                "You currently have limited or no connectivity. Try connecting to a Wi-Fi network.", Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context,String toastMessage){
        Toast.makeText(context, ""+toastMessage, Toast.LENGTH_SHORT).show();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static int getScreenOrientation(FragmentActivity activity){
        int spanCount = 0;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2;
        } else if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        } else if ((activity.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            spanCount = 5;
        }
        return spanCount;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
