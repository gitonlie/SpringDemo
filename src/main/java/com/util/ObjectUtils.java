package com.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ObjectUtils {
    private ObjectUtils() {
    }

    public static Object unserialize(byte[] objBytes) throws Exception {
        if(objBytes != null && objBytes.length != 0) {
            ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            return oi.readObject();
        } else {
            return null;
        }
    }

    public static byte[] serialize(Object obj) throws Exception {
        if(obj == null) {
            return null;
        } else {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            return bo.toByteArray();
        }
    }
}