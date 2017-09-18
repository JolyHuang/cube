package com.sharingif.cube.persistence.mongodb.transport.transform;

import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.Transform;
import org.bson.conversions.Bson;

/**
 * 提供mongodb Bson与java bean之间的转换
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/13 下午5:24
 */
public class BsonTransform<T> implements Transform<T,Bson,Bson,T> {
    @Override
    public void setMarshaller(Marshaller<T, Bson> marshaller) {

    }

    @Override
    public void setUnmarshaller(Marshaller<Bson, T> unmarshaller) {

    }

    @Override
    public Bson marshaller(T data) throws MarshallerException {
        return null;
    }

    @Override
    public T unmarshaller(Bson data) throws MarshallerException {
        return null;
    }
}
