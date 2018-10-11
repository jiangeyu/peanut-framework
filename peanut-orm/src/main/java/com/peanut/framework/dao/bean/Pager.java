package com.peanut.framework.dao.bean;

import com.peanut.framework.bean.BaseBean;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:14 2018/7/25
 * @desc
 */
@Builder
@Data
public class Pager<T> extends BaseBean {

    private int pageNumber;
    private int pageSize;
    private long totalRecord;
    private long totalPage;
    private List<T> recordList;
}
