package com.infusiblecoder.loanappsameed.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

public class BitmapConversion {

    // convert from bitmap to byte array
    public static byte[] bitmap_to_byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap byte_array_to_bitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static byte[] getByteArrayFromImageView(ImageView imageView) {
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        Bitmap bitmap;
        if (bitmapDrawable == null) {
            imageView.buildDrawingCache();
            bitmap = imageView.getDrawingCache();
            imageView.buildDrawingCache(false);
        } else {
            bitmap = bitmapDrawable.getBitmap();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }


    public static Bitmap getBitmapFromString(String img) {
        byte[] decodestring = Base64.decode(img, Base64.DEFAULT);
        Bitmap decodebyte = BitmapFactory.decodeByteArray(decodestring, 0, decodestring.length);
        return decodebyte;
    }

    public static String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap getbitmapfromimageview(ImageView imageView) {

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }


    public static Bitmap uriToBitmap(Uri selectedFileUri, Context context) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {


            try {
                return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), selectedFileUri));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                ParcelFileDescriptor parcelFileDescriptor =
                        context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);


                parcelFileDescriptor.close();

                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}




