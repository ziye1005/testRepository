package edu.njust.service.impl;

import edu.njust.mapper.TargetsRecogWeightsMapper;
import edu.njust.model.TargetRecogWeights;
import edu.njust.service.WeightsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class WeightsServiceImpl implements WeightsService {
    @Resource
    private TargetsRecogWeightsMapper weightsMapper;

    @Override
    public int insert(TargetRecogWeights weights) {
        Random random = new Random();
        String id = String.format("%d",random.nextInt(100));
        weights.setId(id);
        return weightsMapper.insert(weights);
    }
}
