package komiii.dor.organisr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SQLUpdater extends Service {

    SQLUpdaterBR subr = new SQLUpdaterBR();

    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        subr.update(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        subr.update(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
