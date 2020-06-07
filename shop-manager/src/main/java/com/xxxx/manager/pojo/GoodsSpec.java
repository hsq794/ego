package com.xxxx.manager.pojo;

import java.io.Serializable;

/**
 * @author zhoubin 
 * @since 1.0.0
 */
public class GoodsSpec implements Serializable {
    /**
     * 规格id
     */
    private Integer specId;

    /**
     * 规格名称
     */
    private String specName;

    /**
     * 规格分类id
     */
    private Short typeId;

    /**
     * 规格分类名称
     */
    private String typeName;

    /**
     * 规格排序
     */
    private Byte specOrder;

    /**
     * 规格项
     */
    private String specValue;

    /**
     * t_goods_spec
     */
    private static final long serialVersionUID = 1L;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
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

    public Byte getSpecOrder() {
        return specOrder;
    }

    public void setSpecOrder(Byte specOrder) {
        this.specOrder = specOrder;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue == null ? null : specValue.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", specId=").append(specId);
        sb.append(", specName=").append(specName);
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName=").append(typeName);
        sb.append(", specOrder=").append(specOrder);
        sb.append(", specValue=").append(specValue);
        sb.append("]");
        return sb.toString();
    }
}