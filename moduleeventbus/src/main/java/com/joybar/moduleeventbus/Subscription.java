package com.joybar.moduleeventbus;

/**
 * Created by joybar on 2017/11/28.
 */

final public class Subscription {
	final Object subscriber;
	final SubscriberMethod subscriberMethod;

	volatile boolean active;

	Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
		this.subscriber = subscriber;
		this.subscriberMethod = subscriberMethod;
		active = true;
	}


	@Override
	public boolean equals(Object other) {
		if (other instanceof Subscription) {
			Subscription otherSubscription = (Subscription) other;
			return subscriber == otherSubscription.subscriber
					&& subscriberMethod.equals(otherSubscription.subscriberMethod);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return subscriber.hashCode() + subscriberMethod.methodString.hashCode();
	}

	@Override
	public String toString() {
		return "Subscription{" + "subscriber=" + subscriber.getClass().getName() + ", subscriberMethod=" + subscriberMethod.toString() + ", active=" + active
				+ '}';
	}
}
