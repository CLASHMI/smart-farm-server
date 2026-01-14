package com.gcxy.smartfarm.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * PageResult 类
 *
 * @author NaNloopia
 * @version 1.0
 * @created 9:39
 * @since 2026/1/14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long total;
    private List<T> records;
}
