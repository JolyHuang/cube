package com.sharingif.cube.components.sequence;

/**
 * 固定值序列，可为其它序列生成前缀,可以CompositeSequenceGenerator一起使用
 * 2017/6/3 下午9:30
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class FixedSequenceGenerator extends AbstractSequenceGenerator<String> {

    private String type;

    public FixedSequenceGenerator(String type) {
        this.type = type;
    }

    @Override
    public String generateSequence() {
        return type;
    }
}
