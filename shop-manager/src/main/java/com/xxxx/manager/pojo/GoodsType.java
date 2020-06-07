package com.xxxx.manager.pojo;

import java.io.Serializable;

/**
 * @author zhoubin 
 * @since 1.0.0
 */
public class GoodsType implements Serializable {
    /**
     * id自增
     */
    private Short id;

    /**
     * 类型名称
     */
    private String name;

    /**
     * t_goods_type
     */
    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}