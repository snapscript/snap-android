package org.snapscript.android.game;

import static org.snapscript.agent.ProcessMode.SERVICE;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.agent.ProcessAgent;
import org.snapscript.common.thread.ThreadPool;
import org.snapscript.core.MapModel;
import org.snapscript.core.Model;

import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;

public class Agent {

    private static final String TAG = Agent.class.getSimpleName();

    private final Configuration configuration;
    private final AtomicBoolean active;
    private final GameAgent game;
    private final Activity activity;
    private final Executor executor;

    public Agent(GameActivity activity) {
        this.configuration = new Configuration(activity);
        this.executor = new ThreadPool(1);
        this.game = new GameAgent(activity);
        this.active = new AtomicBoolean();
        this.activity = activity;
    }

    public void start() {
        try {
            if (active.compareAndSet(false, true)) {
                log();
                execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execute() {
        try {
            final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            final Map<String, Object> map = new HashMap<String, Object>();
            final Model model = new MapModel(map);
            final ProcessAgent agent = new ProcessAgent(
                    SERVICE,
                    configuration.getRemoteAddress(),
                    configuration.getSystemName(),
                    configuration.getProcessName(),
                    configuration.getLogLevel(),
                    configuration.getThreadCount(),
                    configuration.getStackSize());

            StrictMode.setThreadPolicy(policy);
            map.put(configuration.getContextName(), activity);
            map.put(configuration.getGameName(), game);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        agent.start(model);
                    } catch (Exception e) {
                        Log.e(TAG, "Error starting agent", e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log() {
        try {
            Log.i(TAG, "remote-address=" + configuration.getRemoteAddress());
            Log.i(TAG, "system-name=" + configuration.getSystemName());
            Log.i(TAG, "process-name=" + configuration.getProcessName());
            Log.i(TAG, "log-level=" + configuration.getLogLevel());
            Log.i(TAG, "thread-count=" + configuration.getThreadCount());
            Log.i(TAG, "stack-size=" + configuration.getStackSize());
            Log.i(TAG, "game-name=" + configuration.getGameName());
            Log.i(TAG, "context-name=" + configuration.getContextName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
