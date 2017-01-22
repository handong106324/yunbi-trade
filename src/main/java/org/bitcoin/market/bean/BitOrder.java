package org.bitcoin.market.bean;


import org.bitcoin.common.DoubleUtils;

import java.util.Date;

/**
 * Created by lichang on 14-1-14.
 */
@SuppressWarnings("serial")
public class BitOrder {

    public BitOrder(OrderSide side, double quantity, String text, OrderType orderType) {
        this(side, quantity, text, new Date(), orderType);
    }

    public BitOrder() {
    }

    public BitOrder(OrderSide side, double quantity, String text, Date created, OrderType orderType) {
        this.orderSide = side;
        setOrderAmount(quantity);
        this.info = text;
        this.createTime = created;
        this.orderType = orderType;
    }

    public static long ERROR_ORDER_ID = -1L;

    public static BitOrder getErrorOrder(String info) {
        BitOrder bitOrder = new BitOrder();
        bitOrder.setOrderId(ERROR_ORDER_ID);
        bitOrder.setInfo(info);
        return bitOrder;
    }

    private Long id;

    private String market;
    private Long orderId;
    private Double orderPrice = 0.0;

    private Long appAccountId;

    private Double orderCnyPrice = 0.0;

    private Double orderAmount = 0.0;

    private Long timestamp;
    private Date datetime;
    private Date createTime;

    private Double fee = 0.0;
    private Double processedPrice = 0.0;
    private Double processedCnyPrice = 0.0;
    private Double processedAmount = 0.0;
    private OrderSide orderSide;
    private OrderStatus status = OrderStatus.none;
    private String info;
    private OrderType orderType = OrderType.Limit;


    private Long timeoutTime;

    private Boolean enable = true;

    private Symbol symbol = Symbol.btc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long order_id) {
        this.orderId = order_id;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = DoubleUtils.toFourDecimal(orderPrice);
    }

    public Double getOrderCnyPrice() {
        return orderCnyPrice;
    }

    public void setOrderCnyPrice(Double orderCnyPrice) {
        this.orderCnyPrice = DoubleUtils.toFourDecimal(orderCnyPrice);
    }

    public Double getProcessedCnyPrice() {
        return processedCnyPrice;
    }

    public void setProcessedCnyPrice(Double processedCnyPrice) {
        this.processedCnyPrice = DoubleUtils.toFourDecimal(processedCnyPrice);

    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double order_amount) {
        this.orderAmount = DoubleUtils.toFourDecimal(order_amount);
    }

    public Double getFee() {
        if (fee == null) {
            return 0.0;
        }
        return fee;
    }

    public void setFee(Double fee) {

        this.fee = DoubleUtils.toFourDecimal(fee);
    }

    public Double getProcessedPrice() {
        return processedPrice;
    }

    public void setProcessedPrice(Double processedPrice) {
        this.processedPrice = DoubleUtils.toFourDecimal(processedPrice);
    }

    public Double getProcessedAmount() {
        return processedAmount;
    }

    public void setProcessedAmount(Double processedAmount) {
        this.processedAmount = DoubleUtils.toFourDecimal(processedAmount);

    }

    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide type) {
        this.orderSide = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getInfo() {
        return info == null ? "" : info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getAppAccountId() {
        return appAccountId;
    }

    public void setAppAccountId(Long appAccountId) {
        this.appAccountId = appAccountId;
    }


    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }


    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Long getTimeoutTime() {
        return timeoutTime;
    }

    public void setTimeoutTime(Long timeoutTime) {
        this.timeoutTime = timeoutTime;
    }

    @Override
    public String toString() {
        return "BitOrder{" +
                "id=" + id +
                ", market='" + market + '\'' +
                ", orderId=" + orderId +
                ", orderPrice=" + orderPrice +
                ", appAccountId=" + appAccountId +
                ", orderCnyPrice=" + orderCnyPrice +
                ", orderAmount=" + orderAmount +
                ", timestamp=" + timestamp +
                ", datetime=" + datetime +
                ", createTime=" + createTime +
                ", timeoutTime=" + timeoutTime +
                ", fee=" + fee +
                ", processedPrice=" + processedPrice +
                ", processedCnyPrice=" + processedCnyPrice +
                ", processedAmount=" + processedAmount +
                ", orderSide=" + orderSide +
                ", status=" + status +
                ", info='" + info + '\'' +
                ", orderType=" + orderType +
                ", enable=" + enable +
                '}';
    }

}

