package vn.com.misa.ccl.entity;

import android.app.Activity;

import java.util.Date;

/**
‐ Mục đích Class thực hiện những việc khai báo Order
*
‐ {@link }
*
‐ @created_by cvmanh on 01/25/2021
*/

public class Order {
    private int orderId;

    private int orderStatus;

    private String createdAt;

    private String tableName;

    private int totalPeople;

    private float totalMoney;

    public Order(int orderId, int orderStatus, String createdAt, String tableName, int totalPeople, float totalMoney) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.tableName = tableName;
        this.totalPeople = totalPeople;
        this.totalMoney = totalMoney;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Order(int orderId, int orderStatus, String tableName, int totalPeople, float totalMoney) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.tableName = tableName;
        this.totalPeople = totalPeople;
        this.totalMoney = totalMoney;
    }

    public int getOrderId() {
        return orderId;
    }

    public Order(int orderId, int orderStatus, String tableName, int totalPeople) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.tableName = tableName;
        this.totalPeople = totalPeople;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public Order(int orderId, int orderStatus, String createdAt, String tableName, int totalPeople) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.tableName = tableName;
        this.totalPeople = totalPeople;
    }
}
