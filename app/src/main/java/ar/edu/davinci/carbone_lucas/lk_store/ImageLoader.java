package ar.edu.davinci.carbone_lucas.lk_store;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .into(imageView);
        }
    }

    public static void loadImageWithOptions(ImageView imageView, String imageUrl,
                                            int placeholderResId, int errorResId) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(placeholderResId)
                            .error(errorResId))
                    .into(imageView);
        }
    }
}