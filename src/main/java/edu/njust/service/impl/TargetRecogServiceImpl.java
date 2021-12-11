package edu.njust.service.impl;

import edu.njust.dto.BasicAttributes;
import edu.njust.dto.RecogResult;
import edu.njust.service.TargetRecogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TargetRecogServiceImpl implements TargetRecogService {

    @Override
    public RecogResult finalRecog(List<RecogResult> resultSet) {
        // 调用权值要素输出综合研判结果
        return null;
    }

    @Override
    public RecogResult basicRecog(BasicAttributes params) {
        // 调用目标飞机基本属性匹配算法输出基本属性研判概率
        return null;
    }
}
