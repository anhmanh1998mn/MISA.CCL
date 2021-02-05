package vn.com.misa.ccl.entity;

import android.app.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * ‐ Mục đích Class thực hiện việc định nghĩa OrderServer
 * <p>
 * ‐ {@link vn.com.misa.ccl.model.ActivityLoginModel}
 * <p>
 * ‐ @created_by cvmanh on 02/06/2021
 */

public class OrderServer {

    @SerializedName("OrderIDLocal")
    @Expose
    private int orderIDLocal;

    @SerializedName("OrderStatus")
    @Expose
    private int orderStatus;

    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;

    @SerializedName("TableName")
    @Expose
    private String tableName;

    @SerializedName("TotalPeople")
    @Expose
    private int totalPeople;

    @SerializedName("Amount")
    @Expose
    private float amount;

    public int getOrderIDLocal() {
        return orderIDLocal;
    }

    public void setOrderIDLocal(int orderIDLocal) {
        this.orderIDLocal = orderIDLocal;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public OrderServer(int orderIDLocal, int orderStatus, String createdAt, String tableName, int totalPeople, float amount) {
        this.orderIDLocal = orderIDLocal;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.tableName = tableName;
        this.totalPeople = totalPeople;
        this.amount = amount;
    }
}
