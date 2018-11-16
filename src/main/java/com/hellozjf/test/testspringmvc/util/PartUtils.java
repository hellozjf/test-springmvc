package com.hellozjf.test.testspringmvc.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Part;
import java.io.InputStream;

/**
 * @author Jingfeng Zhou
 */
@Slf4j
public class PartUtils {

    public static byte[] getPartByteArray(Part part) {
        if (part == null) {
            return null;
        }
        try (InputStream in = part.getInputStream()) {
            ByteBuf byteBuf = Unpooled.buffer();
            log.debug("maxCapacity = {}", byteBuf.maxCapacity());
            byte[] bytes = new byte[1024];
            int readNum;
            while ((readNum = in.read(bytes)) > 0) {
                byteBuf.writeBytes(bytes, 0, readNum);
            }
            return byteBuf.array();
        } catch (Exception e) {
            log.error("error = {}", e);
            return null;
        }
    }
}
