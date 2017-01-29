package org.snapscript.android.game;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;

import java.net.URI;

public class Configuration {

    private static final String PROCESS_TEMPLATE = "android-%s";
    private static final String ADDRESS_TEMPLATE = "http://%s:%s/resource";

    private final ContentResolver resolver;
    private final Resources resources;

    public Configuration(Context context) {
        this.resolver = context.getContentResolver();
        this.resources = context.getResources();
    }

    public String getDeviceKey() {
        return Settings.Secure.getString(resolver, Settings.Secure.ANDROID_ID);
    }

    public String getProcessName() {
        return String.format(PROCESS_TEMPLATE, getDeviceKey());
    }

    public URI getRemoteAddress() {
        return URI.create(String.format(ADDRESS_TEMPLATE, getRemoteHost(), getRemotePort()));
    }

    public String getRemoteHost(){
        return resources.getString(R.string.remote_host);
    }

    public int getRemotePort(){
        return Integer.parseInt(resources.getString(R.string.remote_port));
    }

    public int getEventPort(){
        return Integer.parseInt(resources.getString(R.string.event_port));
    }

    public String getLogLevel(){
        return resources.getString(R.string.log_level);
    }

    public String getContextName(){
        return resources.getString(R.string.context_name);
    }

    public String getGameName(){
        return resources.getString(R.string.game_name);
    }

    public int getThreadCount(){
        return Integer.parseInt(resources.getString(R.string.thread_count));
    }

    public int getStackSize(){
        return Integer.parseInt(resources.getString(R.string.stack_size));
    }
}
