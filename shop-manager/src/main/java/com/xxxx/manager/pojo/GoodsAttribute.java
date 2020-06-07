package com.xxxx.manager.pojo;

import java.io.Serializable;

/**
 * @author zhoubin 
 * @since 1.0.0
 */
public class GoodsAttribute implements Serializable {
    /**
     * 属性id
     */
    private Integer attrId;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性分类id
     */
    private Short typeId;

    /**
     * 属性分类名称
     */
    private String typeName;

    /**
     * 0不需要检索 1关键字检索 2范围检索
     */
    private Byte attrIndex;

    /**
     * 0唯一属性 1单选属性 2复选属性
     */
    private Byte attrType;

    /**
     *  0 手工录入 1从列表中选择 2多行文本框
     */
    private Byte attrInputType;

    /**
     * 属性排序
     */
    private Byte attrOrder;

    /**
     * 可选值列表
     */
    private String attrValues;

    /**
     * t_goods_attribute
     */
    private static final long serialVersionUID = 1L;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Byte getAttrIndex() {
        return attrIndex;
    }

    public void setAttrIndex(Byte attrIndex) {
        this.attrIndex = attrIndex;
    }

    public Byte getAttrType() {
        return attrType;
    }

    public void setAttrType(Byte attrType) {
        this.attrType = attrType;
    }

    public Byte getAttrInputType() {
        return attrInputType;
    }

    public void setAttrInputType(Byte attrInputType) {
        this.attrInputType = attrInputType;
    }

    public Byte getAttrOrder() {
        return attrOrder;
    }

    public void setAttrOrder(Byte attrOrder) {
        this.attrOrder = attrOrder;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues == null ? null : attrValues.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attrId=").append(attrId);
        sb.append(", attrName=").append(attrName);
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName=").append(typeName);
        sb.append(", attrIndex=").append(attrIndex);
        sb.append(", attrType=").append(attrType);
        sb.append(", attrInputType=").append(attrInputType);
        sb.append(", attrOrder=").append(attrOrder);
        sb.append(", attrValues=").append(attrValues);
        sb.append("]");
        return sb.toString();
    }
}