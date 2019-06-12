package com.kuradeon;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiezixiao
 * @date 2019-06-12 14:52
 */
public class SplitOrders {

    private static final long MAX_PCK_PRICE = 100000L;

    public class Item {
        /**
         * 卖家用户id
         */
        long sellerId;
        /**
         * 商品价格，单位分
         */
        long price;
    }
    public class Order{
        /**
         * 该订单对应的商品
         */
        List<Item> orderItems;
        /**
         * 该订单金额，单位分
         */
        long totalPrice;
        /**
         * 该订单对应的卖家userId
         */
        long sellerId;
    }
    /**
     * 根据购物车的商品，生成相应的交易订单，根据如下规则
     * 1.每笔交易订单可以有多个商品
     * 2.每笔交易订单的商品只能是同一个卖家
     * 3.每笔交易订单商品的总价格不能超过1000元
     * 4.生成的交易订单数量最小
     * @param items：购物车所有商品
     */
    public List<Order> packageItemsToOrders(List<Item> items){
        if (items == null || items.isEmpty()) {
            return new ArrayList<>();
        }
        Map<Long, List<Item>> sellerItemMap = items
                .stream()
                .collect(Collectors.groupingBy(item -> item.sellerId));
        List<Order> orders = new LinkedList<>();
        for (Map.Entry<Long, List<Item>> entry : sellerItemMap.entrySet()) {
            List<Item> sellerItems = entry.getValue()
                    .stream()
                    .sorted((item1, item2) -> {
                        if (item1.price > item2.price) {
                            return -1;
                        } else if (item1.price < item2.price) {
                            return 1;
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
            orders.addAll(divideOrders(sellerItems));
        }
        return orders;
    }

    /**
     * 贪心算法
     * @param sortedItems 大到小排序的item
     * @return 订单列表
     */
    private List<Order> divideOrders(List<Item> sortedItems) {
        List<Order> orders = new LinkedList<>();
        long minPrice = sortedItems.get(sortedItems.size() - 1).price;
        while (!sortedItems.isEmpty()) {
            Order order = new Order();
            List<Item> orderItems = new LinkedList<>();
            order.orderItems = orderItems;
            Item first = sortedItems.get(0);
            orderItems.add(first);
            sortedItems.remove(0);
            order.sellerId = first.sellerId;
            order.totalPrice = first.price;
            if (first.price < MAX_PCK_PRICE) {
                for (int i = 0; i < sortedItems.size();) {
                    if ((order.totalPrice + minPrice) > MAX_PCK_PRICE) {
                        break;
                    }
                    Item item = sortedItems.get(i);
                    if (item.price + order.totalPrice < MAX_PCK_PRICE) {
                        orderItems.add(item);
                        order.totalPrice += item.price;
                        sortedItems.remove(i);
                    } else if (item.price + order.totalPrice == MAX_PCK_PRICE) {
                        orderItems.add(item);
                        order.totalPrice += item.price;
                        sortedItems.remove(i);
                        break;
                    } else {
                        i++;
                    }
                }
            }
            orders.add(order);
        }
        return orders;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SplitOrders splitOrders = new SplitOrders();
        List<Item> items = new ArrayList<>(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Item item = splitOrders.new Item();
            item.price = (random.nextInt(10000) + 10000L);
            item.sellerId = (random.nextInt(2) + 1);
            items.add(item);
        }
        System.out.println("----------All Items---------");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.println("[" + item.sellerId + "," + item.price + "]");
        }
        System.out.println("----------All Orders---------");
        System.out.println("-----------------------------");
        List<Order> orders = splitOrders.packageItemsToOrders(items);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println("----------Order " + i + "-----------");
            System.out.println("{seller:" + order.sellerId + ", totalPrice:" + order.totalPrice + "}");
            List<Item> orderItems = order.orderItems;
            for (int j = 0; j < orderItems.size(); j++) {
                Item item = orderItems.get(j);
                System.out.print("[" + item.sellerId + "," + item.price + "]\t");
            }
            System.out.println();
        }
    }
}
