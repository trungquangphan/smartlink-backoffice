package ch.smartlink.backoffice.common.util;


import ch.smartlink.backoffice.common.exception.TechnicalException;

import java.io.*;

public class ObjectConvertor {
    public static byte[] convertToBytes(Object object) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new TechnicalException(e.getCause());
        }
    }

    public static Object convertToObject(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            return objectStream.readObject();
        } catch (IOException e) {
            throw new TechnicalException(e);
        } catch (ClassNotFoundException e) {
            throw new TechnicalException(e);
        }
    }

    public static String convertToString(Object object) {
        return Base64.getInstance().encode(convertToBytes(object));
    }

    public static Object convertToObject(String text) {
        return convertToObject(Base64.getInstance().decode(text));
    }
}
