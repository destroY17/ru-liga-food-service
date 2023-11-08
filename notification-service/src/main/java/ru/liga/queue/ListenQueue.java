package ru.liga.queue;

public class ListenQueue {
    public final static String NEW_ORDER_QUEUE = "newOrderToNotification";
    public final static String KITCHEN_ACCEPT_QUEUE = "kitchenAcceptToNotification";
    public final static String KITCHEN_DENIED_QUEUE = "kitchenDeniedToNotification";
    public final static String KITCHEN_COMPLETE_QUEUE = "kitchenCompleteToNotification";
    public final static String DELIVERY_TAKEN_QUEUE = "deliveryTakenToNotification";
    public final static String DELIVERY_COMPLETE_QUEUE = "deliveryCompleteToNotification";
    public final static String NOTIFY_COURIERS_QUEUE = "notifyCouriersToNotification";
}
