/*
 * Copyright 2009 Michael Burton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package roboguice.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.google.inject.Injector;
import com.google.inject.Key;
import java.util.HashMap;
import roboguice.RoboGuice;

/**
 * A {@link RoboPreferenceActivity} extends from {@link PreferenceActivity} to provide
 * dynamic injection of collaborators, using Google Guice.<br />
 *
 * Note: there is currently a limitation that prevents you from using @InjectView if you use setContentView.
 * http://code.google.com/p/roboguice/issues/detail?id=70
 *
 * @author Toly Pochkin
 * @author Rodrigo Damazio
 * @author Mike Burton
 * @see RoboActivity
 */
public abstract class RoboPreferenceActivity extends PreferenceActivity {
    protected HashMap<Key<?>, Object> scopedObjects = new HashMap<Key<?>, Object>();
    protected Injector injector;

    /** {@inheritDoc } */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (injector == null) {
            injector = RoboGuice.getInjector(this);
        }
        injector.injectMembers(this);
    }

    @Override
    protected void onDestroy() {
        RoboGuice.destroyInjector(this);
        super.onDestroy();
    }
}
