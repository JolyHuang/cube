package com.sharingif.cube.components.sequence;

import java.util.List;

/**
 * 将其他序列生成器组合在一起，生成一个组合序列
 * 2017/6/3 下午9:36
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class CompositeSequenceGenerator extends AbstractSequenceGenerator<String> {

    private List<ISequenceGenerator<? extends Object>> sequenceGeneratorList;

    public CompositeSequenceGenerator(List<ISequenceGenerator<? extends Object>> sequenceGeneratorList) {
        this.sequenceGeneratorList = sequenceGeneratorList;
    }

    @Override
    public String generateSequence() {
        StringBuilder sequence = new StringBuilder();

        for(ISequenceGenerator<? extends Object> sequenceGenerator : sequenceGeneratorList) {
            sequence.append(sequenceGenerator.generateSequence());
        }

        return sequence.toString();
    }

}
