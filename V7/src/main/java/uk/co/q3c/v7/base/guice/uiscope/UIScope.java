package uk.co.q3c.v7.base.guice.uiscope;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.q3c.v7.base.ui.ScopedUI;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.vaadin.ui.UI;
import com.vaadin.util.CurrentInstance;

public class UIScope implements Scope {

	private static Logger log = LoggerFactory.getLogger(UIScope.class);

	private static UIScope current;

	private final Map<UIKey, Map<Key<?>, Object>> values = new HashMap<UIKey, Map<Key<?>, Object>>();

	public UIScope() {
		super();
		log.debug("creating UIScope " + this);
	}

	@Override
	public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
		return new Provider<T>() {
			@Override
			public T get() {
				// get the scope cache for the current UI
				UIKey uiKey = null;
				ScopedUI currentUI = (ScopedUI) CurrentInstance.get(UI.class);
				if (currentUI == null) {
					uiKey = CurrentInstance.get(UIKey.class);
				} else {
					uiKey = currentUI.getInstanceKey();
				}
				log.debug("looking for cache for key: " + uiKey);
				Map<Key<?>, Object> scopedObjects = getScopedObjectMap(uiKey);
				// this line should fail tests but having trouble setting up a decent test. TestBench needed?
				// Map<Key<?>, Object> scopedObjects = getScopedObjectMap(CurrentInstance.get(UIKey.class));

				// retrieve an existing instance if possible

				@SuppressWarnings("unchecked")
				T current = (T) scopedObjects.get(key);

				if (current != null) {
					log.debug("returning existing instance of " + current.getClass().getSimpleName());
					return current;
				}

				// or create the first instance and cache it
				current = unscoped.get();
				scopedObjects.put(key, current);
				log.debug("new instance of " + current.getClass().getSimpleName() + " created, as none in cache");
				return current;
			}
		};
	}

	private <T> Map<Key<?>, Object> getScopedObjectMap(UIKey uiKey) {

		// return an existing cache instance
		if (values.containsKey(uiKey)) {
			Map<Key<?>, Object> scopedObjects = values.get(uiKey);
			log.debug("scope cache retrieved for UI key: " + uiKey);
			return scopedObjects;
		} else {

			return createCacheEntry(uiKey);
		}

	}

	public boolean cacheHasEntryFor(UIKey uiKey) {
		return values.containsKey(uiKey);
	}

	public boolean cacheHasEntryFor(ScopedUI ui) {
		return cacheHasEntryFor(ui.getInstanceKey());
	}

	public void startScope(UIKey uiKey) {
		if (!cacheHasEntryFor(uiKey)) {
			createCacheEntry(uiKey);
		}
	}

	private HashMap<Key<?>, Object> createCacheEntry(UIKey uiKey) {
		HashMap<Key<?>, Object> uiEntry = new HashMap<Key<?>, Object>();
		values.put(uiKey, uiEntry);
		log.debug("created a scope cache for UIScope with key: " + uiKey);
		return uiEntry;
	}

	public void releaseScope(UIKey uiKey) {
		values.remove(uiKey);
	}

	public static UIScope getCurrent() {
		if (current == null) {
			current = new UIScope();
		}
		return current;
	}
}