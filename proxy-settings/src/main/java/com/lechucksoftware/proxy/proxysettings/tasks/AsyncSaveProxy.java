package com.lechucksoftware.proxy.proxysettings.tasks;

import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.lechucksoftware.proxy.proxysettings.App;
import com.lechucksoftware.proxy.proxysettings.R;
import com.lechucksoftware.proxy.proxysettings.db.ProxyEntity;
import com.lechucksoftware.proxy.proxysettings.utils.UIUtils;

import be.shouldit.proxy.lib.WiFiAPConfig;

/**
 * Created by Marco on 29/11/13.
 */


public class AsyncSaveProxy extends AsyncTask<Void, String, Boolean>
{
    private final Fragment callerFragment;
    private final ProxyEntity proxyEntity;
    private static final String TAG = AsyncSaveProxy.class.getSimpleName();

    public AsyncSaveProxy(Fragment caller, ProxyEntity proxy)
    {
        callerFragment = caller;
        proxyEntity = proxy;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);

        if (result)
        {
            Toast.makeText(callerFragment.getActivity(), callerFragment.getString(R.string.proxy_saved), Toast.LENGTH_SHORT).show();
        }
        else
        {
            UIUtils.showError(callerFragment.getActivity(), R.string.exception_apl_writeconfig_error_message);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        App.getLogger().startTrace(TAG, "saveProxy", Log.DEBUG);

        try
        {
            if (proxyEntity != null)
            {
                App.getDBManager().upsertProxy(proxyEntity);
            }

            App.getLogger().stopTrace(TAG, "saveProxy", Log.DEBUG);
            return true;
        }
        catch (Exception e)
        {
            App.getEventsReporter().sendException(e);
            return false;
        }
    }
}
