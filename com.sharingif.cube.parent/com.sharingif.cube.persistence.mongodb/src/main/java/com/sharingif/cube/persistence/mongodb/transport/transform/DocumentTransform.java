package com.sharingif.cube.persistence.mongodb.transport.transform;

import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.Transform;
import org.bson.Document;

/**
 * 提供mongodb Document与java bean之间的转换
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/8/13 下午4:18
 */
public class DocumentTransform<T> implements Transform<T,Document,Document,T> {
    @Override
    public void setMarshaller(Marshaller<T, Document> marshaller) {

    }

    @Override
    public void setUnmarshaller(Marshaller<Document, T> unmarshaller) {

    }

    @Override
    public Document marshaller(T data) throws MarshallerException {
        return null;
    }

    @Override
    public T unmarshaller(Document data) throws MarshallerException {
        return null;
    }
}
