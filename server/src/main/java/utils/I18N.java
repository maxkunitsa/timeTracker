package utils;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import ninja.i18n.Messages;

import javax.inject.Singleton;

/**
 * Wrapper for internationalisation to
 * simplify process of getting localised message by key
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 29.10.2015
 */
@Singleton
public class I18N {
    @Inject
    private Messages messages;

    public String get(String key) {
        return messages.get(key, Optional.of("en")).get();
    }

    public String get(String key, Object... params)
    {
        return messages.get(key, Optional.of("en"), params).get();
    }
}
