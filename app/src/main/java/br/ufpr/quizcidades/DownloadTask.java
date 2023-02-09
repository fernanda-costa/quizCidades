package br.ufpr.quizcidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    private ImageView image;
    private ProgressDialog progressDialog;

    public DownloadTask(Context context, ImageView image) {
        this.context = context;
        this.image = image;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Por favor aguarde..", "Baixando imagem");
    }

    public static Bitmap downloadImage(String stringURL) throws IOException {
        URL url = new URL(stringURL);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            bitmap = DownloadTask.downloadImage(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        image.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }
}
