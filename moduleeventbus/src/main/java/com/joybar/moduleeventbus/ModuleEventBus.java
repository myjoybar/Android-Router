package com.joybar.moduleeventbus;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 2017/11/27.
 */

public class ModuleEventBus {

	private static final String TAG = "RouterBus";

	private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap<>();

	private final Map<Class<?>, ArrayList<Subscription>> subscriptionsByEventType = new HashMap<>();


	private static class RouterBusHolder {
		private static ModuleEventBus INSTANCE = new ModuleEventBus();
	}

	public static ModuleEventBus getInstance() {
		return RouterBusHolder.INSTANCE;
	}

	public void register(@NonNull Object subscriber) {
		if (subscriber == null) {
			return;
		}
		Class<?> subscriberClass = subscriber.getClass();
		if (subscriberClass == null) {
			return;
		}
		Method[] methods = subscriberClass.getMethods();
		for (Method method : methods) {
			ModuleEvent event = method.getAnnotation(ModuleEvent.class);
			if (event != null) {
				Log.d(TAG, "subscriber=" + subscriber);
				Log.d(TAG, "method.getName()=" + method.getName());
				Log.d(TAG, "method.getName()=" + method.getParameterTypes());
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					Class<?> eventType = parameterTypes[0];
					SubscriberMethod subscriberMethod = new SubscriberMethod(method, method.getName(), eventType);
					Subscription subscription = new Subscription(subscriber, subscriberMethod);
					ArrayList<Subscription> subscriptions = subscriptionsByEventType.get(eventType);
					if (subscriptions == null) {
						subscriptions = new ArrayList<>();
						subscriptionsByEventType.put(eventType, subscriptions);
					} else {
						if (subscriptions.contains(subscription)) {
							throw new RuntimeException("Subscriber " + subscriber.getClass() + " already registered to event " + eventType);
						}
					}
					subscriptions.add(subscription);
				} else {
					throw new IllegalArgumentException("method:" + method.getName() + " only allow  to have 1 parameter");
				}

			}
		}
	}


	public void unregister(@NonNull Object subscriber) {
		if (subscriber == null) {
			return;
		}
		Class<?> subscriberClass = subscriber.getClass();
		if (subscriberClass == null) {
			return;
		}
		Log.d(TAG, "2222=" + subscriberClass.getName());

		if (subscriptionsByEventType != null && subscriptionsByEventType.size() != 0) {
			Iterator<Map.Entry<Class<?>, ArrayList<Subscription>>> iterator = subscriptionsByEventType.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Class<?>, ArrayList<Subscription>> entry = iterator.next();
				Class<?> key = entry.getKey();
				Log.d(TAG, "333=" + key.getName());

				ArrayList<Subscription> subscriptions = entry.getValue();
				Iterator<Subscription> it = subscriptions.iterator();
				while (it.hasNext()) {
					Subscription subscription = it.next();
					Log.d(TAG, "444=" + subscription.subscriber.getClass().getName());
					if (subscription.subscriber == subscriber) {
						Log.d(TAG, "555=" );
						it.remove();
					}
				}
				if (subscriptions.size() == 0) {
					Log.d(TAG, "666=" );
					iterator.remove();
				}
			}
		}
		Log.d(TAG, "subscriptionsByEventType="+subscriptionsByEventType.size() );
	}

	public void post(Object event) {
		Class<? extends Object> eventClass = event.getClass();
		List<Class<?>> eventTypes = lookupAllEventTypes(eventClass);
		int countTypes = eventTypes.size();
		for (int h = 0; h < countTypes; h++) {
			Class<?> clazz = eventTypes.get(h);
			ArrayList<Subscription> subscriptions;
			synchronized (this) {
				subscriptions = subscriptionsByEventType.get(clazz);
			}
			if (subscriptions != null && !subscriptions.isEmpty()) {
				for (Subscription subscription : subscriptions) {
					SubscriberMethod subscriberMethod = subscription.subscriberMethod;
					Method method = subscriberMethod.method;
					if (method == null) {
						return;
					} else if (method.getParameterTypes() == null) {
						return;
					}
					try {
						subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}


				}
			}else{
				Log.d(TAG, "subscriptions is null or empty");
			}
		}
	}

	private static List<Class<?>> lookupAllEventTypes(Class<?> eventClass) {
		synchronized (eventTypesCache) {
			List<Class<?>> eventTypes = eventTypesCache.get(eventClass);
			if (eventTypes == null) {
				eventTypes = new ArrayList<>();
				Class<?> clazz = eventClass;
				while (clazz != null) {
					eventTypes.add(clazz);
					addInterfaces(eventTypes, clazz.getInterfaces());
					clazz = clazz.getSuperclass();
				}
				eventTypesCache.put(eventClass, eventTypes);
			}
			return eventTypes;
		}
	}

	/**
	 * Recurses through super interfaces.
	 */
	static void addInterfaces(List<Class<?>> eventTypes, Class<?>[] interfaces) {
		for (Class<?> interfaceClass : interfaces) {
			if (!eventTypes.contains(interfaceClass)) {
				eventTypes.add(interfaceClass);
				addInterfaces(eventTypes, interfaceClass.getInterfaces());
			}
		}
	}

}
