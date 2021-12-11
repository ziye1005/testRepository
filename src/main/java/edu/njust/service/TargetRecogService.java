package edu.njust.service;
import edu.njust.dto.BasicAttributes;
import edu.njust.dto.RecogResult;
import edu.njust.model.TYpTargetRecog;

import java.util.List;

public interface TargetRecogService {

    // 输入基本属性研判目标飞机
    public RecogResult basicRecog(BasicAttributes params);

    // 输出综合研判概率
    public RecogResult finalRecog(List<RecogResult> resultSet);

}
