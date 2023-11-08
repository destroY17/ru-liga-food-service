package ru.liga.queue;

public class NotifyQueue {
    public final static String ORDER_SERVICE_TO_KITCHEN = "newOrderToKitchen";
    public final static String KITCHEN_TO_ORDER_SERVICE = "kitchenToOrder";
    public final static String KITCHEN_TO_DELIVERY = "orderToDelivery";
    public final static String DELIVERY_TO_ORDER = "deliveryToOrder";
}
