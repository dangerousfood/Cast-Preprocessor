package me.delong.FileOperations;

import me.delong.DataObjects.Polygon;
import me.delong.DataObjects.Vertex;
import me.delong.Model.Model;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by josephdelong on 11/23/14.
 */
public class ModelHelper {
    //sort polygons
    //sort vertices
    public static void writeToFile(String path, ByteOrder byteOrder){
        String fileName = "My new STL";

        File file = new File(path);
        DataOutputStream output = null;

        try{
            output = new DataOutputStream(new FileOutputStream(path));

            byte[] fileNameByteArray = fileName.getBytes();

            ByteBuffer buffer = ByteBuffer.allocate(500);
            buffer.order(byteOrder);

            byte[] fillerArray = new byte[80];
            System.arraycopy(fileNameByteArray, 0, fillerArray, 0, fileNameByteArray.length);

            buffer.put(fillerArray);
            buffer.putInt(1);

            //Normal
            buffer.putFloat(0.0f);
            buffer.putFloat(-100.0f);
            buffer.putFloat(0.0f);

            //P1
            buffer.putFloat(0.0f);
            buffer.putFloat(0.0f);
            buffer.putFloat(10.0f);
            //P2
            buffer.putFloat(10.0f);
            buffer.putFloat(0.0f);
            buffer.putFloat(0.0f);
            //P3
            buffer.putFloat(0.0f);
            buffer.putFloat(0.0f);
            buffer.putFloat(0.0f);

            buffer.putShort(((short) 0));

            byte[] bytes = buffer.array();
            output.write(bytes);

            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readBinarySTL (String path, Model model){

        File file = new File(path);
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));

            //read file name
            byte[] name = new byte[80];
            input.read(name, 0, 80);
            model.setFileName(new String(name));

            byte[] size = new byte[4];
            input.read(size, 0, 4);

            long polygonCount = ByteBuffer.wrap(size).order(ByteOrder.LITTLE_ENDIAN).getInt();

            //byte[] offset = new byte[4];
            //input.read(size, 0, 4);

            for(int i=0; i<polygonCount; i++){
                Polygon polygon = new Polygon();
                Vertex normal = new Vertex();

                byte[] normal_buffer = new byte[4];

                input.read(normal_buffer, 0, 4);
                float normalx = java.nio.ByteBuffer.wrap(normal_buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                normal.setX(new BigDecimal(normalx));

                input.read(normal_buffer, 0, 4);
                float normaly = java.nio.ByteBuffer.wrap(normal_buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                normal.setY(new BigDecimal(normaly));

                input.read(normal_buffer, 0, 4);
                float normalz = java.nio.ByteBuffer.wrap(normal_buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                normal.setZ(new BigDecimal(normalz));

                polygon.setNormal(normal);

                for(int j=0; j<3; j++){
                    Vertex vertex = new Vertex();
                    byte[] buffer = new byte[4];

                    //Capturing input as float as per the STL file standard
                    //then converting to BigDecimal
                    input.read(buffer, 0, 4);
                    float x = java.nio.ByteBuffer.wrap(buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                    vertex.setX(new BigDecimal(x));

                    input.read(buffer, 0, 4);
                    float y = java.nio.ByteBuffer.wrap(buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                    vertex.setY(new BigDecimal(y));

                    input.read(buffer, 0, 4);
                    float z = java.nio.ByteBuffer.wrap(buffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getFloat();
                    vertex.setZ(new BigDecimal(z));

                    polygon.add(vertex);
                }
                byte[] byteCountBuffer = new byte[2];
                input.read(byteCountBuffer, 0, 2);
                short byteCount = java.nio.ByteBuffer.wrap(byteCountBuffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort();

                model.add(polygon);
            }

        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
