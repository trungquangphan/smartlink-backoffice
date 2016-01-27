package ch.smartlink.backoffice.common.util;

public class Base64 {
    private static Base64 me = new Base64();
    
    public static Base64 getInstance()
    {
      return me;
    }
    
    public String encode(byte[] buffer)
    {
      return new String(org.apache.commons.codec.binary.Base64.encodeBase64(buffer));
    }
    
    public byte[] encodeToBytes(byte[] buffer)
    {
      return org.apache.commons.codec.binary.Base64.encodeBase64(buffer);
    }
    
    public byte[] decode(String base64)
    {
      return org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
    }
    
    public byte[] decodeBytes(byte[] base64)
    {
      return org.apache.commons.codec.binary.Base64.decodeBase64(base64);
    }
    
    public static String decodeToString(String base64)
    {
      return new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes()));
    }
    
    public static String encodeToString(String originalData)
    {
      return new String(org.apache.commons.codec.binary.Base64.encodeBase64(originalData.getBytes()));
    }
}
