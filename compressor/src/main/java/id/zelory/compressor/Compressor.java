package id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created on : June 18, 2016
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class Compressor {
    private static volatile Compressor INSTANCE;
    private Context context;
//    max width and height values of the compressed image is taken as 612x816
//    private float maxWidth = 612.0f;
//    private float maxHeight = 816.0f;
    private float maxSize = 720f;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;
    private int quality = 90;
    private String destinationDirectoryPath;
    private String fileNamePrefix;
    private String fileName;


    private Compressor(Context context) {
        this.context = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.pathSeparator + FileUtil.FILES_PATH;
    }

    public static Compressor getDefault(Context context) {
        if (INSTANCE == null) {
            synchronized (Compressor.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Compressor(context);
                }
            }
        }
        return INSTANCE;
    }

    public File compressToFile(File file) {
        return ImageUtil.compressImage(context, Uri.fromFile(file), maxSize,
            compressFormat, bitmapConfig, quality, destinationDirectoryPath,
            fileNamePrefix, fileName);
    }

    public Bitmap compressToBitmap(File file) {
        return ImageUtil.getScaledBitmap(context, Uri.fromFile(file), maxSize, bitmapConfig);
    }

    public Observable<File> compressToFileAsObservable(final File file) {
        return Observable.defer(new Callable<ObservableSource<? extends File>>() {
            @Override
            public ObservableSource<? extends File> call() throws Exception {
                return Observable.just(compressToFile(file));
            }
        });
    }

    public Observable<Bitmap> compressToBitmapAsObservable(final File file) {
        return Observable.defer(new Callable<ObservableSource<? extends Bitmap>>() {
            @Override
            public ObservableSource<? extends Bitmap> call() throws Exception {
                return Observable.just(compressToBitmap(file));
            }
        });
    }

    public static class Builder {
        private Compressor compressor;

        public Builder(Context context) {
            compressor = new Compressor(context);
        }

        public Builder setMaxSize(float maxSize) {
            compressor.maxSize = maxSize;
            return this;
        }

        public Builder setCompressFormat(Bitmap.CompressFormat compressFormat) {
            compressor.compressFormat = compressFormat;
            return this;
        }

        public Builder setBitmapConfig(Bitmap.Config bitmapConfig) {
            compressor.bitmapConfig = bitmapConfig;
            return this;
        }

        public Builder setQuality(int quality) {
            compressor.quality = quality;
            return this;
        }

        public Builder setDestinationDirectoryPath(String destinationDirectoryPath) {
            compressor.destinationDirectoryPath = destinationDirectoryPath;
            return this;
        }

        public Builder setFileNamePrefix(String prefix) {
            compressor.fileNamePrefix = prefix;
            return this;
        }

        public Builder setFileName(String fileName) {
            compressor.fileName = fileName;
            return this;
        }

        public Compressor build() {
            return compressor;
        }
    }
}
