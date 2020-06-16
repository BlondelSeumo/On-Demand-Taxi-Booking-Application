package in.techware.lataxidriverapp.util.transforms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;


public class CircleTransform implements Transformation<Bitmap> {

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int outWidth, int outHeight) {
        return circleCrop(Glide.get(context).getBitmapPool(), resource);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    private static BitmapResource circleCrop(BitmapPool pool, Resource<Bitmap> resource) {
        if (resource == null) return null;
        Bitmap source = resource.get();

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return BitmapResource.obtain(result, pool);
    }



}
