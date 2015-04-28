package kz.voxpopuli.voxapplication.tools;

/**
 * Created by user on 28.04.15.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import kz.voxpopuli.voxapplication.R;
import kz.voxpopuli.voxapplication.activity.MainActivity;

/**
 * Created by lesha on 06.03.15.
 */
public class ImageUtils {

    public static String mCurrentViewActionPhotoPath;
    public static String mCurrentFilePhotoPath;

    private static ImageUtils instance;

    private ImageUtils() {

    }

    public static ImageUtils getInstance() {
        if(instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    public void selectImage(final Activity context, final String makePhotoText, final String chosePhotoText,
                            final String cancelText, String dialogTitle) {
        final CharSequence[] items = {makePhotoText, chosePhotoText, cancelText};

        final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(makePhotoText)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = null;
                    try {
                        f = createImageFile();
                    } catch (IOException ex) {
                        DialogTools.showInfoDialog(context, context.getString(R.string.error_dialog_title),
                                context.getString(R.string.file_creation_alarm));
                        return;
                    }
                    mCurrentFilePhotoPath = f.getAbsolutePath();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    if(takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivityForResult(intent, MainActivity.GET_PICTURE_REQUEST);
                    }
                } else if (items[item].equals(chosePhotoText)) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    context.startActivityForResult(
                            Intent.createChooser(intent, chosePhotoText),
                            MainActivity.GET_FILE_CHOICER_REQUEST);
                } else if (items[item].equals(cancelText)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public String getFileFromCameraShot(String filePath) {
        File compressed = null;
        try {
            Bitmap bm;
            File uncompressed = new File(filePath);
            BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
            bm = BitmapFactory.decodeFile(filePath, btmapOptions);

            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            compressed = new File(uncompressed.getName(), storageDir.getAbsolutePath());

            OutputStream fOut = null;
            fOut = new FileOutputStream(compressed);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            uncompressed.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compressed.getAbsolutePath();
    }

    public String getFileFromGallery(Uri path, Activity activity) {
        return getPath(path, activity);
    }

    public Bitmap getBitmapFromPath(String path) {
        Bitmap bm;
        BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
        return bm = BitmapFactory.decodeFile(path, btmapOptions);
    }

    public Bitmap getBitmapFromPathWithScale(String path, int width, int height) {
        Bitmap bm;
        BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
        bm = BitmapFactory.decodeFile(path, btmapOptions);
        Bitmap res = Bitmap.createScaledBitmap(bm, width, height, false);
        bm.recycle();
        return res;
    }

    public byte[] getImageBytes(String path) {
        Bitmap bm;
        BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
        bm = BitmapFactory.decodeFile(path, btmapOptions);
        Bitmap res = Bitmap.createScaledBitmap(bm, bm.getWidth() / 4,
                bm.getHeight() / 4, false);
        bm.recycle();;

        ByteBuffer buffer = ByteBuffer.allocate(res.getByteCount());
        res.copyPixelsToBuffer(buffer);
        res.recycle();

        return buffer.array();
    }

    public static String getImageBytesAsString(String path) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > 240 || width > 320) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > 240
                    && (halfWidth / inSampleSize) > 320) {
                inSampleSize *= 2;
            }
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentFilePhotoPath = image.getAbsolutePath();
        mCurrentViewActionPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
